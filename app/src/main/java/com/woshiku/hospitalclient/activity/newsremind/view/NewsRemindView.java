package com.woshiku.hospitalclient.activity.newsremind.view;

import android.app.Activity;

import com.woshiku.hospitalclient.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public interface NewsRemindView {
    void initPage(String title);
    void initTitleBar(String title);
    void initActivity( );
    void setListView(CommonAdapter commonAdapter);
    void setUpdateListView(List<Object> objectList);
    void setLoadListWait();
    void setLoadListFail();
    void setLoadListNoData();
    void setDialogWait();
    void setDialogClose();
    void setToastFail(String msg);
    Activity getActivity();
}
