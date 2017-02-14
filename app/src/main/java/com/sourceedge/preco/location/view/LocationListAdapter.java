package com.sourceedge.preco.location.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.location.controller.MapLocation;
import com.sourceedge.preco.support.Class_Model_DB;

/**
 * Created by Centura User1 on 24-01-2017.
 */

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(Class_Model_DB.Printers.get(position).getTitle());
        holder.address.setText(Class_Model_DB.Printers.get(position).getAddress());
        holder.distance.setText(Class_Model_DB.Printers.get(position).getDistance());
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapLocation.setCameraLocation(position);
               /* Class_Model_DB.SelectedPrinter=Class_Model_DB.Printers.get(position);
                MapLocation.next.performClick();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return Class_Model_DB.Printers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,address,distance;
        LinearLayout next;
        public ViewHolder(View v) {
            super(v);
            title= (TextView) v.findViewById(R.id.title);
            address= (TextView) v.findViewById(R.id.address);
            distance= (TextView) v.findViewById(R.id.distance);
            next= (LinearLayout) v.findViewById(R.id.next);
        }


    }

}
