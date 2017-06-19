package com.woshiku.hospitalclient.activity.prescriptiondetail.presenter;

/**
 * Created by Administrator on 2017/3/6.
 */
public interface PrescribleDetailPresenter {
    void initPage(String title);
    void setPage(int state);
    void enterWebActivity(String url,String intent);
    void enterAddressActivity();
}
