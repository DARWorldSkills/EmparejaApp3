package com.davidpopayan.sena.emparejaapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class AdapterJ extends RecyclerView.Adapter<AdapterJ.Holder>{
    
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
