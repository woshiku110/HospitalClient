package com.woshiku.hospitalclient.activity.prescriptiondetail.model;

import android.content.Intent;
import android.os.Bundle;
import com.woshiku.hospitalclient.utils.LogUtil;

import common.Global;
import domain.PrescriptionDetailData;
import parse.PrescriptionDetailParse;

/**
 * Created by Administrator on 2017/3/6.
 */
public class PrescribleDetailModelImple implements PrescribleDetailModel{

    @Override
    public void initPage(String title, OnPrescribleDetailListener onPrescribleDetailListener) {
        if(onPrescribleDetailListener != null){
            onPrescribleDetailListener.onTitle(title);
            getStateWithDeal(onPrescribleDetailListener);
        }
    }

    @Override
    public void setPage(int state, OnPrescribleDetailListener onPrescribleDetailListener) {
        if(onPrescribleDetailListener != null){
            Bundle bd = onPrescribleDetailListener.onGetActivity().getIntent().getExtras();
            String msg = bd.getString("msg");
            PrescriptionDetailData prescriptionDetailData =  PrescriptionDetailParse.prescriptionDetail(msg);
            onPrescribleDetailListener.onConfigPage(state,prescriptionDetailData);
        }
    }

    @Override
    public void enterWebActivity(String url, String intent, OnPrescribleDetailListener onPrescribleDetailListener) {
        Intent myIntent = new Intent("com.web.QuestionActivity");
        Bundle bd = new Bundle();
        bd.putString("title","订单疑问");
        bd.putString("loadUrl",url);
        bd.putString("intent",intent);
        myIntent.putExtras(bd);
        onPrescribleDetailListener.onGetActivity().startActivity(myIntent);
    }

    @Override
    public void enterAddressActivity(String data, OnPrescribleDetailListener onPrescribleDetailListener) {
        Intent myIntent = new Intent("com.addressmanage.AddressManageActivity");
        Bundle bd = new Bundle();
        bd.putString("data",data);
        myIntent.putExtras(bd);
        onPrescribleDetailListener.onGetActivity().startActivityForResult(myIntent, Global.EnterManageAddressRequest);
    }

    private void getStateWithDeal(OnPrescribleDetailListener onPrescribleDetailListener){
        int state;
        Bundle bd = onPrescribleDetailListener.onGetActivity().getIntent().getExtras();
        String msg = bd.getString("msg");
        PrescriptionDetailData prescriptionDetailData =  PrescriptionDetailParse.prescriptionDetail(msg);
        state = Integer.parseInt(prescriptionDetailData.getOrderState());
        //state = 4;//测试数据
        LogUtil.print("detial model:"+msg);
        LogUtil.print("detail data:",prescriptionDetailData.toString());
        onPrescribleDetailListener.onConfigPage(state,prescriptionDetailData);
    }
}
