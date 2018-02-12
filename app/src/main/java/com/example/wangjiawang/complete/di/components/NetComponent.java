package com.example.wangjiawang.complete.di.components;

import com.example.wangjiawang.complete.di.manage.ApiManage;
import com.example.wangjiawang.complete.di.module.NetModule;
import com.example.wangjiawang.complete.model.api.ApiService;
import com.example.wangjiawang.complete.model.api.NetEaseApiService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
@Component(modules = NetModule.class)
@Singleton
public interface NetComponent {
    @Named(value = ApiManage.VAULE1_LOCAL)
    Retrofit getRetrofitLocal();

    @Named(value = ApiManage.VAULE2_163)
    Retrofit getRetrofit163();

    OkHttpClient getOkHttpClient();

    ApiService getApiService();

    NetEaseApiService getArticleApiService();

}
