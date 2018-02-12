package com.example.wangjiawang.complete.model.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
public class StringConverterFactory extends Converter.Factory {
    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotation, Retrofit retrofit) {
        if(type == String.class) {
            return new StringConverter();
        }
        //其他类型不处理，返回为null
        return null;
    }


}
