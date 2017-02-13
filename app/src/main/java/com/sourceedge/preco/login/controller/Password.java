package com.sourceedge.preco.login.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;
import com.sourceedge.preco.register.controller.PrefManager;
import com.sourceedge.preco.register.controller.WelcomeActivity;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.MyApplication;

public class Password extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    Toolbar toolbar;
    EditText loginPassword;
    TextView passwordNext;
    private PrefManager prefManager;
    SharedPreferences sharedPreferences;
    static GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Enter Password");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginPassword=(EditText)findViewById(R.id.enter_password);
        passwordNext=(TextView)findViewById(R.id.password_next);
        sharedPreferences=this.getSharedPreferences(Class_Genric.MyPref,MODE_PRIVATE);
        mGoogleApiClient = ((MyApplication) getApplication()).getGoogleApiClient(Password.this, this);

        OnClicks();
    }

    private void OnClicks() {
        passwordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager = new PrefManager(Password.this);
                if (!prefManager.isFirstTimeLaunch())
                    startActivity(new Intent(Password.this, HomeScreen.class));
                else startActivity(new Intent(Password.this, WelcomeActivity.class));
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(Class_Genric.Sp_Status,"LoggedIn");
                editor.commit();
                finish();
            }
        });
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
        if (Class_Static.isGoogleSignIn) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Class_Static.isGoogleSignIn = false;
                        }
                    });
        } else if (Class_Static.isFacebookSignIn) {
            Class_Static.isFacebookSignIn = false;
            LoginManager.getInstance().logOut();
        }
        startActivity(new Intent(Password.this,Login.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
