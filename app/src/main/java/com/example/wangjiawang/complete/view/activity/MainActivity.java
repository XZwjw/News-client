package com.example.wangjiawang.complete.view.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.app.CompleteApplication;

import com.example.wangjiawang.complete.di.components.DaggerMainComponent;
import com.example.wangjiawang.complete.di.module.MainModule;
import com.example.wangjiawang.complete.model.entity.Event;
import com.example.wangjiawang.complete.model.entity.News;
import com.example.wangjiawang.complete.model.manage.NewsCategory;
import com.example.wangjiawang.complete.presenter.MainContract;
import com.example.wangjiawang.complete.presenter.MainPresenter;
import com.example.wangjiawang.complete.util.tool.RxBus2;
import com.example.wangjiawang.complete.view.adapter.VerticalViewPagerAdapter;
import com.example.wangjiawang.complete.view.fragment.LeftMenuFragment;
import com.example.wangjiawang.complete.view.fragment.RightMenuFragment;
import com.example.wangjiawang.complete.view.widget.VerticalViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Inject
    protected MainPresenter presenter;

    @BindView(R.id.view_pager)
    VerticalViewPager mViewPager;
    private LeftMenuFragment mLeftMenu;
    private RightMenuFragment mRightMenu;
    private SlidingMenu mSlidingMenu;

    private long mLastPointTime;

    private VerticalViewPagerAdapter adapter;

    private int page = 0;

    private boolean isLoading = true;

    private String category = NewsCategory.DEFAULT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMenu();
        initPage();
        loadData(page);
    }

    private void initPage() {
        adapter = new VerticalViewPagerAdapter(getSupportFragmentManager());
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .netComponent(CompleteApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(adapter.getCount() <= position + 2 && !isLoading) {
                    if(isLoading) {
                        Toast.makeText(MainActivity.this,"努力加载中",Toast.LENGTH_SHORT).show();
                    }
                    Log.d("TAG","page="+page+"lastNewsId="+adapter.getLastNewsId()+"lastCreateTime="+adapter.getLastCreateTime());
                    loadData(page);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG","position:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadData(int page) {
        isLoading = true;
        presenter.getListByPage(category,String.valueOf(page));

    }

    private void initMenu() {
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置触摸屏幕的模式
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置渐入渐出的效果
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setFadeEnabled(true);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.left_menu);
        mLeftMenu = new LeftMenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu, mLeftMenu).commit();
        mSlidingMenu.setSecondaryMenu(R.layout.right_menu);
        mRightMenu = new RightMenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu, mRightMenu).commit();
        RxBus2.getInstance().toObservable(Event.class).subscribe(new Consumer<Event>() {
            @Override
            public void accept(Event event) throws Exception {
                mSlidingMenu.showContent();
            }
        });
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void discussLoading() {

    }

    @Override
    public void showNoData() {
        Toast.makeText(MainActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMore() {
        Toast.makeText(MainActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void updateListUI(List<News> list) {
        Log.d("TAG", "list.size():" + list.size());
        isLoading = false;
        adapter.setArticleList(list);
        page++;
    }

    @Override
    public void showOnFailure() {
        Toast.makeText(MainActivity.this,"加载失败，请检查你的网络连接",Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.left_slide, R.id.right_side})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_slide:
                mSlidingMenu.showMenu();
                mLeftMenu.startAnim();
                break;
            case R.id.right_side:
                mSlidingMenu.showSecondaryMenu();
                mRightMenu.startAnim();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if(mSlidingMenu.isMenuShowing() || mSlidingMenu.isSecondaryMenuShowing()) {
            mSlidingMenu.showContent();
        }else if(System.currentTimeMillis() - mLastPointTime < 2000L) {
            super.onBackPressed();
        } else {
            mLastPointTime = System.currentTimeMillis();
            Snackbar.make(this.findViewById(R.id.frame),"再按一次退出",Snackbar.LENGTH_SHORT).show();
        }
    }
}
