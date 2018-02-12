package com.example.wangjiawang.complete.di.module;

import com.example.wangjiawang.complete.presenter.AtlasContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Created by wangjiawang on 2018/2/12.
 * complete
 */
@Module
public class AtlasModule {
    private AtlasContract.View view;
    public AtlasModule(AtlasContract.View view) {
        this.view = view;
    }

    @Provides
    AtlasContract.View provideView() {
        return view;
    }
}
