package com.woshiku.hospitalclient.activity.checkdetail;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.dialoglib.CheckDetailContentView;
import com.woshiku.dialoglib.CheckDetailContentWithAddressView;
import com.woshiku.dialoglib.CheckManageView;
import com.woshiku.dialoglib.domain.CheckDetailContentData;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.checkdetail.presenter.CheckDetailPresenter;
import com.woshiku.hospitalclient.activity.checkdetail.presenter.CheckDetailPresenterImple;
import com.woshiku.hospitalclient.activity.checkdetail.view.CheckDetailView;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class CheckDetailActivity extends BaseActivity implements CheckDetailView{
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView confirm_bt;
    @InjectView(R.id.icon_return_bt)
    LinearLayout returnLine;
    @InjectView(R.id.check_detail_state_page)
    LinearLayout checkDetailStatePage;
    @InjectView(R.id.check_detail_state_page_bottom)
    LinearLayout checkDetailContent;
    @InjectView(R.id.check_detail_state_bottom)
    LinearLayout payLine;
    CheckDetailPresenter checkDetailPresenter;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_detail);
        ButterKnife.inject(this);
        checkDetailPresenter = new CheckDetailPresenterImple(this);
        checkDetailPresenter.initPage("检查单详情");
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
        confirm_bt.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initActivity() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
        payLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("支付提醒","检查无误后，请支付检查单的费用!");
            }
        });
    }

    @Override
    public void configPage(final int state, final String[] params, final String[] keys, final String[] values, final List<CheckDetailContentData> checkDetailList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (state == 0) {
                    new CheckManageView(CheckDetailActivity.this, checkDetailStatePage, state, false, "");
                    new CheckDetailContentView(CheckDetailActivity.this, checkDetailContent, params[1], checkDetailList, true);
                } else if (state == 1) {
                    new CheckManageView(CheckDetailActivity.this, checkDetailStatePage, state, true, params[0]);
                    new CheckDetailContentWithAddressView(CheckDetailActivity.this, checkDetailContent, params[1], checkDetailList, false, keys, values);
                    payLine.setVisibility(View.GONE);
                } else if (state == 1) {
                    new CheckManageView(CheckDetailActivity.this, checkDetailStatePage, state, true, params[0]);
                    new CheckDetailContentWithAddressView(CheckDetailActivity.this, checkDetailContent, params[1], checkDetailList, false, keys, values);
                    payLine.setVisibility(View.GONE);
                }
            }
        });
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

    private void showDialog(String title,String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                checkDetailPresenter.setPage(2);
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
}
