package com.woshiku.dialoglib;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/2/27.
 */
public class PrescribleManageView extends StateManageView{
    final String []checkState = {"系统录入","等待缴费","平台发货","发货完成"};
    final int []checkPics = {R.mipmap.ico_cf_xtlr,R.mipmap.ico_jcd_ddjf,R.mipmap.ico_cf_ptfh,R.mipmap.ico_cf_fhwc};
    public PrescribleManageView(Context context, LinearLayout parent, int selectedPos, boolean haveNo, String no) {
        super(context, parent, selectedPos,haveNo,no);
        initDatas(selectedPos);
    }
    public PrescribleManageView(Context context, LinearLayout parent, int selectedPos) {
        super(context, parent, selectedPos,false,"");
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
