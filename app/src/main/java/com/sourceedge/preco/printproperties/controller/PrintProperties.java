package com.sourceedge.preco.printproperties.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.viewer.controller.PdfViewer;
import com.sourceedge.preco.viewer.controller.Viewer;

import java.util.ArrayList;

public class PrintProperties extends AppCompatActivity {
    Toolbar toolbar;
    TextView nextButton;
    Spinner spinner;
    ArrayList<String> paperSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_properties);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Print Properties");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nextButton = (TextView) findViewById(R.id.next_button);
        spinner=(Spinner)findViewById(R.id.paper_size_spinner);
        Initialization();
        AddSpinner();
        OnClicks();
    }

    private void AddSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paperSize);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void Initialization() {
        paperSize=new ArrayList<String>();
        paperSize.add("A4");
        paperSize.add("A3");
    }

    private void OnClicks() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Class_Static.ispdf) {
                    startActivity(new Intent(PrintProperties.this, PdfViewer.class));
                } else
                    startActivity(new Intent(PrintProperties.this, Viewer.class));
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
        if (Class_Static.ispdf) {
            startActivity(new Intent(PrintProperties.this, PdfViewer.class));
        } else
            startActivity(new Intent(PrintProperties.this, Viewer.class));
        finish();
    }
}
