package com.woshiku.hospitalclient.inter;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface ResultCallBack {
    void parseSuccess(Object object);
    void parseFail(String msg);
}
