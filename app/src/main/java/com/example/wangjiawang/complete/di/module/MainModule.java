package com.example.wangjiawang.complete.di.module;

import com.example.wangjiawang.complete.presenter.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
@Module
public class MainModule {
    private MainContract.View view;
    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    public MainContract.View provideMainView(){
        return view;
    }

}
