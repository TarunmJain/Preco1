package com.sourceedge.preco.uploadfile.controller;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.internal.Utility;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.plus.Plus;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Change;
import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;
import com.sourceedge.preco.myprofile.controller.MyProfile;
import com.sourceedge.preco.printproperties.controller.PrintProperties;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.support.Class_Model_DB;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.PickAndPreview;
import com.sourceedge.preco.viewer.controller.PdfViewer;
import com.sourceedge.preco.viewer.controller.Viewer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class UploadFile extends AppCompatActivity implements PrintHelper.OnPrintFinishCallback,EasyPermissions.PermissionCallbacks {
    Toolbar toolbar;
    LinearLayout selectimage, selectdoc, clickimage;
    SharedPreferences sharedPreferences;
    public static Uri userPickedUri;
    String mimeType;
    GoogleCredential mCredential;
    ProgressDialog mProgress;
    FileContent mediaContent;
    String filename;
    byte[] data;
    public static InputStream inputstream;

    static final int REQUEST_CLICK_IMAGE = 1;
    static final int REQUEST_SELECT_IMAGE = 2;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String[] SCOPES = {DriveScopes.DRIVE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class_Genric.checkPermission(UploadFile.this);
        setContentView(R.layout.activity_upload_file);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Upload File");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectimage = (LinearLayout) findViewById(R.id.selectimage);
        selectdoc = (LinearLayout) findViewById(R.id.selectdoc);
        clickimage = (LinearLayout) findViewById(R.id.clickimage);
        sharedPreferences = this.getSharedPreferences(Class_Genric.MyPref, MODE_PRIVATE);
        try {
            mCredential = GoogleCredential.fromStream(getResources().openRawResource(R.raw.preco_73c3d093387d)).createScoped(Arrays.asList(SCOPES));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Please wait...");
        OnClicks();
    }

    private void OnClicks() {
        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UploadFile.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CLICK_IMAGE);
                } else Class_Genric.requestCamera(UploadFile.this);

            }
        });

        selectdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UploadFile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    PickAndPreview.performFileSearch(UploadFile.this);
                }else Class_Genric.requestStorage(UploadFile.this);
            }
        });

        clickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UploadFile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_SELECT_IMAGE);
                } else Class_Genric.requestStorage(UploadFile.this);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Class_Static.ispdf = false;
            switch (requestCode) {
                case REQUEST_CLICK_IMAGE:
                    Class_Static.ispdf = false;
                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                        //bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions), 150, 150, true);
                        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                        String path = Environment.getExternalStorageDirectory() + File.separator + "Preco" + File.separator + "Pics";
                        f.delete();
                        OutputStream outFile = null;
                        String filepath = String.valueOf(System.currentTimeMillis()) + ".jpg";
                        File file = new File(path, filepath);
                        //String filepic = filepath.substring(filepath.lastIndexOf(".") + 1);
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outFile);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String path1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
                        userPickedUri=Uri.parse(path1);
                        mimeType = getContentResolver().getType(userPickedUri);
                        Cursor returnCursor = getContentResolver().query(userPickedUri, null, null, null, null);
                        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        returnCursor.moveToFirst();
                        filename = returnCursor.getString(nameIndex);
                        Class_Genric.ShowDialog(UploadFile.this,"Please Wait...",true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getResultsFromApi();
                    break;
                case REQUEST_SELECT_IMAGE:
                    Class_Static.ispdf = false;
                    Uri selectedImage = data.getData();
                    userPickedUri=data.getData();
                    mimeType = getContentResolver().getType(userPickedUri);
                    Cursor returnCursor = getContentResolver().query(userPickedUri, null, null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    returnCursor.moveToFirst();
                    filename = returnCursor.getString(nameIndex);
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    //Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    getResultsFromApi();
                    break;
                case Class_Static.READ_REQUEST_CODE:
                    Class_Static.ispdf = true;
                    if (resultCode == Activity.RESULT_OK) {
                        userPickedUri = data.getData();
                        mimeType = getContentResolver().getType(userPickedUri);
                        Cursor returnCursor1 = getContentResolver().query(userPickedUri, null, null, null, null);
                        int nameIndex1 = returnCursor1.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        returnCursor1.moveToFirst();
                        filename = returnCursor1.getString(nameIndex1);
                        //new File(userPickedUri.getPath());
                        //File myFile = new File(userPickedUri.getPath());
                        SharedPreferences.Editor edit=sharedPreferences.edit();
                        edit.putString(Class_Genric.Sp_Pdf, userPickedUri.getPath());
                        edit.commit();
                    }
                    if(mimeType.matches("application/pdf")){
                        Class_Static.isPdfUri=true;
                        startActivity(new Intent(UploadFile.this, PdfViewer.class));
                    }else getResultsFromApi();
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    private void getResultsFromApi() {
            new MakeRequestTask(mCredential).execute();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                UploadFile.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    private class MakeRequestTask extends AsyncTask<Object, Object, InputStream> {
        private Drive mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.drive.Drive.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Drive API Android Quickstart")
                    .setRootUrl("https://www.googleapis.com/upload/")
                    .build();

        }

        @Override
        protected InputStream doInBackground(Object... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        private InputStream getDataFromApi() throws IOException {
            String googleMimeType = "";
            switch (mimeType) {
                case "text/plain":
                    googleMimeType = "application/vnd.google-apps.document";
                    break;

                case "text/html":
                    googleMimeType = "application/vnd.google-apps.document";
                    break;

                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                    googleMimeType = "application/vnd.google-apps.document";
                    break;

                case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                    googleMimeType = "application/vnd.google-apps.spreadsheet";
                    break;

                case "application/x-vnd.oasis.opendocument.spreadsheet":
                    googleMimeType = "application/vnd.google-apps.spreadsheet";
                    break;

                case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                    googleMimeType = "application/vnd.google-apps.presentation";
                    break;

                case "image/jpeg":
                    googleMimeType = "application/vnd.google-apps.drawing";
                    break;

                case "image/png":
                    googleMimeType = "application/vnd.google-apps.drawing";
                    break;
            }

            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(filename);
            fileMetadata.setMimeType(googleMimeType);
            String a = Class_Genric.getPath(UploadFile.this, userPickedUri);
            java.io.File filePath = new java.io.File(a);
            mediaContent = new FileContent(mimeType, filePath);
            com.google.api.services.drive.model.File file = mService.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            System.out.println("File ID: " + file.getId());

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.drive.Drive.Builder(
                    transport, jsonFactory, mCredential)
                    .setApplicationName("Drive API Android Quickstart")
                    .setRootUrl("https://www.googleapis.com/")
                    .build();

            String fileId = file.getId();
            OutputStream outputStream = new ByteArrayOutputStream();

            mService.files().export(fileId, "application/pdf")
                    .executeMediaAndDownloadTo(outputStream);
            ByteArrayOutputStream bo = (ByteArrayOutputStream) outputStream;

            data = bo.toByteArray();
            InputStream is = new ByteArrayInputStream(data);
            //deleteFile(mService,fileId);
            return is;
        }

        private void deleteFile(Drive service, String fileId) {
            try {
                service.files().delete(fileId).execute();
            } catch (IOException e) {
                System.out.println("An error occurred: " + e);
            }
        }

        @Override
        protected void onPreExecute() {
            Class_Genric.ShowDialog(UploadFile.this,"Please Wait...",true);
        }

        @Override
        protected void onPostExecute(InputStream output) {
            Class_Genric.ShowDialog(UploadFile.this,"Please Wait...",false);
            //Toast.makeText(MainActivity.this,output,Toast.LENGTH_SHORT).show();
            if (output == null) {
               //mOutputText.setText("No results returned.");
            } else {
                Class_Static.isPdfUri=false;
                inputstream=output;
                startActivity(new Intent(UploadFile.this,PdfViewer.class));
            }
        }

        @Override
        protected void onCancelled() {
            Class_Genric.ShowDialog(UploadFile.this,"Please Wait...",false);
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            UploadFile.REQUEST_AUTHORIZATION);
                } else {
                    Log.d("Error",mLastError.getMessage());
                   /* mOutputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());*/
                }
            } else {
                Log.d("Error","Request cancelled.");
               // mOutputText.setText("Request cancelled.");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
