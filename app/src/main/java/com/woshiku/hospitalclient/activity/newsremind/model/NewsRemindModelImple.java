package com.woshiku.hospitalclient.activity.newsremind.model;

import com.woshiku.hospitalclient.adapter.NewsRemindAdapter;
import java.util.ArrayList;
import java.util.List;
import domain.NewsRemindData;

/**
 * Created by Administrator on 2017/3/20.
 */
public class NewsRemindModelImple implements NewsRemindModel {
    NewsRemindAdapter newsRemindAdapter;
    @Override
    public void initPage(String title, List<Object> dataList,NewsRemindListener newsRemindListener) {
        if(newsRemindListener != null){
            newsRemindListener.initPage(title);
            if(dataList != null){
                if(dataList.size() == 0){
                    newsRemindListener.setNoData();
                }else{
                    newsRemindAdapter = new NewsRemindAdapter(newsRemindListener.getActivity(),dataList);
                    newsRemindListener.setListData(newsRemindAdapter);
                }
            }else{
                newsRemindListener.setNoData();
            }
        }
    }

    @Override
    public void updatePage(List<Object> dataList,NewsRemindListener newsRemindListener) {
        if(dataList != null){
            if(dataList.size() == 0){
                newsRemindListener.setNoData();
            }else{
                if(newsRemindAdapter == null){
                    newsRemindAdapter = new NewsRemindAdapter(newsRemindListener.getActivity(),dataList);
                    newsRemindListener.setListData(newsRemindAdapter);
                }else{
                    newsRemindListener.updateListData(dataList);
                }
            }
        }else{
            newsRemindListener.setNoData();
        }
    }

    @Override
    public void dealUserClick(Object object) {

    }

}
