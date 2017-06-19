package com.woshiku.hospitalclient.activity.prescription;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.prescription.presenter.PrescriptionPresenter;
import com.woshiku.hospitalclient.activity.prescription.presenter.PrescriptionPresenterImple;
import com.woshiku.hospitalclient.activity.prescription.view.PrescriptionView;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.view.LoadListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import domain.PrescriptionData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/3/1.
 */
public class PrescriptionActivity extends BaseActivity implements PrescriptionView{
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView confirm_bt;
    @InjectView(R.id.load_listview_line)
    LinearLayout loadListLine;
    @InjectView(R.id.icon_return_bt)
    LinearLayout returnLine;
    LoadListView loadListView;
    Context context;
    PrescriptionPresenter prescriptionPresenter;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_family);
        ButterKnife.inject(this);
        context = PrescriptionActivity.this;
        loadListView = new LoadListView(context,loadListLine);
        prescriptionPresenter = new PrescriptionPresenterImple(this);
        prescriptionPresenter.initPage("处方列表");
        loadListView.setReloadListener(new LoadListView.ReloadListener() {
            @Override
            public void onReload() {
                prescriptionPresenter.loadPrescriptionListData();
            }
        });
        loadListView.setItemChoosedListener(new LoadListView.ItemChoosedListener() {
            @Override
            public void itemChoose(Object object, int pos) {
                PrescriptionData data = (PrescriptionData)object;
                prescriptionPresenter.loadPrescriptionDetail(data.getId());
            }
        });
    }

    @Override
    public void initTitleBar(String title) {
        titleView.setText(title);
        confirm_bt.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initActivity() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            scrollToFinishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
