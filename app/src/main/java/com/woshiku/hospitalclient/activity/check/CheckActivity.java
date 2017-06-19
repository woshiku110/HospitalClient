package com.woshiku.hospitalclient.activity.check;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.check.presenter.CheckPresenter;
import com.woshiku.hospitalclient.activity.check.presenter.CheckPresenterImple;
import com.woshiku.hospitalclient.activity.check.view.CheckView;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.view.LoadListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import domain.CheckData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/2/25.
 */
public class CheckActivity extends BaseActivity implements CheckView{
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView confirm_bt;
    @InjectView(R.id.load_listview_line)
    LinearLayout loadListLine;
    LoadListView loadListView;
    Context context;
    CheckPresenter checkPresenter;
    View view;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_family);
        ButterKnife.inject(this);
        context = CheckActivity.this;
        confirm_bt.setVisibility(View.INVISIBLE);
        checkPresenter = new CheckPresenterImple(this);
        loadListView = new LoadListView(context,loadListLine);
        loadListView.setReloadListener(new LoadListView.ReloadListener() {
            @Override
            public void onReload() {
                checkPresenter.loadCheckListData();
            }
        });
        checkPresenter.initPage("检查单表");
        checkPresenter.loadCheckListData();
        loadListView.setItemChoosedListener(new LoadListView.ItemChoosedListener() {
            @Override
            public void itemChoose(Object object, int pos) {
                CheckData checkData = (CheckData)object;
                checkPresenter.loadCheckDetailData(checkData);
            }
        });
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void initTitleBar(String title) {
        titleView.setText(title);
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
