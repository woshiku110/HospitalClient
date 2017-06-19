package com.woshiku.hospitalclient.activity.newsremind;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.newsremind.presenter.NewsRemindPresenter;
import com.woshiku.hospitalclient.activity.newsremind.presenter.NewsRemindPresenterImple;
import com.woshiku.hospitalclient.activity.newsremind.view.NewsRemindView;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.view.LoadListView;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import domain.NewsRemindData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/3/20.
 */
public class NewsRemindActivity extends BaseActivity implements NewsRemindView{
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView confirm_bt;
    @InjectView(R.id.load_listview_line)
    LinearLayout loadListLine;
    LoadListView loadListView;
    NewsRemindPresenter presenter;
    NewsReceiver newsReceiver;
    List<NewsRemindData> allList;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_family);
        ButterKnife.inject(this);
        initBroadCast();
        presenter = new NewsRemindPresenterImple(this);
        allList = new ArrayList<>();
        if(Global.newsRemindUnreadList != null){
            allList.addAll(Global.newsRemindUnreadList);
        }
        presenter.initPage("消息通知",changeListObject(allList));
    }

    private void initBroadCast(){
        newsReceiver = new NewsReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Global.remindAction);
        registerReceiver(newsReceiver, filter);
    }

    private void removeBroadCast(){
        if(newsReceiver != null){
            unregisterReceiver(newsReceiver);
            newsReceiver = null;
        }
    }

    @Override
    public void initPage(String title) {
        initTitleBar(title);
    }

    @Override
    public void initTitleBar(String title) {
        titleView.setText(title);
        confirm_bt.setVisibility(View.INVISIBLE);
        initActivity();
    }

    @Override
    public void initActivity() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
        loadListView = new LoadListView(this,loadListLine);
        loadListView.setItemChoosedListener(new LoadListView.ItemChoosedListener() {
            @Override
            public void itemChoose(Object object, int pos) {

            }
        });
    }

    @Override
    public void setListView(final CommonAdapter commonAdapter) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadListView.setData(commonAdapter);
            }
        });
    }

    @Override
    public void setUpdateListView(List<Object> objectList) {
        loadListView.updateData(objectList);
    }

    @Override
    public void setLoadListWait() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadListView.loadWaitView();
            }
        });
    }

    @Override
    public void setLoadListFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadListView.loadFailView();
            }
        });
    }

    @Override
    public void setLoadListNoData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadListView.loadNodataView();
            }
        });
    }

    @Override
    public void setDialogWait() {
        openDialog();
    }

    @Override
    public void setDialogClose() {
        closeDialog();
    }

    @Override
    public void setToastFail(String msg) {
        toastShort(msg);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void swipeBackCallback() {
        removeBroadCast();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            scrollToFinishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private List<Object> changeListObject(List<NewsRemindData> newsRemindDataList){
        List<Object> list = new ArrayList<>();
        if(newsRemindDataList != null){
            for(NewsRemindData newsRemindData:newsRemindDataList){
                list.add(newsRemindData);
            }
        }
        return list;
    }

    class NewsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Global.remindAction)){
                Bundle bd = intent.getExtras();
                boolean haveData = bd.getBoolean("haveData");
                if(haveData){
                    if(Global.newsRemindUnreadList != null){
                        allList.addAll(Global.newsRemindUnreadList);
                        presenter.updatePage(changeListObject(allList));
                    }
                    LogUtil.print("haveData", "is have data 消息提醒:" + haveData);
                }else{
                    LogUtil.print("haveData","is have data 消息提醒:"+haveData);
                }
            }
        }
    }
}
