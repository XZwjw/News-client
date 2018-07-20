package com.example.wangjiawang.complete.presenter;

import com.example.wangjiawang.complete.model.entity.News;

import java.util.List;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */

public interface MainContract {
    interface View {
        void showLoading();
        void discussLoading();
        void showNoData();
        void showNoMore();
        void updateListUI(List<News> list,String category);
        void showOnFailure();


    }

    interface Presenter {
        List<String> getAllClassifications();   //获取所有分类
        List<String> getDefaultCategory();      //获取默认分类
        void getListByPage(String category,String pageId);
    }
}
