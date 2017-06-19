package com.woshiku.hospitalclient.activity.check.presenter;

import android.app.Activity;

import com.woshiku.hospitalclient.activity.check.model.CheckModel;
import com.woshiku.hospitalclient.activity.check.model.CheckModelImple;
import com.woshiku.hospitalclient.activity.check.view.CheckView;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;

import domain.CheckData;

/**
 * Created by Administrator on 2017/2/25.
 */
public class CheckPresenterImple implements CheckPresenter,CheckModel.OnCheckListener{
    CheckView checkView;
    CheckModel checkModel;
    public  CheckPresenterImple(CheckView checkView) {
        this.checkView = checkView;
        checkModel = new CheckModelImple();
    }

    @Override
    public void initPage(String title) {
        checkModel.initPage(title, this);
    }

    @Override
    public void loadCheckListData() {
        checkModel.loadCheckListData(this);
    }

    @Override
    public void loadCheckDetailData(CheckData checkData) {
        checkModel.loadCheckDetailData(checkData,this);
    }


    @Override
    public void onTitleBar(String title) {
        if(checkView != null){
            checkView.initTitleBar(title);
        }
    }

    @Override
    public void onActivity() {
        if(checkView != null){
            checkView.initActivity();
        }
    }

    @Override
    public void onListView(CommonAdapter commonAdapter) {
        if(checkView != null){
            checkView.setListView(commonAdapter);
        }
    }

    @Override
    public void onLoadListWait() {
        if(checkView != null){
            checkView.setLoadListWait();
        }
    }

    @Override
    public void onLoadListFail() {
        if(checkView != null){
            checkView.setLoadListFail();
        }
    }

    @Override
    public void onLoadListNoData() {
        if(checkView != null){
            checkView.setLoadListNoData();
        }
    }

    @Override
    public Activity onGetActivity() {
        if(checkView != null){
            return checkView.getActivity();
        }
        return null;
    }

    @Override
    public void onDialogShow() {
        if(checkView != null){
            checkView.setDialogWait();
        }
    }

    @Override
    public void onDialogClose() {
        if(checkView != null){
            checkView.setDialogClose();
        }
    }

    @Override
    public void onFailToast(String msg) {
        if(checkView != null){
            checkView.setToastFail(msg);
        }
    }
}
