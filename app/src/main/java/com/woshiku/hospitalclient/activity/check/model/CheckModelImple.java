package com.woshiku.hospitalclient.activity.check.model;

import android.content.Intent;
import android.os.Bundle;
import com.google.gson.Gson;
import com.woshiku.hospitalclient.adapter.CheckAdapter;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.ArrayList;
import java.util.List;
import common.Global;
import domain.CheckData;
import param.CheckDetailParam;
import param.CheckParam;
import parse.CheckParse;

/**
 * Created by Administrator on 2017/2/25.
 */
public class CheckModelImple implements CheckModel{
    CommonUrl commonUrl;
    @Override
    public void initPage(String title, OnCheckListener onCheckListener) {
        if(onCheckListener != null){
            onCheckListener.onTitleBar(title);
            onCheckListener.onActivity();
            commonUrl = new CommonUrl();
        }
    }

    @Override
    public void loadCheckListData(final OnCheckListener onCheckListener) {
        if(onCheckListener != null){
            new Thread(){
                @Override
                public void run(){
                    super.run();
                    loadUrlData(onCheckListener);
                }
            }.start();
        }
    }

    @Override
    public void loadCheckDetailData(final CheckData checkData, final CheckModel.OnCheckListener onCheckListener) {
        if(onCheckListener != null){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    loadCheckDetailUrlData(checkData,onCheckListener);
                }
            }.start();
        }
    }

    /*检查单列表*/
    private void loadUrlData(OnCheckListener onCheckListener){
        onCheckListener.onLoadListWait();
        Result result = commonUrl.loadUrlAsc(CheckParam.check());
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(), Result.class);
            if(rr.isSuccess()){
                LogUtil.print("check:" + rr.getMsg());
                List<CheckData> checkDataList = CheckParse.check(rr.getMsg());
                if(checkDataList.size()!=0){
                    onCheckListener.onListView(new CheckAdapter(onCheckListener.onGetActivity(),changeListObject(checkDataList)));
                }else{
                    onCheckListener.onLoadListNoData();
                }
            }else{
                onCheckListener.onLoadListFail();
            }
        }else{
            onCheckListener.onLoadListFail();
        }
    }

    /*检查单详情*/
    private void loadCheckDetailUrlData(CheckData checkData,OnCheckListener onCheckListener){
        onCheckListener.onDialogShow();
        Result result = commonUrl.loadUrlAsc(CheckDetailParam.checkDetail(checkData.getCheckId()));
        onCheckListener.onDialogClose();
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(),Result.class);
            LogUtil.print("succ data:"+rr.isSuccess()+rr.getMsg());
            if(rr.isSuccess()){
                Bundle bd = new Bundle();
                bd.putString("data", rr.getMsg());
                bd.putInt("state", Integer.parseInt(checkData.getState()));
                LogUtil.print("check state:"+checkData.getState());
                Intent intent = new Intent("com.checkdetail.CheckDetailActivity");
                intent.putExtras(bd);
                onCheckListener.onGetActivity().startActivityForResult(intent, Global.EnterCheckDetailRequest);
            }else{
                onCheckListener.onFailToast(rr.getMsg());
            }
        }else{
            onCheckListener.onFailToast("由于网络原因加载失败!!!");
        }
    }

    private List<Object> changeListObject(List<CheckData> checkDataList){
        List<Object> list = new ArrayList<>();
        for(CheckData checkData:checkDataList){
            list.add(checkData);
        }
        return list;
    }
}
