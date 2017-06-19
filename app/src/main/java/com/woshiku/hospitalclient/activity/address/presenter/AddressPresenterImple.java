package com.woshiku.hospitalclient.activity.address.presenter;

import android.app.Activity;

import com.woshiku.hospitalclient.activity.address.model.AddressModel;
import com.woshiku.hospitalclient.activity.address.model.AddressModelImple;
import com.woshiku.hospitalclient.activity.address.view.AddressView;
import com.woshiku.hospitalclient.utils.LogUtil;

import domain.AddressData;

/**
 * Created by Administrator on 2017/3/8.
 */
public class AddressPresenterImple implements AddressPresenter,AddressModel.OnAddressListener{
    AddressView addressView;
    AddressModel addressModel;

    public AddressPresenterImple(AddressView addressView) {
        this.addressView = addressView;
        this.addressModel = new AddressModelImple();
    }

    @Override
    public void getDataWithDealPage() {
        addressModel.getDataWithDealPage(this);
    }

    @Override
    public void dealSaveClick() {
        LogUtil.print("deal save data");
        addressModel.dealSaveClick(this);
    }

    @Override
    public void onTitleBar(String title) {
        if(addressView != null){
            addressView.initTitleBar(title);
        }
    }

    @Override
    public Activity onGetActivity() {
        if(addressView != null){
            return addressView.getActivity();
        }
        return null;
    }

    @Override
    public String onGetEditName() {
        if(addressView != null){
            return addressView.getEditName();
        }
        return null;
    }

    @Override
    public String onGetEditPhone() {
        if(addressView != null){
            return addressView.getEditPhone();
        }
        return null;
    }

    @Override
    public String onGetEditArea() {
        if(addressView != null){
            return addressView.getEditArea();
        }
        return null;
    }

    @Override
    public String onGetEditAddress() {
        if(addressView != null){
            return addressView.getEditAddress();
        }
        return null;
    }

    @Override
    public void setPageData(AddressData addressData) {
        if(addressView != null){
            addressView.fillPageData(addressData);
        }
    }

    @Override
    public AddressData getPageData() {
        if(addressView != null){
            return addressView.getFillPageData();
        }
        return null;
    }

    @Override
    public void onDialogShow() {
        if(addressView != null){
            addressView.setDialogShow();
        }
    }

    @Override
    public void onDialogClose() {
        if(addressView != null){
            addressView.setDialogClose();
        }
    }

    @Override
    public void onFailToast(String msg) {
        if(addressView != null){
            addressView.setFailToast(msg);
        }
    }

    @Override
    public void onFinishActivity(boolean isNew,String msg) {
        if(addressView != null){
            addressView.setFinishActivity(isNew,msg);
        }
    }
}
