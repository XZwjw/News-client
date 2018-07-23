package com.example.wangjiawang.complete.cache;

import com.example.wangjiawang.complete.model.entity.News;

import java.util.List;


/**
 * Description:缓存对象(数据库缓存所需)与读取对象T（列表显示需要的List<T>）之间的转换
 * Created by wangjiawang on 2018/7/17.
 * complete
 */

public interface WangCacheIm{
    void doCache(String key,List<News> news);     //存储
    void getCache(String key,Callback callback);              //根据key读取某一条数据。
}
