package com.woshiku.hospitalclient.activity.prescription.presenter;

/**
 * Created by Administrator on 2017/3/1.
 */
public interface PrescriptionPresenter {
    void initPage(String title);
    void loadPrescriptionListData();
    void loadPrescriptionDetail(String orderId);
}
