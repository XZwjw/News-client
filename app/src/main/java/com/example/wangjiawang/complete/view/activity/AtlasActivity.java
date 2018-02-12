package com.example.wangjiawang.complete.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atlas_activity);
        ButterKnife.bind(this);
        initPresenter();

    }

    private void initPresenter() {
        DaggerAtlasComponent.builder()
                .atlasModule(new AtlasModule(this))
                .netComponent(CompleteApplication.getInstance().getNetComponent())
                .build();
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
                contentTv.setText(photos.get(position).getNote());
                return PhotoFragment.getInstance(photoUrlList.get(position));
            }

            @Override
            public int getCount() {
                return photos.size();
            }
        });


    }
}
