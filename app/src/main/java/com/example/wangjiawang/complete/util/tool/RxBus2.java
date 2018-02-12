package com.example.wangjiawang.complete.util.tool;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Description:
 * Created by wangjiawang on 2018/2/6.
 * complete
 */
public class RxBus2 {
    private static volatile RxBus2 instance;
    private Subject<Object>  mBus;

    public static RxBus2 getInstance() {
        if(instance == null) {
            synchronized (RxBus2.class) {
                if(instance == null) {
                    instance = new RxBus2();
                }
            }
        }
        return instance;
    }

    public RxBus2() {
        mBus = PublishSubject.create().toSerialized();
    }

    public void post(Object o) {
        mBus.onNext(o);
    }

    public <T>Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }



}
