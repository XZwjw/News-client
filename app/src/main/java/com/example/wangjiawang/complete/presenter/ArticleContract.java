package com.example.wangjiawang.complete.presenter;

import com.example.wangjiawang.complete.model.entity.Article;

/**
 * Description:
 * Created by wangjiawang on 2018/2/7.
 * complete
 */

public interface ArticleContract {

    interface View {
        void showLoading();
        void dismissLoading();
        void updateUI(Article article);
        void showOnFailure();
    }

    interface Presenter{
        void getArticle(String articleId);
    }
}
