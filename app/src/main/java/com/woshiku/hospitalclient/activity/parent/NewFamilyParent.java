package com.woshiku.hospitalclient.activity.parent;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.preorderlibrary.utils.PermissionHelper;
import com.lidroid.xutils.BitmapUtils;
import com.soundcloud.android.crop.Crop;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.City;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.County;
import com.woshiku.citylibrary.WheelPicker.src.main.java.cn.qqtheme.framework.entity.Province;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.AddressPickTask;
import com.woshiku.hospitalclient.utils.InputTools;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.url.base.CommonUrl;
import com.woshiku.wheelwidgetslib.view.BloodDialog;
import com.woshiku.wheelwidgetslib.view.SexDialog;
import com.woshiku.wheelwidgetslib.view.YmdDialog;
import java.io.File;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import de.hdodenhof.circleimageview.CircleImageView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/1/10.
 */
public abstract class NewFamilyParent extends BaseActivity implements View.OnTouchListener {
    @InjectView(R.id.familiy_input_dateofbirth)
    protected EditText dateOfBirth;
    @InjectView(R.id.family_input_liveCity)
    protected EditText liveCity;
    @InjectView(R.id.family_input_bloodType)
    protected EditText bloodTyped;
    @InjectView(R.id.family_input_sex)
    protected EditText sex;
    @InjectView(R.id.family_input_user_icon)
    protected CircleImageView iconImage;
    @InjectView(R.id.family_input_name)
    protected EditText name;
    @InjectView(R.id.family_input_phone)
    protected EditText phone;
    @InjectView(R.id.family_input_gmy)
    protected EditText gmy;
    @InjectView(R.id.family_input_ywjj)
    protected EditText ywjj;
    @InjectView(R.id.family_input_jwbs)
    protected EditText jwbs;
    @InjectView(R.id.family_icon_text)
    protected TextView iconText;
    @InjectView(R.id.txt_title)
    TextView title;
    @InjectView(R.id.concrete_bt)
    TextView okBt;
    private SwipeBackLayout swipeBackLayout;
    protected PermissionHelper permissionHelper;
    protected static final String IMAGE_SELECTED_ADDR = "imagesAddr";
    //拍照返回值
    protected static final int Camera_Result_Code = 10;
    //权限名称
    protected static final String Camera_Permission = Manifest.permission.CAMERA;
    private BroadcastReceiver myReceiver;
    private IntentFilter intentFilter;
    protected static String actionAlbum = "com.woshiku.albumlibrary.activity.albumResult";
    AddressPickTask task;
    BitmapUtils bitmapUtils;
    protected CommonUrl commonUrl;
    protected int action;
    protected abstract void dealBroadCast(Context context, String type, Bundle bd);
    protected abstract void submitFamilyInfo();
    protected abstract CommonUrlData getFamilyData(String iconPic,boolean isBenRen);
    protected abstract void lookFamilyData(String[] datas,boolean isEdit);
    protected abstract void setWidgetEdit(boolean isEdit);
    protected YmdDialog ymdDialog;
    protected SexDialog sexDialog;
    protected BloodDialog bloodDialog;
    protected String []stateText = {"确定","修改"};
    protected String currentStateText = stateText[0];
    protected boolean isEditUser = false;
    protected String userId;
    protected String userIcon;
    protected boolean isMyUser = false;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_newfamily);
        ButterKnife.inject(this);
        initDatas();
        swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动

    }
    private void initDatas(){
        initBroadcast();
        bitmapUtils = new BitmapUtils(NewFamilyParent.this);
        commonUrl = new CommonUrl();
        try {
            Bundle bd = getIntent().getExtras();
            action = bd.getInt("action");
            if(action == Global.LOOK_USER){
                title.setText("查看用户");
                iconText.setVisibility(View.INVISIBLE);
                String []datas = bd.getStringArray("data");
                if(datas.length>0){
                    lookFamilyData(datas,false);
                    currentStateText = stateText[1];
                    okBt.setText(currentStateText);
                }else{
                    String []dd = new String[8];
                    lookFamilyData(dd,false);
                }
            }else if(action == Global.EDIT_USER){
                title.setText("编辑用户");
                iconText.setVisibility(View.VISIBLE);
                String []datas = bd.getStringArray("data");
                isEditUser = true;
                if(datas.length>0){
                    lookFamilyData(datas,true);
                    currentStateText = stateText[0];
                    okBt.setText(currentStateText);
                }else if(action == Global.REVISE_MY_USER){//用于修改本人用户
                    title.setText("查看用户");
                    iconText.setVisibility(View.INVISIBLE);
                }else{
                    String []dd = new String[8];
                    lookFamilyData(dd,true);
                }
            }else if(action == Global.MY_NEW_USER){
                isMyUser = true;
                title.setText("添加用户");
            }else{
                title.setText("添加用户");
            }
        }catch (Exception e){
        }
        dateOfBirth.setOnTouchListener(this);
        sex.setOnTouchListener(this);
        bloodTyped.setOnTouchListener(this);
        liveCity.setOnTouchListener(this);
        //用户权限
        permissionHelper = new PermissionHelper(this);
    }
    private void initBroadcast(){
        intentFilter = new IntentFilter();
        myReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                dealBroadCast(context, intent.getExtras().getString("type"), intent.getExtras());
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
    public void swipeBackCallback() {
        Log.e("lookat", "调用一下");
        removeAllBroadCast();
        InputTools.HideKeyboard(dateOfBirth);
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

    @OnClick({R.id.icon_return_bt,R.id.concrete_bt,R.id.family_input_user_icon})
    void userClick(View view){
        switch (view.getId()){
            case R.id.icon_return_bt:
                scrollToFinishActivity();
                break;
            case R.id.concrete_bt:
                if(currentStateText == stateText[0]){
                    submitFamilyInfo();
                }else{
                    isEditUser = true;
                    currentStateText = stateText[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title.setText("编辑用户");
                            okBt.setText(currentStateText);
                            iconText.setVisibility(View.VISIBLE);
                        }
                    });
                    setWidgetEdit(true);
                }
                break;
            case R.id.family_input_user_icon:
                iconEdit(dateOfBirth);
                break;
        }
    }

    protected abstract void iconEdit(EditText ed);
    protected abstract void takePhoto();
    public boolean isAllowPhoto(){
        boolean result = permissionHelper.checkPermission(Camera_Permission);
        return result;
    }

    public void setTakePhoto(){
        permissionHelper.permissionsCheck(Camera_Permission, Camera_Result_Code);
    }
    protected void beginCrop(String sourcePath,String destPath) {
        Uri source  = Uri.fromFile(new File(sourcePath));
        Uri destination = Uri.fromFile(new File(destPath));
        Crop.of(source, destination).asSquare().start(this);
    }

    protected boolean handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Crop.getOutput(result);
            /*Log.e("lookat","handle crop"+Crop.getOutput(result));*/
            return true;
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
    protected void loadImage(String path){
        bitmapUtils.display(iconImage,path);
    }
}
