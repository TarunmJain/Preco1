package com.sourceedge.preco.pricinginfo.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourceedge.preco.R;

/**
 * Created by Centura User3 on 1/28/2017.
 */

public class PricingInfoAdapter extends RecyclerView.Adapter<PricingInfoAdapter.ViewHolder>{
    Context mContext;
    public PricingInfoAdapter(Context context){
        this.mContext=context;
    }
    @Override
    public PricingInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pricinginfo,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PricingInfoAdapter.ViewHolder holder, int position) {

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
