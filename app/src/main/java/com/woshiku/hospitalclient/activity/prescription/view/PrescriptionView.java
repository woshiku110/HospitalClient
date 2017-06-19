package com.woshiku.hospitalclient.activity.prescription.view;

import android.app.Activity;

import com.woshiku.hospitalclient.adapter.base.CommonAdapter;

/**
 * Created by Administrator on 2017/3/1.
 */
public interface PrescriptionView {
    void initTitleBar(String title);
    void initActivity();
    void setListView(CommonAdapter commonAdapter);
    void setLoadListWait();
    void setLoadListFail();
    void setLoadListNoData();
    void setDialogWait();
    void setDialogClose();
    void setToastFail(String msg);
    Activity getActivity();
}
