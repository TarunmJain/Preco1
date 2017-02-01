package com.sourceedge.preco.viewer.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.preco.R;
import com.sourceedge.preco.payment.controller.Payments;
import com.sourceedge.preco.printproperties.controller.PrintProperties;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.timeslots.controller.SlotSelection;

public class Viewer extends AppCompatActivity {
    TextView editBtn,nextBtn;
    ImageView imageview;
    SharedPreferences sharedPreferences;
    ImageView incrementCopies,decrementCopies;
    TextView copies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Preview");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editBtn=(TextView)findViewById(R.id.edit_btn);
        nextBtn=(TextView)findViewById(R.id.next_btn);
        incrementCopies = (ImageView) findViewById(R.id.incrementcopies);
        decrementCopies = (ImageView) findViewById(R.id.decrementcopies);
        copies = (TextView) findViewById(R.id.copies);
        imageview=(ImageView)findViewById(R.id.imageview);
        sharedPreferences=this.getSharedPreferences(Class_Genric.MyPref,MODE_PRIVATE);
        //byte[] byteArray = sharedPreferences.getString(Class_Genric.Sp_Image,"");
        byte[] byteArray = Base64.decode(sharedPreferences.getString(Class_Genric.Sp_Image,""), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageview.setImageBitmap(bmp);
        OnClicks();

    }

    private void OnClicks() {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Viewer.this, PrintProperties.class));
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Viewer.this, Payments.class));
            }
        });

        incrementCopies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copies.setText((Integer.parseInt(copies.getText().toString()) + 1) + "");
            }
        });

        decrementCopies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(copies.getText().toString().trim())) {
                    copies.setText("1");
                }
                if (!copies.getText().toString().matches("1")) {
                    copies.setText((Integer.parseInt(copies.getText().toString()) - 1) + "");
                    //productStock.setText("Stock Available : " + ((Integer.parseInt(productStock.getText().toString().split(": ")[1]) + 1) + ""));
                } else
                    Toast.makeText(Viewer.this, "Min Count is 1", Toast.LENGTH_SHORT).show();
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
