package com.example.wangjiawang.complete.di.components;

import com.example.wangjiawang.complete.di.module.AtlasModule;
import com.example.wangjiawang.complete.di.scope.UserScope;
import com.example.wangjiawang.complete.view.activity.AtlasActivity;

import dagger.Component;

/**
 * Description:
 * Created by wangjiawang on 2018/2/12.
 * complete
 */
@UserScope
@Component(modules = AtlasModule.class,dependencies = NetComponent.class)
public interface AtlasComponent {
    void inject(AtlasActivity atlasActivity);
}
