package com.sourceedge.preco.bookphotocopy.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.payment.controller.Payments;

import java.util.ArrayList;

public class Copy extends AppCompatActivity {
    Toolbar toolbar;
    Spinner copySingleDoubleSpinner;
    TextView copyConfirm;
    ArrayList<String> singleDouble;
    CheckBox yesCheckbox,noCheckbox;
    LinearLayout yesBinding,noBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Copy");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        copySingleDoubleSpinner=(Spinner)findViewById(R.id.copy_single_double_spinner);
        copyConfirm=(TextView)findViewById(R.id.copy_confirm);
        yesCheckbox=(CheckBox) findViewById(R.id.yes_checkbox);
        noCheckbox=(CheckBox)findViewById(R.id.no_checkbox);
        yesBinding=(LinearLayout)findViewById(R.id.yes_binding);
        noBinding=(LinearLayout)findViewById(R.id.no_binding);
        singleDouble=new ArrayList<String>();
        singleDouble.add("Single");
        singleDouble.add("Double");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, singleDouble);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        copySingleDoubleSpinner.setAdapter(dataAdapter);
        OnClicks();

    }

    private void OnClicks() {
        copyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Copy.this, Payments.class));
            }
        });

        yesBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(yesCheckbox.isChecked()){
                    yesCheckbox.setChecked(false);
                    noCheckbox.setChecked(true);*/
               // }else {
                    noCheckbox.setChecked(false);
                    yesCheckbox.setChecked(true);
                //}
            }
        });

        noBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(noCheckbox.isChecked()){
                    noCheckbox.setChecked(false);
                    yesCheckbox.setChecked(true);*/
                //}else {
                    yesCheckbox.setChecked(false);
                    noCheckbox.setChecked(true);
               // }
            }
        });

        yesCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noCheckbox.setChecked(false);
                yesCheckbox.setChecked(true);
            }
        });

        noCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yesCheckbox.setChecked(false);
                noCheckbox.setChecked(true);
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
        finish();
    }

}
