package com.woshiku.hospitalclient.activity.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.City;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.County;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.Province;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.address.presenter.AddressPresenter;
import com.woshiku.hospitalclient.activity.address.presenter.AddressPresenterImple;
import com.woshiku.hospitalclient.activity.address.view.AddressView;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.AddressPickTask;
import com.woshiku.hospitalclient.utils.LogUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import domain.AddressData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/3/7.
 */
public class AddressActivity extends BaseActivity implements AddressView{
    @InjectView(R.id.address_name)
    EditText nameView;
    @InjectView(R.id.address_phone)
    EditText phoneView;
    @InjectView(R.id.address_area_relate)
    RelativeLayout areaRelate;
    @InjectView(R.id.address_area)
    TextView areaView;
    @InjectView(R.id.address_detail_address)
    EditText addressView;
    @InjectView(R.id.address_default_choose)
    CheckBox defaultChooseView;
    @InjectView(R.id.address_save_data)
    LinearLayout saveData;
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView confirm_bt;
    AddressPresenter addressPresenter;
    AddressPickTask task;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_address);
        ButterKnife.inject(this);
        addressPresenter = new AddressPresenterImple(this);
        addressPresenter.getDataWithDealPage();
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
    }

    @Override
    public String getEditName() {
        return nameView.getText().toString();
    }

    @Override
    public String getEditPhone() {
        return phoneView.getText().toString();
    }

    @Override
    public String getEditArea() {
        return areaView.getText().toString();
    }


    @Override
    public void setEditArea(final String area) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                areaView.setText(area);
            }
        });
    }

    @Override
    public void setDialogShow() {
        openDialog();
    }

    @Override
    public void setDialogClose() {
        closeDialog();
    }

    @Override
    public void setFailToast(String msg) {
        toastShort(msg);
    }

    @Override
    public void fillPageData(final AddressData addressData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameView.setText(addressData.getName());
                phoneView.setText(addressData.getPhone());
                areaView.setText(addressData.getArea());
                addressView.setText(addressData.getDetailAddress());
                defaultChooseView.setChecked(addressData.isDefaultChoosed());
            }
        });
    }

    @Override
    public void setFinishActivity(boolean isNew,String msg) {
        Intent data = new Intent();
        Bundle bd = new Bundle();
        bd.putString("data", msg);
        data.putExtras(bd);
        if(isNew){
            setResult(Global.CloseAddNewAddressReturn, data);
        }else{
            setResult(Global.CloseEditAddressReturn,data);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scrollToFinishActivity();
            }
        });
    }

    @Override
    public AddressData getFillPageData() {
        AddressData addressData = new AddressData();
        addressData.setName(nameView.getText().toString());
        addressData.setPhone(phoneView.getText().toString());
        addressData.setArea(areaView.getText().toString());
        addressData.setDetailAddress(addressView.getText().toString());
        addressData.setDefaultChoosed(defaultChooseView.isChecked());
        return addressData;
    }

    @Override
    public String getEditAddress() {
        return addressView.getText().toString();
    }


    @Override
    public boolean getCheckBoxState() {
        return defaultChooseView.isChecked();
    }

    @Override
    public Activity getActivity(){
        return this;
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite(){

    }

    @Override
    public void swipeBackCallback() {

    }

    @OnClick({R.id.icon_return_bt,R.id.address_area_relate,R.id.address_save_data})
    void userClick(View view){
        switch (view.getId()){
            case R.id.icon_return_bt:
                scrollToFinishActivity();
                break;
            case R.id.address_area_relate:
                onAddressPicker();
                break;
            case R.id.address_save_data:
                LogUtil.print("save data");
                addressPresenter.dealSaveClick();
                break;
        }
    }

    public void onAddressPicker(){
        if(task == null){
            task = new AddressPickTask(this);
            task.setHideProvince(false);
            task.setHideCounty(false);
            task.setCallback(new AddressPickTask.Callback() {
                @Override
                public void onAddressInitFailed() {
                    Log.e("lookat", "数据初始化失败");
                }
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    if (county == null) {
                        Log.e("lookat", province.getAreaName() + city.getAreaName());
                    } else {
                        areaView.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                        //Log.e("lookat", province.getAreaName() + city.getAreaName() + county.getAreaName());
                    }
                }
            });
            task.execute("贵州", "毕节", "纳雍");
        }else{
            task.openAddressPicker();
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
}
