package com.woshiku.hospitalclient.activity.parent;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.urllibrary.url.base.CommonUrl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import domain.HoldDiagDetailData;

/**
 * Created by Administrator on 2017/2/15.
 */
public abstract class HoldDiagDetailParent extends BaseActivity{
    @InjectView(R.id.txt_title)
    protected TextView title;
    @InjectView(R.id.concrete_bt)
    protected TextView concert_bt;
    @InjectView(R.id.hold_diagnsis_detail_name)
    protected TextView name;
    @InjectView(R.id.hold_diagnsis_detail_sex)
    protected TextView sex;
    @InjectView(R.id.hold_diagnsis_detail_symbol)
    protected TextView symbol;
    @InjectView(R.id.hold_diagnsis_detail_hospital)
    protected TextView hospital;
    @InjectView(R.id.hold_diagnsis_detail_time)
    protected TextView time;
    @InjectView(R.id.hold_diagnsis_detail_doctor)
    protected TextView doctor;
    @InjectView(R.id.hold_diagnsis_detail_rece_address)
    protected TextView rece_address;
    @InjectView(R.id.hold_diagnsis_detail_hold_address)
    protected TextView hold_address;
    @InjectView(R.id.hold_diagnsis_detail_hospital_address)
    protected TextView hospital_address;
    @InjectView(R.id.hold_diagnsis_detail_symbol_text)
    protected TextView symbol_text;
    @InjectView(R.id.hold_diagnsis_detail_symbol_exit)
    protected  LinearLayout exitHold;
    @InjectView(R.id.hold_diagnsis_detail_symbol_confirm)
    protected LinearLayout confirmHold;
    @InjectView(R.id.hold_diagnsis_detail_scrollview)
    protected ScrollView parentScroll;
    @InjectView(R.id.preorder_detail_images_scroll)
    protected ScrollView childScroll;
    @InjectView(R.id.preorder_detail_images_scroll_line)
    protected LinearLayout childScrollLine;
    @InjectView(R.id.preorder_detail_symbol_qans_line)
    protected LinearLayout qosLine;
    protected HoldDiagDetailData holdDiagDetailData;
    protected CommonUrl commonUrl;
    protected String orderId;
    int picAmount = 12;
    protected abstract void init();
    protected abstract void initDatas();
    protected abstract int getPicAmount();
    protected abstract void userExit();
    Context context;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_hold_diagnosis_detail);
        ButterKnife.inject(this);
        context = this;
        init();
        initDatas();
        initControls();
    }

    private void initControls(){
        parentScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                //LogUtil.print("PARENT TOUCH");
                childScroll.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        childScroll.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                //LogUtil.print("CHILD TOUCH");
                if(getPicAmount()>picAmount){
                    int touchWidth = (int)context.getResources().getDimension(R.dimen.gap);
                    int startX = (int)event.getX();
                    if(startX>touchWidth){
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    }else{
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }
    @Override
    public void swipeBackCallback() {

    }
}
