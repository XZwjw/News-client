package com.example.wangjiawang.complete.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.app.CompleteApplication;
import com.example.wangjiawang.complete.di.components.DaggerArticleComponent;
import com.example.wangjiawang.complete.di.module.ArticleModule;
import com.example.wangjiawang.complete.model.entity.Article;
import com.example.wangjiawang.complete.presenter.ArticleContract;
import com.example.wangjiawang.complete.presenter.ArticlePresenter;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Created by wangjiawang on 2018/2/7.
 * complete
 */

public class ArticleActivity extends AppCompatActivity implements ArticleContract.View {

    @BindView(R.id.collection)
    ImageView mCollection;
    @BindView(R.id.write)
    ImageView mWrite;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    ArticlePresenter mPresenter;
    @BindView(R.id.ct_iv)
    ImageView mCtIv;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.type)
    TextView mType;
    @BindView(R.id.time_tv)
    TextView mTime;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.context_ll)
    LinearLayout mContextLL;

    private String mImageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String articleId = getIntent().getStringExtra("article");
        mImageUrl = getIntent().getStringExtra("imageUrl");
        mPresenter.getArticle(articleId);
    }

    private void initPresenter() {
        DaggerArticleComponent.builder()
                .articleModule(new ArticleModule(this))
                .netComponent(CompleteApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);  //左上标图标是否显示(返回键)
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, R.color.primary));
    }

    @OnClick({R.id.collection, R.id.write, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collection:
                break;
            case R.id.write:
                break;
            case R.id.share:
                break;
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void updateUI(Article article) {
        Glide.with(ArticleActivity.this).load(mImageUrl).placeholder(R.drawable.loading_photo).into(mCtIv);
        buildContentLinearLayout(article);
        mType.setText(article.getCategory());
        mTime.setText(article.getPtime());
        mTitle.setText(article.getTitle());
    }

    private void buildContentLinearLayout(Article article) {
        String[] strings = article.getBody().split("<p>");
        List<Article.Img> list = article.getImg();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,10,0,0);
        for(int i = 0,j=0; i < strings.length;i++) {
            TextView textView = new TextView(this);
            String firstString = strings[i].split("</p>")[0];
            int k = 0;
            while(firstString.contains("IMG")) {
                firstString = firstString.replaceFirst("IMG","LLL");
                ImageView imageView = new ImageView(this);
                Glide.with(this).load(list.get(k).getSrc()).into(imageView);
                mContextLL.addView(imageView,layoutParams);
                k++;
            }
            if(k == 0) {
                textView.setText(firstString);
                textView.setTextSize(20);
                textView.setLineSpacing(10,1.5f);
                textView.setTypeface(CompleteApplication.typeface);
                mContextLL.addView(textView,layoutParams);
            } else {

            }

            if(strings[i].contains("IMG")) {
                if(j >= k) {
                    ImageView imageView = new ImageView(this);
                    Glide.with(this).load(list.get(j).getSrc()).into(imageView);
                    mContextLL.addView(imageView,layoutParams);
                }
                j++;
            }
        }
    }

    @Override
    public void showOnFailure() {
        Toast.makeText(ArticleActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
    }
}
