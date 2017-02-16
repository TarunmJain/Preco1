package com.sourceedge.preco.jobs.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.history.view.HistoryAdapter;
import com.sourceedge.preco.support.models.Services;

import java.util.ArrayList;

/**
 * Created by patel on 16-02-2017.
 */

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    Context mContext;

    ArrayList<Services> model;
    Dialog dialog;

    TextView machineId, pageType, pageSize, pageColor, singleDouble, date, time;
    Button view;

    public JobsAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public JobsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jobs,parent,false);
        JobsAdapter.ViewHolder vh=new JobsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(JobsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
        }
    }
}