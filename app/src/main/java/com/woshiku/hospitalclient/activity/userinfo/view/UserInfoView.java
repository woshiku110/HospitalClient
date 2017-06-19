package com.woshiku.hospitalclient.activity.userinfo.view;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import com.woshiku.urllibrary.domain.CommonUrlData;
import de.hdodenhof.circleimageview.CircleImageView;
import domain.UserInfoData;

/**
 * Created by Administrator on 2017/3/21.
 */
public interface UserInfoView {
    void initTitleBar(String title);
    void initActivity();
    void initPage(String title);
    void showWait();
    void closeWait();
    void toastError(String msg);
    void submitSuccess();
    EditText getEditText();
    CircleImageView getUserIcon();
    Activity getActivity();
    UserInfoData getUserInfo();
    CommonUrlData getFamilyData(String iconPic,boolean isBenRen);
    Context getUserInfoContext();
}
