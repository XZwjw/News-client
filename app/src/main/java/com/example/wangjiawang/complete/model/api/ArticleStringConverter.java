package com.example.wangjiawang.complete.model.api;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import retrofit2.Converter;
import retrofit2.Response;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
public class ArticleStringConverter implements Converter<Response, String> {
    private String string;
    public ArticleStringConverter(String string) {
        this.string = string;
    }
    @Override
    public String convert(Response value) throws IOException {
        return convert(value,string);
    }

    private String convert(Response value, String key) {
        if(key == null)
            return null;
        JSONObject json = JSONObject.parseObject(value.toString());
        return json.getString(key);
    }
}
