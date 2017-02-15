package com.sourceedge.preco.printproperties.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.preco.R;
import com.sourceedge.preco.support.Class_Model_DB;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.models.ServiceOptions;
import com.sourceedge.preco.viewer.controller.PdfViewer;

import java.util.ArrayList;

/**
 * Created by Centura User3 on 2/15/2017.
 */

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.ViewHolder> {
    Context mContext;
    ArrayList<ServiceOptions> model;
    ArrayList<String> spinnerlist;

    public PropertiesAdapter(Context context) {
        this.mContext = context;
        spinnerlist = new ArrayList<String>();
        model = Class_Model_DB.getServiceOptionsList();
    }

    @Override
    public PropertiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_properties, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PropertiesAdapter.ViewHolder holder, int position) {
        if (model.get(position).getOptions() != null) {
            if (model.get(position).getOptions().contains(",")) {
                holder.editLayout.setVisibility(View.GONE);
                holder.spinnerLayout.setVisibility(View.VISIBLE);
                holder.spinnerTitle.setText(model.get(position).getName());
                String[] words = model.get(position).getOptions().split(",");
                for (String w : words) {
                    spinnerlist.add(w);
                }
                AddSpinner(holder,position);
            } else {
                holder.editLayout.setVisibility(View.VISIBLE);
                holder.spinnerLayout.setVisibility(View.GONE);
                holder.editTitle.setText(model.get(position).getName());
            }
        } else {
            holder.editLayout.setVisibility(View.GONE);
        }
    }

    private void AddSpinner(final ViewHolder holder,int pos) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, spinnerlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(dataAdapter);
       // Class_Static.selectedoptions = holder.spinner.getItemAtPosition(pos).toString()+",";

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Class_Static.selectedoptions= parentView.getItemAtPosition(position).toString()+"";
                Toast.makeText(mContext,Class_Static.selectedoptions,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        spinnerlist = new ArrayList<String>();

    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout spinnerLayout, editLayout;
        TextView editTitle, spinnerTitle;
        EditText edittext;
        Spinner spinner;

        public ViewHolder(View v) {
            super(v);
            spinnerLayout = (LinearLayout) v.findViewById(R.id.spinner_layout);
            editLayout = (LinearLayout) v.findViewById(R.id.edit_layout);
            editTitle = (TextView) v.findViewById(R.id.edit_title);
            spinnerTitle = (TextView) v.findViewById(R.id.spinner_title);
            edittext = (EditText) v.findViewById(R.id.edittext);
            spinner = (Spinner) v.findViewById(R.id.spinner);
        }
    }
}
