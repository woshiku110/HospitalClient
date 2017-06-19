package com.woshiku.hospitalclient.activity.splash;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.splash.presenter.SplashPresenter;
import com.woshiku.hospitalclient.activity.splash.presenter.SplashPresenterImple;
import com.woshiku.hospitalclient.activity.splash.view.SplashView;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/3/13.
 */
public class SplashActivity extends BaseActivity implements SplashView{
    @InjectView(R.id.line_splash)
    LinearLayout lineSplash;
    SplashPresenter splashPresenter;
    @Override
    protected void initView() {
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=SplashActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        splashPresenter = new SplashPresenterImple(this);
        splashPresenter.initData();
    }
    @Override
    public void initActivity() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(false);//设置可以滑动
    }

    @Override
    public void setToastError(String msg) {
        toastShort(msg);
    }

    /**
     * 显示动画
     * */
    @Override
    public void setAnimViewShow() {
        AnimationSet set = new AnimationSet(false);// 动画集合
        AlphaAnimation alpha = new AlphaAnimation(1, 1);// 渐变动画
        alpha.setDuration(2000);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态
        set.addAnimation(alpha);
        // 设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                splashPresenter.animStartMethod();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {// 动画执行结束
                splashPresenter.animEndMethod();
            }
        });
        lineSplash.startAnimation(set);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void swipeBackCallback() {

    }
}
