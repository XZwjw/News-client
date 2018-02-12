package com.example.wangjiawang.complete.model.entity;

import com.alibaba.fastjson.JSON;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
