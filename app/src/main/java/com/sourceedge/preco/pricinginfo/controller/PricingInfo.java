package com.sourceedge.preco.pricinginfo.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sourceedge.preco.R;
import com.sourceedge.preco.pricinginfo.view.PricingInfoAdapter;

public class PricingInfo extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView pricingInfoRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pricing Info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pricingInfoRecyclerview=(RecyclerView)findViewById(R.id.pricing_info_recyclerview);
        pricingInfoRecyclerview.setLayoutManager(new LinearLayoutManager(PricingInfo.this));
        pricingInfoRecyclerview.setAdapter(new PricingInfoAdapter(PricingInfo.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
