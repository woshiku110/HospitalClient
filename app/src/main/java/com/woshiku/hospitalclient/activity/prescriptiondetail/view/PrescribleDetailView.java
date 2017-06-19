package com.woshiku.hospitalclient.activity.prescriptiondetail.view;

import android.app.Activity;

import domain.PrescriptionDetailData;

/**
 * Created by Administrator on 2017/3/3.
 */
public interface PrescribleDetailView {
    void initTitleBar(String title);
    void initActivity();
    Activity getActivity();
    void configPage(int state, PrescriptionDetailData data);
}
