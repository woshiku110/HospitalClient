package com.woshiku.hospitalclient.activity.prescription.model;

import android.app.Activity;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
/**
 * Created by Administrator on 2017/3/1.
 */
public interface PrescriptionModel {
    void initPage(String title,OnPrescriptionListener onPrescriptionListener);
    void loadPrescriptionListData(OnPrescriptionListener onPrescriptionListener);
    void loadPrescriptionDetail(String orderId,OnPrescriptionListener onPrescriptionListener);
    interface OnPrescriptionListener{
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
