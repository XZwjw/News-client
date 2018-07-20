package com.example.wangjiawang.complete.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ViewPagerIndicator extends LinearLayout {
//    private ViewPager mViewPager;
//    private List<TextView> mTitleList;
    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setItemClick();
    }
//    public void setmTitleList(List<TextView> list) {
//        mTitleList = list;
//    }
//    public void setViewPager(ViewPager viewPager) {
//        this.mViewPager = viewPager;
//    }
//
//    private void setItemClick() {
//        int cCount = getChildCount();
//        for(int i = 0;i < cCount;i++) {
//            View view = getChildAt(i);
//            final int j = i;
//            view.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mViewPager.setCurrentItem(j);
//                }
//            });
//        }
//    }

}
