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
 * Created by Administrator on 2017/3/6.
 */
public class PrescribleWaitPayView {
    View view;
    Context context;
    TextView payMoneyTextView,qosText;
    List<CheckDetailContentData> checkList;
    LinearLayout parent,containLayout,parentLine,childLine;
    RelativeLayout receRelateLayout;
    TextView recePersonView,receAddrView,recePhoneView;
    boolean isVisble,canAddress = false;
    String keys[],values[];

    public PrescribleWaitPayView(LinearLayout parent, Context context,String hospital,List<CheckDetailContentData> mList,boolean isVisble,String []keys,String []values) {
        this.parent = parent;
        this.context = context;
        this.checkList = mList;
        this.isVisble = isVisble;
        this.keys = keys;
        this.values = values;
        initViews(hospital, checkList);
    }
    public PrescribleWaitPayView(LinearLayout parent, Context context,String hospital,List<CheckDetailContentData> mList,boolean isVisble,String []keys,String []values,boolean canAddress) {
        this.parent = parent;
        this.context = context;
        this.checkList = mList;
        this.isVisble = isVisble;
        this.canAddress = canAddress;
        this.keys = keys;
        this.values = values;
        initViews(hospital, checkList);
    }
    private void initViews(String hospital,List<CheckDetailContentData> checkList){
        parent.removeAllViews();
        LinearLayout.LayoutParams viewParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view = View.inflate(context,R.layout.item_prescrible_wait_pay,null);
        recePersonView = (TextView)view.findViewById(R.id.item_rece_people);
        receAddrView = (TextView)view.findViewById(R.id.item_rece_address);
        recePhoneView = (TextView)view.findViewById(R.id.item_rece_phone);
        qosText = (TextView)view.findViewById(R.id.item_prescrible_wait_pay_qos_txt);
        containLayout = (LinearLayout)view.findViewById(R.id.item_prescrible_wait_pay_contain);
        parentLine = (LinearLayout)view.findViewById(R.id.item_prescrible_parent_line);
        childLine = (LinearLayout)view.findViewById(R.id.item_prescrible_child_line);
        receRelateLayout = (RelativeLayout)view.findViewById(R.id.item_rece_line_layout);
        containLayout.addView(getHospitalLayout(hospital,checkList,false));
        parent.addView(view, viewParam);
        if(!isVisble){
            parentLine.setVisibility(View.GONE);
        }else{
            addItems();
        }
    }

    private LinearLayout getHospitalLayout(String hospital,List<CheckDetailContentData> checkList,boolean isEditMode){
        int topMargin = (int)context.getResources().getDimension(R.dimen.check_detail_top_view_margin);
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
        containLayout.setLayoutParams(containParam);
        outsideLayout.addView(containLayout);
        return outsideLayout;
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

    private void addItems(){
        childLine.removeAllViews();
        for(int i=0;i<keys.length;i++){
            int marginLeft = (int)context.getResources().getDimension(R.dimen.check_detail_view_margin);
            int marginTop = (int)context.getResources().getDimension(R.dimen.check_detail_view_item_check_margin);
            LinearLayout lineLayout = new LinearLayout(context);
            lineLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView keyView = new TextView(context);
            keyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            keyView.setTextColor(context.getResources().getColor(R.color.hospital_txt_color));
            TextView valueView = new TextView(context);
            valueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            valueView.setTextColor(context.getResources().getColor(R.color.hospital_txt_color));
            keyView.setText(keys[i]);
            valueView.setText(values[i]);
            LinearLayout.LayoutParams keyParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            keyParam.leftMargin = marginLeft;
            keyParam.topMargin = marginTop;
            keyView.setLayoutParams(keyParam);
            LinearLayout.LayoutParams valueParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            valueParam.topMargin = marginTop;
            valueView.setLayoutParams(valueParam);
            lineLayout.addView(keyView);
            lineLayout.addView(valueView);
            LinearLayout.LayoutParams lineParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            childLine.addView(lineLayout,lineParam);
        }
    }
    public TextView getText(){
        return qosText;
    }
    public RelativeLayout getReceRelateLayout(){
        return receRelateLayout;
    }

    public void setAddress(String name,String address,String phone){
        recePersonView.setText("收货人:"+name);
        receAddrView.setText("收货地址:"+address);
        recePhoneView.setText(phone);
    }
}
