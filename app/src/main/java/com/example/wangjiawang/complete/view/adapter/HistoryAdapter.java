package com.example.wangjiawang.complete.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.model.manage.Feature;
import com.example.wangjiawang.complete.view.activity.ArticleActivity;
import com.example.wangjiawang.complete.view.activity.AtlasActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> mList;
    private LayoutInflater mInflater;
    public HistoryAdapter(Context mContext, List<News> mList) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.mList = mList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_item,parent,false);
        HistoryAdapter.MyViewHolder viewHolder = new HistoryAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final News news = mList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(news.getNewstype().endsWith(News.TYPE_ARTICLE)) {
                    intent = new Intent(mContext, ArticleActivity.class);
                    intent.putExtra("articleId",news.getArticleId());
                    intent.putExtra("imageUrl",news.getImgurl());
                    mContext.startActivity(intent);
                }else if(news.getNewstype().endsWith(News.TYPE_PHOTOSET)) {
                    intent = new Intent(mContext, AtlasActivity.class);
                    intent.putExtra("path",news.getAtlasId());
                    mContext.startActivity(intent);
                }

            }
        });
        holder.mTime.setText(news.getTime());
        holder.mTitle.setText(news.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.history_time)
        TextView mTime;
        @BindView(R.id.history_digest)
        TextView mTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
