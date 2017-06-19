package com.example.preorderlibrary.utils;

import com.example.preorderlibrary.domain.BodyPartImproveData;
import com.example.preorderlibrary.domain.PersonData;
import com.example.preorderlibrary.domain.UrlResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */
public class ParseDataUtil {

    public static List<PersonData> getPersonDatas(String msg){
        Gson gson = new Gson();
        Type type = new TypeToken<List<String[]>>(){}.getType();
        List<String[]> list = gson.fromJson(msg, type);
        List<PersonData> listData = new ArrayList<>();
        for(String []strs:list){
            listData.add(getPersonData(strs));
        }
        return listData;
    }
    private static PersonData getPersonData(String []strs){
        //String id, String icon, String name, String sex, String birthday, String allergicHistory
        PersonData personData = new PersonData(strs[0],strs[1],strs[2],strs[3],strs[4],strs[5]);
        return personData;
    }
    /**
     * 解析身体所有部分
     * */
    public static List<BodyPartImproveData> parseAllBodyParts(String msg){
        List<BodyPartImproveData> mList = null;
        Gson gson = new Gson();
        UrlResult urlResult = gson.fromJson(msg,UrlResult.class);
        if(urlResult.success){
            mList = parseBodyMsg(urlResult.msg);
        }
        return mList;
    }
    private static List<BodyPartImproveData> parseBodyMsg(String msg){
        List<BodyPartImproveData> bodyList = new ArrayList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String[]>>(){}.getType();
        List<String[]> dataList= gson.fromJson(msg, listType);
        for(String[]strs:dataList){
            bodyList.add(parseSingleBodyMsg(strs));
        }
        return bodyList;
    }
    private static BodyPartImproveData parseSingleBodyMsg(String[] items){
        BodyPartImproveData bodyData = new BodyPartImproveData();
        bodyData.bodyId = items[0];
        bodyData.bodyName = items[1];
        String itemMsg = items[2];
        bodyData.amount = items[3];
        Type type = new TypeToken<List<String[]>>(){}.getType();
        Gson gson = new Gson();
        List<String[]> partList = gson.fromJson(itemMsg,type);
        List<BodyPartImproveData.PartItem> mList = new ArrayList<>();
        for(String[]partStrs:partList){
            BodyPartImproveData.PartItem partItem = new BodyPartImproveData().new PartItem(partStrs[0],partStrs[1]);
            mList.add(partItem);
        }
        bodyData.partItemList = mList;
        return bodyData;
    }
}
