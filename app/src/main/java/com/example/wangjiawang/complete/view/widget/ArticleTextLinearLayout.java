package com.example.wangjiawang.complete.view.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangjiawang.complete.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Description:
 * Created by wangjiawang on 2018/2/11.
 * complete
 */

public class ArticleTextLinearLayout extends LinearLayout {
    private Context context;
    public ArticleTextLinearLayout(Context context) {
        super(context);
        this.context = context;
        startAddLayout();
    }

    private void startAddLayout() {

    }

    public void addText(String string) {
        TextView textView = new TextView(context);
        textView.setText(string);
        addView(textView);
    }

    public void addImageView(String url) {
        ImageView imageView = new ImageView(context);
        Glide.with(context).load(url).into(imageView);
        addView(imageView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)getResources().getDimension(R.dimen.article_ImageView_height)));
    }

    class StringConfig {
        private LinkedHashMap<String,String> map = new LinkedHashMap<>();
        StringConfig(String text) {
            String[] strings = text.split("<p>");
        }
    }


}
