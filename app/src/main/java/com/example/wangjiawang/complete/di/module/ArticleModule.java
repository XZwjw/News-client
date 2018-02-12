package com.example.wangjiawang.complete.di.module;

import com.example.wangjiawang.complete.presenter.ArticleContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Created by wangjiawang on 2018/2/8.
 * complete
 */
@Module
public class ArticleModule {
    private ArticleContract.View view;
    public ArticleModule(ArticleContract.View view) {
        this.view = view;
    }

    @Provides
    protected ArticleContract.View provideView() {
        return view;
    }
}
