package com.woshiku.hospitalclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.dialoglib.ItemsDialog;
import com.woshiku.dialoglib.utils.ManageItem;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.parent.MyFamilyParent;
import com.woshiku.hospitalclient.adapter.MyFamilyAdapter;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import butterknife.OnClick;
import common.Global;
import domain.MyFamilyData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import param.DeleFamilyParam;
import param.LookFamilyDetail;
import param.MyFamilyParam;
import param.UpdateFamilyParam;
import parse.MyFamilyParse;

/**
 * Created by Administrator on 2017/2/16.
 */
public class MyFamilyActivity extends MyFamilyParent{
    @Override
    public void init() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
    }

    @Override
    public void initDatas() {
        title.setText("我的家庭");
        concert_bt.setVisibility(View.INVISIBLE);
        commonUrl = new CommonUrl();
        loadPage(WAITTING);
    }

    @Override
    public void loadPage(final int state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (state) {
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

    @Override
    protected void loadUrl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dealUrlMsg();
            }
        }).start();
    }
    protected void loadFamilyDetail(final String id, final boolean isEdit){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = dealDetailFamily(id);
                if(msg != null){
                    Gson  gson = new Gson();
                    Type type = new TypeToken<String[]>(){}.getType();
                    String[] datas = gson.fromJson(msg, type);
                    Intent intent = new Intent("com.myfamily.NewFamilyActivity");
                    Bundle bd = new Bundle();
                    if(isEdit){
                        bd.putInt("action", Global.EDIT_USER);
                    }else{
                        bd.putInt("action", Global.LOOK_USER);
                    }
                    bd.putStringArray("data",datas);
                    intent.putExtras(bd);
                    startActivityForResult(intent, Global.CreateFamilyRequest);
                }
            }
        }).start();
    }

    @Override
    protected void dealTypeOne(MyFamilyData data) {
        loadFamilyDetail(data.getId(), false);
    }

    @Override
    protected void dealTypeTwo(MyFamilyData data) {
        Intent intent = new Intent("com.myfamily.NewFamilyActivity");
        Bundle bd = new Bundle();
        bd.putInt("action", Global.NEW_USER);
        intent.putExtras(bd);
        startActivityForResult(intent, Global.CreateFamilyRequest);
    }

    @Override
    protected void dealLongType(final MyFamilyData data,int pos) {
        if(data.isTypeOne()){
            String []items = this.getResources().getStringArray(R.array.item_myfamily);
            if(pos == 0){
                items = new String[]{"编辑"};
            }
            final ItemsDialog itemsDialog = new ItemsDialog(this,items);
            itemsDialog.getManageItem().setItemClickListener(new ManageItem.ItemClickListener() {
                @Override
                public void itemClick(int pos) {
                    if (pos == 0) {//编辑用户
                        loadFamilyDetail(data.getId(), true);
                    } else {
                        deleFamilyMember(data.getId());
                    }
                    LogUtil.print("pos:" + pos);
                    itemsDialog.dismiss();
                }
            });
            itemsDialog.show();
        }
    }

    private void dealUrlMsg(){
        Result result = commonUrl.loadUrlAsc(MyFamilyParam.myFamily());
        /*LogUtil.print(result.getMsg());*/
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(),Result.class);
            if(rr.isSuccess()){
                final List<MyFamilyData> familyList = MyFamilyParse.myFamily(rr.getMsg());
                familyList.add(new MyFamilyData(false));
                final List<MyFamilyData> finalList = familyList;
                LogUtil.print(finalList.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(familyList.size() == 0){
                            loadPage(NODATA);
                        }else{
                            loadListView.setData(new MyFamilyAdapter(MyFamilyActivity.this, changeData(finalList)));
                        }
                    }
                });
            }else{
                loadPage(FAIL);
            }
        }else{
            toastShort("由于网络原因,加载失败!!!");
        }
    }
    private void deleFamilyMember(final String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                openDialog();
                Result result = commonUrl.loadUrlAsc(DeleFamilyParam.deleFamilyMember(id));
                if(result.isSuccess()){
                    Gson gson = new Gson();
                    Result rr = gson.fromJson(result.getMsg(), Result.class);
                    if(rr.isSuccess()){
                        //更新家庭成员信息
                        Result updateResult = commonUrl.loadUrlAsc(MyFamilyParam.myFamily());
                        if(updateResult.isSuccess()){
                            Result updateRR = gson.fromJson(updateResult.getMsg(), Result.class);
                            if(updateRR.isSuccess()){
                                updateData(updateRR.getMsg());
                            }
                        }
                        submitOkDialog();
                    }else{
                        toastShort("删除失败!");
                    }
                }else {
                    toastShort("网络原因,连接失败!");
                }
                closeDialog();
            }
        }).start();
    }
    private String dealDetailFamily(String id){
        String msg = null;
        openDialog();
        Result result = commonUrl.loadUrlAsc(LookFamilyDetail.lookFamily(id));
        closeDialog();
        LogUtil.print(result.getMsg());
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(),Result.class);
            if(rr.isSuccess()){
                LogUtil.print("加载成功！");
                msg = rr.getMsg();
                return msg;
            }else{
                toastShort("加载失败!!!");
            }
        }else{
            toastShort("由于网络原因,加载失败!!!");
        }
        return msg;
    }
    private void updateData(String msg){
        List<MyFamilyData> mList = MyFamilyParse.myFamily(msg);
        mList.add(new MyFamilyData(false));
        loadListView.updateData(changeData(mList));
    }

    protected List<Object> changeData(List<MyFamilyData> mList){
        List<Object> list = new ArrayList<>();
        for(MyFamilyData holdDiagnosisData:mList){
            list.add(holdDiagnosisData);
        }
        return list;
    }

    @OnClick(R.id.icon_return_bt)
    void userClick(){
        scrollToFinishActivity();
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void swipeBackCallback() {
        /*Intent intent = new Intent();
        intent.putExtra("myfamily","my family");
        setResult(10001, intent);*/
        LogUtil.print("my family end");
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
        if(requestCode == Global.CreateFamilyRequest && resultCode==Global.CreateFamilyReturn){
            String msg = data.getExtras().getString("myfamily");
            if(!TextUtils.isEmpty(msg)){
                LogUtil.print("更新数据");
                updateData(msg);
            }
            LogUtil.print("有返回值");
        }
    }
}
