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
import com.sourceedge.preco.login.controller.Login;
import com.sourceedge.preco.support.Class_Genric;

public class NewRegister extends AppCompatActivity {
    Toolbar toolbar;
    EditText registerUsername, registerPassword, registerConfirmPassword, registerPhoneno, registerReferralCode;
    Button registerNowButton;
    private PrefManager prefManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sign Up");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = this.getSharedPreferences(Class_Genric.MyPref, MODE_PRIVATE);
        registerUsername = (EditText) findViewById(R.id.register_username);
        registerPassword = (EditText) findViewById(R.id.register_password);
        registerConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        registerPhoneno = (EditText) findViewById(R.id.register_phoneno);
        registerReferralCode = (EditText) findViewById(R.id.register_referral_code);
        registerNowButton = (Button) findViewById(R.id.register_now);
        OnClicks();
    }

    private void OnClicks() {
        registerNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!registerUsername.getText().toString().matches("")) {
                    if (!registerPassword.getText().toString().matches("")) {
                        if (!registerConfirmPassword.getText().toString().matches("")) {
                            if (!registerPhoneno.getText().toString().matches("")) {
                                prefManager = new PrefManager(NewRegister.this);
                                if (!prefManager.isFirstTimeLaunch()) {
                                    startActivity(new Intent(NewRegister.this, HomeScreen.class));
                                }else
                                    startActivity(new Intent(NewRegister.this, WelcomeActivity.class));
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Class_Genric.Sp_Status, "LoggedIn");
                                editor.commit();
                                finish();
                            } else registerPhoneno.setError("Field cannot be empty");
                        } else registerConfirmPassword.setError("Field cannot be empty");
                    } else registerPassword.setError("Field cannot be empty");
                } else registerUsername.setError("Field cannot be empty");
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
        startActivity(new Intent(NewRegister.this,Login.class));
        finish();
    }
}
