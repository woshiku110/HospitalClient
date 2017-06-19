package com.woshiku.hospitalclient.activity.address.model;

import android.app.Activity;

import domain.AddressData;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface AddressModel {
    void getDataWithDealPage(OnAddressListener onAddressListener);
    void initPage(String title,AddressData addressData,OnAddressListener onAddressListener);
    void dealSaveClick(OnAddressListener onAddressListener);
    void addNewAddress(OnAddressListener onAddressListener);
    void updateAddress(OnAddressListener onAddressListener);
    interface OnAddressListener{
        void onTitleBar(String title);
        Activity onGetActivity();
        String onGetEditName();
        String onGetEditPhone();
        String onGetEditArea();
        String onGetEditAddress();
        void setPageData(AddressData addressData);
        AddressData getPageData();
        void onDialogShow();
        void onDialogClose();
        void onFailToast(String msg);
        void onFinishActivity(boolean isNew,String msg);
    }
}
