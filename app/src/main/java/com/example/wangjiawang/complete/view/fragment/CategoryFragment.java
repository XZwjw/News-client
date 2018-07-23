package com.example.wangjiawang.complete.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.app.CompleteApplication;
import com.example.wangjiawang.complete.cache.Callback;
import com.example.wangjiawang.complete.cache.WangCache;
import com.example.wangjiawang.complete.cache.WangCacheIm;
import com.example.wangjiawang.complete.di.components.DaggerMainComponent;
import com.example.wangjiawang.complete.di.module.MainModule;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.presenter.MainContract;
import com.example.wangjiawang.complete.presenter.MainPresenter;
import com.example.wangjiawang.complete.view.adapter.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryFragment extends LazyFragment implements MainContract.View {

    @Inject
    protected MainPresenter mMainPresenter;

    @BindView(R.id.recyclerView_category)
    RecyclerView mRecyclerView;
    @BindView(R.id.category_progressBar)
    ProgressBar mProgressBar;

    private RecycleViewAdapter adapter;
    private Unbinder unbinder;
    private static final String CATEGORY_DEFAULT = "art";
    private String category;
    private int mPage = 0;
    private LinearLayoutManager mLinearLayoutManager;
    private String title;   //标题

    public final static String DEFAULT_TITLE = "title";
    public final static String DEFAULT_CATEGORY = "category";
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public WangCacheIm wangCache;
    private String CACHE_KEY_PRE;
    public final String regularExpression = "#";
    private static final String SHARED_PREFERENCES_NAME = "CACHE";
    private static final String TAG = "HomeFragment";
    public List<News> cacheList = new ArrayList<>();



    @Override
    protected int getLayoutRes() {
        return R.layout.category;
    }

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = getArguments().getString(DEFAULT_TITLE);
            category = getArguments().getString("category");
        }
        if (category != null) {
            sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        wangCache = new WangCache();
        init();
    }


    @Override
    protected void requestData() {
        showLoading();
        loadData(category, mPage);
    }

    public static CategoryFragment newInstance(String title, String category) {
        Bundle bundle = new Bundle();
        bundle.putString(DEFAULT_TITLE, title);
        bundle.putString(DEFAULT_CATEGORY, category);
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    public void init() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .netComponent(CompleteApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
    }

    private void loadData(String category, int page) {
        mMainPresenter.getListByPage(category, String.valueOf(page));
    }


    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void discussLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void updateListUI(List<News> list, String category) {
        adapter = new RecycleViewAdapter(getContext(), list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        discussLoading();
        doCache(list);
    }

    @Override
    public void showOnFailure() {
        Toast.makeText(getContext(), "网络加载失败，请检查网络", Toast.LENGTH_SHORT).show();
        if (cacheList.size() == 0) {
            initListByCache();
        } else {
            adapter = new RecycleViewAdapter(getContext(), cacheList);
            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setAdapter(adapter);
            discussLoading();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 数据缓存
     *
     * @param list
     */
    public void doCache(List<News> list) {
        wangCache.doCache(category, list);
    }

    /**
     * 获取缓存
     * 使用split将所有的key值字符串拆分开得到所有key值
     *
     * @return
     */
    private void initListByCache() {
        wangCache.getCache(category, new Callback() {
            @Override
            public void onSuccess(List<News> list) {
                if (mLinearLayoutManager == null) {
                    mLinearLayoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                }
                adapter = new RecycleViewAdapter(getContext(), list);
                mRecyclerView.setAdapter(adapter);
                cacheList = list;
                discussLoading();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
