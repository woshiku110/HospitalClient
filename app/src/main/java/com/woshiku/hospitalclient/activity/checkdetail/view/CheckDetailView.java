package com.woshiku.hospitalclient.activity.checkdetail.view;

import android.app.Activity;

import com.woshiku.dialoglib.domain.CheckDetailContentData;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public interface CheckDetailView {
    void initTitleBar(String title);
    void initActivity();
    /**
     * @param params 0订单编号 1医院地址
     * @param keys 用于医院服务台字段（单据打印地点 联系人 电话）
     * @param values 用于医院服务台要显示的数据
     * */
    void configPage(int state,String []params,String []keys,String []values,List<CheckDetailContentData> checkDetailList);
    Activity getActivity();
}
