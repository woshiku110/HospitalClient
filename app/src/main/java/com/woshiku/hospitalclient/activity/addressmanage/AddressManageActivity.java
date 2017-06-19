package com.woshiku.hospitalclient.activity.addressmanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.addressmanage.presenter.AddressManagePresenter;
import com.woshiku.hospitalclient.activity.addressmanage.presenter.AddressManagePresenterImple;
import com.woshiku.hospitalclient.activity.addressmanage.view.AddressManageView;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.view.LoadListView;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import domain.AddressData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;


/**
 * Created by Administrator on 2017/3/6.
 */
public class AddressManageActivity extends BaseActivity implements AddressManageView{
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView confirm_bt;
    @InjectView(R.id.load_listview_line)
    LinearLayout loadListLine;
    LoadListView loadListView;
    AddressManagePresenter presenter;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_address_manage);
        ButterKnife.inject(this);
        loadListView = new LoadListView(this,loadListLine);
        presenter = new AddressManagePresenterImple(this);
        presenter.initPage("地址管理");
        presenter.loadUrl();
        loadListView.setReloadListener(new LoadListView.ReloadListener() {
            @Override
            public void onReload() {
                presenter.loadUrl();
            }
        });
        loadListView.setItemChoosedListener(new LoadListView.ItemChoosedListener() {
            @Override
            public void itemChoose(Object object, int pos) {
                LogUtil.print("item pos:"+pos);
                //选项被选择
                AddressData addressData = (AddressData)object;
                Intent intent = new Intent();
                Bundle bd= new Bundle();
                bd.putString("address",new Gson().toJson(addressData));
                intent.putExtras(bd);
                setResult(Global.CloseManageAddressReturn, intent);
                scrollToFinishActivity();
            }
        });
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
    public void setUpdateListView(final List<Object> objectList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadListView.updateData(objectList);
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

    @Override
    protected void allowWrite() {

    }

    @Override
    protected void allowPhoto() {

    }

    @OnClick({R.id.icon_return_bt,R.id.address_manage_bottom})
    void userClick(View view){
        switch (view.getId()){
            case R.id.icon_return_bt:
                scrollToFinishActivity();
                break;
            case R.id.address_manage_bottom:
                Intent intent = new Intent("com.address.AddressActivity");
                Bundle bd = new Bundle();
                bd.putString("intent","newAddress");
                intent.putExtras(bd);
                startActivityForResult(intent, Global.EnterAddNewAddressRequest);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Global.CloseAddNewAddressReturn:
                if(requestCode == Global.EnterAddNewAddressRequest){
                    Bundle bd = data.getExtras();
                    LogUtil.print("data:"+bd.getString("data"));
                    presenter.updateMsgDataAddressList(bd.getString("data"));
                }
                break;
            case Global.CloseEditAddressReturn:
                if(requestCode == Global.EnterEditAddressRequest){
                    Bundle bd = data.getExtras();
                    LogUtil.print("data:"+bd.getString("data"));
                    presenter.updateMsgDataAddressList(bd.getString("data"));
                }
                break;
        }
    }
}
