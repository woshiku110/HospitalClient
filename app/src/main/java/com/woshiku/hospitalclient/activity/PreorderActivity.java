package com.woshiku.hospitalclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.google.gson.Gson;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.parent.PreorderParent;
import com.woshiku.hospitalclient.adapter.PreorderAdapter;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.ArrayList;
import java.util.List;
import butterknife.OnClick;
import common.Global;
import domain.PreorderData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import param.PreorderDetailParam;
import param.PreorderParam;
import parse.PreorderParse;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PreorderActivity extends PreorderParent{

    @Override
    public void init() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
    }

    @Override
    public void initDatas() {
        title.setText("预约列表");
        concert_bt.setVisibility(View.INVISIBLE);
        commonUrl = new CommonUrl();
        loadPage(Global.WAITTING);
        concert_bt.setVisibility(View.INVISIBLE);
        dealUrl();
    }

    @Override
    public void loadPage(final int state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (state) {
                    case Global.WAITTING:
                        loadListView.loadWaitView();
                        break;
                    case Global.FAIL:
                        loadListView.loadFailView();
                        break;
                    case Global.NODATA:
                        loadListView.loadNodataView();
                        break;
                    case Global.OK:
                        break;
                }
            }
        });
    }

    @Override
    protected void dealUrl() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                dealUrlMsg();
            }
        }.start();
    }

    @Override
    protected void dealDetailUrl(final PreorderData preorderData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dealDetailMsg(preorderData.getOrderId());
            }
        }).start();
    }

    private void dealUrlMsg(){
        Result result = commonUrl.loadUrlAsc(PreorderParam.preorder("1"));
        if(result.isSuccess()){
            LogUtil.print(result.getMsg());
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(), Result.class);
            if(rr.isSuccess()){
                final List<PreorderData> preorderDataList = PreorderParse.preorder(rr.getMsg());
                if(preorderDataList.size() != 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadListView.setData(new PreorderAdapter(context, changeData(preorderDataList)));
                        }
                    });
                }else {
                    loadPage(Global.NODATA);
                }
            }else{
                loadPage(Global.FAIL);
                toastShort(rr.getMsg());
            }
        }else {
            loadPage(Global.FAIL);
            toastShort("网络加载失败!!!");
        }
    }

    private void dealDetailMsg(String id){
        openDialog();
        Result result = commonUrl.loadUrlAsc(PreorderDetailParam.preorderDetail(id));
        if(result.isSuccess()){
            LogUtil.print(result.getMsg());
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(),Result.class);
            if(rr.isSuccess()){
                enterDetailActivity(rr.getMsg(),id);
            }else{
                toastShort(rr.getMsg());
            }
        } else {
            toastShort("网络加载失败!!!");
        }
        closeDialog();
    }

    protected void enterDetailActivity(String msg,String orderId){
        Intent intent = new Intent("com.myfamily.PreorderDetailActivity");
        Bundle bd = new Bundle();
        bd.putString("data",msg);
        bd.putString("orderId", orderId);
        intent.putExtras(bd);
        startActivityForResult(intent, Global.EnterPreorderDetailRequest);
        //startActivity(intent);
    }

    protected List<Object> changeData(List<PreorderData> mList){
        List<Object> list = new ArrayList<>();
        for(PreorderData holdDiagnosisData:mList){
            list.add(holdDiagnosisData);
        }
        return list;
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

    @Override
    public void swipeBackCallback() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Global.EnterPreorderDetailRequest && resultCode == Global.ClosePreorderDetailReturn){
            Bundle bundle = data.getExtras();
            String msg = bundle.getString("data");
            if(loadListView.getCommonAdapter() != null){
                loadListView.updateData(changeData(PreorderParse.preorder(msg)));
            }else{
                loadListView.setData(new PreorderAdapter(context, changeData(PreorderParse.preorder(msg))));
            }
        }
    }
}
