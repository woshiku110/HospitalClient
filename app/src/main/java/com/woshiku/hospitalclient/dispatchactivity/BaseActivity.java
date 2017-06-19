package com.woshiku.hospitalclient.dispatchactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;
import com.example.preorderlibrary.utils.PermissionHelper;
import com.example.preorderlibrary.views.ToastSuccView;
import com.woshiku.urllibrary.url.base.CommonUrl;
import com.woshiku.waitlibrary.WaitDialog;
import common.Global;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
/**
 * Created by fhf11991 on 2016/7/25.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected abstract void initView();
    protected WaitDialog waitDialog;
    protected CommonUrl hpUtil;
    protected Handler mHandler;
    protected SwipeBackActivityHelper mHelper;
    private PermissionHelper permissionHelper;//权限问题
    //拍照返回值
    private static final int Camera_Result_Code = 10;
    private static final int Write_Result_Code = 11;
    //权限名称
    private static final String Camera_Permission = Manifest.permission.CAMERA;
    private static final String Write_Permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    protected abstract void allowPhoto();
    protected abstract void allowWrite();
    private String who = "";//哪个fragment
    private View view = null;//哪个view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.setSwipeBackListener(new SwipeBackActivityHelper.SwipeBackListener() {
            @Override
            public void swipeBack() {
                swipeBackCallback();
            }
        });
        mHelper.onActivityCreate();
        setGesture(false);
        initView();
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
        initDatas(parentView);
        initPermission();//初始化权限
    }
    private void initPermission(){
        //用户权限
        permissionHelper = new PermissionHelper(this);
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

    private void initDatas(View parentView){
        if(waitDialog == null){
            waitDialog = new WaitDialog(this,parentView);
        }
        hpUtil = new CommonUrl();
        if(mHandler == null){
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case Global.loading:
                            waitDialog.showDialog();
                            break;
                        case Global.loadOk:
                            waitDialog.closeDialog();
                            break;
                        case Global.loadFail:
                            Toast.makeText(BaseActivity.this,"网络加载失败!!!",Toast.LENGTH_SHORT).show();
                            break;
                        case Global.parseFail:
                            Toast.makeText(BaseActivity.this,"数据解析失败!!!",Toast.LENGTH_SHORT).show();
                            break;
                        case Global.parseFailWithReason:
                            Toast.makeText(BaseActivity.this,msg.getData().getString("failreason"),Toast.LENGTH_SHORT).show();
                            break;
                        case Global.shortToast:
                            String shortToast = msg.getData().getString("shortToast");
                            Toast.makeText(BaseActivity.this,shortToast,Toast.LENGTH_SHORT).show();
                            break;
                        case Global.submitOk:
                            new ToastSuccView(BaseActivity.this).showShort();
                            break;
                    }
                }
            };
        }
    }
    public void openDialog(){
        mHandler.sendEmptyMessage(Global.loading);
    }
    public void closeDialog(){
        mHandler.sendEmptyMessage(Global.loadOk);
    }
    protected void submitOkDialog(){
        mHandler.sendEmptyMessage(Global.submitOk);
    }
    protected void toastShort(String msg){
       Message message = new Message();
       Bundle bd = new Bundle();
       bd.putString("shortToast", msg);
       message.setData(bd);
       message.setData(bd);
       message.what = Global.shortToast;
       mHandler.sendMessage(message);
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
    public boolean isAllowPhoto(String who,View view){
        this.who =who;
        this.view = view;
        boolean result = permissionHelper.checkPermission(Camera_Permission);
        return result;
    }
    public void setTakePhoto(){
        permissionHelper.permissionsCheck(Camera_Permission, Camera_Result_Code);
    }
    public boolean isAllowWrite(String who,View view){
        this.who =who;
        this.view = view;
        boolean result = permissionHelper.checkPermission(Write_Permission);
        return result;
    }
    public void setTakeWrite(){
        permissionHelper.permissionsCheck(Write_Permission, Write_Result_Code);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case Camera_Result_Code://打开手机的的相机
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    allowPhoto();
                } else {
                    permissionHelper.startAppSettings();//如果请求失败
                    Toast.makeText(this, "请打开手机拍照权限，否则软件将无法正常运行。", Toast.LENGTH_SHORT).show();
                }
                break;
            case Write_Result_Code:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    allowWrite();
                } else {
                    permissionHelper.startAppSettings();//如果请求失败
                    Toast.makeText(this, "请打开手机存储权限，否则软件将无法正常运行。", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    protected String getWho(){
        return who;
    }
    protected View getFragmentView(){
        return view;
    }
}
