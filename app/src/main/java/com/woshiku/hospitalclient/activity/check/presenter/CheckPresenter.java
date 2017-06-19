package com.woshiku.hospitalclient.activity.check.presenter;

import domain.CheckData;

/**
 * Created by Administrator on 2017/2/25.
 */
public interface CheckPresenter {
    void initPage(String title);
    void loadCheckListData();
    void loadCheckDetailData(CheckData checkData);
}
