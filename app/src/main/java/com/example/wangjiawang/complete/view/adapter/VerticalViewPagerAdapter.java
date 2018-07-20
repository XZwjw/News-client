package com.example.wangjiawang.complete.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.view.fragment.MainItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by wangjiawang on 2018/2/7.
 * complete
 */
public class VerticalViewPagerAdapter extends FragmentStatePagerAdapter {
    private String TAG = "VerticalViewPagerAdapter";
    private List<News> mArticleList = new ArrayList<>();

    public VerticalViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainItemFragment.instance(mArticleList.get(position));
    }


    @Override
    public int getCount() {
        return mArticleList.size();
    }

    public void setArticleList(List<News> articleList) {
        mArticleList.addAll(articleList);
        notifyDataSetChanged();
    }


    public String getLastNewsId() {
        News news = mArticleList.get(mArticleList.size()-1);
        String str = news.getDocurl();
        //http://lady.163.com/18/0201/10/D9I8AAB300267VA9.html
        if(str.contains("html")) {
            str = str.substring(0,str.length()-5);
        }else {
            return "0";
        }
        //http://lady.163.com/18/0201/10/D9I8AAB300267VA9

        String[] strArray = str.split("/");
        return strArray[strArray.length-1];
    }

    public String getLastCreateTime() {
        News news = mArticleList.get(mArticleList.size()-1);
        return news.getTime();
    }



}
