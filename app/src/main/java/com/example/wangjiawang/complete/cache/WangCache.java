package com.example.wangjiawang.complete.cache;

import android.os.Message;

import com.example.wangjiawang.complete.cache.entry.HomeCache;
import com.example.wangjiawang.complete.model.entity.News;

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
    private Callback callback;
    private MyHandler handler = new MyHandler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<News> list = (List<News>) msg.obj;
            callback.onSuccess(list);
        }
    };

    @Override
    public void doCache(String key,List<News> news) {
        executor.execute(new DoCacheRunnable(key,news));
    }

    @Override
    public void getCache(String key,Callback callback) {
        executor.execute(new GetCacheRunnable(key));
       this.callback = callback;
    }

    class DoCacheRunnable implements Runnable {
        private String key;
        private List<News> list;
        public DoCacheRunnable(String key,List<News> list) {
            this.key = key;
            this.list = list;
        }
        @Override
        public void run() {
            List<HomeCache> cacheList = HistoryCache.NewsToCache(list,key);
            DatabaseManager.insertCache(cacheList);
        }
    }

    class GetCacheRunnable implements Runnable{

        private String key;
        GetCacheRunnable(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<HomeCache> list  = DatabaseManager.getCache(key);
            List<News> newsList = HistoryCache.cacheToNews(list);
            Message msg = handler.obtainMessage();
            msg.obj = newsList;
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
