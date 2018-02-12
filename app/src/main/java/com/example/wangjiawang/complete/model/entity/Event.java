package com.example.wangjiawang.complete.model.entity;

/**
 * Description:
 * Created by wangjiawang on 2018/2/6.
 * complete
 */
public class Event {
    private long id;
    private String name;

    public Event(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
