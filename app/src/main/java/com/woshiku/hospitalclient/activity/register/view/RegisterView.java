package com.woshiku.hospitalclient.activity.register.view;

import domain.RegisterData;

/**
 * Created by Administrator on 2017/3/13.
 */
public interface RegisterView {
    void initPage(String title);
    void initTitleBar(String title);
    void initActivity();
    void setToastError(String msg);
    void setDialogOpen();
    void setDialogClose();
    void registerSuccess();
    RegisterData getInputData();
}
