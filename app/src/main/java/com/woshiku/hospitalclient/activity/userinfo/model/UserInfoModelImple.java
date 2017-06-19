package com.woshiku.hospitalclient.activity.userinfo.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import com.example.preorderlibrary.utils.FileUtils;
import com.example.preorderlibrary.utils.UploadFileUtil;
import com.example.preorderlibrary.views.PhotoView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.soundcloud.android.crop.Crop;
import com.woshiku.albumlibrary.activity.AlbumActivity;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.io.File;
import java.util.List;
import common.Global;
import domain.UserInfoData;

/**
 * Created by Administrator on 2017/3/21.
 */
public class UserInfoModelImple implements UserInfoModel {
    public static final int ACTION_TAKE_PICTURE = 0x000000;
    protected static final String IMAGE_SELECTED_ADDR = "imagesAddr";
    String takePhotoAddr;//用户拍照地址
    String userCropPath;//用户裁剪地址
    UserInfoListener userInfoListener;
    CommonUrl commonUrl;
    BitmapUtils bitmapUtil;
    @Override
    public void initPage(String title, UserInfoListener userInfoListener) {
        this.userInfoListener = userInfoListener;
        userInfoListener.initPage(title);
        bitmapUtil = new BitmapUtils(userInfoListener.getUserContext());
        bitmapUtil.configDefaultLoadFailedImage(R.mipmap.icon_head);
        commonUrl = new CommonUrl();
    }

    @Override
    public void submitFamilyInfo(final UserInfoListener userInfoListener) {
        UserInfoData userInfoData = userInfoListener.getUserInfo();
        if(!TextUtils.isEmpty(userInfoData.getName())){

        }else{
            userInfoListener.toastError("用户名不能为空");
            return;
        }
        if(!TextUtils.isEmpty(userInfoData.getSex())){

        }else{
            userInfoListener.toastError("性别不能为空");
            return;
        }
        if(!TextUtils.isEmpty(userInfoData.getDateOfBirth())){

        }else{
            userInfoListener.toastError("出生日期不能为空");
            return;
        }
        if(!TextUtils.isEmpty(userCropPath)){
            userInfoListener.showWait();
            UploadFileUtil uploadFileUtil = new UploadFileUtil(userInfoListener.getActivity(),userCropPath);
            uploadFileUtil.setUploadFileListener(new UploadFileUtil.UploadFileListener() {
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
                                addMyFamily(result.getMsg());//用于我的界面头像
                            }else{
                                userInfoListener.toastError("由于网络原因,上传失败!");
                                userInfoListener.closeWait();
                            }
                            break;
                        case 4:
                            userInfoListener.toastError("由于网络原因,上传失败!");
                            userInfoListener.closeWait();
                            break;
                        case 5:
                            userInfoListener.toastError("由于图片地址原因,上传失败!");
                            userInfoListener.closeWait();
                            break;
                    }
                }
            });
            uploadFileUtil.start(Global._id);
        }else{
            userInfoListener.toastError("请选择完头像后再提交");
        }
    }

    @Override
    public void dealActivityReturn(int requestCode, int resultCode, Intent data,UserInfoListener userInfoListener) {
        if(resultCode == userInfoListener.getActivity().RESULT_OK){
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
    public void dealBroadCast(Context context, String type, Bundle bd,UserInfoListener userInfoListener) {
        if(type.equals("albumResult")){
            List<String> selectedPicList = bd.getStringArrayList(IMAGE_SELECTED_ADDR);
            String path = selectedPicList.get(0);
            Log.e("lookat","my dest"+FileUtils.getUserIconName());
            userCropPath = FileUtils.getUserIconName();
            beginCrop(path, userCropPath);
            Log.e("lookat", "path" + selectedPicList.get(0));
        }
    }
    /**
     * 编辑图片对话框
     * */
    @Override
    public void editPicDialog(UserInfoListener userInfoListener) {
        iconEdit(userInfoListener.getEditText());
    }

    private void loadImage(String userCropPath) {
        bitmapUtil.display(userInfoListener.getUserIcon(),userCropPath);
    }

    /**
     * 添加我的家庭用于我的家庭主界面更新
     * */
    protected void addMyFamily(final String iconPath){
        new Thread(new Runnable() {
            @Override
            public void run(){
                Result result = commonUrl.loadUrlAsc(userInfoListener.getFamilyData(iconPath, true));
                if(result.isSuccess()){
                    userInfoListener.closeWait();
                    userInfoListener.submitSuccess();//提交成功
                }else{
                    userInfoListener.closeWait();
                    userInfoListener.toastError("由于网络原因,上传失败!");
                }
            }
        }).start();
    }
    /**
     * 拍照
     * */
    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.addCategory("android.intent.category.DEFAULT");
        takePhotoAddr = FileUtils.generImageName();
        File file = new File(takePhotoAddr);
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent .putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头 startActivityForResult(intent, 1);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        userInfoListener.getActivity().startActivityForResult(openCameraIntent, ACTION_TAKE_PICTURE);
    }

    /**
     * 拍照是否成功
     * */
    public void photoIsOk(boolean success){
        if(success){
            //拍照成功后加入数据库
            userInfoListener.getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(takePhotoAddr))));
            userCropPath = FileUtils.getUserIconName();
            beginCrop(takePhotoAddr,userCropPath);
            Log.e("lookat", "ph" + takePhotoAddr);
        }else{
            takePhotoAddr = "";
        }
    }

    /**
     * 裁剪图片
     * */
    protected void beginCrop(String sourcePath,String destPath) {
        Uri source  = Uri.fromFile(new File(sourcePath));
        Uri destination = Uri.fromFile(new File(destPath));
        Crop.of(source, destination).asSquare().start(userInfoListener.getActivity());
    }
    public boolean handleCrop(int resultCode, Intent result) {
        if(resultCode == userInfoListener.getActivity().RESULT_OK) {
            Crop.getOutput(result);
            return true;
        } else if (resultCode == Crop.RESULT_ERROR) {
            userInfoListener.toastError(Crop.getError(result).getMessage());
            return false;
        }
        return false;
    }

    /**
     * 头像编辑
     * */
    protected void iconEdit(EditText editText) {
        new PhotoView(userInfoListener.getActivity(),editText).addAlbumChooseListener(new PhotoView.AlbumChooseListener() {
            @Override
            public void albumChoose() {
                Intent intent = new Intent(userInfoListener.getActivity(), AlbumActivity.class);
                Bundle bd = new Bundle();
                bd.putInt("mode", 1);
                intent.putExtras(bd);
                userInfoListener.getActivity().startActivity(intent);
            }
        }).addAlbumPhotoListener(new PhotoView.AlbumPhotoListener() {
            @Override
            public void albumPhoto(){
                takePhoto();//拍照
            }
        }).showDialog();
    }
}
