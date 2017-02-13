package com.sourceedge.preco.summary.controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
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

import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;

public class Summary extends AppCompatActivity {
    //TextView homescreen;
    RatingBar rating;
    TextView ratingSubmit;
    String rating1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        rating=(RatingBar)findViewById(R.id.rating);
        ratingSubmit=(TextView)findViewById(R.id.rating_submit);

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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
