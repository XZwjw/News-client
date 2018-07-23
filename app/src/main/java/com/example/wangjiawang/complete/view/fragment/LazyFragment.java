package com.example.wangjiawang.complete.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.ContentValues.TAG;

/**
 * Description:
 * Created by wangjiawang on 2018/7/23.
 * complete
 */

public abstract class LazyFragment extends Fragment {
    private View rootView = null;
    private boolean isViewCreated;
    private boolean isFirstVisible = true;
    private boolean isFragmentVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isFragmentVisible = isVisibleToUser;

        //当 View 创建完成切 用户可见的时候请求 且仅当是第一次对用户可见的时候请求自动数据
        if (isVisibleToUser && isViewCreated && isFirstVisible) {
            Log.e(TAG, "只有自动请求一次数据  requestData");
            requestData();
            isFirstVisible = false;

        }

        // 由于每次可见都需要刷新所以我们只需要判断  Fragment 展示在用户面面前了，view 初始化完成了 然后即可以请求数据了
        if (isVisibleToUser && isViewCreated) {
            // Log.e(TAG, "每次都可见数据  requestDataAutoRefresh");
            requestDataAutoRefresh();
        }

        if (!isVisibleToUser && isViewCreated) {
            stopRefresh();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(getLayoutRes(),container,false);
        }
        isViewCreated = true;
        initView(rootView);

        if(isFragmentVisible && isFirstVisible) {
            requestData();
            isFirstVisible = false;
        }
        return rootView;
    }
    /**
     * 只有在 Fragment 第一次对用户可见的时候才去请求
     */
    protected void requestData() {
    }

    /**
     * 每次 Fragment 对用户可见都会去请求
     */
    protected void requestDataAutoRefresh() {

    }

    /**
     * 当 Fragment 不可见的时候停止某些轮询请求的时候调用该方法停止请求
     */
    protected void stopRefresh() {

    }

    /**
     * 返回布局 resId
     *
     * @return layoutId
     */
    protected abstract int getLayoutRes();


    /**
     * 初始化view
     *
     * @param rootView
     */
    protected abstract void initView(View rootView);
}
