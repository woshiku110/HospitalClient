package com.woshiku.hospitalclient.activity.addressmanage.model;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.woshiku.hospitalclient.adapter.AddressListAdapter;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.ArrayList;
import java.util.List;

import common.Global;
import domain.AddressData;
import param.AddressListParam;
import param.AddressManageParam;
import parse.AddressListParse;

/**
 * Created by Administrator on 2017/3/6.
 */
public class AddressManageModelImple implements AddressManageModel, AddressListAdapter.AddressListListener {
    CommonUrl commonUrl;
    AddressListAdapter addressListAdapter;
    @Override
    public void initPage(String title, OnAddressListener onCheckListener) {
        if(onCheckListener != null){
            onCheckListener.onTitleBar(title);
            commonUrl = new CommonUrl();
        }
    }

    @Override
    public void loadAddressListenerListData(final OnAddressListener onCheckListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadUrlData(onCheckListener);
            }
        }).start();
    }

    @Override
    public void updateAddressListData(OnAddressListener onCheckListener) {
         updateUrlData(onCheckListener);
    }

    @Override
    public void updateMsgDataAddressList(final String msg, final OnAddressListener onCheckListener) {
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                updateMsgData(msg, onCheckListener);
            }
        });
    }

    /*地址管理列表*/
    private void loadUrlData(OnAddressListener onCheckListener){
        onCheckListener.onLoadListWait();
        Result result = commonUrl.loadUrlAsc(AddressListParam.addressList());
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(), Result.class);
            if(rr.isSuccess()){
                LogUtil.print("address list:" + rr.getMsg());
                List<AddressData> addressDataList = AddressListParse.addressList(rr.getMsg());
                if(addressDataList.size()!=0){
                    addressListAdapter = new AddressListAdapter(onCheckListener.onGetActivity(),changeListObject(addressDataList));
                    addressListAdapter.setAddressListListener(this,onCheckListener);
                    onCheckListener.onListView(addressListAdapter);
                }else{
                    onCheckListener.onLoadListNoData();
                }
            }else{
                onCheckListener.onLoadListFail();
            }
        }else{
            onCheckListener.onLoadListFail();
        }
    }

    private void updateUrlData(OnAddressListener onCheckListener){
        Result result = commonUrl.loadUrlAsc(AddressListParam.addressList());
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(), Result.class);
            if(rr.isSuccess()){
                LogUtil.print("address list:" + rr.getMsg());
                List<AddressData> addressDataList = AddressListParse.addressList(rr.getMsg());
                if(addressDataList.size()!=0){
                    onCheckListener.onUpdateListView(changeListObject(addressDataList));
                }else{
                    onCheckListener.onLoadListNoData();
                }
            }else{
                onCheckListener.onLoadListFail();
            }
        }else{
            onCheckListener.onLoadListFail();
        }
    }
    private void updateMsgData(String msg,OnAddressListener onCheckListener){
        if(!TextUtils.isEmpty(msg)){
            Gson gson = new Gson();
            Result result = gson.fromJson(msg,Result.class);
            if(result.isSuccess()){
                List<AddressData> addressDataList = AddressListParse.addressList(result.getMsg());
                if(addressDataList.size()!=0){
                    onCheckListener.onUpdateListView(changeListObject(addressDataList));
                }
            }
        }
    }
    private List<Object> changeListObject(List<AddressData> checkDataList){
        List<Object> list = new ArrayList<>();
        for(AddressData checkData:checkDataList){
            list.add(checkData);
        }
        return list;
    }

    @Override
    public void addressListEvent(String intent, AddressData addressData,OnAddressListener onAddressListener) {
        if(intent.equals("checkbox")){
            showDialog("提示","是否设为默认地址?",intent,addressData,onAddressListener);
        }else if(intent.equals("editLine")){
            showDialog("提示","是否要编辑地址?",intent,addressData,onAddressListener);
        }else if(intent.equals("deleLine")){
            showDialog("提示","是否要删除地址?",intent,addressData,onAddressListener);
        }
    }

    private void showDialog(String title,String content,final String intent, final AddressData addressData, final OnAddressListener onAddressListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(onAddressListener.onGetActivity());
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(intent.equals("checkbox")){
                    setDefault(onAddressListener,addressData);
                }else if(intent.equals("editLine")){
                    updateData(onAddressListener,addressData);
                }else if(intent.equals("deleLine")){
                    deleAddress(onAddressListener,addressData);
                }
            }
        });
        builder.show();
    }

    private void setDefault(final OnAddressListener onAddressListener,final AddressData addressData){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                onAddressListener.onDialogShow();
                Result result = commonUrl.loadUrlAsc(AddressManageParam.defaultAddress(addressData));
                if (result.isSuccess()) {
                    updateAddressListData(onAddressListener);
                } else {
                    onAddressListener.onFailToast("操作失败,请重新操作!!!");
                }
                LogUtil.print("default:" + result.getMsg() + "data:" + addressData.toString());
                onAddressListener.onDialogClose();
            }
        });
    }

    private void deleAddress(final OnAddressListener onAddressListener,final AddressData addressData){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                onAddressListener.onDialogShow();
                Result result = commonUrl.loadUrlAsc(AddressManageParam.deleAddress(addressData));
                if (result.isSuccess()) {
                    updateAddressListData(onAddressListener);
                } else {
                    onAddressListener.onFailToast("操作失败,请重新操作!!!");
                }
                onAddressListener.onDialogClose();
            }
        });
    }

    private void updateData(OnAddressListener onAddressListener,AddressData addressData){
        Intent intent = new Intent("com.address.AddressActivity");
        Bundle bd = new Bundle();
        bd.putString("intent","updateAddress");
        bd.putString("data",new Gson().toJson(addressData));
        intent.putExtras(bd);
        onAddressListener.onGetActivity().startActivityForResult(intent, Global.EnterEditAddressRequest);
    }
}
