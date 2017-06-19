package com.woshiku.hospitalclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.example.preorderlibrary.views.ToastSuccView;
import com.google.gson.Gson;
import com.woshiku.dialoglib.BackOrderDialog;
import com.woshiku.dialoglib.ScaleImagePop;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.parent.PreorderDetailParent;
import com.woshiku.hospitalclient.inter.ResultCallBack;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.view.PicViewManage;
import com.woshiku.hospitalclient.view.QosAnsManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;

import java.util.Arrays;

import butterknife.OnClick;
import common.Global;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import param.BackOrderParam;
import param.PreorderParam;
import parse.PreorderDetailParse;
import parse.PreorderParse;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PreorderDetailActivity extends PreorderDetailParent{
    @Override
    public void init() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
        context = this;
    }

    @Override
    public void initDatas() {
        title.setText("预约详情");
        concert_bt.setVisibility(View.INVISIBLE);
        Bundle bd = getIntent().getExtras();
        preorderDetailData = PreorderDetailParse.preorderDetail(bd.getString("data"));
        orderId = bd.getString("orderId");
        LogUtil.print(bd.getString("data")+"");
        //String []strs = {"test.jpg","test.jpg","test.jpg","test.jpg","test.jpg","test.jpg","test.jpg","test.jpg","test.jpg","test.jpg","test.jpg","test.jpg"};
        //preorderDetailData.getPics()
        if(preorderDetailData.getQosAnsList() != null){
            new QosAnsManage(context,qosLine,preorderDetailData.getQosAnsList());
        }
        name.setText(preorderDetailData.getName());
        sex.setText(preorderDetailData.getSex());
        symbol.setText(preorderDetailData.getSymbol());
        symolDesc.setText(preorderDetailData.getSymbolDesc());
        if(preorderDetailData.getPics()!=null){
            if(preorderDetailData.getPics().length==0){
                picsLine.setVisibility(View.GONE);
                childScrollView.setVisibility(View.GONE);
            }else{
                new PicViewManage(context,childScrollView,scrollViewLine,preorderDetailData.getPics(), Global.fileAddr)
                        .setPicClickListener(new PicViewManage.PicClickListener() {
                            @Override
                            public void picClickShow(int index, String selectedPic, String[] allPics) {
                                //LogUtil.print("index:"+index+"sele"+selectedPic+"pics:"+Arrays.asList(allPics).toString());
                                //enterActivity("PhotoView/index.html?name=" + selectedPic);
                                new ScaleImagePop(PreorderDetailActivity.this,childScrollView,Global.fileAddr+selectedPic).show();
                            }
                        });
            }
        }else{
            picsLine.setVisibility(View.GONE);
            childScrollView.setVisibility(View.GONE);
        }
        commonUrl = new CommonUrl();
    }

    @Override
    protected int getPicAmount() {
        return preorderDetailData.getPics().length;
    }
    private void orderBackUrl(final String orderId, final String backReason){
        new Thread(){
            @Override
            public void run() {
                super.run();
                openDialog();
                Result result = commonUrl.loadUrlAsc(BackOrderParam.backOrder(orderId,backReason));
                if(result.isSuccess()){
                    Gson gson = new Gson();
                    Result rr = gson.fromJson(result.getMsg(), Result.class);
                    if(rr.isSuccess()){
                        Result oneResult = commonUrl.loadUrlAsc(PreorderParam.preorder("1"));
                        PreorderParse.preorder(oneResult, new ResultCallBack() {
                            @Override
                            public void parseSuccess(Object object) {
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString("data",(String)object);
                                intent.putExtras(bundle);
                                setResult(Global.ClosePreorderDetailReturn,intent);
                            }
                            @Override
                            public void parseFail(String msg) {

                            }
                        });
                        closeDialog();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new ToastSuccView(context).showShort();
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
        }.start();
    }
    @OnClick({R.id.icon_return_bt,R.id.preorder_detail_bottom})
    void userClick(View view){
        switch (view.getId()){
            case R.id.icon_return_bt:
                scrollToFinishActivity();
                break;
            case R.id.preorder_detail_bottom:
                new BackOrderDialog(this,picsLine).setUserChooseListener(new BackOrderDialog.UserChooseListener() {
                    @Override
                    public void userChoose(String msg) {
                        LogUtil.print(msg);
                        orderBackUrl(orderId, msg);
                    }
                });
                break;
        }

    }
    private void enterActivity(String path){
        Intent intent = new Intent("com.web.WebActivity");
        Bundle bd = new Bundle();
        bd.putString("title","查看图片");
        bd.putString("intent","loadasset");
        bd.putString("loadUrl",path);
        intent.putExtras(bd);
        startActivity(intent);
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

    @Override
    public void swipeBackCallback() {

    }
}
