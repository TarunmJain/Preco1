package com.sourceedge.preco.timeslots.controller;

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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.timeslots.view.DateSelectionAdapter;
import com.sourceedge.preco.timeslots.view.SlotAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SlotSelection extends AppCompatActivity {
    Toolbar toolbar;
    TextView slotNo,dateTextview;
    ImageButton previousDate,nextDate;
    //RecyclerView recyclerView;
    GridView gridView;
    Calendar c;
    String formattedDate;
    SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_selection);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Slot Selection");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dateTextview=(TextView)findViewById(R.id.textview_date);
        previousDate=(ImageButton)findViewById(R.id.previousdate);
        nextDate=(ImageButton)findViewById(R.id.nextdate);
        //recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        gridView=(GridView)findViewById(R.id.gridview);
        slotNo=(TextView)findViewById(R.id.slotno);
        //recyclerView.setLayoutManager(new LinearLayoutManager(SlotSelection.this,LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setAdapter(new DateSelectionAdapter(SlotSelection.this));
        c = Calendar.getInstance();
        gridView.setAdapter(new SlotAdapter(this, getTimeSlots(c).getTime()));

        OnClicks();
    }



    private void OnClicks() {
        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DATE, 1);
                if(c.getTime().getDate()==Calendar.getInstance().getTime().getDate())
                {
                    c = Calendar.getInstance();
                    gridView.setAdapter(new SlotAdapter(SlotSelection.this, getTimeSlots(c).getTime()));
                }
                else {
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    gridView.setAdapter(new SlotAdapter(SlotSelection.this, getTimeSlots(c).getTime()));
                }
                formattedDate = df.format(c.getTime());
                dateTextview .setText(formattedDate);
            }
        });

        previousDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DATE, -1);
                if(c.getTime().getDate()==Calendar.getInstance().getTime().getDate())
                {
                    c = Calendar.getInstance();
                    gridView.setAdapter(new SlotAdapter(SlotSelection.this, getTimeSlots(c).getTime()));
                }
                else {
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    gridView.setAdapter(new SlotAdapter(SlotSelection.this, getTimeSlots(c).getTime()));
                }

                formattedDate = df.format(c.getTime());

                //Log.v("PREVIOUS DATE : ", formattedDate);
                dateTextview.setText(formattedDate);
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
    public Calendar getTimeSlots(Calendar c){
        df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c.getTime());
        dateTextview.setText(formattedDate);
        Calendar temp= (Calendar) c.clone();
        if(c.getTime().getMinutes()%5==0)
            temp.add(Calendar.MINUTE, 5);
        else
            temp.add(Calendar.MINUTE, 5-(c.getTime().getMinutes()%5));
        Calendar finalCalender = temp;
        return finalCalender;
    }

}
