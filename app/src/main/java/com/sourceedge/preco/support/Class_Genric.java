package com.sourceedge.preco.support;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.maps.android.SphericalUtil;
import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;
import com.sourceedge.preco.login.controller.Login;
import com.sourceedge.preco.myprofile.controller.MyProfile;
import com.sourceedge.preco.pricinginfo.controller.PricingInfo;
import com.sourceedge.preco.printproperties.controller.PrintProperties;
import com.sourceedge.preco.support.models.KeyValuePair;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by RVU on 09-Jan-17.
 */

public class Class_Genric {
    public static final String MyPref = "MyPref";
    public static final String Sp_Status = "Status";
    public static final String Sp_Token = "Token";
    public static final String Sp_Balance = "Balance";
    public static final String Sp_Image = "Image";
    public static final String Sp_Pdf = "Pdf";
    public static boolean progressAlive = false;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int PERMISSION_REQUEST_CODE = 1;
    public static final int PERMISSION_REQUEST_CAMERA = 1;
    public static final int PERMISSION_REQUEST_STORAGE = 1;


    static ProgressDialog pDialog;
    static Activity a;
    public static PopupMenu popup;
    public static Menu popupMenu;
    static SharedPreferences sharedPreferences;
    static DrawerLayout drawer;
    static LinearLayout notification, pricingInfo, profile, faqs, helpSupport, logout;


    public static void setupDrawer(Toolbar toolbar, DrawerLayout drawer, ActionBarDrawerToggle mDrawerToggle, Context mContext) {
        mDrawerToggle = new ActionBarDrawerToggle((Activity) mContext, drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public static void DrawerOnClicks(Context context) {
        a = (Activity) context;
        sharedPreferences = a.getSharedPreferences(MyPref, a.MODE_PRIVATE);
        drawer = (DrawerLayout) a.findViewById(R.id.navigation_drawer);


        notification = (LinearLayout) a.findViewById(R.id.notification);
        pricingInfo = (LinearLayout) a.findViewById(R.id.pricing_info);
        profile = (LinearLayout) a.findViewById(R.id.profile);
        faqs = (LinearLayout) a.findViewById(R.id.faqs);
        helpSupport = (LinearLayout) a.findViewById(R.id.helps_support);
        logout = (LinearLayout) a.findViewById(R.id.logout);

        OnClicks(a);
    }

    private static void OnClicks(final Activity activity) {

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pricingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, PricingInfo.class));
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, MyProfile.class));
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        helpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreen.logout(activity);
            }
        });
    }

    public static void ShowDialog(Context context, String message, Boolean flag) {
        if (flag) {
            if (progressAlive) {
                pDialog.cancel();
                progressAlive = false;
            }

               /* pDialog=new Dialog(context);
                pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pDialog.setContentView(R.layout.customload);*/

                pDialog = new ProgressDialog(context);
                //pDialog.setCancelable(false);
                pDialog.setMessage(message);
                if (message.contains("Loading"))
                    pDialog.setCanceledOnTouchOutside(false);
                if (message.contains("Please Wait..."))
                    pDialog.setCanceledOnTouchOutside(false);
                progressAlive = true;
                pDialog.show();

        } else {
            if (progressAlive) {
                pDialog.dismiss();
                pDialog.cancel();
                progressAlive = false;
            }
        }
    }

    public static String generateUrl(String Url, ArrayList<KeyValuePair> params) {
        if (params.size() > 0) {
            Url += "?";
            for (KeyValuePair data : params) {
                if (data.getKey().trim().length() > 0)
                    Url += data.getKey() + "=" + data.getValue() + "&&";
            }
            Url = Url.substring(0, Url.length() - 2);
        }
        return Url;
    }

    public static boolean NetAvailable(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static boolean NetCheck(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected == false) {
            /*Toast.makeText(context, "Please Check Internet Connectivity", Toast.LENGTH_LONG).show();
            return false;*/
            try {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.checkinternet);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button btn = (Button) dialog.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        NetCheck(context);
                    }
                });
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return true;
    }

    public static int convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
      /*  DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;*/
    }


    public static void setImage(Context context, String url, ImageView image){
        if (url == null)
            image.setImageResource(R.drawable.noimage);
        else if (url.matches("")) {
            image.setImageResource(R.drawable.noimage);
        } else {
            Glide
                    .with(context)
                    .load(url)
                    .error(R.drawable.noimage)
                    .into(image);
        }
    }

    /*public static void logout(final Context context) {
        FacebookSdk.sdkInitialize(context);
        a = ((Activity) context);
        sharedPreferences = a.getSharedPreferences(Class_Genric.MyPref, a.MODE_PRIVATE);
        final Dialog dialog = new Dialog(a);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_alert);
        button = (Button) dialog.findViewById(R.id.btn);
        button1 = (Button) dialog.findViewById(R.id.btn1);
        dialog.show();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                a.startActivity(new Intent(a, Login.class));
                a.finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }*/


    public static boolean checkPassword(String password) {
        int length;
        length = password.length();
        if (length < 6 || length > 11) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetter(password.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean checkPassWordAndConfirmPassword(String password, String confirmPassword) {
        boolean pstatus = false;
        if (confirmPassword != null && password != null) {
            if (password.equals(confirmPassword)) {
                pstatus = true;
            }
        }
        return pstatus;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    public static boolean checkLocationPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            } else {
                requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    public static void askUserToOpenGPS(final Context context) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        mAlertDialog.setTitle("Location not available, Open GPS?")
                .setMessage("Activate GPS to use location services?")
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        turnGPSOn(context);
                    }
                }).show();
    }

    public static void turnGPSOn(Context context) {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) {
            askUserToOpenGPS(context);
        }
    }

    // Method to turn off the GPS
    public void turnGPSOff(Context context) {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);
        }
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public static void requestCamera(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        } else {
            Class_Genric.requestStorage(context);
        }
    }

    public static void requestStorage(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
            } else {
                requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
            }
        }
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                String storageDefinition;

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else {
                    //return System.getenv(storageDefinition) + "/" + split[1];
                    return "/storage" + "/" + split[0] + "/" + split[1];
                    // TODO handle non-primary volumes
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static void PopupMenu(Context context, View v, int i) {
        popup = new PopupMenu(context, v);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popupMenu = popup.getMenu();
        switch (i) {
            case 1:
                popupMenu.findItem(R.id.paging_popup).setVisible(true);
                break;
            case 2:
                popupMenu.findItem(R.id.papersize_popup).setVisible(true);
                break;
            case 3:
                popupMenu.findItem(R.id.color_popup).setVisible(true);
                break;
            case 4:
                popupMenu.findItem(R.id.singledouble_popup).setVisible(true);
                break;
            case 5:
                popupMenu.findItem(R.id.pagenumbers_popup).setVisible(true);
                break;
            case 6:
                popupMenu.findItem(R.id.pagetype_popup).setVisible(true);
                break;
            case 7:
                popupMenu.findItem(R.id.collateduncollated_popup).setVisible(true);
                break;
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                popup.dismiss();
                return true;
            }
        });
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                popup.dismiss();
            }
        });
        popup.show();
    }
}
