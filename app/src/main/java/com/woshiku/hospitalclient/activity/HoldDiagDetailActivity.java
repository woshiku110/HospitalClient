package com.woshiku.hospitalclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.google.gson.Gson;
import com.woshiku.dialoglib.BackOrderDialog;
import com.woshiku.dialoglib.ScaleImagePop;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.parent.HoldDiagDetailParent;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.view.PicViewManage;
import com.woshiku.hospitalclient.view.QosAnsManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import butterknife.OnClick;
import common.Global;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import param.BackOrderParam;
import param.HoldDiagDetailParam;
import param.HoldDiagnosisParam;
import parse.HoldDiagDetailParse;

/**
 * Created by Administrator on 2017/2/15.
 */
public class HoldDiagDetailActivity extends HoldDiagDetailParent{

    @Override
    protected void init() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
    }

    @Override
    protected void initDatas() {
        commonUrl = new CommonUrl();
        title.setText("候诊详情");
        concert_bt.setVisibility(View.INVISIBLE);
        Bundle bd = getIntent().getExtras();
        //String []strs = bd.getStringArray("data");
        String msg = bd.getString("data");
        orderId = bd.getString("orderId");
        LogUtil.print("hold detail:"+msg);
        holdDiagDetailData = HoldDiagDetailParse.holdDiagDetail(msg);
        //LogUtil.print("hold data data:"+holdDiagDetailData.toString());
        name.setText(holdDiagDetailData.getName());
        sex.setText(holdDiagDetailData.getSex());
        symbol.setText(holdDiagDetailData.getSymbol());
        hospital.setText(holdDiagDetailData.getHospital());
        time.setText(holdDiagDetailData.getTime());
        doctor.setText(holdDiagDetailData.getDoctor());
        rece_address.setText(holdDiagDetailData.getReceAddress());
        hold_address.setText(holdDiagDetailData.getHoldAddress());
        hospital_address.setText(holdDiagDetailData.getHospitalAddress());
        symbol_text.setText(holdDiagDetailData.getSymbolText());
        if(holdDiagDetailData.getQosAnsList() != null){
            new QosAnsManage(HoldDiagDetailActivity.this,qosLine,holdDiagDetailData.getQosAnsList(),"");
        }
        if(holdDiagDetailData.getPics()!=null){
            if(holdDiagDetailData.getPics().length==0){
                childScrollLine.setVisibility(View.GONE);
                childScroll.setVisibility(View.GONE);
            }else{
                new PicViewManage(this,childScroll,childScrollLine,holdDiagDetailData.getPics(), Global.fileAddr)
                        .setPicClickListener(new PicViewManage.PicClickListener() {
                            @Override
                            public void picClickShow(int index, String selectedPic, String[] allPics) {
                                //LogUtil.print("index:"+index+"sele"+selectedPic+"pics:"+Arrays.asList(allPics).toString());
                                //enterActivity("PhotoView/index.html?name=" + selectedPic);
                                new ScaleImagePop(HoldDiagDetailActivity.this,childScroll,Global.fileAddr+selectedPic).show();
                            }
                        });
            }
        }else{
            childScrollLine.setVisibility(View.GONE);
            childScroll.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getPicAmount() {
        return holdDiagDetailData.getPics().length;
    }

    @Override
    protected void userExit() {
        scrollToFinishActivity();
    }
    @OnClick({R.id.hold_diagnsis_detail_symbol_exit,R.id.hold_diagnsis_detail_symbol_confirm})
    void userClcik(View view){
        switch (view.getId()){
            case R.id.hold_diagnsis_detail_symbol_exit:
                new BackOrderDialog(this,symbol_text).setUserChooseListener(new BackOrderDialog.UserChooseListener() {
                    @Override
                    public void userChoose(String msg) {
                        LogUtil.print(msg);
                        tuidanResaon(orderId,msg);
                    }
                });
                break;
            case R.id.hold_diagnsis_detail_symbol_confirm:
                dealUrl(orderId);
                break;
        }
    }
    private void dealUrl(final String orderId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                openDialog();
                Result result = commonUrl.loadUrlAsc(HoldDiagDetailParam.illHold(orderId));
                if(result.isSuccess()){
                    Gson gson = new Gson();
                    Result rr = gson.fromJson(result.getMsg(),Result.class);
                    if(rr.isSuccess()){
                        Result rrUpdate = commonUrl.loadUrlAsc(HoldDiagnosisParam.holdDiagnosis("4"));
                        Intent intent = new Intent();
                        if(rrUpdate.isSuccess()){
                            intent.putExtra("update", rrUpdate.getMsg());
                        }else{
                            intent.putExtra("update","");
                        }
                        setResult(1001, intent);
                        closeDialog();
                        submitOkDialog();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                scrollToFinishActivity();
                            }
                        });
                    }else{
                        closeDialog();
                        toastShort(rr.getMsg());
                    }
                }else{
                    closeDialog();
                    toastShort("由于网络原因,加载失败!");
                }
            }
        }).start();
    }

    private void tuidanResaon(final String orderId,final String reason){
        new Thread(new Runnable() {
            @Override
            public void run() {
                openDialog();
                Result result = commonUrl.loadUrlAsc(BackOrderParam.backOrder(orderId,reason));
                if(result.isSuccess()){
                    Gson gson = new Gson();
                    Result rr = gson.fromJson(result.getMsg(),Result.class);
                    if(rr.isSuccess()){
                        Result rrUpdate = commonUrl.loadUrlAsc(HoldDiagnosisParam.holdDiagnosis("4"));
                        Intent intent = new Intent();
                        if(rrUpdate.isSuccess()){
                            intent.putExtra("update", rrUpdate.getMsg());
                        }else{
                            intent.putExtra("update","");
                        }
                        setResult(1001, intent);
                        closeDialog();
                        submitOkDialog();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                scrollToFinishActivity();
                            }
                        });
                    }else{
                        toastShort(rr.getMsg());
                        closeDialog();
                    }
                }else{
                    toastShort("由于网络原因,加载失败!");
                    closeDialog();
                }
            }
        }).start();
    }

    @OnClick(R.id.icon_return_bt)
    void userClick(){
        scrollToFinishActivity();
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
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }
}
