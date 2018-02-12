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
        void updateListUI(List<News> list);
        void showOnFailure();
    }

    interface Presenter {
        void getListByPage(String category,String pageId);
    }
}
