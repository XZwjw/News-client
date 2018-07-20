package com.example.wangjiawang.complete.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 某一条新闻的json数据（以此为依据建表）
 {
 "id": null,
 "title": "十二星座男如何取舍对前任的爱",
 "digest": null,
 "docurl": "http://lady.163.com/18/0702/18/DLNSUHI800267VA9.html",
 "commenturl": "http://comment.lady.163.com/lady_bbs/DLNSUHI800267VA9.html",
 "tienum": 38,
 "tlastid": "<a href='http://lady.163.com/' class='link'>女人</a>",
 "tlink": "http://lady.163.com/",
 "label": "女人",
 "keywords": [
 {
 "id": null,
 "akey_link": "http://lady.163.com/keywords/5/4/524d4efb/1.html",
 "keyname": "前任"
 },
 {
 "id": null,
 "akey_link": "http://lady.163.com/keywords/6/1/661f5ea77537/1.html",
 "keyname": "星座男"
 },
 {
 "id": null,
 "akey_link": "http://lady.163.com/keywords/5/0/5206624b/1.html",
 "keyname": "分手"
 }
 ],
 "time": "07/02/2018 18:28:10",
 "newstype": "article",
 "pics3": null,
 "channelname": null,
 "imgurl": "http://cms-bucket.nosdn.127.net/2018/07/02/9c64df5c68b847189ce66f10c3ddb40a.png?imageView&thumbnail=190y120",
 "add1": "",
 "add2": "",
 "add3": ""
 }
 */

/**
 * Description:新闻缓存数据库创建
 * Created by wangjiawang on 2018/7/11.
 * complete
 */

public class DatabaseHelper extends SQLiteOpenHelper {


//    public static final String URL = "url";       //json数据所在的url
//    public static final String DATA = "data";     //json中的数据

    //方法一:将JSON拆开进行建表
    private static final String CREATE_CacheTABLE = "CREATE TABLE IF NOT EXISTS "+HistoryCache.History_home+"("
            +"key Text,"
            +"_id integer primary key autoincrement,id varchar(20),title varchar(20),"
            +"digest varchar(40),docurl TEXT,commenturl varchar(100),tienum varchar(20),"
            +"tlastid TEXT,tlink varchar(40),label varchar(10),keywords text,"
            +"time varchar(20),newstype varchar(20),pic3 varchar(20),channelname varchar(20),"
            +"imgurl TEXT,add1 TEXT,add2 TEXT,add3 TEXT,last_time TEXT)";
    //keywords关键字表字符串
    private static final String CREATE_CacheKeywords = "CREATE TABLE IF NOT EXISTS "+HistoryCache.History_keywords+"(_id integer primary key autoincrement,link TEXT,name TEXT,last_time TEXT)";
//    //方法二:将JSON作为一个整体的字符串进行建表
//    private static final String CREATE_CacheTABLE = "CREATE TABLE IF NOT EXISTS new("
//            +table_CACHE+"TEXT,"
//            +table__ID+" integer primary key autoincrement,"
//            +table_URL+"TEXT,"
//            +table_DATA +"TEXT,"
//            +table_TIME +"TEXT)";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 数据库建表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CacheTABLE);
        db.execSQL(CREATE_CacheKeywords);

    }

    /**
     * 数据库版本更新
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
