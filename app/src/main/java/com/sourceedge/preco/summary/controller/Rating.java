package com.sourceedge.preco.summary.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;

public class Rating extends AppCompatActivity {
    Toolbar toolbar;
    RatingBar rating;
    TextView ratingSubmit;
    String rating1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ratings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rating=(RatingBar)findViewById(R.id.rating);
        ratingSubmit=(TextView)findViewById(R.id.rating_submit);
        rating.setRating((float) 1.0);
        rating1= String.valueOf(rating.getRating());
        OnClicks();
    }

    private void OnClicks() {
        ratingSubmit.setOnClickListener(new View.OnClickListener() {
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
        /*if(rating1.matches("0.0")){
            Toast.makeText(Rating.this,"Please Rate it...",Toast.LENGTH_SHORT).show();
        }else {*/
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        //}
    }
}
