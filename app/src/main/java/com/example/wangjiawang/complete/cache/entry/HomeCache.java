package com.example.wangjiawang.complete.cache.entry;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.wangjiawang.complete.cache.HistoryCache;
import com.example.wangjiawang.complete.model.entity.News;

import java.util.List;

/**
 * Description:主要存储主页的新闻数据
 * Created by wangjiawang on 2018/2/5.
 * complete
 */

/**
 *   {
 "title": "继《摔跤吧爸爸》后印度又出神作：离婚吧妈妈！",
 "docurl": "http://lady.163.com/18/0130/21/D9E8ALP100267VA9.html",
 "commenturl": "http://comment.lady.163.com/lady_bbs/D9E8ALP100267VA9.html",
 "tienum": 1914,
 "tlastid": "<a href='http://lady.163.com/' class='link'>女人</a>",
 "tlink": "http://lady.163.com/",
 "label": "女人",
 "keywords": [
 {
 "akey_link": "http://lady.163.com/keywords/7/b/79bb5a5a/1.html",
 "keyname": "离婚"
 },
 {
 "akey_link": "http://lady.163.com/keywords/5/b/5bb666b4/1.html",
 "keyname": "家暴"
 },
 {
 "akey_link": "http://lady.163.com/keywords/5/0/5c0f5c39/1.html",
 "keyname": "小尹"
 }
 ],
 "time": "01/30/2018 21:26:15",
 "newstype": "article",
 "imgurl": "bigimg||http://cms-bucket.nosdn.127.net/bd3404bb6b954796bd629b9514db3d0e20180130212613.png||||||||",
 "add1": "http://cms-bucket.nosdn.127.net/bd3404bb6b954796bd629b9514db3d0e20180130212613.png?imageView&thumbnail=600y300",
 "add2": "",
 "add3": ""
 }
 */

public class HomeCache extends News{
    private String key;
    private Class aClass;

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    private HomeCache(){}
    public HomeCache(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
