package com.woshiku.hospitalclient.activity.register.model;

import domain.RegisterData;

/**
 * Created by Administrator on 2017/3/13.
 */
public interface RegisterModel {
    void initPage(String title,RegisterListener registerListener);
    void login(RegisterListener registerListener);
    interface RegisterListener{
        RegisterData getInputData();
        void initPage(String title);
        void toastError(String msg);
        void registerSuccess();
        void openDialog();
        void closeDialog();
    }
}
