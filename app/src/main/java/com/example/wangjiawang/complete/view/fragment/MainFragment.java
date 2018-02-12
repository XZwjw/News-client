package com.example.wangjiawang.complete.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.model.manage.NewsType;
import com.example.wangjiawang.complete.view.activity.ArticleActivity;
import com.example.wangjiawang.complete.view.activity.AtlasActivity;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:
 * Created by wangjiawang on 2018/2/7.
 * complete
 */
public class MainFragment extends Fragment {
    @BindView(R.id.advertise)
    ImageView mAdvertise;
    @BindView(R.id.image_news)
    ImageView mImageNews;
    @BindView(R.id.image_type)
    ImageView mImageType;
    @BindView(R.id.image_cloud)
    ImageView mImageCloud;

    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.content_tv)
    TextView mContentTv;
    @BindView(R.id.divider)
    View mDivider;
    @BindView(R.id.author_tv)
    TextView mAuthorTv;
    @BindView(R.id.comment_tv)
    TextView mCommentTv;
    @BindView(R.id.collection_tv)
    TextView mCollectionTv;
    @BindView(R.id.readCount_tv)
    TextView mReadCountTv;
    @BindView(R.id.top_panel)
    RelativeLayout topPanel;
    @BindView(R.id.type_panel)
    RelativeLayout typePanel;
    @BindView(R.id.page_content)
    RelativeLayout mPageContent;
    @BindView(R.id.type_container)
    LinearLayout mTypeContainer;
    @BindView(R.id.keyword1_tv)
    TextView mKeyword1Tv;
    @BindView(R.id.keyword2_tv)
    TextView mKeyword2Tv;
    @BindView(R.id.keyword3_tv)
    TextView mKeyword3Tv;
    @BindView(R.id.keywords_ll)
    LinearLayout mKeywordsLl;
    Unbinder unbinder;
    private int model;
    private String newsType;
    private String articleId;
    private String imageUrl;
    private String docUrl;


    public static Fragment instance(News news) {
        Fragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("news", news);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        News news = getArguments().getParcelable("news");
        articleId = news.getArticleId();
        imageUrl = news.getImgurl();
        docUrl = news.getDocurl();
        Log.d("TAG","articleId:"+articleId);
        newsType = news.getNewstype();
        if (newsType.equals(NewsType.TYPE_ARTICLE) || newsType.equals(NewsType.TYPE_PHOTO_SET)) {
            mAdvertise.setVisibility(View.GONE);
            mPageContent.setVisibility(View.VISIBLE);
            final String add1 = news.getAdd1();
            Glide.with(getContext()).load(news.getImgurl()).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    Glide.with(getContext()).load(add1).placeholder(R.drawable.loading_photo).into(mImageNews);
                    imageUrl = add1;
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).into(mImageNews);
            List<News.Keywords> keywords = news.getKeywords();
            int count = keywords.size();
            if(count >= 1) {
                String wordName1 = keywords.get(0).getKeyname();
                if(wordName1 != null && !wordName1.equals("")) {
                    mKeyword1Tv.setVisibility(View.VISIBLE);
                    mKeyword1Tv.setText(news.getKeywords().get(0).getKeyname());
                }
            }
            if(count >= 2) {
                String wordName2 = keywords.get(1).getKeyname();
                if(wordName2 != null && !wordName2.equals("")) {
                    mKeyword2Tv.setVisibility(View.VISIBLE);
                    mKeyword2Tv.setText(news.getKeywords().get(1).getKeyname());
                }
            }
            if(count >= 3) {
                String wordName3 = keywords.get(2).getKeyname();
                if(wordName3 != null && !wordName3.equals("")) {
                    mKeyword3Tv.setVisibility(View.VISIBLE);
                    mKeyword3Tv.setText(news.getKeywords().get(2).getKeyname());
                }
            }

            mTitleTv.setText(news.getNewstype());
            mContentTv.setText(news.getTitle());
            mAuthorTv.setText(news.getLabel());
            mCommentTv.setText(String.valueOf(news.getTienum()));
            mCollectionTv.setText(String.valueOf(new Random().nextInt(100)));

        }



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.type_container)
    public void onViewClicked() {
        Intent intent;
        switch (newsType) {
            case NewsType.TYPE_ARTICLE:
                intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("article",articleId);
                intent.putExtra("imageUrl",imageUrl);
                startActivity(intent);
                //转换到articleActivity
                break;
            case NewsType.TYPE_PHOTO_SET:
                //转换到photoSetActivity
                intent = new Intent(getActivity(), AtlasActivity.class);
                //docUrl:"http://lady.163.com/photoview/78LH0026/112106.html"
                String[] strings = docUrl.substring(0,docUrl.length()-5).split("/");
                int size = strings.length;
                String path = strings[size-2].substring(3)+strings[size-1];
                intent.putExtra("path",path);
                break;
        }
    }

}
