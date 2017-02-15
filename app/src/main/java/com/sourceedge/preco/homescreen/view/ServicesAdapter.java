package com.sourceedge.preco.homescreen.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.bookphotocopy.controller.Copy;
import com.sourceedge.preco.bookphotocopy.controller.Scan;
import com.sourceedge.preco.forms.controller.Forms;
import com.sourceedge.preco.support.Class_Model_DB;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.Class_SyncApi;
import com.sourceedge.preco.support.models.Services;

import java.util.ArrayList;

/**
 * Created by Centura User3 on 2/14/2017.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Services> model;
    Dialog dialog;
    CheckBox blackAndWhiteCheckbox, colorCheckbox;
    LinearLayout blackAndWhiteColor, colorType;
    TextView colorConfirm;

    public ServicesAdapter(Context context) {
        this.mContext = context;
        model = Class_Model_DB.getServiceslist();
    }

    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_services, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ServicesAdapter.ViewHolder holder, int position) {
        holder.textView.setText(model.get(position).getName());
        if (model.get(position).getName().matches("Printing"))
            holder.imageView.setImageResource(R.drawable.print);
        else if (model.get(position).getName().matches("Scanning"))
            holder.imageView.setImageResource(R.drawable.scan);
        else if (model.get(position).getName().matches("Copy"))
            holder.imageView.setImageResource(R.drawable.copy);
        else if (model.get(position).getName().matches("Binding"))
            holder.imageView.setImageResource(R.drawable.jobs);
        OnClicks(holder,position);

    }

    private void OnClicks(ViewHolder holder, final int pos) {

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.get(pos).getName().matches("Printing")){
                    Class_Static.isCopyScan = false;
                    dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.dialog_copies);
                    blackAndWhiteCheckbox = (CheckBox) dialog.findViewById(R.id.black_and_white_checkbox);
                    colorCheckbox = (CheckBox) dialog.findViewById(R.id.color_checkbox);
                    blackAndWhiteColor = (LinearLayout) dialog.findViewById(R.id.black_and_white_color);
                    colorType = (LinearLayout) dialog.findViewById(R.id.color_type);
                    colorConfirm = (TextView) dialog.findViewById(R.id.color_confirm);
                    dialog.show();

                    blackAndWhiteColor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            blackAndWhiteCheckbox.setChecked(true);
                            colorCheckbox.setChecked(false);
                        }
                    });

                    colorType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            blackAndWhiteCheckbox.setChecked(false);
                            colorCheckbox.setChecked(true);
                        }
                    });

                    blackAndWhiteCheckbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            blackAndWhiteCheckbox.setChecked(true);
                            colorCheckbox.setChecked(false);
                        }
                    });

                    colorCheckbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            blackAndWhiteCheckbox.setChecked(false);
                            colorCheckbox.setChecked(true);
                        }
                    });

                    colorConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Class_Static.serviceid=model.get(pos).getId();
                            Class_SyncApi.GetLocationApi(mContext,model.get(pos).getId());
                            dialog.dismiss();
                        }
                    });
                } else if (model.get(pos).getName().matches("Scanning")){
                    Class_Static.isCopyScan = false;
                    mContext.startActivity(new Intent(mContext, Scan.class));
                } else if (model.get(pos).getName().matches("Copy")){
                    Class_Static.isCopyScan = false;
                    mContext.startActivity(new Intent(mContext, Copy.class));
                } else if (model.get(pos).getName().matches("Binding"))
                    mContext.startActivity(new Intent(mContext, Forms.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageview);
            textView = (TextView) v.findViewById(R.id.textview);
        }
    }
}
