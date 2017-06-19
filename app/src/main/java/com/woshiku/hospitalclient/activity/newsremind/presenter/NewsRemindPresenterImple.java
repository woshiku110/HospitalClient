package com.woshiku.hospitalclient.activity.newsremind.presenter;

import android.app.Activity;

import com.woshiku.hospitalclient.activity.newsremind.model.NewsRemindModel;
import com.woshiku.hospitalclient.activity.newsremind.model.NewsRemindModelImple;
import com.woshiku.hospitalclient.activity.newsremind.view.NewsRemindView;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class NewsRemindPresenterImple implements NewsRemindPresenter,NewsRemindModel.NewsRemindListener{
    NewsRemindView newsRemindView;
    NewsRemindModel newsRemindModel;

    public NewsRemindPresenterImple(NewsRemindView newsRemindView) {
        this.newsRemindView = newsRemindView;
       this.newsRemindModel = new NewsRemindModelImple();
    }

    @Override
    public void initPage(String title,List<Object> mList) {
        if(newsRemindModel != null){
            newsRemindModel.initPage(title,mList,this);
        }
    }

    @Override
    public void updatePage(List<Object> mList) {
        if(newsRemindModel != null){
            newsRemindModel.updatePage(mList,this);
        }
    }

    @Override
    public void dealUserClick(Object object) {
        if(newsRemindModel != null){
            newsRemindModel.dealUserClick(object);
        }
    }

    @Override
    public void initPage(String title) {
        if(newsRemindView != null){
            newsRemindView.initPage(title);
        }
    }

    @Override
    public void setListData(CommonAdapter commonAdapter) {
        if(newsRemindView != null){
            newsRemindView.setListView(commonAdapter);
        }
    }

    @Override
    public void updateListData(List<Object> mList) {
        if(newsRemindView != null){
            newsRemindView.setUpdateListView(mList);
        }
    }

    @Override
    public void setNoData() {
        if(newsRemindView != null){
            newsRemindView.setLoadListNoData();
        }
    }

    @Override
    public Activity getActivity() {
        if(newsRemindView != null){
            return newsRemindView.getActivity();
        }
        return null;
    }
}
