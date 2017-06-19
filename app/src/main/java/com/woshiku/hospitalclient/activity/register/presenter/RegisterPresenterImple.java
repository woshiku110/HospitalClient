package com.woshiku.hospitalclient.activity.register.presenter;

import com.woshiku.hospitalclient.activity.register.model.RegisterModel;
import com.woshiku.hospitalclient.activity.register.model.RegisterModelImple;
import com.woshiku.hospitalclient.activity.register.view.RegisterView;
import domain.RegisterData;

/**
 * Created by Administrator on 2017/3/13.
 */
public class RegisterPresenterImple implements RegisterPresenter,RegisterModel.RegisterListener{
    RegisterModel registerModel;
    RegisterView registerView;

    public RegisterPresenterImple(RegisterView registerView) {
        this.registerView = registerView;
        this.registerModel = new RegisterModelImple();
    }

    @Override
    public void loadPage(String title) {
        registerModel.initPage(title,this);
    }

    @Override
    public void login() {
        registerModel.login(this);
    }

    @Override
    public void initPage(String title) {
        if(registerView != null){
            registerView.initPage(title);
        }
    }

    @Override
    public RegisterData getInputData() {
        if(registerView != null){
            return registerView.getInputData();
        }
        return null;
    }

    @Override
    public void toastError(String msg) {
        if(registerView != null){
            registerView.setToastError(msg);
        }
    }

    @Override
    public void registerSuccess() {
        if(registerView != null){
            registerView.registerSuccess();
        }
    }

    @Override
    public void openDialog() {
        if(registerView != null){
            registerView.setDialogOpen();
        }
    }

    @Override
    public void closeDialog() {
        if(registerView != null){
            registerView.setDialogClose();
        }
    }
}
