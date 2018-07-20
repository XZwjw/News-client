package com.example.wangjiawang.complete.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> mList;
    private LayoutInflater mInflater;
    private String features;
    public RecycleViewAdapter(Context mContext, List<News> mList) {
        this(mContext,mList,null);
    }

    public RecycleViewAdapter(Context mContext, List<News> mList,String features) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.mList = mList;
        this.features = features;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(features!= null && features.equals(Feature.History)) {
            view = mInflater.inflate(R.layout.history,parent,false);
        }else {
            view = mInflater.inflate(R.layout.category_item,parent,false);
        }
        MyViewHolder viewHolder = new MyViewHolder(view);
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
        if(features == null) {
            Glide.with(mContext).load(news.getImgurl()).into(holder.mImageView);
            holder.mNewsType.setText(news.getNewstype());
        }
        holder.mTime.setText(news.getTime());
        holder.mTitle.setText(news.getTitle());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView_category_item)
        ImageView mImageView;
        @BindView(R.id.textView_newsType_category_item)
        TextView mNewsType;
        @BindView(R.id.textView_time_category_item)
        TextView mTime;
        @BindView(R.id.textView_title_category_item)
        TextView mTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
