package com.example.wangjiawang.complete.cache;

import com.example.wangjiawang.complete.cache.entry.HomeCache;
import com.example.wangjiawang.complete.model.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:缓存对应所建的表
 * Created by wangjiawang on 2018/7/16.
 * complete
 */

public class HistoryCache {
    public static String History_home = "HomeCache";    //首页(推荐页)
    public static String Histtory_classification = "Classification";   //分类
    //为了存储List而建的keyWords表
    public static String History_keywords = "Keywords";

    /**
     * 将缓存数据(数据库中的数据)转换到读取对象T（List<T>）中
     * @param list
     * @return
     */
    public static List<News> cacheToNews(List<HomeCache> list) {
        List<News> list1 = new ArrayList<>();
        for (HomeCache homeCache:
             list) {
            News news = new News();
            news.setAdd1(homeCache.getAdd1());
            news.setAdd2(homeCache.getAdd2());
            news.setAdd3(homeCache.getAdd3());
            news.setCommenturl(homeCache.getCommenturl());
            news.setDocurl(homeCache.getDocurl());
            news.setId(homeCache.getId());
            news.setImgurl(homeCache.getImgurl());
            news.setKeywords(homeCache.getKeywords());
            news.setLabel(homeCache.getLabel());
            news.setNewstype(homeCache.getNewstype());
            news.setTienum(homeCache.getTienum());
            news.setTime(homeCache.getTime());
            news.setTitle(homeCache.getTitle());
            news.setTlastid(homeCache.getTlastid());
            news.setTlink(homeCache.getTlink());
            list1.add(news);
        }

        return list1;
    }

    /**
     * 将读取对象T（List<T>）转换到缓存数据(数据库中的数据)中
     */
    public static List<HomeCache> NewsToCache(List<News> list, String key) {
        List<HomeCache> list1 = new ArrayList<>();
        for (News news:
             list) {
            HomeCache cache = new HomeCache(key);
            cache.setAdd1(news.getAdd1());
            cache.setAdd2(news.getAdd2());
            cache.setAdd3(news.getAdd3());
            cache.setCommenturl(news.getCommenturl());
            cache.setDocurl(news.getDocurl());
            cache.setId(news.getId());
            cache.setImgurl(news.getImgurl());
            cache.setKeywords(news.getKeywords());
            cache.setLabel(news.getLabel());
            cache.setNewstype(news.getNewstype());
            cache.setTienum(news.getTienum());
            cache.setTime(news.getTime());
            cache.setTitle(news.getTitle());
            cache.setTlastid(news.getTlastid());
            cache.setTlink(news.getTlink());
            list1.add(cache);

        }
        return list1;
    }

}
