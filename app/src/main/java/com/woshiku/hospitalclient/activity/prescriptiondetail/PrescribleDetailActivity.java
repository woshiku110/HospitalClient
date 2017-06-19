package com.woshiku.hospitalclient.activity.prescriptiondetail;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.woshiku.dialoglib.PrescribleManageView;
import com.woshiku.dialoglib.PrescribleQosView;
import com.woshiku.dialoglib.PrescribleWaitPayView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.prescriptiondetail.presenter.PrescribleDetailPresenter;
import com.woshiku.hospitalclient.activity.prescriptiondetail.presenter.PrescribleDetailPresenterImple;
import com.woshiku.hospitalclient.activity.prescriptiondetail.view.PrescribleDetailView;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.utils.PrescribleDetailUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import domain.AddressData;
import domain.PrescriptionDetailData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
/**
 * Created by Administrator on 2017/3/3.
 */
public class PrescribleDetailActivity extends BaseActivity implements PrescribleDetailView{
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView confirm_bt;
    @InjectView(R.id.check_detail_state_page)
    LinearLayout checkDetailStatePage;
    @InjectView(R.id.check_detail_state_page_bottom)
    LinearLayout checkDetailContent;
    @InjectView(R.id.check_detail_state_scrollview)
    ScrollView scrollView;
    @InjectView(R.id.check_detail_state_bottom)
    LinearLayout payLine;
    PrescribleDetailPresenter presenter;
    PrescribleWaitPayView prescribleWaitPayView;
    Intent addrIntent;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_detail);
        ButterKnife.inject(this);
        presenter = new PrescribleDetailPresenterImple(this);
        presenter.initPage("处方详情");
    }

    @Override
    public void initTitleBar(String title) {
        titleView.setText(title);
        confirm_bt.setVisibility(View.INVISIBLE);
        payLine.setVisibility(View.GONE);
        payLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("支付提醒", "检查无误后，请支付处方的费用!");
            }
        });
    }

    @Override
    public void initActivity() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void configPage(int state, PrescriptionDetailData data) {
        if(state <= 3){//系统录入
            new PrescribleManageView(this,checkDetailStatePage,0);
            scrollView.setBackgroundColor(Color.WHITE);
            new PrescribleQosView(checkDetailContent,this).getQosText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.enterWebActivity("http://www.baidu.com", "wait");
                }
            });
        }
        if(state == 4){//等待缴费
            new PrescribleManageView(this,checkDetailStatePage,1);
            scrollView.setBackgroundColor(getResources().getColor(R.color.check_detail_bg));
            payLine.setVisibility(View.VISIBLE);
            prescribleWaitPayView = new PrescribleWaitPayView(checkDetailContent,this,data.getHospitalName(),PrescribleDetailUtil.getPrescribleDetail(data),false,null,null);
            prescribleWaitPayView.getText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.enterWebActivity("http://www.baidu.com", "wait");
                }
            });
            prescribleWaitPayView.getReceRelateLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.print("enter rece");
                    presenter.enterAddressActivity();
                }
            });
            setUserAddr();
        }
        if(state >= 5&&state <=7){//待发货
            new PrescribleManageView(this,checkDetailStatePage,2);
            scrollView.setBackgroundColor(getResources().getColor(R.color.check_detail_bg));
            payLine.setVisibility(View.GONE);
            String []keys = {"支付交易号 : ","创建时间 : ","付款时间 : "};
            String []values = {data.getPayNo(),data.getCreateTime(),data.getPayTime()};
            prescribleWaitPayView = new PrescribleWaitPayView(checkDetailContent,this,data.getHospitalName(),PrescribleDetailUtil.getPrescribleDetail(data),true,keys,values);
            prescribleWaitPayView.getText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.enterWebActivity("http://www.baidu.com","wait");
                }
            });
            prescribleWaitPayView.getReceRelateLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.print("enter rece");
                    presenter.enterAddressActivity();
                }
            });
            setUserAddr();
        }

        if(state == 8){//发货完成
            new PrescribleManageView(this,checkDetailStatePage,3);
            scrollView.setBackgroundColor(getResources().getColor(R.color.check_detail_bg));
            String []keys = {"支付交易号 : ","创建时间 : ","付款时间 : "};
            String []values = {data.getPayNo(),data.getCreateTime(),data.getPayTime()};
            prescribleWaitPayView = new PrescribleWaitPayView(checkDetailContent,this,data.getHospitalName(),PrescribleDetailUtil.getPrescribleDetail(data),true,keys,values);
            prescribleWaitPayView.getText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.enterWebActivity("http://www.baidu.com", "wait");
                }
            });
            setUserAddr();
        }
    }

    @OnClick({R.id.icon_return_bt})
    void userClick(View view){
        switch (view.getId()){
            case R.id.icon_return_bt:
                scrollToFinishActivity();
                break;
        }
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void swipeBackCallback() {

    }
    private void showDialog(String title,String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                presenter.setPage(6);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        payLine.setVisibility(View.GONE);
                    }
                });
            }
        });
        builder.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            scrollToFinishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Global.EnterManageAddressRequest == requestCode && Global.CloseManageAddressReturn == resultCode){
            addrIntent = data;
            setUserAddr();
        }
    }
    private void setUserAddr(){
        if(addrIntent != null){
            Bundle bd = addrIntent.getExtras();
            try{
                String addr = bd.getString("address");
                Gson gson = new Gson();
                final AddressData addressData = gson.fromJson(addr, AddressData.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prescribleWaitPayView.setAddress(addressData.getName(),addressData.getArea()+addressData.getDetailAddress(),addressData.getPhone());
                    }
                });
            }catch (Exception e){

            }
        }
    }
}
