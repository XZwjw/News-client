package com.example.wangjiawang.complete.util.tool;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.wangjiawang.complete.model.entity.Article;
import com.google.gson.Gson;

import io.reactivex.functions.Function;

/**
 * Description:
 * Created by wangjiawang on 2018/2/9.
 * complete
 */

public class StringToArticleFunction implements Function<String,Article> {
    private String mArticleKey;
    public StringToArticleFunction(String articleKey){
        mArticleKey = articleKey;
    }
    @Override
    public Article apply(String s) throws Exception {
        Log.d("TAG111111",s);
        org.json.JSONObject jsonObject = new org.json.JSONObject(s);
        String realString = jsonObject.getString(mArticleKey);
        Log.d("TAG11111111","realString:"+realString);
        Gson gson = new Gson();
        Article article = gson.fromJson(realString,Article.class);
        return article;
    }


}
