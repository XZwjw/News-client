package com.example.wangjiawang.complete.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.cache.WangCache;
import com.example.wangjiawang.complete.cache.WangCacheIm;
import com.example.wangjiawang.complete.di.components.DaggerNetComponent;
import com.example.wangjiawang.complete.di.components.NetComponent;
import com.example.wangjiawang.complete.di.module.NetModule;
import com.example.wangjiawang.complete.model.entity.Event;
import com.example.wangjiawang.complete.util.tool.RxBus2;
import com.example.wangjiawang.complete.view.fragment.LeftMenuFragment;
import com.example.wangjiawang.complete.view.fragment.RightMenuFragment;
import com.facebook.stetho.Stetho;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import io.reactivex.functions.Consumer;


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
        Stetho.initializeWithDefaults(this);
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

    public static Context getContext(){
        return instance.getApplicationContext();
    }


}
