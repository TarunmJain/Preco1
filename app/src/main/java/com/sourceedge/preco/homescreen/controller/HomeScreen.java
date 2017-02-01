package com.sourceedge.preco.homescreen.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.Plus;
import com.sourceedge.preco.R;
import com.sourceedge.preco.bookphotocopy.controller.BookPhotoCopy;
import com.sourceedge.preco.bookphotocopy.controller.Scan;
import com.sourceedge.preco.history.controller.History;
import com.sourceedge.preco.location.controller.Locations;
import com.sourceedge.preco.location.controller.MapsActivity;
import com.sourceedge.preco.login.controller.Login;
import com.sourceedge.preco.login.controller.Splash;
import com.sourceedge.preco.payment.controller.AddPrecoPoints;
import com.sourceedge.preco.printproperties.controller.PrintProperties;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.support.Class_Model_DB;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.MyApplication;
import com.sourceedge.preco.support.PickAndPreview;
import com.sourceedge.preco.uploadfile.controller.UploadFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HomeScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    CoordinatorLayout coordinatorLayout;
    ImageView print, copyScan, bookXerox, jobs;
    RelativeLayout addPointLayout,historyLayout;
    static Activity a;
    static SharedPreferences sharedPreferences;
    static Button button, button1;
    static GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        if (!Class_Genric.checkLocationPermission(HomeScreen.this)) {
            Class_Genric.requestPermission(HomeScreen.this);
        } else
            //Toast.makeText(HomeScreen.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        //turnGPSOn(HomeScreen.this);
        //displayPromptForEnablingGPS(HomeScreen.this);
        Class_Genric.turnGPSOn(HomeScreen.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbar.setTitle("Preco");
        setSupportActionBar(toolbar);
        jobs = (ImageView) findViewById(R.id.job);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        Class_Genric.setupDrawer(toolbar, drawer, mDrawerToggle, HomeScreen.this);
        print = (ImageView) findViewById(R.id.print);
        copyScan = (ImageView) findViewById(R.id.copy_scan);
        bookXerox = (ImageView) findViewById(R.id.book_xerox);
        addPointLayout=(RelativeLayout)findViewById(R.id.addpointlay);
        historyLayout=(RelativeLayout)findViewById(R.id.historylay);
        mGoogleApiClient = ((MyApplication) getApplication()).getGoogleApiClient(HomeScreen.this, this);
        OnClicks();
        Class_Genric.DrawerOnClicks(HomeScreen.this);
    }

    private void OnClicks() {
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //REVERT BACK
                Class_Static.isCopyScan = false;
                startActivity(new Intent(HomeScreen.this, Locations.class));
                //startActivity(new Intent(HomeScreen.this, UploadFile.class));
                //PickAndPreview.performFileSearch(HomeScreen.this);
            }
        });

        copyScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //REVERT BACK

                startActivity(new Intent(HomeScreen.this, Scan.class));
                //startActivity(new Intent(HomeScreen.this,UploadFile.class));
            }
        });

        bookXerox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Static.isCopyScan = false;
                startActivity(new Intent(HomeScreen.this, BookPhotoCopy.class));

            }
        });

        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        startActivity(new Intent(HomeScreen.this, BookPhotoCopy.class));

            }
        });

        addPointLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, AddPrecoPoints.class));
            }
        });

        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, History.class));
            }
        });
    }

    public static void logout(final Context context) {
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
                if (Class_Static.isGoogleSignIn) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    Class_Static.isGoogleSignIn = false;
                                }
                            });
                } else if (Class_Static.isFacebookSignIn) {
                    Class_Static.isFacebookSignIn=false;
                    LoginManager.getInstance().logOut();
                }
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
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
