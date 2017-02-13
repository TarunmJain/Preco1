package com.sourceedge.preco.myprofile.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.support.Class_Genric;

public class ChangePassword extends AppCompatActivity {
    Toolbar toolbar;
    EditText changePasswordOld,changePasswordNew,changePasswordConfirm;
    TextView changePasswordCancel,changePasswordSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Change Password");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changePasswordOld=(EditText)findViewById(R.id.changepassword_old);
        changePasswordNew=(EditText)findViewById(R.id.changepassword_new);
        changePasswordConfirm=(EditText)findViewById(R.id.changepassword_confirm);
        changePasswordCancel=(TextView)findViewById(R.id.changepassword_cancel);
        changePasswordSave=(TextView)findViewById(R.id.changepassword_save);
        OnClicks();
    }

    private void OnClicks() {
        changePasswordSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Class_Genric.checkPassWordAndConfirmPassword(changePasswordNew.getText().toString(),changePasswordConfirm.getText().toString())){

                }
                onBackPressed();
            }
        });

        changePasswordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
}
