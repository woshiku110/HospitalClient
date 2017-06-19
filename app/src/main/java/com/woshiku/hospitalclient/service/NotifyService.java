package com.woshiku.hospitalclient.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;
import com.google.gson.Gson;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.MainActivity;
import com.woshiku.hospitalclient.activity.comment.CommentActivity;
import com.woshiku.hospitalclient.utils.DealNewsUtils;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.utils.NotifyServiceManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.ArrayList;
import java.util.List;
import common.Global;
import domain.CommentData;
import domain.NewsRemindData;
import domain.NotifyData;
import param.CommentInfoParam;
import param.NotifyListParam;
import parse.CommentInfoParse;
import parse.NotifyListParse;
/**
 * Created by Administrator on 2017/3/17.
 */
public class NotifyService extends Service{
    private static final int notify_id = 1;
    private static boolean isRunning = false;
    private static final int timeScan = 5*1000;
    CommonUrl commonUrl;
    @Override
    public void onCreate() {
        super.onCreate();
        Notification notify = initNotify();
        startForeground(notify_id, notify);//前台service
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.print("notify service restart");
        carryNotifyService();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification initNotify(){
        Notification notification;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("欢迎使用金康晟");//Ticker是状态栏显示的提示
        builder.setContentTitle("金康晟");//通常作为通知栏标题
        builder.setContentText("金康晟陪伴您到永远...");//第二行内容 通常是通知正文
        builder.setSmallIcon(R.mipmap.ico_jks);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ico_jks));
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Intent appIntent = new Intent(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.setComponent(new ComponentName(this, MainActivity.class));
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//关键的一步，设置启动模式
        PendingIntent contentIntent =PendingIntent.getActivity(this,
                0,appIntent,0);
        builder.setContentIntent(contentIntent);
        notification = builder.build();
        return notification;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.print("end service");
        isRunning = false;
        //Toast.makeText(this, "结束前台", Toast.LENGTH_SHORT).show();
        stopForeground(true);
    }
    private void carryNotifyService(){
        if(commonUrl == null){
            commonUrl = new CommonUrl();
        }
        if(isRunning){
            runNotify();
        }else{
            isRunning = true;
            runNotify();
        }
    }
    private void runNotify(){
        NotifyServiceManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                while(isRunning){
                    if(!isRunning){
                        break;
                    }
                    try {
                        Thread.sleep(timeScan);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!isRunning){
                        break;
                    }
                    Result unReadResult = commonUrl.loadUrlAsc(NotifyListParam.notifyUnreadList());//未读结果
                    if(unReadResult.isSuccess()){
                        LogUtil.print("未读消息"+unReadResult.getMsg());
                        Result rr = new Gson().fromJson(unReadResult.getMsg(),Result.class);
                        if(rr.isSuccess()){
                            List<NotifyData> mList = NotifyListParse.notifyList(rr.getMsg());
                            if(mList.size()>0){//有新通知
                                Global.unreadNewsAmount = mList.size();
                                List<NewsRemindData> newsRemindDataList = changeData(mList);
                                LogUtil.print("未读消息数量:"+mList.size());
                                Global.newsRemindUnreadList = newsRemindDataList;
                                findAndOpenComment(newsRemindDataList);
                                sendNotify(true);
                            }else{//没有新通知
                                sendNotify(false);
                            }
                        }
                    }

                    Result readResult = commonUrl.loadUrlAsc(NotifyListParam.notifyList());
                    if(readResult.isSuccess()){
                        LogUtil.print("已读消息"+readResult.getMsg());
                        Result rr = new Gson().fromJson(readResult.getMsg(),Result.class);
                        LogUtil.print("haveData","msg:"+rr.getMsg());
                        if(rr.isSuccess()){
                            List<NotifyData> mList = NotifyListParse.notifyList(rr.getMsg());
                            LogUtil.print("已读消息数量:"+mList.size());
                            if(mList.size()>0){//历史消息有数据
                                List<NewsRemindData> newsRemindDataList = changeData(mList);
                                Global.newsRemindReadedList = newsRemindDataList;
                            }
                        }
                    }
                }
            }
        });
    }
    private void openComment(String orderId){//打开评论
        Result result = commonUrl.loadUrlAsc(CommentInfoParam.commentInfo(orderId));
        if(result.isSuccess()){
            try{
                Result rr = new Gson().fromJson(result.getMsg(),Result.class);
                if(rr.isSuccess()){
                    LogUtil.print(rr.getMsg());
                    Intent commentIntent = new Intent(this, CommentActivity.class);
                    commentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bd = new Bundle();
                    bd.putString("data",rr.getMsg());
                    commentIntent.putExtras(bd);
                    startActivity(commentIntent);
                    CommentData commentData = CommentInfoParse.commentInfo(rr.getMsg());
                    LogUtil.print(commentData.toString());
                }else{
                    LogUtil.print("获取评论失败");
                }
            }catch(Exception e){
                LogUtil.print("打开评论失败");
            }
        }
    }

    private List<NewsRemindData> changeData(List<NotifyData> notifyDataList){
        List<NewsRemindData> newsRemindDataList = new ArrayList<>();
        for(NotifyData notifyData:notifyDataList){
            newsRemindDataList.add(DealNewsUtils.getDealNews(notifyData));
        }
        return newsRemindDataList;
    }
    private void findAndOpenComment(List<NewsRemindData> newsRemindDataList){
        for(NewsRemindData newsRemindData:newsRemindDataList){
            if(newsRemindData.getEnterActivityIndex() == 3){//评论
                openComment(newsRemindData.getOrderId());
            }
        }
    }

    private void sendNotify(boolean haveNotify){
        Intent intent = new Intent();
        intent.setAction(Global.remindAction);
        Bundle bd = new Bundle();
        bd.putBoolean("haveData",haveNotify);
        intent.putExtras(bd);
        sendBroadcast(intent);
    }
}
