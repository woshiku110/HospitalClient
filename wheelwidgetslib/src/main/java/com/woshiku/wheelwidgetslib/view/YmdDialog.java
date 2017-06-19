package com.woshiku.wheelwidgetslib.view;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.woshiku.wheelwidgetslib.utils.CommonUtil;
import com.woshiku.wheelwidgetslib.widget.WheelView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 * @desc 年月日对话框
 */
public class YmdDialog extends PopupWindow{
    View view;
    WheelView yearView;
    WheelView monthView;
    WheelView dayView;
    List<String> yearList;
    List<String> monthList;
    List<String> dayList;
    private int defaultYear,defaultMonth,defaultDay;
    private int yearIndex,monthIndex,dayIndex;
    Context context;

    private void initDatas(){
        defaultYear = CommonUtil.getTime()[0];
        defaultMonth = CommonUtil.getTime()[1];
        defaultDay = CommonUtil.getTime()[2];
        yearList = CommonUtil.getYear();
        monthList = CommonUtil.getMonth();
        dayList = CommonUtil.getDay(defaultYear, defaultMonth);
        yearIndex = CommonUtil.getYearIndex(defaultYear);
        monthIndex = CommonUtil.getMonthIndex(defaultMonth);
        dayIndex = CommonUtil.getDayIndex(defaultYear,defaultMonth,defaultDay)-1;
    }

    private void initViews(View view,Context context){

    }
}
