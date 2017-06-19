package com.woshiku.hospitalclient.activity.newsremind.model;

import android.app.Activity;

import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public interface NewsRemindModel {
    void initPage(String title,List<Object> dataList,NewsRemindListener newsRemindListener );
    void updatePage(List<Object> dataList,NewsRemindListener newsRemindListener);
    void dealUserClick(Object object);
    interface NewsRemindListener{
        void initPage(String title);
        void setListData(CommonAdapter commonAdapter);
        void updateListData(List<Object> mList);
        void setNoData();
        Activity getActivity();
    }
}
