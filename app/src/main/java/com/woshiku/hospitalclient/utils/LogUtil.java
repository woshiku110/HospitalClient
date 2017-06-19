package com.woshiku.hospitalclient.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/12/14.
 */
public class LogUtil {
    static boolean isDebug = true;
    public static void print(String strs){
        if(isDebug){
            Log.e("lookat",strs);
        }
    }
    public static void printError(Exception e){
        if(isDebug){
            Log.e("lookat","",e);
        }
    }
    public static void print(String tag,String strs){
        if(isDebug){
            Log.e(tag,strs);
        }
    }
}
