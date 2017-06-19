package com.woshiku.hospitalclient.activity.addressmanage.presenter;

import android.app.Activity;

import com.woshiku.hospitalclient.activity.addressmanage.model.AddressManageModel;
import com.woshiku.hospitalclient.activity.addressmanage.model.AddressManageModelImple;
import com.woshiku.hospitalclient.activity.addressmanage.view.AddressManageView;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public class AddressManagePresenterImple implements AddressManagePresenter,AddressManageModel.OnAddressListener{
    AddressManageView addressManageView;
    AddressManageModel addressManageModel;

    public AddressManagePresenterImple(AddressManageView addressManageView) {
        this.addressManageView = addressManageView;
        this.addressManageModel = new AddressManageModelImple();
    }

    @Override
    public void initPage(String title) {
        addressManageModel.initPage(title,this);
    }

    @Override
    public void loadUrl() {
        addressManageModel.loadAddressListenerListData(this);
    }

    @Override
    public void updateMsgDataAddressList(String msg) {
        addressManageModel.updateMsgDataAddressList(msg,this);
    }

    @Override
    public void onTitleBar(String title) {
        if(addressManageView != null){
            addressManageView.initTitleBar(title);
        }
    }

    @Override
    public void onActivity() {
        if(addressManageView != null){
            addressManageView.getActivity();
        }
    }

    @Override
    public void onListView(CommonAdapter commonAdapter) {
        if(addressManageView != null){
            addressManageView.setListView(commonAdapter);
        }
    }

    @Override
    public void onUpdateListView(List<Object> mList) {
        if(addressManageView != null){
            addressManageView.setUpdateListView(mList);
        }
    }

    @Override
    public void onLoadListWait() {
        if(addressManageView != null){
            addressManageView.setLoadListWait();
        }
    }

    @Override
    public void onLoadListFail() {
        if(addressManageView != null){
            addressManageView.setLoadListFail();
        }
    }

    @Override
    public void onLoadListNoData() {
        if(addressManageView != null){
            addressManageView.setLoadListNoData();
        }
    }

    @Override
    public Activity onGetActivity() {
        if(addressManageView != null){
            return addressManageView.getActivity();
        }
        return null;
    }

    @Override
    public void onDialogShow() {
        if(addressManageView != null){
            addressManageView.setDialogWait();
        }
    }

    @Override
    public void onDialogClose() {
        if(addressManageView != null){
            addressManageView.setDialogClose();
        }
    }

    @Override
    public void onFailToast(String msg) {
        if(addressManageView != null){
            addressManageView.setToastFail(msg);
        }
    }
}
