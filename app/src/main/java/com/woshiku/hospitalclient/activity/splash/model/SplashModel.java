package com.woshiku.hospitalclient.activity.splash.model;

import android.app.Activity;

/**
 * Created by Administrator on 2017/3/13.
 */
public interface SplashModel {
    void initData(SplashModelListener splashListener);
    void animStartMethod(SplashModelListener splashListener);
    void animEndMethod(SplashModelListener splashListener);
    interface SplashModelListener{
        void initPage();
        void toastError(String msg);
        Activity getActivity();
        void endActivity();
    }
}
