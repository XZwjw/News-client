package com.example.wangjiawang.complete.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.model.entity.Event;
import com.example.wangjiawang.complete.util.tool.RxBus2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:
 * Created by wangjiawang on 2018/2/6.
 * complete
 */
public class RightMenuFragment extends Fragment {
    @BindView(R.id.right_menu_close)
    ImageView mRightMenuClose;
    @BindView(R.id.setting)
    ImageView mSetting;
    @BindView(R.id.user_photo)
    ImageView mUserPhoto;
    @BindView(R.id.msg)
    TextView mMsg;
    @BindView(R.id.collection)
    TextView mCollection;
    @BindView(R.id.offline)
    TextView mOffline;
    @BindView(R.id.notes)
    TextView mNotes;
    Unbinder unbinder;

    private List<View> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_right_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        mList.add(mMsg);
        mList.add(mCollection);
        mList.add(mOffline);
        mList.add(mNotes);
    }

    public void startAnim() {
        startIconAnim(mRightMenuClose);
        startIconAnim(mSetting);
        startColumnAnim();
    }

    private void startColumnAnim() {
        TranslateAnimation localAnimation = new TranslateAnimation(0.0f,0.0f,0.0f,0.0f);
        for(int i = 0;i < mList.size();i++) {
            View localView = mList.get(i);
            localView.startAnimation(localAnimation);
            localAnimation = new TranslateAnimation(i * -35f,0.0f,0.0f,0.0f);
            localAnimation.setDuration(700L);
        }
    }

    private void startIconAnim(View paramView) {

        float f1 = paramView.getWidth() /2;
        float f2 = paramView.getHeight() /2;
        ScaleAnimation  localAnimation = new ScaleAnimation(0.1f,0.6f,0.1f,0.6f,f1,f2);
        localAnimation.setDuration(1000L);
        localAnimation.setFillAfter(true);
        localAnimation.setInterpolator(new BounceInterpolator());
        paramView.startAnimation(localAnimation);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.right_menu_close, R.id.setting, R.id.msg, R.id.collection, R.id.offline, R.id.notes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_menu_close:
                RxBus2.getInstance().post(new Event(1000,"closeMenu"));
                break;
            case R.id.setting:
                break;
            case R.id.msg:
                break;
            case R.id.collection:
                break;
            case R.id.offline:
                break;
            case R.id.notes:
                break;
        }
    }
}
