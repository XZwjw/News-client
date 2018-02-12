package com.example.wangjiawang.complete.di.components;

import com.example.wangjiawang.complete.di.module.MainModule;
import com.example.wangjiawang.complete.di.scope.UserScope;
import com.example.wangjiawang.complete.view.activity.MainActivity;

import dagger.Component;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
@UserScope
@Component(modules = MainModule.class ,dependencies = NetComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
