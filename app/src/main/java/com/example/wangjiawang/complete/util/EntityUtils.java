package com.example.wangjiawang.complete.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.TimeTypeAdapter;

import org.joda.time.DateTime;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
public final class EntityUtils {
    private EntityUtils(){}
    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class,new TimeTypeAdapter())
            .create();
}
