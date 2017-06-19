package com.woshiku.hospitalclient.activity.userinfo.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import com.woshiku.hospitalclient.activity.userinfo.model.UserInfoModel;
import com.woshiku.hospitalclient.activity.userinfo.model.UserInfoModelImple;
import com.woshiku.hospitalclient.activity.userinfo.view.UserInfoView;
import com.woshiku.urllibrary.domain.CommonUrlData;
import domain.UserInfoData;

/**
 * Created by Administrator on 2017/3/22.
 */
public class UserInfoPresenterImple implements UserInfoPresenter,UserInfoModel.UserInfoListener{
    UserInfoModel userInfoModel;
    UserInfoView userInfoView;

    public UserInfoPresenterImple(UserInfoView userInfoView) {
        this.userInfoView = userInfoView;
        this.userInfoModel = new UserInfoModelImple();
    }

    @Override
    public void loadPage(String title) {
        if(userInfoModel != null){
            userInfoModel.initPage(title,this);
        }
    }

    @Override
    public void submitFamilyInfo() {
        if(userInfoModel != null){
            userInfoModel.submitFamilyInfo(this);
        }
    }

    @Override
    public void dealActivityReturn(int requestCode, int resultCode, Intent data) {
        if(userInfoModel != null){
            userInfoModel.dealActivityReturn(requestCode,resultCode,data,this);
        }
    }

    @Override
    public void dealBroadCast(Context context, String type, Bundle bd) {
        if(userInfoModel != null){
            userInfoModel.dealBroadCast(context,type,bd,this);
        }
    }

    @Override
    public void editPicDialog() {
        if(userInfoModel != null){
            userInfoModel.editPicDialog(this);
        }
    }

    /**
     * 要用实现的接口
     * **/
    @Override
    public void initPage(String title) {
        if(userInfoView != null){
            userInfoView.initPage(title);
        }
    }

    @Override
    public void showWait() {
        if(userInfoView != null){
            userInfoView.showWait();
        }
    }

    @Override
    public void closeWait() {
        if(userInfoView != null){
            userInfoView.closeWait();
        }
    }

    @Override
    public void toastError(String msg) {
        if(userInfoView != null){
            userInfoView.toastError(msg);
        }
    }

    @Override
    public EditText getEditText() {
        if(userInfoView != null){
            return userInfoView.getEditText();
        }
        return null;
    }

    @Override
    public ImageView getUserIcon() {
        if(userInfoView != null){
            return userInfoView.getUserIcon();
        }
        return null;
    }

    @Override
    public UserInfoData getUserInfo() {
        if(userInfoView != null){
            return userInfoView.getUserInfo();
        }
        return null;
    }

    @Override
    public Activity getActivity() {
        if(userInfoView != null){
            return userInfoView.getActivity();
        }
        return null;
    }

    @Override
    public CommonUrlData getFamilyData(String iconPic, boolean isBenRen) {
        if(userInfoView != null){
            return userInfoView.getFamilyData(iconPic,isBenRen);
        }
        return null;
    }

    @Override
    public Context getUserContext() {
        if(userInfoView != null){
            return userInfoView.getUserInfoContext();
        }
        return null;
    }

    @Override
    public void submitSuccess() {
        if(userInfoView != null){
            userInfoView.submitSuccess();
        }
    }
}
