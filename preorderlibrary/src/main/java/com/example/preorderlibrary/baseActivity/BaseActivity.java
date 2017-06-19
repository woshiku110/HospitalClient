package com.example.preorderlibrary.baseActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;
import com.example.preorderlibrary.fragments.impleContent.DiseaseDescription;
import com.example.preorderlibrary.fragments.impleContent.FragmentFactory;
import com.example.preorderlibrary.utils.PermissionHelper;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 *Created by willyou on 2016/11/25.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private BroadcastReceiver myReceiver;
    private IntentFilter intentFilter;
    protected String receMainaction = "com.woshiku.homehospital.base.mainAction";
    protected static String actionAlbum = "com.woshiku.albumlibrary.activity.albumResult";
    protected static final String IMAGE_SELECTED_ADDR = "imagesAddr";
    private PermissionHelper permissionHelper;
    //拍照返回值
    private static final int Camera_Result_Code = 10;
    //权限名称
    private static final String Camera_Permission = Manifest.permission.CAMERA;
    protected SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StatusBarCompat.compat(this, getResources().getColor(R.color.blue));
        intentFilter = new IntentFilter();
        myReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                dealBroadCast(context, intent.getExtras().getString("type"), intent.getExtras());
            }
        };
        addBroadCastAction(actionAlbum);
        //用户权限
        permissionHelper = new PermissionHelper(this);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.setSwipeBackListener(new SwipeBackActivityHelper.SwipeBackListener() {
            @Override
            public void swipeBack() {
                swipeBackCallback();
            }
        });
        mHelper.onActivityCreate();
        initViews();
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    public boolean isAllowPhoto(){
        boolean result = permissionHelper.checkPermission(Camera_Permission);
        return result;
    }
    public void setTakePhoto(){
        permissionHelper.permissionsCheck(Camera_Permission, Camera_Result_Code);
    }

    public abstract void initViews();

    public abstract void dealBroadCast(Context context, String type, Bundle bd);

    protected void addBroadCastAction(String action) {
        intentFilter.addAction(action);
        registerReceiver(myReceiver, intentFilter);
    }

    protected void sendMessage(String action, Bundle bd, String type) {
        Intent intent = new Intent();
        bd.putString("type", type);
        intent.setAction(action);
        intent.putExtras(bd);
        sendBroadcast(intent);
    }

    public  void sendMainMessage(Bundle bd, String type) {
        Intent intent = new Intent();
        bd.putString("type", type);
        intent.setAction(receMainaction);
        intent.putExtras(bd);
        sendBroadcast(intent);
    }

    public void removeAllBroadCast() {
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Camera_Result_Code:
                //打开手机的的相机
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DiseaseDescription diseaseDescription = (DiseaseDescription)FragmentFactory.getFragment(4);
                    diseaseDescription.takePhoto();
                } else {
                    //如果请求失败
                    permissionHelper.startAppSettings();
                    Toast.makeText(this, "请打开手机拍照权限，否则软件将无法正常运行。", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public abstract void swipeBackCallback();
    public SwipeBackLayout getSwipeBackLayout(){
        return mHelper.getSwipeBackLayout();
    }
    public void setGesture(boolean isok){
        mHelper.getSwipeBackLayout().setEnableGesture(isok);
    }
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        mHelper.getSwipeBackLayout().scrollToFinishActivity();
    }
}
