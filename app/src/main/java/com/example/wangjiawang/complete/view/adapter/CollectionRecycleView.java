package com.example.wangjiawang.complete.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangjiawang.complete.model.entity.News;

import java.util.List;
import java.util.zip.Inflater;

public class CollectionRecycleView extends RecyclerView.Adapter<CollectionRecycleView.MyViewHolder> {

    private Context mContext;
    private Inflater mInflater;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
