package com.woshiku.hospitalclient.activity.userinfo.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import com.woshiku.urllibrary.domain.CommonUrlData;
import domain.UserInfoData;

/**
 * Created by Administrator on 2017/3/21.
 */
public interface UserInfoModel {
    void initPage(String title,UserInfoListener userInfoListener);
    void submitFamilyInfo(UserInfoListener userInfoListener);
    void dealActivityReturn(int requestCode, int resultCode, Intent data,UserInfoListener userInfoListener);
    void dealBroadCast(Context context, String type, Bundle bd,UserInfoListener userInfoListener);
    void editPicDialog(UserInfoListener userInfoListener);
    interface UserInfoListener{
        void initPage(String title);
        void showWait();
        void closeWait();
        void toastError(String msg);
        EditText getEditText();
        ImageView getUserIcon();
        UserInfoData getUserInfo();
        Activity getActivity();
        CommonUrlData getFamilyData(String iconPic, boolean isBenRen);
        Context getUserContext();
        void submitSuccess();
    }
}
