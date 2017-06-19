package com.woshiku.hospitalclient.activity.splash.view;

import android.app.Activity;

/**
 * Created by Administrator on 2017/3/13.
 */
public interface SplashView {
    void initActivity();
    void setToastError(String msg);
    void setAnimViewShow();
    Activity getActivity();
    void endActivity();
}
