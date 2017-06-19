package com.woshiku.hospitalclient.utils;

import domain.NewsRemindData;
import domain.NotifyData;

/**
 * Created by Administrator on 2017/3/20.
 */
public class DealNewsUtils {
    public static String []activityNames = {"com.myfamily.PreorderActivity","com.web.HistoryActivity","com.hold.HoldDiagnsisActivity","com.comment.CommentActivity","com.prescription.PrescriptionActivity","com.check.CheckActivity"};
    public static NewsRemindData getDealNews(NotifyData notifyData){
        String title="暂无标题",content = "暂无数据";
        int enterActivityIndex = -1;
        if(notifyData.categroy.equals("1")){
            if(notifyData.getState().equals("02")){
                title = "回退预约";
                content = "您提交的订单有问题,无法分诊,请重新提交。";
                enterActivityIndex = 0;
            }else if(notifyData.getState().equals("01")){
                title = "历史订单";
                content = "你提交的订单已成功处理";
                enterActivityIndex = 1;
            }else if(notifyData.getState().equals("4")){
                title = "候诊界面详情";
                content = "你提交的订单已被接单,请按时就诊。";
                enterActivityIndex = 2;
            }else if(notifyData.getState().equals("04")){
                title = "历史详情";
                content = "你已过预约时间,点击查看详情。";
                enterActivityIndex = 2;
            }else if(notifyData.getState().equals("03")){
                title = "回退预约";
                content = "你提交的订单已退单,请重新提交。";
                enterActivityIndex = 0;
            } else if(notifyData.getState().equals("6")){
                title = "候诊详情";
                content = "医生叫号进入诊室";
                enterActivityIndex = 2;
            }else if(notifyData.getState().equals("7")){
                title = "评价";
                content = "医生诊断完成,请对诊疗作出评价。";
                enterActivityIndex = 3;
            }
        }else if(notifyData.categroy.equals("2")){
            if(notifyData.getState().equals("4")){
                title = "处方详情";
                content = "医生已处理完您的处方订单,请点击查看详情并缴费。";
                enterActivityIndex = 4;
            }
        }else {
            if(notifyData.getState().equals("3")){
                title = "检查单详情";
                content = "医生为您创建了检查单,请点击查看详情并缴费。";
                enterActivityIndex = 5;
            }
        }
        return new NewsRemindData(notifyData.getOrderId(),title,content,notifyData.getSendTime(),enterActivityIndex);
    }
}
