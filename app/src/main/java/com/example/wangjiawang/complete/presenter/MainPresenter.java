package com.example.wangjiawang.complete.presenter;


import android.content.res.TypedArray;
import android.util.Log;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.app.CompleteApplication;
import com.example.wangjiawang.complete.model.api.ApiService;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.model.entity.Result;


import java.util.ArrayList;
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

    /**
     * 获取所有分类
     * @return
     */
    @Override
    public List<String> getAllClassifications() {
        return getList(R.array.allChannelArray);
    }

    /**
     * 获取默认分类
     * @return
     */
    @Override
    public List<String> getDefaultCategory() {
        return getList(R.array.DefaultChannel);
    }

    /**
     * 获取array文件中的字符数组
     * @param id
     * @return
     */
    public List<String> getList(int id) {
        List<String> list = new ArrayList<>();
        TypedArray array = CompleteApplication.getInstance().getResources().obtainTypedArray(id);
        for(int i = 0;i < array.length();i++) {
            list.add(array.getString(i));
        }
        array.recycle();
        return list;
    }

    @Override
    public void getListByPage(final String category, String pageId) {
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
                            view.updateListUI(listData.getData(),category);
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
                        Log.d("TAG","onComplete");
                    }
                });

    }
}
