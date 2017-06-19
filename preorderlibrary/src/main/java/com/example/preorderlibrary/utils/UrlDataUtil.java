package com.example.preorderlibrary.utils;

import com.example.preorderlibrary.common.Global;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.url.base.CommonUrl;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by willyou on 2016/11/8.
 */

public class UrlDataUtil {
    //获取登录信息的方法，需要传递的参数有用户名和密码
    public static CommonUrlData getLoginData(String userName, String passWord) {
        String baseUrl = Global.baseUrl;
        String actionUrl = "upms/patient_login";
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", passWord);
        String intent = "login";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }

    //获取家庭成员信息的方法，参数只需要id
    public static CommonUrlData getContactData(String token) {
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_listMyFamily";
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        String intent = "contact_data";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }

    public static CommonUrlData getBodychoosePre(String id, String token) {
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getBodyDirectories";
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", token);
        String intent = "bodychoosePre";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }

    public static CommonUrlData getBodychoose(String id, String token) {
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getBodyDirectories";
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", token);
        String intent = "bodychoose_data";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }
    /**
     * 获得身体所有名称以及所有下级目录
     * */
    public static CommonUrlData getBodyAllParts(String id, String token){
        String baseUrl = Global.baseUrl;
        String actionUrl = "manage/PartManage_partAllList";
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", token);
        String intent = "bodychoose_allparts";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }
    public static CommonUrlData getDiseaseMessage(String partIds, String token) {
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getBobySymptom";
        Map<String, String> map = new HashMap<>();
        map.put("partIds", partIds);
        map.put("token", token);
        String intent = "getDiseaseMessage";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }

    public static CommonUrlData getDiseaseQuestionList(String codes, String token) {
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getQuestionInfoList";
        Map<String, String> map = new HashMap<>();
        map.put("codes", codes);
        map.put("token", token);
        String intent = "getDiseaseQuestion";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }

    public static CommonUrlData orderSubmit(String token,String memberId,String partId,String images,String desc,String dates,String doctorVisible,String qosAns,String diseaIds) {
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentProcess_submitOrder";
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("order.memberId", memberId);
        map.put("order.partId",partId);
        map.put("order.images",images);
        map.put("order.describle",desc);
        map.put("order.qwrq",dates);
        map.put("order.qa",qosAns);
        map.put("order.tpxz",doctorVisible);
        map.put("order.zzid",diseaIds);
        map.put("order.type",Global.pre_type);
        String intent = "orderSubmit";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }
}
