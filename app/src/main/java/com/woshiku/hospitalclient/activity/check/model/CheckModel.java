package com.woshiku.hospitalclient.activity.check.model;

import android.app.Activity;

import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import domain.CheckData;

/**
 * Created by Administrator on 2017/2/25.
 */
public interface CheckModel {
    void initPage(String title,OnCheckListener onCheckListener);
    void loadCheckListData(OnCheckListener onCheckListener);
    void loadCheckDetailData(CheckData checkData,OnCheckListener onCheckListener);
    interface OnCheckListener{
        void onTitleBar(String title);
        void onActivity();
        void onListView(CommonAdapter commonAdapter);
        void onLoadListWait();
        void onLoadListFail();
        void onLoadListNoData();
        Activity onGetActivity();
        void onDialogShow();
        void onDialogClose();
        void onFailToast(String msg);
    }
}
