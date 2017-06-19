package com.woshiku.hospitalclient.activity.addressmanage.model;

import android.app.Activity;

import com.woshiku.hospitalclient.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public interface AddressManageModel {
    void initPage(String title,OnAddressListener onCheckListener);
    void loadAddressListenerListData(OnAddressListener onCheckListener);
    void updateAddressListData(OnAddressListener onCheckListener);
    void updateMsgDataAddressList(String msg,OnAddressListener onCheckListener);
    interface OnAddressListener{
        void onTitleBar(String title);
        void onActivity();
        void onListView(CommonAdapter commonAdapter);
        void onUpdateListView(List<Object> mList);
        void onLoadListWait();
        void onLoadListFail();
        void onLoadListNoData();
        Activity onGetActivity();
        void onDialogShow();
        void onDialogClose();
        void onFailToast(String msg);
    }
}
