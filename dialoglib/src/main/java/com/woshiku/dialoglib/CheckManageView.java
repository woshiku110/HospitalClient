package com.woshiku.dialoglib;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/2/27.
 */
public class CheckManageView extends StateManageView{
    final String []checkState = {"等待缴费","打印检查单","前去检查"};
    final int []checkPics = {R.mipmap.ico_jcd_ddjf,R.mipmap.ico_jcd_dyjc,R.mipmap.ico_jcd_qujc};
    public CheckManageView(Context context, LinearLayout parent, int selectedPos,boolean haveNo,String no) {
        super(context, parent, selectedPos,haveNo,no);
        initDatas(selectedPos);
    }

    @Override
    String[] getTextState() {
        return checkState;
    }

    @Override
    int[] getStatePics() {
        return checkPics;
    }
}
