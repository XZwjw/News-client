package com.example.wangjiawang.complete.di.module;

import com.example.wangjiawang.complete.BuildConfig;
import com.example.wangjiawang.complete.di.manage.ApiManage;
import com.example.wangjiawang.complete.model.api.ApiService;
import com.example.wangjiawang.complete.model.api.NetEaseApiService;
import com.example.wangjiawang.complete.model.api.StringConverterFactory;
import com.example.wangjiawang.complete.util.EntityUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
@Module
public class NetModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
        return client;

    }

    @Provides
    @Singleton
    @Named(value = ApiManage.VAULE1_LOCAL)
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://10.23.103.164:8080")
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    @Named(value = ApiManage.VAULE2_163)
    Retrofit provideRetrofit2(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://c.m.163.com/")
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }


    @Provides
    @Singleton
    ApiService provideApiService(@Named(value = ApiManage.VAULE1_LOCAL) Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    NetEaseApiService provideArticleApiService(@Named(value = ApiManage.VAULE2_163)Retrofit retrofit) {
        return retrofit.create(NetEaseApiService.class);
    }



}
