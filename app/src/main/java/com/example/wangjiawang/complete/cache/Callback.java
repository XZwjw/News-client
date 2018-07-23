package com.example.wangjiawang.complete.cache;

import com.example.wangjiawang.complete.model.entity.News;

import java.util.List;


/**
 * Description:一个回调函数
 * Created by wangjiawang on 2018/7/19.
 * complete
 */

public interface Callback {
    void onSuccess(List<News> news);
}
