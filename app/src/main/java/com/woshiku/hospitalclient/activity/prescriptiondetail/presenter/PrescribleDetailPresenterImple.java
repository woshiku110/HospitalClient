package com.woshiku.hospitalclient.activity.prescriptiondetail.presenter;

import android.app.Activity;
import com.woshiku.hospitalclient.activity.prescriptiondetail.model.PrescribleDetailModel;
import com.woshiku.hospitalclient.activity.prescriptiondetail.model.PrescribleDetailModelImple;
import com.woshiku.hospitalclient.activity.prescriptiondetail.view.PrescribleDetailView;

import domain.PrescriptionDetailData;

/**
 * Created by Administrator on 2017/3/6.
 */
public class PrescribleDetailPresenterImple implements PrescribleDetailPresenter,PrescribleDetailModel.OnPrescribleDetailListener{
    PrescribleDetailView prescribleDetailView;
    PrescribleDetailModel prescribleDetailModel;

    public PrescribleDetailPresenterImple(PrescribleDetailView prescribleDetailView) {
        this.prescribleDetailView = prescribleDetailView;
        this.prescribleDetailModel = new PrescribleDetailModelImple();
    }

    @Override
    public void initPage(String title) {
        prescribleDetailModel.initPage(title,this);
    }

    @Override
    public void setPage(int state) {
        prescribleDetailModel.setPage(state,this);
    }

    @Override
    public void enterWebActivity(String url, String intent) {
        prescribleDetailModel.enterWebActivity(url,intent,this);
    }

    @Override
    public void enterAddressActivity() {
        prescribleDetailModel.enterAddressActivity("",this);
    }

    @Override
    public Activity onGetActivity() {
        if(prescribleDetailView != null){
            return prescribleDetailView.getActivity();
        }
        return null;
    }

    @Override
    public void onTitle(String title) {
        if(prescribleDetailView != null){
            prescribleDetailView.initTitleBar(title);
            prescribleDetailView.initActivity();
        }
    }

    @Override
    public void onConfigPage(int state, PrescriptionDetailData data) {
        if(prescribleDetailView != null){
            prescribleDetailView.configPage(state,data);
        }
    }
}
