package com.woshiku.hospitalclient.activity.splash.model;

import android.content.Intent;

import com.google.gson.Gson;
import com.woshiku.hospitalclient.service.NotifyService;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.utils.RdUtil;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;

import common.Global;
import domain.LoginData;
import param.LoginParam;

/**
 * Created by Administrator on 2017/3/13.
 */
public class SplashModelImple implements SplashModel{
    boolean isLogined = false;
    CommonUrl commonUrl;
    @Override
    public void initData(SplashModelListener splashListener) {
        commonUrl = new CommonUrl();
        splashListener.initPage();
    }

    @Override
    public void animStartMethod(final SplashModelListener splashListener) {//动画一开始调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                dealCondition(splashListener);
            }
        }).start();
    }

    @Override
    public void animEndMethod(SplashModelListener splashListener) {//动画结束调用
        if(isLogined){//进入主页面
            Intent intent = new Intent("com.woshiku.MainActivity");
            beginService(splashListener);
            splashListener.getActivity().startActivity(intent);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            splashListener.endActivity();
        }else{//进入注册界面
            Intent intent = new Intent("com.woshiku.LoginActivity");
            splashListener.getActivity().startActivity(intent);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            splashListener.endActivity();
        }
    }

    private void dealCondition(SplashModelListener splashListener){
        String value = RdUtil.readData("login");
        if(value.equals("done")){//表示已经登录过了
            String name = RdUtil.readData("username");
            String pass = RdUtil.readData("password");
            Result result = commonUrl.loadUrlAsc(LoginParam.login(name, pass));
            if(result.isSuccess()){
                Gson gson = new Gson();
                try{
                    LoginData loginData = gson.fromJson(result.getMsg(), LoginData.class);
                    if(loginData.success){
                        LoginData.Message msg = gson.fromJson(loginData.msg, LoginData.Message.class);
                        Global.loginMessage = msg;
                        Global._token = Global.loginMessage.token;
                        Global._id = Global.loginMessage.id;
                        LogUtil.print("闪屏页拿到Token" + Global._token);
                        isLogined = true;
                    }else{
                        splashListener.toastError("闪屏页登录接口拿取数据异常!!!");
                        isLogined = false;
                    }
                }catch (Exception e){
                    isLogined = false;
                }
            }else{
                splashListener.toastError("联网失败,请联网后再打开!!!");
            }
        }else{//表示没有登录
            isLogined = false;
        }
    }
    /**
     * 开启服务
     * */
    private void beginService(final SplashModelListener splashListener){
        //splashListener.toastError("调用前台");
        Global.notifyIntent = new Intent(splashListener.getActivity(), NotifyService.class);
        splashListener.getActivity().startService(Global.notifyIntent);
    }
}
