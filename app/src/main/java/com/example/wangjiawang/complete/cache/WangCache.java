package com.example.wangjiawang.complete.cache;

import android.os.Message;
import android.util.Log;

import com.example.wangjiawang.complete.cache.entry.HomeCache;
import com.example.wangjiawang.complete.model.entity.News;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Description:
 * Created by wangjiawang on 2018/7/18.
 * complete
 */

public class WangCache implements WangCacheIm{
    private static final String TAG = "WangCache";
    private ExecutorService executor = newCacheThreadPool();
    /**
     *
     * @param key
     * @param news
     * @return
     */
    private List<Callback> callbackList = new ArrayList<>();
    public int position;

    private MyHandler handler = new MyHandler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<News> list = (List<News>) msg.obj;
            callbackList.get(msg.what).onSuccess(list);
        }
    };

    @Override
    public void storeCache(String key,List<News> news) {
        executor.execute(new storeCacheRunnable(key,news));
    }

    @Override
    public void obtainCache(String key,Callback callback) {
        callbackList.add(position,callback);
        executor.execute(new obtainCacheRunnable(key,position++));
    }

    class storeCacheRunnable implements Runnable {
        private String key;
        private List<News> list;
        public storeCacheRunnable(String key,List<News> list) {
            this.key = key;
            this.list = list;
        }
        @Override
        public void run() {
            List<HomeCache> cacheList = HistoryCache.NewsToCache(list,key);
            DatabaseManager.insertCache(cacheList);
        }
    }

    class obtainCacheRunnable implements Runnable{

        private String key;
        private int position;
        obtainCacheRunnable(String key,int position) {
            this.key = key;
            this.position = position;
        }

        @Override
        public void run() {
            List<HomeCache> list  = DatabaseManager.getCache(key);
            List<News> newsList = HistoryCache.cacheToNews(list);
            Message msg = handler.obtainMessage();
            msg.obj = newsList;
            msg.what = position;
            handler.sendMessage(msg);
        }
    }



    static class MyHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    public static ExecutorService newCacheThreadPool() {
        return new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
    }

}
