package com.woshiku.hospitalclient.activity.newsremind.presenter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public interface NewsRemindPresenter {
    void initPage(String title,List<Object> mList);
    void updatePage(List<Object> mList);
    void dealUserClick(Object object);
}
