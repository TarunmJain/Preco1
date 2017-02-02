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
import android.widget.Spinner;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.payment.controller.Payments;

import java.util.ArrayList;

public class Scan extends AppCompatActivity {
    Toolbar toolbar;
    Spinner scanSingleDoubleSpinner;
    ArrayList<String> singleDouble;
    TextView scanConfirm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Scan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scanSingleDoubleSpinner=(Spinner)findViewById(R.id.scan_single_double_spinner);
        scanConfirm=(TextView)findViewById(R.id.scan_confirm);
        singleDouble=new ArrayList<String>();
        singleDouble.add("Single");
        singleDouble.add("Double");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, singleDouble);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scanSingleDoubleSpinner.setAdapter(dataAdapter);
        OnClicks();
    }

    private void OnClicks() {
        scanConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Scan.this, Payments.class));
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
