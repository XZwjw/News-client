package com.example.wangjiawang.complete.view.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.example.wangjiawang.complete.R;
import com.example.wangjiawang.complete.app.CompleteApplication;

import com.example.wangjiawang.complete.model.entity.Event;
import com.example.wangjiawang.complete.util.tool.RxBus2;
import com.example.wangjiawang.complete.view.fragment.LeftMenuFragment;
import com.example.wangjiawang.complete.view.fragment.RightMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity {

    public static SlidingMenu mSlidingMenu;
    public static LeftMenuFragment mLeftMenu;
    public static RightMenuFragment mRightMenu;
    private long mLastPointTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSlidingMenu();
    }



    /**
     * 左右菜单初始化
     */
    private void initSlidingMenu() {
        mLeftMenu = new LeftMenuFragment();
        mRightMenu = new RightMenuFragment();
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置触摸屏幕的模式
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置渐入渐出的效果
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setFadeEnabled(true);
        mSlidingMenu.setMenu(R.layout.left_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu, mLeftMenu).commit();
        mSlidingMenu.setSecondaryMenu(R.layout.right_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu, mRightMenu).commit();
        RxBus2.getInstance().toObservable(Event.class).subscribe(new Consumer<Event>() {
            @Override
            public void accept(Event event) throws Exception {
                mSlidingMenu.showContent();
            }
        });
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

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
