package com.example.wangjiawang.complete.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wangjiawang.complete.app.CompleteApplication;
import com.example.wangjiawang.complete.cache.entry.HomeCache;
import com.example.wangjiawang.complete.cache.entry.CacheString;
import com.example.wangjiawang.complete.model.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:缓存工具类
 * Created by wangjiawang on 2018/7/11.
 * complete
 */

public class DatabaseManager {
    private static Context mContext;
    private static DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDbDatabase;
    private static final String mDbName = "newsDB";    //数据库名
    private static final int mDbVersion = 1;
    public DatabaseManager(Context context,DatabaseHelper dbHelper){
        mContext = context;
        mDbHelper = dbHelper;
    }


    /**
     * 存入数据,如果存在则替代,不存在则增加
     * @return
     */
    public synchronized static void insertCache(HomeCache cacheEntry) {
        initDatabaseManger();
        ContentValues values = new ContentValues();
        values.put(CacheString.key,cacheEntry.getKey());
        values.put(CacheString.add1,cacheEntry.getAdd1());
        values.put(CacheString.add2,cacheEntry.getAdd2());
        values.put(CacheString.add3,cacheEntry.getAdd3());
        values.put(CacheString.commenturl,cacheEntry.getCommenturl());
        values.put(CacheString.docurl,cacheEntry.getDocurl());
        values.put(CacheString.id,cacheEntry.getId());
        values.put(CacheString.imgurl,cacheEntry.getImgurl());
        values.put(CacheString.label,cacheEntry.getLabel());
        values.put(CacheString.newstype,cacheEntry.getNewstype());
        values.put(CacheString.tienum,cacheEntry.getTienum());
        values.put(CacheString.time,cacheEntry.getTime());
        values.put(CacheString.title,cacheEntry.getTitle());
        values.put(CacheString.tlastid,cacheEntry.getTlastid());
        values.put(CacheString.lastTime,System.currentTimeMillis());

        String key = cacheEntry.getKey();
        boolean isHave = search_key(key);
        String[] keywordIdArray = null;
        if(isHave) {
            keywordIdArray = getKeywordsId(key);
        }

        //keywords列表的存储。
        long value; //插入表得到的返回值
        StringBuilder sb = new StringBuilder("");    //将所有的返回值变为字符串
        ContentValues values2 = new ContentValues();
        List<News.Keywords> keywordsList = cacheEntry.getKeywords();

        for(int i = 0;i < cacheEntry.getKeywords().size();i++) {
            values2.put(CacheString.CacheKeywords.link,keywordsList.get(i).getAkey_link());
            values2.put(CacheString.CacheKeywords.name,keywordsList.get(i).getKeyname());
            values2.put(CacheString.CacheKeywords.lastTime,System.currentTimeMillis());
            try {
                mDbDatabase.beginTransaction();
                if(isHave) {
                    mDbDatabase.update(HistoryCache.History_keywords,values2,"_id=?",new String[]{keywordIdArray[i]});
                    value = Long.valueOf(keywordIdArray[i]);
                }else {
                    value = mDbDatabase.insert(HistoryCache.History_keywords,null,values2);

                }
                if(i == cacheEntry.getKeywords().size() - 1) {
                    sb.append(value);
                }else {
                    sb.append(value+",");
                }
                mDbDatabase.setTransactionSuccessful();

            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                mDbDatabase.endTransaction();
            }


        }
        values.put(CacheString.keywordsRawNumberList,sb.toString());
        try {
            mDbDatabase.beginTransaction();
            if(isHave) {
                mDbDatabase.update(HistoryCache.History_home,values,"key=?",new String[]{cacheEntry.getKey()});
            }else {
                mDbDatabase.insert(HistoryCache.History_home,null,values);
            }
            mDbDatabase.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            mDbDatabase.endTransaction();
        }
    }

   

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public synchronized static HomeCache getCache(String key) {
        HomeCache cacheEntry = new HomeCache(key);
        initDatabaseManger();
        Cursor cursor = mDbDatabase.rawQuery("SELECT * FROM " + HistoryCache.History_home + " WHERE key=?", new String[]{key});
        try {
            while(cursor != null && cursor.getCount() == 1) {
                cursor.moveToNext();
                int add1 = cursor.getColumnIndex(CacheString.add1);
                int add2 = cursor.getColumnIndex(CacheString.add2);
                int add3 = cursor.getColumnIndex(CacheString.add3);
                int commenturl = cursor.getColumnIndex(CacheString.commenturl);
                int docurl = cursor.getColumnIndex(CacheString.docurl);
                int id2 = cursor.getColumnIndex(CacheString.id);
                int imgurl = cursor.getColumnIndex(CacheString.imgurl);
                int keywordsRawNumberList = cursor.getColumnIndex(CacheString.keywordsRawNumberList);
                int label = cursor.getColumnIndex(CacheString.label);
                int newstype = cursor.getColumnIndex(CacheString.newstype);
                int tienum = cursor.getColumnIndex(CacheString.tienum);
                int time = cursor.getColumnIndex(CacheString.time);
                int title = cursor.getColumnIndex(CacheString.title);
                int tlastid = cursor.getColumnIndex(CacheString.tlastid);
                int tlink = cursor.getColumnIndex(CacheString.tlink);

                cacheEntry.setAdd1(cursor.getString(add1));
                cacheEntry.setAdd2(cursor.getString(add2));
                cacheEntry.setAdd3(cursor.getString(add3));
                cacheEntry.setCommenturl(cursor.getString(commenturl));
                cacheEntry.setDocurl(cursor.getString(docurl));
                cacheEntry.setId(cursor.getString(id2));
                cacheEntry.setImgurl(cursor.getString(imgurl));
                cacheEntry.setLabel(cursor.getString(label));
                cacheEntry.setNewstype(cursor.getString(newstype));
                cacheEntry.setTienum(cursor.getShort(tienum));
                cacheEntry.setTime(cursor.getString(time));
                cacheEntry.setTitle(cursor.getString(title));
                cacheEntry.setTlastid(cursor.getString(tlastid));
                cacheEntry.setTlink(cursor.getString(tlink));

                //Keywords的获取
                List<News.Keywords> kwList = new ArrayList<>();
                String[] kwArray = cursor.getString(keywordsRawNumberList).split(",");
                for(int i = 0;i < kwArray.length;i++) {
                    Cursor kwCursor = mDbDatabase.rawQuery("select * from "+HistoryCache.History_keywords+" where _id = ?",new String[]{kwArray[i]});
                    while (kwCursor.moveToNext()) {
                        News.Keywords keywords = new News.Keywords();
                        int link = kwCursor.getColumnIndex(CacheString.CacheKeywords.link);
                        int name = kwCursor.getColumnIndex(CacheString.CacheKeywords.name);
                        keywords.setAkey_link(kwCursor.getString(link));
                        keywords.setKeyname(kwCursor.getString(name));
                        kwList.add(keywords);
                    }
                    kwCursor.close();
                }

                cacheEntry.setKeywords(kwList);
            }
        }catch (Exception  e){
            e.printStackTrace();
        }finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return cacheEntry;
    }

