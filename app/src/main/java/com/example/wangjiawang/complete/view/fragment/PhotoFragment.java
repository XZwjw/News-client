package com.example.wangjiawang.complete.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wangjiawang.complete.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Created by wangjiawang on 2018/2/12.
 * complete
 */

public class PhotoFragment extends Fragment {
    @BindView(R.id.iv)
    ImageView mIv;
    Unbinder unbinder;
    private String photoUrl;

    public static PhotoFragment getInstance(String photourl) {
        PhotoFragment instance = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("photoUrl", photourl);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_fragment, container, false);
        photoUrl = getArguments().getString("photoUrl");
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getContext()).load(photoUrl).into(mIv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
