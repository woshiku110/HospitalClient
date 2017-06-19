package com.woshiku.hospitalclient.activity.web;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.web.presenter.WebPresenter;
import com.woshiku.hospitalclient.activity.web.presenter.WebPresenterImple;
import com.woshiku.hospitalclient.activity.web.view.WebView;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.progresslibrary.RoundCornerProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/2/22.
 *用于显示网络页面
 * 这次使用mvp设计模式做的，看看效果怎么样
 */
public abstract class WebActivity extends BaseActivity implements WebView {
    @InjectView(R.id.txt_title)
    protected TextView titleView;
    @InjectView(R.id.concrete_bt)
    protected TextView concert_bt;
    @InjectView(R.id.web_panel_pb)
    protected RoundCornerProgressBar roundPb;
    @InjectView(R.id.web_panel_webview)
    protected android.webkit.WebView webView;
    protected WebPresenter webPresenter;
    protected Activity activity;
    abstract void loadChildrenMethod();//用于孩子的初始化
    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
        ButterKnife.inject(this);
        activity = this;
        webPresenter = new WebPresenterImple(this);
        webPresenter.getActivityDatas();
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void setInitView() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
        concert_bt.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setWebTitle(String title) {
        titleView.setText(title);
    }

    @Override
    public void setloadUrlPageProgress(int pb) {
        roundPb.setProgress(pb);
    }

    @Override
    public void setLoadPageFinish() {

    }

    @Override
    public void setWebLoadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void setProgressShow() {
        roundPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressHide() {
        roundPb.setVisibility(View.GONE);
    }

    @Override
    public Activity getWebActivity() {
        return activity;
    }

    @Override
    public android.webkit.WebView getWebView() {
        return webView;
    }

    @Override
    public void loadChild() {
        loadChildrenMethod();
    }
}
