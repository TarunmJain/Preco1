package com.sourceedge.preco.payment.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.preco.R;
import com.sourceedge.preco.summary.controller.Summary;

public class Payments extends AppCompatActivity {
    Toolbar toolbar;
    TextView payNow,cancel,addPrecoPoints;
    LinearLayout couponCode,availablePrecoPoints;
    FloatingActionButton fab,cancelFab,applyFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Payment Gateway");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        payNow=(TextView)findViewById(R.id.paynow);
        cancel=(TextView)findViewById(R.id.cancel);
        addPrecoPoints=(TextView)findViewById(R.id.add_preco_points);
        availablePrecoPoints=(LinearLayout) findViewById(R.id.available_preco_points);
        couponCode=(LinearLayout) findViewById(R.id.coupon_code);

        fab=(FloatingActionButton)findViewById(R.id.fab);
        cancelFab=(FloatingActionButton)findViewById(R.id.cancel_fab);
        applyFab=(FloatingActionButton)findViewById(R.id.apply_fab);
        OnClicks();
    }

    private void OnClicks() {
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payments.this, Summary.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addPrecoPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payments.this, AddPrecoPoints.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                availablePrecoPoints.setVisibility(View.GONE);
                couponCode.setVisibility(View.VISIBLE);
            }
        });

        applyFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                availablePrecoPoints.setVisibility(View.VISIBLE);
                couponCode.setVisibility(View.GONE);
            }
        });

        cancelFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                availablePrecoPoints.setVisibility(View.VISIBLE);
                couponCode.setVisibility(View.GONE);
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
