package com.woshiku.hospitalclient.fragment.impleHome.presenter;

import android.webkit.WebView;

import com.woshiku.hospitalclient.fragment.impleHome.model.CheckModel;
import com.woshiku.hospitalclient.fragment.impleHome.model.CheckModelImple;
import com.woshiku.hospitalclient.fragment.impleHome.view.CheckView;
import com.woshiku.progresslibrary.RoundCornerProgressBar;

/**
 * Created by Administrator on 2017/3/9.
 */
public class CheckPresenterImple implements CheckPresenter,CheckModel.OnCheckModel{
    CheckView checkView;
    CheckModel checkModel;

    public CheckPresenterImple(CheckView checkView) {
        this.checkView = checkView;
        checkModel = new CheckModelImple();
    }

    @Override
    public void initPage(String title, String url) {
        checkModel.initPage(title,url,this);
    }

    @Override
    public void initLocalPage(String title, String url) {
        checkModel.initLocalPage(title,url,this);
    }

    @Override
    public void showProgress() {
        if(checkView != null){
            checkView.setShowProgress();
        }
    }

    @Override
    public void hideProgress() {
        if(checkView != null){
            checkView.setHideProgress();
        }
    }

    @Override
    public void progressShow(int percent) {
        if(checkView != null){
            checkView.setProgress(percent);
        }
    }

    @Override
    public void configPage(String title, String url) {
        if(checkView != null){
            checkView.initPage(title,url);
        }
    }

    @Override
    public void configLocalPage(String title, String url) {
        if(checkView != null){
            checkView.initLocalPage(title,url);
        }
    }

    @Override
    public void showBack(boolean isShow) {
        if(checkView != null){
            checkView.isShowBack(isShow);
        }
    }

    @Override
    public WebView getWebView() {
        if(checkView != null){
            return checkView.getWebView();
        }
        return null;
    }

    @Override
    public RoundCornerProgressBar getProgressBar() {
        if(checkView != null){
            return checkView.getProgressBar();
        }
        return null;
    }
}
