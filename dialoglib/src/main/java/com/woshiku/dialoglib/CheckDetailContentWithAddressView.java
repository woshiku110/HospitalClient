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
public class CheckDetailContentWithAddressView {
    LinearLayout parent;
    Context context;
    List<CheckDetailContentData> checkList;
    TextView payMoneyTextView;
    /*List<MyCheckBox> myCheckBoxList;*/
    public CheckDetailContentWithAddressView(Context context, LinearLayout parent, String hospital, List<CheckDetailContentData> checkList, boolean isEditMode,String[] keys,String[] vaules) {
        this.parent = parent;
        this.context = context;
        this.checkList = checkList;
        int topMargin = (int)context.getResources().getDimension(R.dimen.check_detail_top_view_margin);
        parent.removeAllViews();
        parent.setBackgroundColor(context.getResources().getColor(R.color.check_detail_bg));
        LinearLayout outsideLayout = new LinearLayout(context);
        outsideLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams outsideParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        outsideParam.topMargin = topMargin;
        outsideLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        outsideLayout.setLayoutParams(outsideParam);
        LinearLayout containLayout = new LinearLayout(context);
        containLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams containParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        containParam.topMargin = 1;
        containParam.bottomMargin =1;
        containLayout.setBackgroundColor(Color.WHITE);
        containLayout.addView(getAddress(context, hospital));
        containLayout.addView(getCheckItems(context, checkList, isEditMode));
        containLayout.addView(getTotalItem(getAllMoney()));
        containLayout.addView(getMarkLayout());
        containLayout.setLayoutParams(containParam);
        outsideLayout.addView(containLayout);
        parent.addView(outsideLayout);
        parent.addView(getService(keys,vaules));
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
        outLayout.addView(innerLayout,innerParam);
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
        myCheckBox.getTextView().setTextColor(context.getResources().getColor(R.color.check_detail_txt_gray_state));
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
        /*myCheckBox.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckDetailContentData contentData = checkList.get(pos);
                contentData.setIsSelected(!contentData.isSelected());
                checkList.set(pos, contentData);
                myCheckBox.getCheckBox().setChecked(contentData.isSelected());
                if (payMoneyTextView != null) {
                    payMoneyTextView.setText("￥" + changeMoney(getAllMoney()));
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
                if (payMoneyTextView != null) {
                    payMoneyTextView.setText("￥" + changeMoney(getAllMoney()));
                }
            }
        });*/
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
        MyCheckBox myCheckBox = new MyCheckBox(context,"合计：",false);
        RelativeLayout.LayoutParams checkParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        checkParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        myCheckBox.setLayoutParams(checkParam);
        myCheckBox.getTextView().setTextColor(context.getResources().getColor(R.color.new_orange_color));
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
    private LinearLayout getMarkLayout(){
        int height = (int)context.getResources().getDimension(R.dimen.check_detail_view_item_height);
        int margin = (int)context.getResources().getDimension(R.dimen.check_detail_view_margin);
        LinearLayout outLayout = new LinearLayout(context);
        outLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams outParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        outParam.leftMargin = margin;
        outParam.rightMargin = margin;
        outLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        outLayout.setLayoutParams(outParam);
        LinearLayout markLayout = new LinearLayout(context);
        markLayout.setOrientation(LinearLayout.HORIZONTAL);
        markLayout.setBackgroundColor(Color.WHITE);
        markLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams markParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        markParam.topMargin = 1;
        markLayout.setLayoutParams(markParam);
        TextView desc = new TextView(context);
        desc.setText("注: " + "*号标注项需空腹检查");
        desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        desc.setTextColor(context.getResources().getColor(R.color.new_orange_color));
        desc.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams descParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        markLayout.addView(desc,descParam);
        outLayout.addView(markLayout);
        return outLayout;
    }
    private LinearLayout[] generOutInner(){
        LinearLayout lines[] = new LinearLayout[2];
        LinearLayout outLayout = new LinearLayout(context);
        outLayout.setOrientation(LinearLayout.VERTICAL);
        outLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        LinearLayout.LayoutParams outParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        outParam.topMargin = (int)context.getResources().getDimension(R.dimen.check_detail_top_view_margin);
        outLayout.setLayoutParams(outParam);
        LinearLayout innerLayout = new LinearLayout(context);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams innerParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        innerParam.topMargin = 1;
        innerParam.bottomMargin = 1;
        innerLayout.setLayoutParams(innerParam);
        outLayout.addView(innerLayout);
        lines[0] = outLayout;
        lines[1] = innerLayout;
        return lines;
    }
    private LinearLayout getService(String[] keys,String[] values){
        int height = (int)context.getResources().getDimension(R.dimen.check_detail_view_item_height);
        int margin = (int)context.getResources().getDimension(R.dimen.check_detail_view_margin);
        LinearLayout []lines = generOutInner();
        LinearLayout topInnerLayout = lines[1];
        LinearLayout outLayout = new LinearLayout(context);
        outLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        LinearLayout.LayoutParams outParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        outParam.leftMargin = margin;
        outParam.rightMargin = margin;
        outLayout.setLayoutParams(outParam);
        LinearLayout innerLayout = new LinearLayout(context);
        LinearLayout.LayoutParams innerParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        innerLayout.setBackgroundColor(Color.WHITE);
        innerLayout.setLayoutParams(innerParam);
        innerLayout.setGravity(Gravity.CENTER_VERTICAL);
        View roundView = new View(context);
        int roundSize = (int)context.getResources().getDimension(R.dimen.check_detail_view_round);
        LinearLayout.LayoutParams roundParam = new LinearLayout.LayoutParams(roundSize,roundSize);
        roundParam.rightMargin = (int)context.getResources().getDimension(R.dimen.check_detail_view_round_margin);
        roundView.setBackground(context.getResources().getDrawable(R.drawable.check_round_blue_ring));
        innerLayout.addView(roundView, roundParam);
        LinearLayout.LayoutParams hospitalParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView hospitalName = new TextView(context);
        hospitalName.setText("医院服务台");
        hospitalName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        hospitalName.setTextColor(context.getResources().getColor(R.color.hospital_txt_color));
        innerLayout.addView(hospitalName, hospitalParam);
        outLayout.addView(innerLayout);
        topInnerLayout.addView(outLayout);
        for(int i=0;i<keys.length;i++){
            topInnerLayout.addView(getServieItem(keys[i], values[i]));
        }
        return lines[0];
    }
    private LinearLayout getServieItem(String key,String value){
        int height = (int)context.getResources().getDimension(R.dimen.check_detail_view_item_height);
        int margin = (int)context.getResources().getDimension(R.dimen.check_detail_view_margin);
        LinearLayout outLayout = new LinearLayout(context);
        outLayout.setBackgroundColor(context.getResources().getColor(R.color.check_detail_line_bg));
        LinearLayout.LayoutParams outParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        outParam.leftMargin = margin;
        outParam.rightMargin = margin;
        LinearLayout innerLayout = new LinearLayout(context);
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams innerParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        innerParam.topMargin = 1;
        innerLayout.setBackgroundColor(Color.WHITE);
        innerLayout.setGravity(Gravity.CENTER_VERTICAL);
        innerLayout.setLayoutParams(innerParam);
        TextView keyText = new TextView(context);
        keyText.setText(key);
        keyText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        keyText.setTextColor(context.getResources().getColor(R.color.hospital_txt_color));
        TextView valueText = new TextView(context);
        valueText.setText(value);
        valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        valueText.setTextColor(context.getResources().getColor(R.color.hospital_txt_color));
        keyText.setGravity(Gravity.CENTER_VERTICAL);
        valueText.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        innerLayout.addView(keyText,textParam);
        innerLayout.addView(valueText,textParam);
        outLayout.setLayoutParams(outParam);
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
