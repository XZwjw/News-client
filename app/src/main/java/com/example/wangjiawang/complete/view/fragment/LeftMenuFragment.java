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
public class LeftMenuFragment extends Fragment {


    @BindView(R.id.left_menu_close)
    ImageView mLeftMenuClose;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.home)
    TextView mHome;
    @BindView(R.id.word)
    TextView mWord;
    @BindView(R.id.voice)
    TextView mVoice;
    @BindView(R.id.television)
    TextView mTelevision;
    Unbinder unbinder;

    private List<View> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_left_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        mList.add(mHome);
        mList.add(mWord);
        mList.add(mVoice);
        mList.add(mTelevision);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.left_menu_close, R.id.search, R.id.home, R.id.word, R.id.voice, R.id.television})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_menu_close:
                RxBus2.getInstance().post(new Event(10000,"closeMenu"));
                break;
            case R.id.search:
                break;
            case R.id.home:
                RxBus2.getInstance().post(new Event(10000,"closeMenu"));
                break;
            case R.id.word:
                break;
            case R.id.voice:
                break;
            case R.id.television:
                break;
        }
    }

    public void startAnim() {
        startIconAnim(mLeftMenuClose);
        startIconAnim(mSearch);
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

    private void startIconAnim(View mView) {
        float f1 = mView.getWidth() / 2;
        float f2 = mView.getHeight() / 2;
        ScaleAnimation localAnimation = new ScaleAnimation(0.1f,0.6f,0.1f,0.6f,f1,f2);
        localAnimation.setDuration(1000L);
        localAnimation.setFillAfter(true);
        localAnimation.setInterpolator(new BounceInterpolator());
        mView.startAnimation(localAnimation);

    }
}
