package com.example.wangjiawang.complete.model.api;

import com.example.wangjiawang.complete.model.entity.Article;
import com.example.wangjiawang.complete.model.entity.Atlas;

import javax.inject.Qualifier;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Description: ApiService2的Api
 * Created by wangjiawang on 2018/2/5.
 * complete
 */

public interface NetEaseApiService {
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
    @GET("nc/article/{articleId}/full.html")
    Observable<String> getArticle(@Path("articleId") String articleId);

    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
    @GET("photo/api/set/{path}.json")
    Observable<Atlas> getAtlas(@Path("path") String path);
}
