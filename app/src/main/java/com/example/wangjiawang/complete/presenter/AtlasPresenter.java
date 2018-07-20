package com.example.wangjiawang.complete.presenter;


import com.example.wangjiawang.complete.model.api.NetEaseApiService;
import com.example.wangjiawang.complete.model.entity.Atlas;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:
 * Created by wangjiawang on 2018/2/12.
 * complete
 */

public class AtlasPresenter implements AtlasContract.Presenter {
    private AtlasContract.View view;
    private NetEaseApiService apiService;

    @Inject
    public AtlasPresenter(AtlasContract.View view,NetEaseApiService apiService){
        this.apiService = apiService;
        this.view = view;
    }

    @Override
    public void getAtlas(String path) {
        apiService.getAtlas(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Atlas>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Atlas atlas) {
                        view.updateUI(atlas);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
