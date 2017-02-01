package com.sourceedge.preco.timeslots.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.payment.controller.Payments;

import java.util.ArrayList;

/**
 * Created by Centura User3 on 1/12/2017.
 */

public class SlotAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> slots;

    public SlotAdapter(Context context) {
        this.mContext = context;
        slots = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 6; j++) {
                if (i < 10) {
                    if(j==0)
                    {
                        slots.add("0" + i + ":" + "00");
                    }else {
                        slots.add("0" + i + ":" + j * 10);
                    }
                } else slots.add(i + ":" + j * 10);
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Payments.class));
            }
        });

        return grid;
    }
}
