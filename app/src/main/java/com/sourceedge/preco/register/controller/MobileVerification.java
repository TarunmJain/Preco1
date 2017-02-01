package com.sourceedge.preco.register.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;
import com.sourceedge.preco.support.Class_Genric;

public class MobileVerification extends AppCompatActivity {
    Toolbar toolbar;
    EditText enterCode;
    Button submit;
    private PrefManager prefManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Mobile Verification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences=this.getSharedPreferences(Class_Genric.MyPref,MODE_PRIVATE);
        enterCode=(EditText)findViewById(R.id.enter_code);
        submit=(Button)findViewById(R.id.button_submit);
        OnClicks();

    }

    private void OnClicks() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager = new PrefManager(MobileVerification.this);
                if (!prefManager.isFirstTimeLaunch())
                    startActivity(new Intent(MobileVerification.this, HomeScreen.class));
                else startActivity(new Intent(MobileVerification.this, WelcomeActivity.class));
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
