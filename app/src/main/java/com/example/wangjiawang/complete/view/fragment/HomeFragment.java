package com.example.wangjiawang.complete.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.app.CompleteApplication;
import com.example.wangjiawang.complete.cache.DatabaseManager;
import com.example.wangjiawang.complete.cache.WangCache;
import com.example.wangjiawang.complete.cache.WangCacheIm;
import com.example.wangjiawang.complete.di.components.DaggerMainComponent;
import com.example.wangjiawang.complete.di.module.MainModule;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.model.manage.NewsCategory;
import com.example.wangjiawang.complete.presenter.MainContract;
import com.example.wangjiawang.complete.presenter.MainPresenter;
import com.example.wangjiawang.complete.view.activity.MainActivity;
import com.example.wangjiawang.complete.view.adapter.VerticalViewPagerAdapter;
import com.example.wangjiawang.complete.view.widget.VerticalViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements MainContract.View{

    @Inject
    protected MainPresenter presenter;

    @BindView(R.id.view_pager)
    VerticalViewPager mViewPager;
    Unbinder unbinder;

    private VerticalViewPagerAdapter adapter;
    private int page = 0;
    private boolean isLoading = true;
    private String category = NewsCategory.DEFAULT;
    public List<News> list = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String CACHE_KEY_PRE = "HOME";
    public static WangCacheIm wangCache = new WangCache();
    public final String regularExpression = "#";
    private static final String SHARED_PREFERENCES_NAME = "CACHE";
    private static final String TAG = "HomeFragment";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,container,false);
        unbinder = ButterKnife.bind(this,view);
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        initPage();
        loadData(page);
        return view;
    }

    @Override
    public void onResume() {
        MainActivity.mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置触摸屏幕的模式
        MainActivity.mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        super.onResume();
    }

    private void initPage() {
        adapter = new VerticalViewPagerAdapter(getActivity().getSupportFragmentManager());
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .netComponent(CompleteApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(adapter.getCount() <= position + 2 && !isLoading) {
                    if(isLoading) {
                        Toast.makeText(getContext(),"努力加载中",Toast.LENGTH_SHORT).show();
                    }
                    Log.d("TAG","page="+page+"lastNewsId="+adapter.getLastNewsId()+"lastCreateTime="+adapter.getLastCreateTime());
                    loadData(page);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG","position:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadData(int page) {
        isLoading = true;
        presenter.getListByPage(category,String.valueOf(page));

    }

//    public String getRecommendedCategory() {
//        category = getCategoryByHistory();
//        return category;
//    }
//
//    private String getCategoryByHistory() {
//
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void discussLoading() {

    }

    @Override
    public void showNoData() {
        Toast.makeText(getContext(),"没有数据",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMore() {
        Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void updateListUI(List<News> list,String category) {
        Log.d("TAG", "list.size():" + list.size());
        isLoading = false;
        adapter.setArticleList(list);
        page++;
        this.list = list;
        doCache(list);      //进行缓存
//        DatabaseManager.deleteCache();
    }


    /**
     * 网络不存在从缓存中获取
     */
    @Override
    public void showOnFailure() {
        Toast.makeText(getContext(),"加载失败，请检查你的网络连接",Toast.LENGTH_SHORT).show();
        if(list == null||list.size() == 0) {
            initListByCache();
        } else {
            adapter.setArticleList(list);
        }

    }

    @OnClick({R.id.left_slide, R.id.right_side})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_slide:
                MainActivity.mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
                MainActivity.mSlidingMenu.showMenu();
                MainActivity.mLeftMenu.startAnim();
                break;
            case R.id.right_side:
                MainActivity.mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
                MainActivity.mSlidingMenu.showSecondaryMenu();
                MainActivity.mRightMenu.startAnim();
                break;
        }
    }

    /**
     * 数据缓存
     * 因为shared...无法存储有序结合，所以这里以字符串进行连接，然后使用split函数获取有序字符集合
     * @param list
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void doCache(List<News> list) {
        wangCache.storeCache(CACHE_KEY_PRE,list);
    }

    /**
     * 获取缓存
     * 使用split将所有的key值字符串拆分开得到所有key值
     * @return
     */
    private void initListByCache() {
        wangCache.obtainCache(CACHE_KEY_PRE, new com.example.wangjiawang.complete.cache.Callback() {
            @Override
            public void onSuccess(List<News> list) {
                adapter.setArticleList(list);
            }
        });
    }


}
