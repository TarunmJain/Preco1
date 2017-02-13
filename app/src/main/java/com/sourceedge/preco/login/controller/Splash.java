package com.sourceedge.preco.login.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.MapsInitializer;
import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;
import com.sourceedge.preco.location.controller.Locations;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.FeedData;

public class Splash extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    static ProgressBar progressBar;
    static SharedPreferences sharedPreferences;
    public static final int SPLASH_DISPLAY_LENGTH = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Class_Genric.NetCheck(Splash.this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        Class_Static.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        MapsInitializer.initialize(getApplicationContext());
        sharedPreferences = this.getSharedPreferences(Class_Genric.MyPref, this.MODE_PRIVATE);
        FeedData.printersData();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharedPreferences.getString(Class_Genric.Sp_Status, "").matches("LoggedIn")) {
                    startActivity(new Intent(Splash.this, HomeScreen.class));
                } else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
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

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
