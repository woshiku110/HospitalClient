package com.woshiku.hospitalclient.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.fragment.impleHome.presenter.CheckPresenter;
import com.woshiku.hospitalclient.fragment.impleHome.presenter.CheckPresenterImple;
import com.woshiku.hospitalclient.fragment.impleHome.view.CheckView;
import com.woshiku.progresslibrary.RoundCornerProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
/**
 * Created by Administrator on 2017/3/10.
 */
@SuppressLint("ValidFragment")
public abstract class WebFragment extends BaseFragment implements CheckView {
    View mView;
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.web_panel_pb)
    RoundCornerProgressBar pb;
    @InjectView(R.id.web_panel_webview)
    WebView webView;
    CheckPresenter checkPresenter;
    public abstract void childLoad();
    @SuppressLint("ValidFragment")
    public WebFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_main_check,null);
        ButterKnife.inject(this, mView);
        childLoad();
        return mView;
    }

    @Override
    public void initPage(String title, String url) {
        webView.loadUrl(url);
    }

    @Override
    public void initLocalPage(String title,String url) {
        webView.loadUrl("file:///android_asset/" + url);
    }

    @Override
    public void setProgress(final int percent) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pb.setProgress(percent);
            }
        });
    }

    @Override
    public void setShowProgress() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void setHideProgress() {
        pb.setVisibility(View.GONE);
    }

    @Override
    public WebView getWebView() {
        return webView;
    }

    @Override
    public void isShowBack(final boolean isShow) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public RoundCornerProgressBar getProgressBar() {
        return pb;
    }



    protected void loadUrl(String url){
        checkPresenter = new CheckPresenterImple(this);
        checkPresenter.initPage("检查页面", url);
    }

    protected void loadLocalUrl(String url){
        checkPresenter = new CheckPresenterImple(this);
        checkPresenter.initLocalPage("检查页面", url);
    }
}
