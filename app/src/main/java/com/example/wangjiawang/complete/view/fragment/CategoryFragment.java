package com.example.wangjiawang.complete.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class CategoryFragment extends Fragment implements MainContract.View{

    @Inject
    protected MainPresenter mMainPresenter;

    @BindView(R.id.recyclerView_category)
    RecyclerView mRecyclerView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category,container,false);
        unbinder = ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        if(bundle != null) {
            title = getArguments().getString(DEFAULT_TITLE);
            category = getArguments().getString("category");
        }
        if(category != null) {
            sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }

        wangCache = new WangCache();
        init();
        loadData(category,mPage);
        return view;
    }





    public static CategoryFragment newInstance(String title,String category) {
        Bundle bundle = new Bundle();
        bundle.putString(DEFAULT_TITLE,title);
        bundle.putString(DEFAULT_CATEGORY,category);
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
    private void loadData(String category,int page) {
        mMainPresenter.getListByPage(category,String.valueOf(page));
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void updateListUI(List<News> list, String category) {
        adapter = new RecycleViewAdapter(getContext(),list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        doCache(list);
    }

    @Override
    public void showOnFailure() {
        Toast.makeText(getContext(),"网络加载失败，请检查网络",Toast.LENGTH_SHORT).show();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        if(cacheList ==null || cacheList.size() == 0) {
            initListByCache(new Callback() {
                @Override
                public void onSuccess(List<News> list) {
                    adapter = new RecycleViewAdapter(getContext(),list);
                    mRecyclerView.setAdapter(adapter);
                    cacheList = list;
                }

                @Override
                public void onFailure(List<News> list) {
                    Toast.makeText(getContext(),"网络加载超时，请检查网络",Toast.LENGTH_SHORT).show();
                    if(list!=null) {
                        adapter = new RecycleViewAdapter(getContext(),list);
                        mRecyclerView.setAdapter(adapter);
                    }
                }
            });
        }else {
            adapter = new RecycleViewAdapter(getContext(),cacheList);
            mRecyclerView.setAdapter(adapter);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 数据缓存
     * 因为shared...无法存储有序结合，所以这里以字符串进行连接，然后使用split函数获取有序字符集合
     * @param list
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void doCache(List<News> list) {
        StringBuilder sb = new StringBuilder("");
        for(int i = 0;i < list.size();i++) {
            String key = category + i;
            sb.append(key);
            if(i != list.size() - 1) {
                sb.append(regularExpression);
            }
            wangCache.doCache(key,list.get(i));
        }
        editor = sharedPreferences.edit();
        editor.putString(category,sb.toString());
        editor.apply();
    }

    /**
     * 获取缓存
     * 使用split将所有的key值字符串拆分开得到所有key值
     * @return
     */
    private void initListByCache(final Callback callback) {
        final List<News> list = new ArrayList<>();
        final String keysString = sharedPreferences.getString(category,null);
        final long startTime = System.currentTimeMillis();
        if(keysString != null) {
            for (String key:
                    keysString.split(regularExpression)) {
                wangCache.getCache(key, new com.example.wangjiawang.complete.cache.Callback() {
                    @Override
                    public void onSuccess(News news) {
                        list.add(news);
                        long duration = System.currentTimeMillis() - startTime;
                        if(list.size() == keysString.split(regularExpression).length || duration > 2000) {  //所有数据加载完成或者如果时长大于2秒，则回调刷新列表
                            if(list.size() == keysString.split(regularExpression).length) {
                                callback.onSuccess(list);
                            }else {
                                callback.onFailure(list);
                            }

                        }
                    }
                });
            }
        }
    }
    /**
     * 回调函数，当子线程所有数据加载完成或者超时进行回调
     */
    interface Callback {
        void onSuccess(List<News> list);    //成功加载
        void onFailure(List<News> list);   //超时回调
    }
}
