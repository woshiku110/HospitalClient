package com.woshiku.hospitalclient.activity.checkdetail.model;

import android.app.Activity;

import com.woshiku.dialoglib.domain.CheckDetailContentData;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public interface CheckDetailModel {
    void initPage(String title,OnCheckDetailListener onCheckDetailListener);
    void setPage(int state,OnCheckDetailListener onCheckDetailListener);
    interface OnCheckDetailListener{
        Activity onGetActivity();
        void onTitle(String title);
        /**
         * @param params 0订单编号 1医院地址
         * @param keys 用于医院服务台字段（单据打印地点 联系人 电话）
         * @param values 用于医院服务台要显示的数据
         * */
        void onPage(int state,String []params,String []keys,String []values,List<CheckDetailContentData> checkDetailList);
    }
}
