package com.woshiku.hospitalclient.activity.prescription.model;

import android.content.Intent;
import android.os.Bundle;
import com.google.gson.Gson;
import com.woshiku.hospitalclient.adapter.PrescriptionAdapter;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.ArrayList;
import java.util.List;
import domain.PrescriptionData;
import param.PrescriptionDetailParam;
import param.PrescriptionParam;
import parse.PrescriptionParse;

/**
 * Created by Administrator on 2017/3/1.
 */
public class PrescriptionModelImple implements PrescriptionModel{
    CommonUrl commonUrl;
    @Override
    public void initPage(String title, OnPrescriptionListener onPrescriptionListener) {
        if(onPrescriptionListener != null){
            onPrescriptionListener.onTitleBar(title);
            onPrescriptionListener.onActivity();
            commonUrl = new CommonUrl();
            loadPrescriptionListData(onPrescriptionListener);
        }
    }

    /*处方列表*/
    private void loadUrlData(OnPrescriptionListener onPrescriptionListener){
        onPrescriptionListener.onLoadListWait();
        Result result = commonUrl.loadUrlAsc(PrescriptionParam.prescription());
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(), Result.class);
            if(rr.isSuccess()){
                List<PrescriptionData> list = PrescriptionParse.prescription(rr.getMsg());
                if(list.size()==0){
                    onPrescriptionListener.onLoadListNoData();
                }else{
                    onPrescriptionListener.onListView(new PrescriptionAdapter(onPrescriptionListener.onGetActivity(),changeListObject(list)));
                }
            }else{
                onPrescriptionListener.onLoadListFail();
            }
        }else{
            onPrescriptionListener.onLoadListFail();
        }
    }

    @Override
    public void loadPrescriptionListData(final OnPrescriptionListener onPrescriptionListener) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                loadUrlData(onPrescriptionListener);
            }
        }.start();
    }

    @Override
    public void loadPrescriptionDetail(final String orderId, final OnPrescriptionListener onPrescriptionListener) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                onPrescriptionListener.onDialogShow();
                Result result = commonUrl.loadUrlAsc(PrescriptionDetailParam.prescriptionDetail(orderId));
                if(result.isSuccess()){
                    Result rr = new Gson().fromJson(result.getMsg(),Result.class);
                    if(rr.isSuccess()){
                        Intent intent = new Intent("com.prescriptiondetail.PrescribleDetailActivity");
                        Bundle bd = new Bundle();
                        bd.putString("msg", rr.getMsg());
                        intent.putExtras(bd);
                        onPrescriptionListener.onGetActivity().startActivity(intent);
                    }else{
                        onPrescriptionListener.onFailToast("加载失败,请重新打开!!!");
                    }
                }else{
                    onPrescriptionListener.onFailToast("网络原因加载失败!!!");
                }
                onPrescriptionListener.onDialogClose();
            }
        }.start();
    }

    private List<Object> changeListObject(List<PrescriptionData> checkDataList){
        List<Object> list = new ArrayList<>();
        for(PrescriptionData checkData:checkDataList){
            list.add(checkData);
        }
        return list;
    }
}
