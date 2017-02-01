package com.sourceedge.preco.timeslots.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sourceedge.preco.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Centura User3 on 1/12/2017.
 */

public class DateSelectionAdapter extends RecyclerView.Adapter<DateSelectionAdapter.ViewHolder> {
    Context mContext;
    public DateSelectionAdapter(Context context){
        this.mContext=context;
        Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
    }


    @Override
    public DateSelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_selector,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DateSelectionAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
