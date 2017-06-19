package com.example.preorderlibrary.utils;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;
import com.example.preorderlibrary.common.Global;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.xutils.common.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/6.
 */
public class UploadFileUtil {
    private String saveFilePath;
    private Context context;
    public interface UploadFileListener{
        /**
         * @param state compressPrepare-2 compressfail -1 compressok 0 start 1 loading 2 success 3 fail 4 file no exist 5
         * @param progress
         * */
        void uploadFile(int state,String progress,String successMsg);
    }
    public void setUploadState(int state,String progress,String successMsg){
        if(uploadFileListener != null){
            uploadFileListener.uploadFile(state,progress,successMsg);
        }
    }
    private UploadFileListener uploadFileListener;

    public void setUploadFileListener(UploadFileListener uploadFileListener) {
        this.uploadFileListener = uploadFileListener;
    }

    public UploadFileUtil(Context context,String saveFilePath) {
        this.saveFilePath = saveFilePath;
        this.context = context;
    }
    public void start(){
        uploadOneFile(saveFilePath);
    }
    public void start(String id){
        uploadFile(saveFilePath,id);
    }
    private void uploadFile(String filePath,String id){
        final File uploadFile = new File(filePath);
        if(uploadFile.exists()){
            //以下是xutils2.0的方法上传框架
            HttpUtils http = new HttpUtils();
            RequestParams params = new RequestParams();
            params.addBodyParameter("upload", uploadFile);
            params.addBodyParameter("userId", id);
            http.send(HttpRequest.HttpMethod.POST,
                    Global.uploadUrl,
                    params, new RequestCallBack<String>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            setUploadState(1, null, null);
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            super.onLoading(total, current, isUploading);
                            String pb = Formatter.formatFileSize(context, current) + "/" + Formatter.formatFileSize(context, total);
                            setUploadState(2, pb, null);
                        }

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            setUploadState(3, null, responseInfo.result);
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            setUploadState(4, null, null);
                        }
                    });
        }else{
            Log.e("lookat","uploadFile no exist");
            setUploadState(5,null,null);
        }
    }
    /**
     * @desc 上传单个文件
     * @param filePath 要上传文件的路径
     * @return void
     * */
    private void uploadOneFile(String filePath){
        final File uploadFile = new File(filePath);
        if(uploadFile.exists()){
            /*String url = Global.uploadUrl;
            Map<String,Object> map = new HashMap<>();
            map.put("upload",uploadFile);
            map.put("userid",Global.id);
            XUtil.UpLoadFile(url, map, new Callback.ProgressCallback() {
                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {
                    setUploadState(1, null, null);
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    String pb = Formatter.formatFileSize(context, current) + "/" + Formatter.formatFileSize(context, total);
                    setUploadState(2, pb, null);
                }

                @Override
                public void onSuccess(Object result) {
                    Log.e("lookat","test result");
                    Log.e("lookat",result+"");
                    Log.e("lookat","test result one");
                    setUploadState(3, null, "icon.png");
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    setUploadState(4, null, null);
                    Log.e("lookerror", "error", ex);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });*/
            Log.e("lookat","uploadFile exist");
            HttpUtils http = new HttpUtils();
            RequestParams params = new RequestParams();
            params.addBodyParameter("upload", uploadFile);
            params.addBodyParameter("userId", Global.id);
            http.send(HttpRequest.HttpMethod.POST,
                    Global.uploadUrl,
                    params, new RequestCallBack<String>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            setUploadState(1, null, null);
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            super.onLoading(total, current, isUploading);
                            String pb = Formatter.formatFileSize(context, current) + "/" + Formatter.formatFileSize(context, total);
                            setUploadState(2, pb, null);
                        }

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            setUploadState(3, null, responseInfo.result);
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            setUploadState(4, null, null);
                        }
                    });
        }else{
            Log.e("lookat","uploadFile no exist");
            setUploadState(5,null,null);
        }
    }
}
