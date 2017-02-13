package com.sourceedge.preco.timeslots.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.preco.R;
import com.sourceedge.preco.payment.controller.Payments;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Centura User3 on 1/12/2017.
 */

public class SlotAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> slots;
    Date currenttime;
    int r1, r2, r3, r4;
    Random r;
    int j;

    public SlotAdapter(Context context, Date time) {
        this.mContext = context;
        this.currenttime = time;
        slots = new ArrayList<String>();
        r = new Random();
        r1 = r.nextInt(80 - 5) + 5;
        r = new Random();
        r2 = r.nextInt(80 - 5) + 5;
        r = new Random();
        r3 = r.nextInt(80 - 5) + 5;
        r = new Random();
        r4 = r.nextInt(80 - 5) + 5;

        for (int i = currenttime.getHours(); i < 24; i++) {
            for (j = currenttime.getMinutes(); j < 60; j += 5) {
                String hour=""+i;
                String min=""+j;
                if(i<10)
                    hour="0"+i;
                if(j<10)
                    min="0"+j;
               /* if (i < 10) {
                    if (j == 0) {
                        slots.add("0" + i + ":" + "00");
                    } else {
                        slots.add("0" + i + ":" + j );
                    }
                } else slots.add(i + ":" + j);*/
                slots.add(hour+" : "+min);
            }
        }
    }

    @Override
    public int getCount() {
        return slots.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_slot, null);
            //TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            //ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            //textView.setText(web[position]);
            //imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }
        TextView textView = (TextView) grid.findViewById(R.id.slottext);
        textView.setText(slots.get(position));
        if (position == r1 || position == r2 || position == r3 || position == r4)
            textView.setBackgroundColor(Color.RED);
        else
            textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == r1 || position == r2 || position == r3 || position == r4) {
                    Toast.makeText(mContext,"Slot Reserved",Toast.LENGTH_SHORT).show();
                } else
                    mContext.startActivity(new Intent(mContext, Payments.class));
            }
        });

        return grid;
    }
}
