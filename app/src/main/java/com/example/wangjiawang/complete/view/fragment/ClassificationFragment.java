package com.example.wangjiawang.complete.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.app.CompleteApplication;

import com.example.wangjiawang.complete.di.components.DaggerMainComponent;

import com.example.wangjiawang.complete.di.module.MainModule;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.model.manage.NewsCategory;
import com.example.wangjiawang.complete.presenter.MainContract;
import com.example.wangjiawang.complete.presenter.MainPresenter;
import com.example.wangjiawang.complete.view.activity.MainActivity;
import com.example.wangjiawang.complete.view.widget.ViewPagerIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassificationFragment extends Fragment implements MainContract.View, View.OnClickListener {
    @Inject
    protected MainPresenter mMainPresenter;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.categoryId)
    ViewPagerIndicator mCategoryLL;
    @BindView(R.id.imageView_left_slide)
    ImageView mLeftImageView;
    @BindView(R.id.imageView_right_side)
    ImageView mRightImageView;

    private int mCount;
    private int mPage = 0;
    private List<String> mList; //导航栏列表
    List<CategoryFragment> mFragmentList;

    public final static int DEFAULT_PAGE_COUNT = 6;
    private List<TextView> mTextviewList = new ArrayList<>();
    private int mPagePosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classification,container,false);
        ButterKnife.bind(this,view);
        initPresenter();
        initNavigationBar();
        initFragment();
        initViewPager();
        LeftOrRightClick();
        return view;
    }

    @Override
    public void onResume() {
        if(mPagePosition == 0) {
            MainActivity.mSlidingMenu.setMode(SlidingMenu.LEFT);
        }
        super.onResume();
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        for(int i = 0; i < mList.size();i++) {
            CategoryFragment fragment = CategoryFragment.newInstance(mList.get(i),NewsCategory.getCategoryByPosition(i+1));
            mFragmentList.add(fragment);
        }
    }

    private void initPresenter() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .netComponent(CompleteApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
    }


    private void LeftOrRightClick() {
        mLeftImageView.setOnClickListener(this);
        mRightImageView.setOnClickListener(this);
    }


    private void initViewPager() {

        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
        mTextviewList.get(0).setTextColor(Color.RED);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG","滑动2");
                Log.d("TAG","position:"+position);
                int lastPosition = mFragmentList.size() - 1;
                if(position == 0) {
                    MainActivity.mSlidingMenu.setMode(SlidingMenu.LEFT);
                    MainActivity.mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

                }else if(position == lastPosition) {
                    MainActivity.mSlidingMenu.setMode(SlidingMenu.RIGHT);
                    MainActivity.mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else {
                    MainActivity.mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
                    MainActivity.mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                }
                changeToWhite();
                mTextviewList.get(position).setTextColor(Color.RED);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("TAG","滑动3");

            }
        });

        for(int i = 0;i < mTextviewList.size();i++) {
            final int j = i;
            mTextviewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    public void changeToWhite() {
        for(int i = 0 ; i < mTextviewList.size();i++) {
            mTextviewList.get(i).setTextColor(Color.WHITE);
        }
    }

    /**
     * 初始化导航栏
     */
    private void initNavigationBar() {
        WindowManager vm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = vm.getDefaultDisplay().getWidth() / DEFAULT_PAGE_COUNT;
//        mList = mMainPresenter.getDefaultCategory();
        mList = mMainPresenter.getAllClassifications();
        mCount = mList.size();
        for(int i = 0;i < mList.size();i++) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
            tv.setText(mList.get(i));
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            mCategoryLL.addView(tv);
            mTextviewList.add(tv);
        }
//        TextView tv = new TextView(getContext());
//        tv.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
//        tv.setText("+");
//        tv.setTextColor(Color.WHITE);
//        tv.setGravity(Gravity.CENTER);
//        tv.setTextSize(20);
//        mCategoryLL.addView(tv);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void discussLoading() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void updateListUI(List<News> list,String category) {
        switch (category) {
            case NewsCategory.CATEGORY_1:
                break;
            case NewsCategory.CATEGORY_2:
                break;
            case NewsCategory.CATEGORY_3:
                break;
            case NewsCategory.CATEGORY_4:
                break;
            case NewsCategory.CATEGORY_5:
                break;
            case NewsCategory.CATEGORY_6:
                break;
            case NewsCategory.CATEGORY_7:
                break;
            case NewsCategory.CATEGORY_8:
                break;
            case NewsCategory.CATEGORY_9:
                break;
            case NewsCategory.CATEGORY_10:
                break;
            case NewsCategory.CATEGORY_11:
                break;
            case NewsCategory.CATEGORY_12:
                break;
        }
    }

    @Override
    public void showOnFailure() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_left_slide:
                MainActivity.mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
                MainActivity.mSlidingMenu.showMenu();
                MainActivity.mLeftMenu.startAnim();
                break;
            case R.id.imageView_right_side:
                MainActivity.mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
                MainActivity.mSlidingMenu.showSecondaryMenu();
                MainActivity.mRightMenu.startAnim();
        }
    }
}