    /**
     * 删除缓存
     */
    public static synchronized void deleteCache(){
        initDatabaseManger();
        mContext.deleteDatabase(mDbHelper.getDatabaseName());
    }

    /**
     * 查询表中是否含有该key所对应的该行数据,返回该行对应表keywords的id（在表History_Home中的keywords字段中存储）。
     * @param key
     * @return
     */
    private static boolean search_key(String key) {
        boolean result = false;
        Cursor cursor = mDbDatabase.rawQuery("select * from "+HistoryCache.History_home+" WHERE key=?",new String[]{key});
        while (cursor.moveToNext()) {
            result = true;
        }
        cursor.close();
        return result;

    }

    /**
     * 获取表History_Home中对应的History_keywords表中的id数组。
     * @param key
     * @return
     */
    public static String[] getKeywordsId(String key) {
       String[] result = null;
       Cursor cursor = mDbDatabase.rawQuery("select keywords from "+HistoryCache.History_home+" WHERE key=?",new String[]{key});
       while (cursor.moveToNext()) {
           int keywordsColumn = cursor.getColumnIndex(CacheString.keywordsRawNumberList);
           result = cursor.getString(keywordsColumn).split(",");
       }
       cursor.close();
       return result;
    }

    /**
     * 初始化数据库
     */
    private static void initDatabaseManger() {
        mContext = CompleteApplication.getContext();
        if(mDbHelper == null) {
            mDbHelper = new DatabaseHelper(mContext,mDbName,null,mDbVersion);
        }
        if(mDbDatabase == null) {
            mDbDatabase = mDbHelper.getWritableDatabase();
        }
    }

//    public synchronized static HomeCache getCacheById(String id) {
//        Cursor cursor = mDbDatabase.rawQuery("select * from "+HistoryCache.History_home+" where _id=?",new String[]{id});
//
//    }

}
