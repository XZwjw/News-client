package com.example.wangjiawang.complete.presenter;

import com.example.wangjiawang.complete.model.entity.Atlas;

/**
 * Description:
 * Created by wangjiawang on 2018/2/12.
 * complete
 */

public interface AtlasContract {
    interface View{
        void showProgress();
        void discusProgress();
        void onFailure();
        void updateUI(Atlas atlas);
    }

    interface Presenter{
        void getAtlas(String path);
    }
}
