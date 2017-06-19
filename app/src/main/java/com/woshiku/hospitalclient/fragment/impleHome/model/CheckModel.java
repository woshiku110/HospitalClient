package com.woshiku.hospitalclient.fragment.impleHome.model;

import android.webkit.WebView;

import com.woshiku.progresslibrary.RoundCornerProgressBar;

/**
 * Created by Administrator on 2017/3/9.
 */
public interface CheckModel {
    void initPage(String title,String url,OnCheckModel onCheckModel);
    void initLocalPage(String title,String url,OnCheckModel onCheckModel);
    interface OnCheckModel{
        void showProgress();
        void hideProgress();
        void progressShow(int percent);
        void configPage(String title,String url);
        void configLocalPage(String title,String url);
        void showBack(boolean isShow);
        WebView getWebView();
        RoundCornerProgressBar getProgressBar();
    }
}
