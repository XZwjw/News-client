package com.example.wangjiawang.complete.app;

import android.app.Application;
import android.graphics.Typeface;

import com.example.wangjiawang.complete.di.components.DaggerNetComponent;
import com.example.wangjiawang.complete.di.components.NetComponent;
import com.example.wangjiawang.complete.di.module.NetModule;


/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
public class CompleteApplication extends Application{
    private static CompleteApplication instance;
    private NetComponent netComponent;
    public static Typeface typeface;
    public static final String DAY_THEME = "DAY_THEME";
    public static final String NIGHT_THEME = "NIGHT_THEME";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLogger();
        initNet();
        initDatabase();
        initTypeFace();
        typeface = Typeface.createFromAsset(getAssets(),"Italics.ttf");
    }

    /**
     * 初始化字体
     */
    private void initTypeFace() {

    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {

    }

    /**
     * 初始化日志
     */
    private void initLogger() {
    }

    /**
     * 初始化网络
     */
    private void initNet() {
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public static CompleteApplication getInstance() {
        return instance;
    }
}
