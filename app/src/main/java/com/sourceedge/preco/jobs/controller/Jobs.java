package com.sourceedge.preco.jobs.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sourceedge.preco.R;
import com.sourceedge.preco.jobs.view.JobsAdapter;

public class Jobs extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView jobsRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Jobs");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        jobsRecyclerview = (RecyclerView) findViewById(R.id.jobs_recyclerview);
        jobsRecyclerview.setLayoutManager(new LinearLayoutManager(com.sourceedge.preco.jobs.controller.Jobs.this));
        jobsRecyclerview.setAdapter(new JobsAdapter(com.sourceedge.preco.jobs.controller.Jobs.this));
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
