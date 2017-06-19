package com.woshiku.hospitalclient.activity.splash.presenter;

import android.app.Activity;

import com.woshiku.hospitalclient.activity.splash.model.SplashModel;
import com.woshiku.hospitalclient.activity.splash.model.SplashModelImple;
import com.woshiku.hospitalclient.activity.splash.view.SplashView;

/**
 * Created by Administrator on 2017/3/13.
 */
public class SplashPresenterImple implements SplashPresenter,SplashModel.SplashModelListener{
    SplashModel splashModel;
    SplashView splashView;

    public SplashPresenterImple(SplashView splashView) {
        this.splashView = splashView;
        this.splashModel = new SplashModelImple();
    }

    @Override
    public void initData() {
        splashModel.initData(this);
    }

    @Override
    public void animStartMethod() {
        splashModel.animStartMethod(this);
    }

    @Override
    public void animEndMethod() {
        splashModel.animEndMethod(this);
    }

    @Override
    public void initPage() {
        if(splashView != null){
            splashView.initActivity();
            splashView.setAnimViewShow();
        }
    }

    @Override
    public void toastError(String msg) {
        if(splashView != null){
            splashView.setToastError(msg);
        }
    }

    @Override
    public Activity getActivity() {
        if(splashView != null){
            return splashView.getActivity();
        }
        return null;
    }

    @Override
    public void endActivity() {
        if(splashView != null){
            splashView.endActivity();
        }
    }
}
