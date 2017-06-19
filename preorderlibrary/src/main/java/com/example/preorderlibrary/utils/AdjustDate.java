package com.example.preorderlibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017-05-26.
 */

public class AdjustDate {
    public static List<String> getDateList(List<String> dateList){
        List<String> mList = new ArrayList<>();
        for(String date:dateList){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                Date dd = sdf.parse(date);
                SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
                mList.add(sdfOne.format(dd));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return mList;
    }
}
