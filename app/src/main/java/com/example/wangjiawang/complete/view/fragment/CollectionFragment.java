package com.example.wangjiawang.complete.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.presenter.MainContract;
import com.example.wangjiawang.complete.view.adapter.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收藏模块
 */
public class CollectionFragment extends Fragment implements MainContract.View{

    @BindView(R.id.collection_recycleView)
    RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collection,container,false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    /**
     * 初始化收藏列表
     */
    private void initData() {
        adapter = new RecycleViewAdapter(getContext(), getList());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
//    public List<HomeCache> getListByFile(String filename) {
//
//    }
    @Override
    public void onStart() {
        super.onStart();

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
    public void updateListUI(List<News> list, String category) {

    }

    @Override
    public void showOnFailure() {

    }

    /**
     * 假数据
     * @return
     */
    public List<News> getList() {
        List<News> list = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("朱婷16分中国女排3-0阿根廷 世联赛夺香港站首胜");
        titleList.add("纪念达芬奇逝世500周年 原作《美丽的公主》中国首展");
        titleList.add("小米手环3曝光：将配备OLED触摸屏 或将售169元");
        List<String> timeList = new ArrayList<>();
        timeList.add("05/29/2018 22:23:33");
        timeList.add("05/29/2018 20:05:03");
        timeList.add("05/29/2018 08:03:35");

        List<String> imgList = new ArrayList<>();
        imgList.add("http://cms-bucket.nosdn.127.net/2018/05/29/61cef132e5b249869bb6f1bff145d0f3.png?imageView&thumbnail=140y88");
        imgList.add("http://cms-bucket.nosdn.127.net/b841a1aa42164bab84104c2a74d19d7320180529200401.png");
        imgList.add("http://cms-bucket.nosdn.127.net/7b11568cc04d4a79b2c6aa68c3bf03a320180529161741.png?imageView&thumbnail=190y120");

        List<String> labelList = new ArrayList<>();
        labelList.add("综合体育");
        labelList.add("女人");
        labelList.add("其他");
        for(int i = 0;i < 3;i++) {
            News news = new News();
            news.setTitle(titleList.get(i));
            news.setImgurl(imgList.get(i));
            news.setNewstype(labelList.get(i));
            news.setTime(timeList.get(i));
            list.add(news);
        }
        return list;
    }
}
