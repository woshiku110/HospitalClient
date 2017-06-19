package com.woshiku.hospitalclient.activity.address.model;

import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.woshiku.hospitalclient.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import domain.AddressData;
import param.AddressListParam;
import param.AddressManageParam;
/**
 * Created by Administrator on 2017/3/8.
 */
public class AddressModelImple implements AddressModel{
    String addressId;
    CommonUrl commonUrl;
    boolean isAddAddr = true;
    @Override
    public void getDataWithDealPage(OnAddressListener onAddressListener) {
        commonUrl = new CommonUrl();
        Bundle bd = onAddressListener.onGetActivity().getIntent().getExtras();
        String intent = bd.getString("intent");
        if(intent.equals("newAddress")){
            initPage("添加新地址",null,onAddressListener);
        }else if(intent.equals("updateAddress")){
            isAddAddr = false;
            String data = bd.getString("data");
            AddressData addressData = new Gson().fromJson(data,AddressData.class);
            addressId = addressData.getId();
            initPage("修改地址",addressData,onAddressListener);
        }
    }

    @Override
    public void initPage(String title,AddressData addressData, OnAddressListener onAddressListener) {
        onAddressListener.onTitleBar(title);
        if(addressData != null){
            addressId = addressData.getId();
            onAddressListener.setPageData(addressData);
        }else{

        }
    }

    @Override
    public void dealSaveClick(OnAddressListener onAddressListener) {
        if(isAddAddr){
            addNewAddress(onAddressListener);
        }else{
            updateAddress(onAddressListener);
        }
    }

    @Override
    public void addNewAddress(final OnAddressListener onAddressListener) {
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                AddressData addressData = onAddressListener.getPageData();
                if(!judgeCondition(addressData,onAddressListener)){
                    dealNewAddressUrl(addressData, onAddressListener);
                }
            }
        });
    }

    @Override
    public void updateAddress(final OnAddressListener onAddressListener) {
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                AddressData addressData = onAddressListener.getPageData();
                addressData.setId(addressId);
                if(!judgeCondition(addressData,onAddressListener)) {
                    dealUpdateAddressUrl(addressData, onAddressListener);
                }
            }
        });
    }
    private boolean judgeCondition(AddressData addressData,OnAddressListener onAddressListener){
        if(TextUtils.isEmpty(addressData.getName())){
            onAddressListener.onFailToast("姓名不能为空!!!");
            return true;
        }
        if(TextUtils.isEmpty(addressData.getPhone())){
            onAddressListener.onFailToast("手机号不能为空!!!");
            return true;
        }
        if(TextUtils.isEmpty(addressData.getArea())||addressData.getArea().equals("请填写")){
            onAddressListener.onFailToast("地区不能为空!!!");
            return true;
        }
        if(TextUtils.isEmpty(addressData.getDetailAddress())){
            onAddressListener.onFailToast("详细地址不能为空!!!");
            return true;
        }
        return false;
    }
    private void dealNewAddressUrl(AddressData addressData,OnAddressListener onAddressListener){
        onAddressListener.onDialogShow();
        Result result =  commonUrl.loadUrlAsc(AddressManageParam.addAddress(addressData));
        if(result.isSuccess()){
            Result rr = commonUrl.loadUrlAsc(AddressListParam.addressList());
            if(rr.isSuccess()){
                //有返回的数据
                onAddressListener.onDialogClose();
                onAddressListener.onFinishActivity(true,rr.getMsg());
            }else{
                onAddressListener.onDialogClose();
                onAddressListener.onFinishActivity(true,"");
            }
        }else {
            onAddressListener.onFailToast("由于网络原因,加载失败!");
            onAddressListener.onDialogClose();
        }
    }

    private void dealUpdateAddressUrl(AddressData addressData,OnAddressListener onAddressListener){
        onAddressListener.onDialogShow();
        Result result =  commonUrl.loadUrlAsc(AddressManageParam.updateAddress(addressData));
        if(result.isSuccess()){
            Result rr = commonUrl.loadUrlAsc(AddressListParam.addressList());
            if(rr.isSuccess()){
                //有返回的数据
                onAddressListener.onDialogClose();
                onAddressListener.onFinishActivity(false,rr.getMsg());
            }else{
                onAddressListener.onDialogClose();
                onAddressListener.onFinishActivity(false,"");
            }
        }else {
            onAddressListener.onFailToast("由于网络原因,加载失败!");
            onAddressListener.onDialogClose();
        }
    }
}
