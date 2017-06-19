package com.example.preorderlibrary.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.preorderlibrary.common.PhotoData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017/1/9.
 */
public class CompressFileUtil {
    private List<PhotoData> uploadFileList;
    private List<String> imageList;
    private ManageThread manageThread;
    private int count;
    private Object obj = new Object();
    private CompressResultListener compressResultListener;

    public interface CompressResultListener{
        void compressResult(int size,boolean isEq);
    }

    public CompressFileUtil setCompressResultListener(CompressResultListener compressResultListener) {
        this.compressResultListener = compressResultListener;
        return this;
    }

    public CompressFileUtil(List<PhotoData> uploadFileList,ManageThread manageThread) {
        this.uploadFileList = uploadFileList;
        this.manageThread = manageThread;
    }

    /**
     * @desc 压缩图片
     * @param picPath 图片的路径
     * @param savePath 图片的保存路径
     * @return void
     * */
    private void compressOnePic(String picPath,String savePath){
        Bitmap bmp = PicPressUtil.getSmallBitmap(picPath);
        PicPressUtil.saveBitmap(bmp, savePath);
        synchronized (obj){
            count++;
            Log.e("lookat", "imageAddr" + picPath + "\t" + "pressed pic" + savePath+"\tcount:"+count);
            if(count==imageList.size()){
                if(compressResultListener != null){
                    compressResultListener.compressResult(count,true);
                    Log.e("lookat", "compress finished");
                }
            }
        }
    }
    public void start(){
        imageList = new ArrayList<>();
        for(PhotoData uploadFile:uploadFileList){
            if(uploadFile.type != 0){
                imageList.add(uploadFile.imageAddr);
            }
        }
        for(int i=0;i<imageList.size();i++){
            manageThread.carryCompressed(new CompressedFile(imageList.get(i), FileUtils.getPressedName(i)));
        }
    }

    class CompressedFile implements Callable<Object>{
        private String compressedName;
        private String uploadFilePath;
        public CompressedFile(String uploadFilePath,String compressedName) {
            this.uploadFilePath = uploadFilePath;
            this.compressedName = compressedName;
        }
        @Override
        public Object call() throws Exception {
            compressOnePic(uploadFilePath,compressedName);
            return null;
        }
    }
}
