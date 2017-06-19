package com.woshiku.hospitalclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.google.gson.Gson;
import com.woshiku.hospitalclient.activity.parent.HoldDiagnosisParent;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;
import domain.HoldDiagnosisData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import param.HoldDiagDetailParam;
import parse.HoldDiagDetailParse;

/**
 * Created by Administrator on 2017/2/14.
 */
public class HoldDiagnsisActivity extends HoldDiagnosisParent {

    @Override
    protected void init() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
    }

    @Override
    protected void userExit() {
        scrollToFinishActivity();
    }

    @Override
    protected void enterDetailPage(HoldDiagnosisData holdDiagnosisData) {
        dealUrlData(holdDiagnosisData.getOrderId());
    }

    private void dealUrlData(final String orderId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                openDialog();
                Result result = commonUrl.loadUrlAsc(HoldDiagDetailParam.holdDiagnosis(orderId));
                if(result.isSuccess()){
                    closeDialog();
                    Gson gson = new Gson();
                    Result rr = gson.fromJson(result.getMsg(),Result.class);
                    if(rr.isSuccess()){
                        Intent intent = new Intent(HoldDiagnsisActivity.this,HoldDiagDetailActivity.class);
                        Bundle bd = new Bundle();
                        //bd.putStringArray("data", HoldDiagDetailParse.holdDiagDetailArray(rr.getMsg()));
                        bd.putString("data",rr.getMsg());
                        bd.putString("orderId", orderId);
                        intent.putExtras(bd);
                        startActivityForResult(intent, 1000);
                        LogUtil.print(rr.getMsg());
                        //LogUtil.print(HoldDiagDetailParse.holdDiagDetail(rr.getMsg()).toString());
                    }else{
                        toastShort("由于网络原因,加载失败");
                    }
                }else{
                    toastShort("由于网络原因,加载失败");
                    closeDialog();
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 &&resultCode == 1001){
            String msg = data.getStringExtra("update");
            if(!TextUtils.isEmpty(msg)){
                updateResult(msg);
                LogUtil.print(msg);
            }
        }
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
