package com.woshiku.hospitalclient.activity.prescription.presenter;

import android.app.Activity;

import com.woshiku.hospitalclient.activity.prescription.model.PrescriptionModel;
import com.woshiku.hospitalclient.activity.prescription.model.PrescriptionModelImple;
import com.woshiku.hospitalclient.activity.prescription.view.PrescriptionView;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import domain.CheckData;

/**
 * Created by Administrator on 2017/3/1.
 */
public class PrescriptionPresenterImple implements PrescriptionPresenter,PrescriptionModel.OnPrescriptionListener{
    PrescriptionView prescriptionView;
    PrescriptionModel prescriptionModel;

    public PrescriptionPresenterImple(PrescriptionView prescriptionView) {
        this.prescriptionView = prescriptionView;
        this.prescriptionModel = new PrescriptionModelImple();
    }

    @Override
    public void initPage(String title) {
        prescriptionModel.initPage(title,this);
    }

    @Override
    public void loadPrescriptionListData() {
        prescriptionModel.loadPrescriptionListData(this);
    }

    @Override
    public void loadPrescriptionDetail(String orderId) {
        prescriptionModel.loadPrescriptionDetail(orderId,this);
    }


    @Override
    public void onTitleBar(String title) {
        if(prescriptionView != null){
            prescriptionView.initTitleBar(title);
        }
    }

    @Override
    public void onActivity() {
        if(prescriptionView != null){
            prescriptionView.initActivity();
        }
    }

    @Override
    public void onListView(CommonAdapter commonAdapter) {
        if(prescriptionView != null){
            prescriptionView.setListView(commonAdapter);
        }
    }

    @Override
    public void onLoadListWait() {
        if(prescriptionView != null){
            prescriptionView.setLoadListWait();
        }
    }

    @Override
    public void onLoadListFail() {
        if(prescriptionView != null){
            prescriptionView.setLoadListFail();
        }
    }

    @Override
    public void onLoadListNoData() {
        if(prescriptionView != null){
            prescriptionView.setLoadListNoData();
        }
    }

    @Override
    public Activity onGetActivity() {
        if(prescriptionView != null){
            return prescriptionView.getActivity();
        }
        return null;
    }

    @Override
    public void onDialogShow() {
        if(prescriptionView != null){
            prescriptionView.setDialogWait();
        }
    }

    @Override
    public void onDialogClose() {
        if(prescriptionView != null){
            prescriptionView.setDialogClose();
        }
    }

    @Override
    public void onFailToast(String msg) {
        if(prescriptionView != null){
            prescriptionView.setToastFail(msg);
        }
    }
}
