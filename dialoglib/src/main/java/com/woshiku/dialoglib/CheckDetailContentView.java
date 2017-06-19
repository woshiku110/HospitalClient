package com.woshiku.dialoglib;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.woshiku.dialoglib.domain.CheckDetailContentData;
import com.woshiku.dialoglib.view.MyCheckBox;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 * 用于展示地址和各类检查项目
 */
public class CheckDetailContentView {
    LinearLayout parent;
    Context context;
    List<CheckDetailContentData> checkList;
    TextView payMoneyTextView;
    /*List<MyCheckBox> myCheckBoxList;*/
    public CheckDetailContentView(Context context, LinearLayout parent, String hospital,List<CheckDetailContentData> checkList,boolean isEditMode) {
        this.parent = parent;
        this.context = context;
        this.checkList = checkList;
        int topMargin = (int)context.getResources().getDimension(R.dimen.check_detail_top_view_margin);
        LinearLayout.LayoutParams parentParam = (LinearLayout.LayoutParams)parent.getLayoutParams();
        parentParam.topMargin = topMargin;
        parent.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        parent.removeAllViews();
        LinearLayout containLayout = new LinearLayout(context);
        containLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams containParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        containParam.topMargin = 1;
        containParam.bottomMargin =1;
        containLayout.setBackgroundColor(Color.WHITE);
        containLayout.addView(getAddress(context, hospital));
        containLayout.addView(getCheckItems(context, checkList, isEditMode));
        containLayout.addView(getTotalItem(getAllMoney()));
        containLayout.addView(getBackup());
        containLayout.setLayoutParams(containParam);
        parent.addView(containLayout);
    }
    private LinearLayout getAddress(Context context,String name){
        int outHeight = (int)context.getResources().getDimension(R.dimen.check_detail_view_address_height);
        int margin = (int)context.getResources().getDimension(R.dimen.check_detail_view_margin);
        LinearLayout outLayout = new LinearLayout(context);
        LinearLayout.LayoutParams outParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,outHeight);
        outParam.leftMargin = margin;
        outParam.rightMargin = margin;
        outLayout.setOrientation(LinearLayout.HORIZONTAL);
        outLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        LinearLayout innerLayout = new LinearLayout(context);
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        innerLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams innerParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        innerParam.bottomMargin = 1;
        View roundView = new View(context);
        int roundSize = (int)context.getResources().getDimension(R.dimen.check_detail_view_round);
        LinearLayout.LayoutParams roundParam = new LinearLayout.LayoutParams(roundSize,roundSize);
        roundParam.rightMargin = (int)context.getResources().getDimension(R.dimen.check_detail_view_round_margin);
        roundView.setBackground(context.getResources().getDrawable(R.drawable.check_round_ring));
        innerLayout.addView(roundView, roundParam);
        LinearLayout.LayoutParams hospitalParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView hospitalName = new TextView(context);
        hospitalName.setText(name);
        hospitalName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        hospitalName.setTextColor(context.getResources().getColor(R.color.hospital_txt_color));
        innerLayout.addView(hospitalName, hospitalParam);
        innerLayout.setBackgroundColor(Color.WHITE);
        outLayout.addView(innerLayout, innerParam);
        outLayout.setLayoutParams(outParam);
        return outLayout;
    }
    private LinearLayout getCheckItems(Context context,List<CheckDetailContentData> checkList,boolean isLookMode){
        LinearLayout containLayout = new LinearLayout(context);
        LinearLayout.LayoutParams containParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        containLayout.setOrientation(LinearLayout.VERTICAL);
        int margin = (int)context.getResources().getDimension(R.dimen.check_detail_view_margin);
        containParam.leftMargin = margin;
        containParam.rightMargin = margin;
        containLayout.setLayoutParams(containParam);
        for(int i=0;i<checkList.size();i++){
            containLayout.addView(generItem(checkList.get(i),isLookMode,i));
        }
        return containLayout;
    }

    private LinearLayout generItem(CheckDetailContentData checkDetailContentData,boolean isEditMode, final int pos){
        int height = (int)context.getResources().getDimension(R.dimen.check_detail_view_item_height);
        LinearLayout outLayout = new LinearLayout(context);
        outLayout.setOrientation(LinearLayout.HORIZONTAL);
        outLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        LinearLayout.LayoutParams outParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        outLayout.setLayoutParams(outParam);
        RelativeLayout innerLayout = new RelativeLayout(context);
        innerLayout.setBackgroundColor(Color.WHITE);
        innerLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams innerParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        innerParam.bottomMargin = 1;
        innerLayout.setLayoutParams(innerParam);
        String backUp = "";
        if(!TextUtils.isEmpty(checkDetailContentData.getDesc())){
            backUp = "("+checkDetailContentData.getDesc()+")";
        }
        final MyCheckBox myCheckBox = new MyCheckBox(context,checkDetailContentData.getName()+backUp,isEditMode);
        RelativeLayout.LayoutParams checkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        checkParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        myCheckBox.setLayoutParams(checkParam);
        myCheckBox.getCheckBox().setChecked(true);
        innerLayout.addView(myCheckBox);
        TextView money = new TextView(context);
        money.setText("￥" + changeMoney(Float.parseFloat(checkDetailContentData.getPrice())));
        money.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        money.setTextColor(context.getResources().getColor(R.color.hospital_txt_color));
        RelativeLayout.LayoutParams moneyParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        moneyParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        money.setLayoutParams(moneyParam);
        money.setGravity(Gravity.CENTER_VERTICAL);
        innerLayout.addView(money);
        outLayout.addView(innerLayout);
        myCheckBox.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckDetailContentData contentData = checkList.get(pos);
                contentData.setIsSelected(!contentData.isSelected());
                checkList.set(pos, contentData);
                myCheckBox.getCheckBox().setChecked(contentData.isSelected());
                if(payMoneyTextView != null){
                    payMoneyTextView.setText("￥"+changeMoney(getAllMoney()));
                }
            }
        });
        innerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckDetailContentData contentData = checkList.get(pos);
                contentData.setIsSelected(!contentData.isSelected());
                checkList.set(pos, contentData);
                myCheckBox.getCheckBox().setChecked(contentData.isSelected());
                if(payMoneyTextView != null){
                    payMoneyTextView.setText("￥"+changeMoney(getAllMoney()));
                }
            }
        });
        return outLayout;
    }

    private LinearLayout getTotalItem(float allmoney){
        int height = (int)context.getResources().getDimension(R.dimen.check_detail_view_item_height);
        int margin = (int)context.getResources().getDimension(R.dimen.check_detail_view_margin);
        LinearLayout outLayout = new LinearLayout(context);
        outLayout.setOrientation(LinearLayout.HORIZONTAL);
        outLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        LinearLayout.LayoutParams outParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        outLayout.setLayoutParams(outParam);
        outParam.leftMargin = margin;
        outParam.rightMargin = margin;
        RelativeLayout innerLayout = new RelativeLayout(context);
        innerLayout.setBackgroundColor(Color.WHITE);
        innerLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams innerParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //innerParam.bottomMargin = 1;
        innerLayout.setLayoutParams(innerParam);
        MyCheckBox myCheckBox = new MyCheckBox(context,"合计",false,true);
        RelativeLayout.LayoutParams checkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        checkParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        myCheckBox.setLayoutParams(checkParam);
        innerLayout.addView(myCheckBox);
        TextView money = new TextView(context);
        money.setText("￥" + changeMoney(allmoney));
        money.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        money.setTextColor(context.getResources().getColor(R.color.new_orange_color));
        RelativeLayout.LayoutParams moneyParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        moneyParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        money.setLayoutParams(moneyParam);
        money.setGravity(Gravity.CENTER_VERTICAL);
        payMoneyTextView = money;
        innerLayout.addView(money);
        outLayout.addView(innerLayout);
        return outLayout;
    }

    private LinearLayout getBackup(){
        int height = (int)context.getResources().getDimension(R.dimen.check_detail_view_item_height);
        int margin = (int)context.getResources().getDimension(R.dimen.check_detail_view_margin);
        LinearLayout outLayout = new LinearLayout(context);
        outLayout.setOrientation(LinearLayout.HORIZONTAL);
        outLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        LinearLayout.LayoutParams outParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        outLayout.setLayoutParams(outParam);
        outParam.leftMargin = margin;
        outParam.rightMargin = margin;
        RelativeLayout innerLayout = new RelativeLayout(context);
        innerLayout.setBackgroundColor(Color.WHITE);
        innerLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams innerParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        innerParam.topMargin = 1;
        innerLayout.setLayoutParams(innerParam);
        MyCheckBox myCheckBox = new MyCheckBox(context,"注: " + "*号标注项需空腹检查",false,true);
        RelativeLayout.LayoutParams checkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        checkParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        myCheckBox.setLayoutParams(checkParam);
        innerLayout.addView(myCheckBox);
        outLayout.addView(innerLayout);
        return outLayout;
    }
    private String changeMoney(float money){
        DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        return p;
    }
    private float getAllMoney(){
        float money = 0f;
        for(CheckDetailContentData data:checkList){
            if(data.isSelected()){
                money += Float.parseFloat(data.getPrice());
            }
        }
        return money;
    }
}
