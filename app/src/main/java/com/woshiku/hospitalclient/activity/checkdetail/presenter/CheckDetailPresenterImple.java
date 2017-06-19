package com.woshiku.hospitalclient.activity.checkdetail.presenter;

import android.app.Activity;
import com.woshiku.dialoglib.domain.CheckDetailContentData;
import com.woshiku.hospitalclient.activity.checkdetail.model.CheckDetailModel;
import com.woshiku.hospitalclient.activity.checkdetail.model.CheckDetailModelImple;
import com.woshiku.hospitalclient.activity.checkdetail.view.CheckDetailView;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public class CheckDetailPresenterImple implements CheckDetailPresenter,CheckDetailModel.OnCheckDetailListener{
    CheckDetailView checkDetailView;
    CheckDetailModel checkDetailModel;

    public CheckDetailPresenterImple(CheckDetailView checkDetailView) {
        this.checkDetailView = checkDetailView;
        checkDetailModel = new CheckDetailModelImple();
    }

    @Override
    public void initPage(String title) {
        if(checkDetailView != null){
            checkDetailModel.initPage(title,this);
            checkDetailView.initActivity();
        }
    }

    @Override
    public void setPage(int state) {
        checkDetailModel.setPage(state,this);
    }

    @Override
    public Activity onGetActivity() {
        if(checkDetailView != null){
            return checkDetailView.getActivity();
        }
        return null;
    }

    @Override
    public void onTitle(String title) {
        if(checkDetailView != null){
            checkDetailView.initTitleBar(title);
        }
    }

    @Override
    public void onPage(int state, String[] params, String[] keys, String[] values, List<CheckDetailContentData> checkDetailList) {
        if(checkDetailView != null){
            checkDetailView.configPage(state,params,keys,values,checkDetailList);
        }
    }
}
