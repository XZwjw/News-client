package com.example.wangjiawang.complete.presenter;


import android.util.Log;

import com.example.wangjiawang.complete.model.api.ApiService;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.model.entity.Result;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:
 * Created by wangjiawang on 2018/2/5.
 * complete
 */
public class MainPresenter implements MainContract.Presenter{
    private MainContract.View view;
    private ApiService apiService;

    @Inject
    public MainPresenter(MainContract.View view,ApiService service) {
        this.view = view;
        this.apiService = service;
    }

    @Override
    public void getListByPage(String category,String pageId) {
        apiService.getList(category,pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<List<News>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<News>> listData) {
                        int size = listData.getData().size();
                        if(size > 0) {
                            view.updateListUI(listData.getData());
                        } else {
                            view.showNoMore();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showOnFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
