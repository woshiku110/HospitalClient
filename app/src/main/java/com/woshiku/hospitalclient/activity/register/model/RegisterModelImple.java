package com.woshiku.hospitalclient.activity.register.model;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.woshiku.hospitalclient.inter.ResultCallBack;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import domain.RegisterData;
import param.LoginParam;
import param.RegisterParam;
import parse.LoginParse;

/**
 * Created by Administrator on 2017/3/13.
 */
public class RegisterModelImple implements RegisterModel{
    CommonUrl commonUrl;

    @Override
    public void initPage(String title, RegisterListener registerListener) {
        commonUrl = new CommonUrl();
        registerListener.initPage(title);
    }

    @Override
    public void login(final RegisterListener registerListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dealRegisterUrl(registerListener);
            }
        }).start();
    }

    private void dealRegisterUrl(RegisterListener registerListener){
        RegisterData registerData = registerListener.getInputData();
        if(TextUtils.isEmpty(registerData.getPhone())){
            registerListener.toastError("请输入手机号");
            return;
        }
        if(!isMobileNO(registerData.getPhone())){
            registerListener.toastError("手机号输入不正确");
            return;
        }
        if(TextUtils.isEmpty(registerData.getEmail())){
            registerListener.toastError("请输入邮箱号");
            return;
        }
        if(!emailValidation(registerData.getEmail())){
            registerListener.toastError("请输入正确的邮箱格式");
            return;
        }
        if(TextUtils.isEmpty(registerData.getPassword())){
            registerListener.toastError("请输入密码");
            return;
        }else{
            if(!registerData.getPassword().equals(registerData.getRepassword())){
                registerListener.toastError("两次密码不一致");
                return;
            }
        }

        if(commonUrl != null){
            registerListener.openDialog();
            Result result = commonUrl.loadUrlAsc(RegisterParam.register(registerData.getPhone(),registerData.getEmail(),registerData.getPassword()));//String phone, String email,String passWord
            if(result.isSuccess()){
                Result rr = new Gson().fromJson(result.getMsg(),Result.class);
                LogUtil.print("register param"+rr.getMsg());
                if(rr.isSuccess()){
                    registerListener.closeDialog();
                    getUserIdandToken(registerData,registerListener);
                }else{
                    registerListener.toastError(rr.getMsg());
                }
            }else{
                registerListener.toastError("联网失败,请检查一下网络!!!");
            }
            registerListener.closeDialog();
        }else{
            LogUtil.print("common is empty");
        }
    }
    /**
     * 用于拿到用户的ID和token值
     * */
    private void getUserIdandToken(RegisterData registerData,final RegisterListener registerListener){
        CommonUrlData commonUrlData = LoginParam.login(registerData.getPhone(),registerData.getPassword());
        Result result = commonUrl.loadUrlAsc(commonUrlData);
        LoginParse.login(result, new ResultCallBack() {
            @Override
            public void parseSuccess(Object object) {
                registerListener.registerSuccess();
            }

            @Override
            public void parseFail(String msg) {
                registerListener.toastError(msg);
            }
        });
    }
    /**
     * 验证手机格式
     */
    private static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    /**
     * 验证邮箱格式是否正确
     */
    private boolean emailValidation(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }
}
