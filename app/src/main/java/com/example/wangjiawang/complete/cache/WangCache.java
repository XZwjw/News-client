package com.example.wangjiawang.complete.cache;

import android.os.Message;

import com.example.wangjiawang.complete.cache.entry.HomeCache;
import com.example.wangjiawang.complete.model.entity.News;

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
    private ExecutorService executor = newCacheThreadPool();
    /**
     *
     * @param key
     * @param news
     * @return
     */
    private Callback callback;
    private MyHandler handler = new MyHandler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            News news = (News) msg.obj;
            callback.onSuccess(news);
        }
    };

    @Override
    public void doCache(String key,News news) {
        executor.execute(new DoCacheRunnable(key,news));
    }

    @Override
    public void getCache(String key,Callback callback) {
        executor.execute(new GetCacheRunnable(key));
       this.callback = callback;
    }

    class DoCacheRunnable implements Runnable {
        private String key;
        private News news;
        public DoCacheRunnable(String key,News news) {
            this.key = key;
            this.news = news;
        }
        @Override
        public void run() {
            HomeCache cache = HistoryCache.NewsToCache(news,key);
            DatabaseManager.insertCache(cache);
        }
    }

    class GetCacheRunnable implements  Runnable{

        private String key;
        GetCacheRunnable(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            HomeCache cache  = DatabaseManager.getCache(key);
            News news = HistoryCache.cacheToNews(cache);
            Message msg = handler.obtainMessage();
            msg.obj = news;
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
