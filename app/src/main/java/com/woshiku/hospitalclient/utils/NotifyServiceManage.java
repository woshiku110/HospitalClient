package com.woshiku.hospitalclient.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/3.
 */
public class NotifyServiceManage {
    private static int maxThreadAmount = 1;
    private static ExecutorService executorService;
    private static NotifyServiceManage threadManage;
    private NotifyServiceManage() {

    }

    public static NotifyServiceManage getInstance(){
        if(threadManage == null){
            threadManage = new NotifyServiceManage();
            init(maxThreadAmount);
        }
        return threadManage;
    }

    private static void init(int amount){
        maxThreadAmount = amount;
        executorService = Executors.newFixedThreadPool(amount);
    }

    public void setThreadAmount(int amount){
        NotifyServiceManage.executorService = Executors.newFixedThreadPool(amount);
    }
    public void carry(Runnable runnable){
        executorService.execute(runnable);
    }
}
