package com.example.wangjiawang.complete.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.app.CompleteApplication;
import com.example.wangjiawang.complete.di.components.DaggerAtlasComponent;
import com.example.wangjiawang.complete.di.module.AtlasModule;
import com.example.wangjiawang.complete.model.entity.Atlas;
import com.example.wangjiawang.complete.presenter.AtlasContract;
import com.example.wangjiawang.complete.presenter.AtlasPresenter;
import com.example.wangjiawang.complete.view.fragment.PhotoFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:图集Activity
 * Created by wangjiawang on 2018/2/12.
 * complete
 */

@SuppressLint("Registered")
public class AtlasActivity extends AppCompatActivity implements AtlasContract.View {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.atlas_toolbar)
    Toolbar toolbar;
    @BindView(R.id.atlas_back)
    ImageView imageView;
    @Inject
    AtlasPresenter atlasPresenter;

    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atlas_activity);
        ButterKnife.bind(this);
        backInit();
        initPresenter();

    }

    /**
     * 返回键处理
     */
    private void backInit() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        setSupportActionBar(toolbar);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        atlasPresenter.getAtlas(path);
    }

    private void initPresenter() {
        DaggerAtlasComponent.builder()
                .atlasModule(new AtlasModule(this))
                .netComponent(CompleteApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void discusProgress() {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void updateUI(Atlas atlas) {
        final List<Atlas.Photos> photos = atlas.getPhotos();
        final List<String> photoUrlList = new ArrayList<>();
        for(int i = 0;i < photos.size();i++) {
            photoUrlList.add(photos.get(i).getSquareimgurl());
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                String atlasString = photos.get(position).getNote();
                contentTv.setText(atlasString);
                return PhotoFragment.getInstance(photoUrlList.get(position));
            }

            @Override
            public int getCount() {
                return photos.size();
            }
        });


    }
}
