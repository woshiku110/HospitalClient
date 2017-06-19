package com.woshiku.hospitalclient.fragment.impleHome.model;

import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/3/9.
 */
public class CheckModelImple implements CheckModel{

    @Override
    public void initPage(String title, String url, OnCheckModel onCheckModel) {
        if(onCheckModel != null){
            initWeb(onCheckModel);
            onCheckModel.configPage(title,url);
        }
    }

    @Override
    public void initLocalPage(String title, String url, OnCheckModel onCheckModel) {
        if(onCheckModel != null){
            initWeb(onCheckModel);
            onCheckModel.configLocalPage(title,url);
        }
    }

    private void initWeb(final OnCheckModel onCheckModel){
        if(onCheckModel != null){
            //能够的调用JavaScript代码
            WebSettings webSettings = onCheckModel.getWebView().getSettings();
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
            //webView.addJavascriptInterface(new JsInteration(), "control");
            onCheckModel.getWebView().setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(android.webkit.WebView view, String url) {
                    super.onPageFinished(view, url);
                    //加载完成
                    onCheckModel.hideProgress();
                    if(onCheckModel.getWebView().canGoBack()){
                        onCheckModel.showBack(true);
                    }else{
                        onCheckModel.showBack(false);
                    }
                }

                @Override
                public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    //开始加载
                    onCheckModel.showProgress();
                }

                @Override
                public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onReceivedError(android.webkit.WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }
            });
            onCheckModel.getWebView().setWebChromeClient(new WebChromeClient() {
                @Override
                public void onReceivedTitle(android.webkit.WebView view, String title) {
                    super.onReceivedTitle(view, title);
                }

                @Override
                public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    onCheckModel.progressShow(newProgress);
                }
            });
        }
    }
}
