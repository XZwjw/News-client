package com.example.wangjiawang.complete.model.api;

import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.model.entity.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Description:retrofit的Api
 * Created by wangjiawang on 2018/2/5.
 * complete
 *
 */

public interface ApiService {
    @GET("/{category}/find/")
    Observable<Result<List<News>>> getList(@Path(value = "category") String category,@Query("page") String page);

}
