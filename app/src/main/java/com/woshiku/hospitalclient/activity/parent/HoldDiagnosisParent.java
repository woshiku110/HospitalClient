package com.woshiku.hospitalclient.activity.parent;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.adapter.HoldDiagnosisAdapter;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.view.LoadListView;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import domain.HoldDiagnosisData;
import param.HoldDiagnosisParam;
import parse.HoldDiagnosisParse;

/**
 * Created by Administrator on 2017/2/10.
 */
public abstract class HoldDiagnosisParent extends BaseActivity{
    @InjectView(R.id.txt_title)
    TextView title;
    @InjectView(R.id.concrete_bt)
    TextView concert_bt;
    @InjectView(R.id.load_listview_line)
    LinearLayout parentLine;
    LoadListView loadListView;
    protected CommonUrl commonUrl;
    Context context;
    protected final int WAITTING=0,FAIL=1,NODATA=2,OK=3;
    protected abstract void init();
    @Override
    protected void initView() {
        setContentView(R.layout.activity_hold_diagnosis);
        ButterKnife.inject(this);
        context = this;
        loadListView = new LoadListView(context,parentLine);
        loadListView.setReloadListener(new LoadListView.ReloadListener() {
            @Override
            public void onReload() {
                loadUrlData();
            }
        });
        loadListView.setItemChoosedListener(new LoadListView.ItemChoosedListener() {
            @Override
            public void itemChoose(Object object,int pos) {
                HoldDiagnosisData holdDiagnosisData = (HoldDiagnosisData)object;
                /*LogUtil.print("pos:"+pos+"\t"+holdDiagnosisData.toString());*/
                enterDetailPage(holdDiagnosisData);
            }
        });
        initDatas();
        init();
    }

    private void initDatas(){
        title.setText("候诊列表");
        concert_bt.setVisibility(View.INVISIBLE);
        commonUrl = new CommonUrl();
        loadUrlData();
    }
    private void loadUrlData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadPage(WAITTING);
                Result result = commonUrl.loadUrlAsc(HoldDiagnosisParam.holdDiagnosis("4"));
                dealResult(result);
            }
        }).start();
    }
    protected void updateUrlData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result result = commonUrl.loadUrlAsc(HoldDiagnosisParam.holdDiagnosis("4"));
                updateResult(result);
            }
        }).start();
    }
    private void dealResult(Result result){
        if(result.isSuccess()){
            LogUtil.print(result.getMsg());
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(), Result.class);
            if(rr.isSuccess()){
                final List<HoldDiagnosisData> mList = HoldDiagnosisParse.holdDiagnosis(rr.getMsg());
                if(mList.size() == 0){
                    loadPage(NODATA);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadListView.setData(new HoldDiagnosisAdapter(context, changeData(mList)));
                        }
                    });
                }
            }else{
                loadPage(FAIL);
            }
        }else{
            loadPage(FAIL);
        }
    }
    private void updateResult(Result result){
        if(result.isSuccess()){
            LogUtil.print(result.getMsg());
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(), Result.class);
            if(rr.isSuccess()){
                final List<HoldDiagnosisData> mList = HoldDiagnosisParse.holdDiagnosis(rr.getMsg());
                if(mList.size() == 0){
                    loadPage(NODATA);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadListView.updateData(changeData(mList));
                        }
                    });
                }
            }else{
                loadPage(FAIL);
            }
        }else{
            loadPage(FAIL);
        }
    }
    protected void updateResult(String msg){
        Gson gson = new Gson();
        Result rr = gson.fromJson(msg, Result.class);
        if(rr.isSuccess()){
            final List<HoldDiagnosisData> mList = HoldDiagnosisParse.holdDiagnosis(rr.getMsg());
            if(mList.size() == 0){
                loadPage(NODATA);
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadListView.updateData(changeData(mList));
                    }
                });
            }
        }else{
            loadPage(FAIL);
        }
    }
    protected List<Object> changeData(List<HoldDiagnosisData> mList){
        List<Object> list = new ArrayList<>();
        for(HoldDiagnosisData holdDiagnosisData:mList){
            list.add(holdDiagnosisData);
        }
        return list;
    }
    private void loadPage(final int state){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (state){
                    case WAITTING:
                        loadListView.loadWaitView();
                        break;
                    case FAIL:
                        loadListView.loadFailView();
                        break;
                    case NODATA:
                        loadListView.loadNodataView();
                        break;
                    case OK:
                        break;
                }
            }
        });
    }
    protected abstract void userExit();
    protected abstract void enterDetailPage(HoldDiagnosisData holdDiagnosisData);
    @OnClick(R.id.icon_return_bt)
    void userClick(){
        userExit();
    }
    @Override
    public void swipeBackCallback() {

    }
}
