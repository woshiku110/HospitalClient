package com.woshiku.hospitalclient.activity.address.view;

import android.app.Activity;

import domain.AddressData;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface AddressView {
    void initPage(String title);
    void initTitleBar(String title);
    void initActivity();
    void setEditArea(String area);
    void setDialogShow();
    void setDialogClose();
    void setFailToast(String msg);
    void fillPageData(AddressData addressData);
    void setFinishActivity(boolean isNew,String msg);
    AddressData getFillPageData();
    String getEditName();
    String getEditPhone();
    String getEditArea();
    String getEditAddress();
    boolean getCheckBoxState();
    Activity getActivity();
}
