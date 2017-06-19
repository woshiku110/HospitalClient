package com.woshiku.hospitalclient.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;
import com.example.preorderlibrary.utils.FileUtils;
import com.example.preorderlibrary.utils.UploadFileUtil;
import com.example.preorderlibrary.views.PhotoView;
import com.google.gson.Gson;
import com.soundcloud.android.crop.Crop;
import com.woshiku.albumlibrary.activity.AlbumActivity;
import com.woshiku.hospitalclient.activity.parent.NewFamilyParent;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.domain.Result;
import java.io.File;
import java.util.List;
import common.Global;
import param.CreateFamilyParam;
import param.MyFamilyParam;
import param.UpdateFamilyParam;

/**
 * Created by Administrator on 2017/1/10.
 * public void enterEditActivty(String imageAddr,String saveAddr){
 Intent it = new Intent(this, EditImageActivity.class);
 it.putExtra(EditImageActivity.FILE_PATH, imageAddr);
 File outputFile = new File(saveAddr);
 it.putExtra(EditImageActivity.EXTRA_OUTPUT,
 outputFile.getAbsolutePath());
 startActivityForResult(it,
 ACTION_REQUEST_EDITIMAGE);
 }
 */
public class NewFamilyActivity extends NewFamilyParent implements UploadFileUtil.UploadFileListener {
    public static final int ACTION_TAKE_PICTURE = 0x000000;
    public static final int ACTION_REQUEST_EDITIMAGE = 9;
    String takePhotoAddr;
    String userCropPath;
    /**
     * 拍照
     * */
    @Override
    protected void takePhoto(){
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.addCategory("android.intent.category.DEFAULT");
        takePhotoAddr = FileUtils.generImageName();
        File file = new File(takePhotoAddr);
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent .putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头 startActivityForResult(intent, 1);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, ACTION_TAKE_PICTURE);
    }
    /**
     * 拍照是否成功
     * */
    public void photoIsOk(boolean success){
        if(success){
            //拍照成功后加入数据库
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(takePhotoAddr))));
            userCropPath = FileUtils.getUserIconName();
            beginCrop(takePhotoAddr,userCropPath);
            Log.e("lookat","ph"+takePhotoAddr);
        }else{
            takePhotoAddr = "";
        }
    }

    @Override
    protected void dealBroadCast(Context context, String type, Bundle bd) {
        if(type.equals("albumResult")){
            List<String> selectedPicList = bd.getStringArrayList(IMAGE_SELECTED_ADDR);
            String path = selectedPicList.get(0);
            Log.e("lookat","my dest"+FileUtils.getUserIconName());
            userCropPath = FileUtils.getUserIconName();
            beginCrop(path, userCropPath);
            Log.e("lookat", "path" + selectedPicList.get(0));
        }
    }

    @Override
    protected void submitFamilyInfo() {
        if(!TextUtils.isEmpty(name.getText().toString())){

        }else{
            toastShort("用户名不能为空");
            return;
        }
        if(!TextUtils.isEmpty(sex.getText().toString())){

        }else{
            toastShort("性别不能为空");
            return;
        }
        if(!TextUtils.isEmpty(dateOfBirth.getText())){

        }else{
            toastShort("出生日期不能为空");
            return;
        }
        //是否是编辑自身
        if(isEditUser){
            if(!TextUtils.isEmpty(userCropPath)){//用户选择了新的头像
                openDialog();
                UploadFileUtil uploadFileUtil = new UploadFileUtil(this,userCropPath);
                uploadFileUtil.setUploadFileListener(this);
                uploadFileUtil.start(Global._id);
                LogUtil.print("编辑自身"+isEditUser+"\t"+"上传头像");
            }else{//用户没有选择头像
                openDialog();
                editFamily(userId, userIcon);
                LogUtil.print("编辑自身" + isEditUser + "\t" + "本身头像");
            }
        }else{
            if(!TextUtils.isEmpty(userCropPath)){
                if(action == Global.NEW_USER){
                    openDialog();
                    UploadFileUtil uploadFileUtil = new UploadFileUtil(this,userCropPath);
                    uploadFileUtil.setUploadFileListener(this);
                    uploadFileUtil.start(Global._id);
                }else if(action == Global.MY_NEW_USER){
                    openDialog();
                    UploadFileUtil uploadFileUtil = new UploadFileUtil(this,userCropPath);
                    uploadFileUtil.setUploadFileListener(this);
                    uploadFileUtil.start(Global._id);
                }
            }else{
                toastShort("请选择完头像后再提交");
            }
        }

    }

    /**
     * state start 1 loading 2 success 3 fail 4 file no exist 5
     * */
    @Override
    public void uploadFile(int state, String progress, String successMsg) {
        switch (state){
            case 1:

                break;
            case 2:

                break;
            case 3:
                Result result = new Gson().fromJson(successMsg,Result.class);
                if(result.isSuccess()){
                    LogUtil.print(result.getMsg());
                    if(isEditUser){//编辑用户
                        editFamily(userId,result.getMsg());
                    }else{//新增用户
                        if(isMyUser){
                            addMyFamily(result.getMsg());//用于我的界面头像
                        }else{
                            addFamily(result.getMsg());
                        }
                    }
                }else{
                    toastShort("由于网络原因,上传失败!");
                    closeDialog();
                }
                break;
            case 4:
                toastShort("由于网络原因,上传失败!");
                closeDialog();
                break;
            case 5:
                toastShort("由于图片地址原因,上传失败!");
                closeDialog();
                break;
        }
    }

    protected void addFamily(final String iconPath){
        new Thread(new Runnable() {
            @Override
            public void run(){
                Result result = commonUrl.loadUrlAsc(getFamilyData(iconPath,false));
                if(result.isSuccess()){
                    closeDialog();
                    submitOkDialog();
                    final Intent intent = new Intent();
                    Bundle bd = new Bundle();
                    bd.putString("myfamily",getMyFamilyMsg());
                    intent.putExtras(bd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setResult(Global.CreateFamilyReturn,intent);
                            scrollToFinishActivity();
                        }
                    });
                }else{
                    closeDialog();
                    toastShort("由于网络原因,上传失败!");
                }
            }
        }).start();
    }
    //添加我的家庭用于我的家庭主界面更新
    protected void addMyFamily(final String iconPath){
        new Thread(new Runnable() {
            @Override
            public void run(){
                Result result = commonUrl.loadUrlAsc(getFamilyData(iconPath,true));
                if(result.isSuccess()){
                    closeDialog();
                    submitOkDialog();
                    final Intent intent = new Intent();
                    Bundle bd = new Bundle();
                    bd.putString("myfamily","update");
                    intent.putExtras(bd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setResult(Global.CreateMyFamilyReturn,intent);
                            scrollToFinishActivity();
                        }
                    });
                }else{
                    closeDialog();
                    toastShort("由于网络原因,上传失败!");
                }
            }
        }).start();
    }
    //编辑家庭(用户选择了头像)
    protected void editFamily(final String id, final String iconPath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result result = commonUrl.loadUrlAsc(updateFamilyData(id,iconPath));
                if(result.isSuccess()){
                    LogUtil.print(result.getMsg());
                    closeDialog();
                    submitOkDialog();
                    final Intent intent = new Intent();
                    Bundle bd = new Bundle();
                    bd.putString("myfamily", getMyFamilyMsg());
                    intent.putExtras(bd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setResult(Global.CreateFamilyReturn,intent);
                            scrollToFinishActivity();
                        }
                    });
                }else{
                    closeDialog();
                    toastShort("由于网络原因,上传失败!");
                }
            }
        }).start();
    }
    protected String getMyFamilyMsg(){
        String msg = null;
        Result result = commonUrl.loadUrlAsc(MyFamilyParam.myFamily());
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(),Result.class);
            if(rr.isSuccess()){
                return rr.getMsg();
            }
        }
        return msg;
    }
    protected CommonUrlData getFamilyData(String iconPic,boolean isBenRen){
        String birthText = dateOfBirth.getText().toString();
        String cityText = liveCity.getText().toString();
        String bloodText = bloodTyped.getText().toString();
        String sexText = sex.getText().toString();
        String nameText = name.getText().toString();
        String phoneText = phone.getText().toString();
        String gmyText = gmy.getText().toString();
        String ywjjText = ywjj.getText().toString();
        String jwbsText = jwbs.getText().toString();
        return CreateFamilyParam.createFamily(iconPic, nameText, sexText, phoneText, birthText,
                cityText, bloodText, gmyText, ywjjText, jwbsText,isBenRen);
    }
    protected CommonUrlData updateFamilyData(String id,String iconPic){
        String birthText = dateOfBirth.getText().toString();
        String cityText = liveCity.getText().toString();
        String bloodText = bloodTyped.getText().toString();
        String sexText = sex.getText().toString();
        String nameText = name.getText().toString();
        String phoneText = phone.getText().toString();
        String gmyText = gmy.getText().toString();
        String ywjjText = ywjj.getText().toString();
        String jwbsText = jwbs.getText().toString();
        return UpdateFamilyParam.updateFamily(id, iconPic, nameText, sexText, phoneText, birthText,
                cityText, bloodText, gmyText, ywjjText, jwbsText);
    }
    //\"1\",\"张小胖\",\"男\",\"2010-11-09\",\"A\",\"对素食过敏 就得吃肉\",\"  禁吃过期的\",\"一直有神经病\",\"13838385438\",\"北京\
    @Override
    protected void lookFamilyData(String[] datas,boolean isEdit) {
        userId = datas[0];
        name.setText(datas[1]);
        sex.setText(datas[2]);
        dateOfBirth.setText(datas[3]);
        bloodTyped.setText(datas[4]);
        gmy.setText(datas[5]);
        ywjj.setText(datas[6]);
        jwbs.setText(datas[7]);
        phone.setText(datas[8]);
        liveCity.setText(datas[9]);
        loadImage(Global.fileAddr + datas[10]);
        userIcon = datas[10];
        setWidgetEdit(isEdit);
    }

    @Override
    protected void setWidgetEdit(boolean isEdit){
        name.setEnabled(isEdit);
        sex.setEnabled(isEdit);
        dateOfBirth.setEnabled(isEdit);
        bloodTyped.setEnabled(isEdit);
        gmy.setEnabled(isEdit);
        ywjj.setEnabled(isEdit);
        jwbs.setEnabled(isEdit);
        phone.setEnabled(isEdit);
        liveCity.setEnabled(isEdit);
        iconImage.setEnabled(isEdit);
    }
    /**
     * 头像编辑
     * */
    @Override
    protected void iconEdit(EditText editText) {
        new PhotoView(this,editText).addAlbumChooseListener(new PhotoView.AlbumChooseListener() {
            @Override
            public void albumChoose() {
                Intent intent = new Intent(NewFamilyActivity.this, AlbumActivity.class);
                Bundle bd = new Bundle();
                bd.putInt("mode",1);
                intent.putExtras(bd);
                startActivity(intent);
            }
        }).addAlbumPhotoListener(new PhotoView.AlbumPhotoListener() {
            @Override
            public void albumPhoto() {
                if (isAllowPhoto()) {
                    takePhoto();
                } else {
                    setTakePhoto();
                }
            }
        }).showDialog();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case ACTION_TAKE_PICTURE:
                    if(resultCode == -1){
                        photoIsOk(true);//拍照成功
                    }else{
                        photoIsOk(false);//拍照失败
                    }
                    break;
                case Crop.REQUEST_CROP:
                    if(handleCrop(resultCode, data)){
                        loadImage(userCropPath);
                    }
                    break;
            }
        }
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Camera_Result_Code:
                //打开手机的的相机
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    //如果请求失败
                    permissionHelper.startAppSettings();
                    Toast.makeText(this, "请打开手机拍照权限，否则软件将无法正常运行。", Toast.LENGTH_SHORT).show();
                }
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
}
