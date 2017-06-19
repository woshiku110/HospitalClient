package com.woshiku.hospitalclient.activity.prescriptiondetail.model;

import android.app.Activity;

import domain.PrescriptionDetailData;

/**
 * Created by Administrator on 2017/3/6.
 * 处方详情
 */
public interface PrescribleDetailModel {
    void initPage(String title,OnPrescribleDetailListener onPrescribleDetailListener);
    void setPage(int state,OnPrescribleDetailListener onPrescribleDetailListener);
    void enterWebActivity(String url,String intent,OnPrescribleDetailListener onPrescribleDetailListener);
    void enterAddressActivity(String data,OnPrescribleDetailListener onPrescribleDetailListener);
    interface OnPrescribleDetailListener{
        Activity onGetActivity();
        void onTitle(String title);
        void onConfigPage(int state, PrescriptionDetailData data);
    }
}
