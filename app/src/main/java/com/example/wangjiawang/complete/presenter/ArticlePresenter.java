package com.example.wangjiawang.complete.presenter;

import com.example.wangjiawang.complete.model.api.NetEaseApiService;
import com.example.wangjiawang.complete.model.entity.Article;
import com.example.wangjiawang.complete.util.tool.StringToArticleFunction;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:
 * Created by wangjiawang on 2018/2/8.
 * complete
 */

public class ArticlePresenter implements ArticleContract.Presenter {

    private ArticleContract.View view;
    private NetEaseApiService articleApiService;

    @Inject
    public ArticlePresenter(ArticleContract.View view,NetEaseApiService apiService){
        this.view = view;
        this.articleApiService = apiService;
    }

    @Override
    public void getArticle(final String articleId) {
        articleApiService.getArticle(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new StringToArticleFunction(articleId))
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Article article) {
                        view.updateUI(article);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
