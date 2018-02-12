package com.example.wangjiawang.complete.di.components;

import com.example.wangjiawang.complete.di.module.ArticleModule;
import com.example.wangjiawang.complete.di.scope.UserScope;
import com.example.wangjiawang.complete.view.activity.ArticleActivity;

import dagger.Component;

/**
 * Description:
 * Created by wangjiawang on 2018/2/8.
 * complete
 */
@UserScope
@Component(modules = ArticleModule.class,dependencies = NetComponent.class)
public interface ArticleComponent {
    void inject(ArticleActivity articleActivity);
}
