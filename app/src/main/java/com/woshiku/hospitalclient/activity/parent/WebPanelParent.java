package com.woshiku.hospitalclient.activity.parent;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.progresslibrary.RoundCornerProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/14.
 */
public abstract class WebPanelParent extends BaseActivity{
    @InjectView(R.id.web_panel_webview)
    WebView webView;
    @InjectView(R.id.web_panel_pb)
    RoundCornerProgressBar pb;
    @InjectView(R.id.txt_title)
    TextView title;
    @InjectView(R.id.concrete_bt)
    TextView concert_bt;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
        ButterKnife.inject(this);
        concert_bt.setVisibility(View.GONE);
        initDatas();
        init();
    }
    protected void initDatas(){
        initHandler();
        initWebView();
    }
    protected abstract void init();
    private void initHandler(){
        if(mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 0: //在bodychoose界面加载网络数据的时候用到的progressbar
                            int progress = Integer.parseInt(msg.getData().getString("msg"));
                            pb.setProgress(progress);
                            break;
                    }
                }
            };
        }
    }
    public void sendMessage(int what,String msg){
        Message message = new Message();
        message.what = what;
        Bundle bd = new Bundle();
        bd.putString("msg",msg);
        message.setData(bd);
        mHandler.sendMessage(message);
    }
    @SuppressLint("JavascriptInterface")
    public void initWebView(){
        //能够的调用JavaScript代码
        WebSettings webSettings = webView.getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setSupportZoom(true);//设定支持缩放
        webSettings.setLoadWithOverviewMode(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        //webView.loadUrl("file:///android_asset/111.html");
        webView.addJavascriptInterface(new JsInteration(), "control");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //开始加载
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                sendMessage(0, newProgress + "");
            }
        });

    }
    protected void setText(final String titleText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                title.setText(titleText);
            }
        });
    }
    protected void loadLocalUrl(String path){
        webView.loadUrl("file:///android_asset/" + path);
    }
    protected void loadUrl(String url){
        webView.loadUrl(url);
    }
    public class JsInteration {
        /*@JavascriptInterface
        public void onChooseResult(boolean male,String part){
            BodyChooseFragment bodyChooseFragment = (BodyChooseFragment) FragmentFactory.getFragment(1);
            if(bodyChooseFragment!=null){
                bodyChooseFragment.bodyChooseResult(male,part);
            }
        }*/
    }
    /*返回命令*/
    protected abstract void backOrder();
    @OnClick(R.id.concrete_bt)
    void userClick(){
        backOrder();
    }

    @Override
    public void swipeBackCallback() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            backOrder();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
