package com.woshiku.hospitalclient.activity.userinfo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.City;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.County;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.Province;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.userinfo.presenter.UserInfoPresenter;
import com.woshiku.hospitalclient.activity.userinfo.presenter.UserInfoPresenterImple;
import com.woshiku.hospitalclient.activity.userinfo.view.UserInfoView;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.AddressPickTask;
import com.woshiku.hospitalclient.utils.InputTools;
import com.woshiku.hospitalclient.view.ToastSuccView;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.wheelwidgetslib.view.BloodDialog;
import com.woshiku.wheelwidgetslib.view.SexDialog;
import com.woshiku.wheelwidgetslib.view.YmdDialog;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import de.hdodenhof.circleimageview.CircleImageView;
import domain.UserInfoData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import param.UpdateFamilyParam;
/**
 * Created by Administrator on 2017/3/21.
 */
public class UserInfoActivity extends BaseActivity implements UserInfoView, View.OnTouchListener {
    @InjectView(R.id.familiy_input_dateofbirth)
    EditText dateOfBirth;
    @InjectView(R.id.family_input_liveCity)
    EditText liveCity;
    @InjectView(R.id.family_input_bloodType)
    EditText bloodTyped;
    @InjectView(R.id.family_input_sex)
    EditText sex;
    @InjectView(R.id.family_input_user_icon)
    CircleImageView iconImage;
    @InjectView(R.id.family_input_name)
    EditText name;
    @InjectView(R.id.family_input_phone)
    EditText phone;
    @InjectView(R.id.family_input_gmy)
    EditText gmy;
    @InjectView(R.id.family_input_ywjj)
    EditText ywjj;
    @InjectView(R.id.family_input_jwbs)
    EditText jwbs;
    @InjectView(R.id.family_icon_text)
    TextView iconText;
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView okBt;
    @InjectView(R.id.icon_return_bt)
    LinearLayout returnBt;
    protected YmdDialog ymdDialog;
    protected SexDialog sexDialog;
    protected BloodDialog bloodDialog;
    AddressPickTask task;
    private BroadcastReceiver myReceiver;
    private IntentFilter intentFilter;
    protected static String actionAlbum = "com.woshiku.albumlibrary.activity.albumResult";
    UserInfoPresenter presenter;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_newfamily);
        ButterKnife.inject(this);
        presenter = new UserInfoPresenterImple(this);
        presenter.loadPage("完善用户信息");
        initBroadcast();
        dateOfBirth.setOnTouchListener(this);
        sex.setOnTouchListener(this);
        bloodTyped.setOnTouchListener(this);
        liveCity.setOnTouchListener(this);
        phone.setText(Global.loginMessage.phone);//用户手机号
        phone.setEnabled(false);
    }
    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void swipeBackCallback() {
        removeAllBroadCast();//移动广播
    }

    @Override
    public void initPage(String title) {
        initTitleBar(title);
    }

    @Override
    public void showWait() {
        openDialog();
    }

    @Override
    public void closeWait() {
        closeDialog();
    }

    @Override
    public void toastError(String msg) {
        toastShort(msg);
    }

    @Override
    public void submitSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ToastSuccView(UserInfoActivity.this).showShort();
                scrollToFinishActivity();
            }
        });
    }

    @Override
    public EditText getEditText() {
        return liveCity;
    }

    @Override
    public CircleImageView getUserIcon() {
        return iconImage;
    }

    @Override
    public Activity getActivity() {
        return UserInfoActivity.this;
    }

    private void initBroadcast(){
        intentFilter = new IntentFilter();
        myReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                presenter.dealBroadCast(context, intent.getExtras().getString("type"), intent.getExtras());
            }
        };
        addBroadCastAction(actionAlbum);
    }

    protected void addBroadCastAction(String action) {
        intentFilter.addAction(action);
        registerReceiver(myReceiver, intentFilter);
    }
    public void removeAllBroadCast() {
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
    }
    @Override
    public UserInfoData getUserInfo() {
        String nameText = name.getText().toString();
        String sexText = sex.getText().toString();
        String phoneText = phone.getText().toString();
        String bloodText = bloodTyped.getText().toString();
        String birthText = dateOfBirth.getText().toString();
        String gmyText = gmy.getText().toString();
        String jwbsText = jwbs.getText().toString();
        String liveCityText = liveCity.getText().toString();
        String ywjjText = ywjj.getText().toString();
        return new UserInfoData(nameText,sexText,phoneText,bloodText,birthText,gmyText,jwbsText,liveCityText,ywjjText);
    }

    @Override
    public CommonUrlData getFamilyData(String iconPic, boolean isBenRen) {
        String birthText = dateOfBirth.getText().toString();
        String cityText = liveCity.getText().toString();
        String bloodText = bloodTyped.getText().toString();
        String sexText = sex.getText().toString();
        String nameText = name.getText().toString();
        String phoneText = phone.getText().toString();
        String gmyText = gmy.getText().toString();
        String ywjjText = ywjj.getText().toString();
        String jwbsText = jwbs.getText().toString();
        return UpdateFamilyParam.updateFamily(Global._id, iconPic, nameText, sexText, phoneText, birthText,
                cityText, bloodText, gmyText, ywjjText, jwbsText);
    }

    @Override
    public Context getUserInfoContext() {
        return UserInfoActivity.this;
    }

    @Override
    public void initTitleBar(String title) {
        titleView.setText("  "+title);
        returnBt.setVisibility(View.GONE);
        okBt.setText("注册");
        initActivity();
    }

    @Override
    public void initActivity() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(false);//设置可以滑动
    }

    @OnClick({R.id.icon_return_bt,R.id.family_input_user_icon,R.id.concrete_bt})
    void userClick(View view){
        switch (view.getId()){
            case R.id.icon_return_bt:
                scrollToFinishActivity();
                break;
            case R.id.family_input_user_icon:
                presenter.editPicDialog();//编辑图片
                break;
            case R.id.concrete_bt:
                presenter.submitFamilyInfo();
                break;
        }
    }

    private void dealItemClick(EditText view){
        switch (view.getId()){
            case R.id.familiy_input_dateofbirth:
                dateOfBirth.setCursorVisible(false);
                if(ymdDialog == null){
                    ymdDialog = new YmdDialog(this,dateOfBirth).setChooseYmdListener(new YmdDialog.ChooseYmdListener() {
                        @Override
                        public void chooseYmd(int year, int month, int day) {
                            String m,d;
                            if(month<10){
                                m = "0"+month;
                            }else{
                                m = month+"";
                            }
                            if(day<10){
                                d = "0"+day;
                            }else{
                                d = day+"";
                            }
                            dateOfBirth.setText(year + "-" + m + "-" + d);
                        }
                    });
                }else{
                    ymdDialog.showYmd();
                }
                break;
            case R.id.family_input_sex:
                sex.setCursorVisible(false);
                if(sexDialog == null){
                    sexDialog = new SexDialog(this,sex).setSexListener(new SexDialog.SexListener() {
                        @Override
                        public void userChooseSex(boolean isNan) {
                            if(isNan){
                                sex.setText("男");
                            }else{
                                sex.setText("女");
                            }
                        }
                    });
                }else{
                    sexDialog.showSex();
                }
                break;
            case R.id.family_input_bloodType:
                bloodTyped.setCursorVisible(false);
                if(bloodDialog == null){
                    bloodDialog = new BloodDialog(this, bloodTyped).setBloodListener(new BloodDialog.BloodListener() {
                        @Override
                        public void bloodType(String bloodType) {
                            bloodTyped.setText(bloodType);
                        }
                    });
                }else {
                    bloodDialog.showBlood();
                }
                break;
            case R.id.family_input_liveCity:
                liveCity.setCursorVisible(false);
                onAddressPicker();
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
                        liveCity.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.dealActivityReturn(requestCode,resultCode,data);
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
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                InputTools.HideKeyboard(dateOfBirth);
                break;
            case MotionEvent.ACTION_UP:
                dealItemClick((EditText) v);
                break;
        }
        return false;
    }
}
