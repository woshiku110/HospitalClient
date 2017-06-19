package com.example.preorderlibrary.fragments.impleContent;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.preorderlibrary.PreOrderActivity;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.domain.UrlResult;
import com.example.preorderlibrary.fragments.base.BaseFragment;
import com.example.preorderlibrary.utils.AdjustDate;
import com.example.preorderlibrary.utils.FileUtils;
import com.example.preorderlibrary.utils.ManageThread;
import com.example.preorderlibrary.utils.UploadFileUtil;
import com.example.preorderlibrary.utils.UrlDataUtil;
import com.example.preorderlibrary.views.ToastSuccView;
import com.google.gson.Gson;
import com.woshiku.mydatepicker.cons.DPMode;
import com.woshiku.mydatepicker.views.DatePicker;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.inter.CommonUrlListener;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
/**
 * Created by willyou on 2016/12/11.
 * 日期选择
 * 最终提交订单数据在此Fragment中保存
 */

@SuppressLint("ValidFragment")
public class OrderDataFragment extends BaseFragment implements CommonUrlListener {
    private Calendar calendar;
    private String submitPersonId;//当前提交病人ID
    private String partsId;//身体部位ID
    private String description;//病症描述
    private String submitPhotoTitles;//病人提交照片标题
    private String questionAnswers;//病症问题答案集合
    private String exOrderDate;//期望预约日期
    private String diseaseImageIds;//病症部位图片ID
    private List<String> failUploadList;//图片上传失败的列表
    private List<String> succUploadList;//图片上传成功列表
    private String userChooseDate;//用户选择的日期
    private ManageThread manageThread;
    private boolean havePics = false;
    private boolean isFinished = false;
    private int pressedPicAmount = 0;
    private int orderState = 0;
    CommonUrl commonUrl;
    @SuppressLint("ValidFragment")
    public OrderDataFragment(FragmentActivity mActiviy) {
        super(mActiviy);
    }

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_orderdate, null);
        fm = (FrameLayout) mView.findViewById(R.id.fm_layout);
        TextView titleText = (TextView) mView.findViewById(R.id.txt_title);
        DatePicker datePicker = (DatePicker) mView.findViewById(R.id.date_picker);
        final Button dateSubmitBtn = (Button) mView.findViewById(R.id.date_submit);
        //为日历设置当前系统时间
        calendar = Calendar.getInstance();
        datePicker.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1);
        datePicker.setFestivalDisplay(false);
        datePicker.setHolidayDisplay(false);
        datePicker.setMode(DPMode.MULTIPLE);
        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                if (date.size() == 0) {
                    dateSubmitBtn.setEnabled(false);
                } else {
                    dateSubmitBtn.setEnabled(true);
                }
                Gson gson = new Gson();
                userChooseDate = gson.toJson(AdjustDate.getDateList(date));
                Log.e("partId",userChooseDate);
            }
        });
        titleText.setText("选择时间");
        mView.findViewById(R.id.icon_return_bt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Global.pre_type.equals("3")){//健康
                            switchHealthyViewPager(1);
                        }else {//中医 西医
                            if (Global.diseaseIsEmpty) {
                                switchViewPager(DISEASEDESCROPTION);
                            } else {
                                switchViewPager(DISEASEQUESTION);
                            }
                        }
                    }
                });
        dateSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lookat","submit data");
                uploadFiles();//上传文件
            }
        });
        showContent();
        initData();
        return mView;
    }
    private void initData(){
        commonUrl = new CommonUrl();
        commonUrl.setCommonUrlListener(this);
        manageThread = ManageThread.getInstance();
        manageThread.createThread(1);//管理两个线程
    }
    @Override
    public void setFragmentAdapter() {

    }

    @Override
    public void updateFragmentAdapter() {

    }

    //以下方法为需要提交的数据的getter和setter方法
    public void setSubmitPersonId(String submitPersonId) {
        this.submitPersonId = submitPersonId;
    }

    public void setPartsId(String partsId) {
        this.partsId = partsId;
    }

    public void setQuestionAnswers(String questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public void setDiseaseImageIds(String diseaseImageIds) {
        this.diseaseImageIds = diseaseImageIds;
    }
    public void setDesc(String description,boolean hasPics){
        this.description = description;
        this.havePics = hasPics;
    }
    public void initPicList() {
        failUploadList = new ArrayList<>();
        succUploadList = new ArrayList<>();
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void setAmountAndFinish(int amount,boolean isFinished){
        this.pressedPicAmount = amount;
        this.isFinished = isFinished;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    private void uploadFiles(){
        Log.e("lookat","havepics"+havePics+"\tisFinished"+isFinished);
        if(havePics){
            if(isFinished){
                if(failUploadList.size()==0){//没有上传过文件或者图片全部成功订单没有提交成功
                    if(succUploadList.size() == pressedPicAmount){//图片全部成功订单没有提交成功
                        openDialog();
                        submitOrders(true);
                    }else{//没有上传过文件
                        List<String> pressedPics = FileUtils.getPressedList(pressedPicAmount);
                        for(String pic:pressedPics){
                            manageThread.carry(new UploadFile(pic));
                        }
                    }
                }else{
                    openDialog();//上传对话框
                    List<String> tempFailList = new ArrayList<>();
                    tempFailList.addAll(failUploadList);
                    failUploadList = new ArrayList<>();
                    for(int i=0;i<tempFailList.size();i++){
                        String failPicPath = tempFailList.get(i);//上传过文件,但有些没上传成功
                        manageThread.carry(new UploadFile(failPicPath));
                    }
                }
            }else{
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "选中图片正在压缩中,请稍等一下", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            //没有图片
            openDialog();
            submitOrders(false);
        }
    }
    /**
     * 当图片上传成功时提交订单
     * */
    private void submitOrders(boolean havePics){
        String picListStr = "";
        if(havePics){
            picListStr = new Gson().toJson(succUploadList);
        }
        Log.e("lookat","submitPersonId"+submitPersonId);
        Log.e("lookat","partsId"+partsId);
        Log.e("lookat", "description" + description);
        if(Global.diseaseIsEmpty){
            if(Global.pre_type.equals("3")){//健康
                commonUrl.loadUrl(UrlDataUtil.orderSubmit(Global.token, submitPersonId, "",picListStr,description, userChooseDate,orderState+"","",""));
            }else{//中医 心理
                commonUrl.loadUrl(UrlDataUtil.orderSubmit(Global.token, submitPersonId, partsId,picListStr,description, userChooseDate,orderState+"","",""));
            }
        }else{
            Log.e("lookat","questionAnswers"+questionAnswers);
            Log.e("lookat", "diseaseImageIds" + diseaseImageIds);
            commonUrl.loadUrl(UrlDataUtil.orderSubmit(Global.token, submitPersonId, partsId, picListStr, description, userChooseDate, orderState + "", questionAnswers, diseaseImageIds));
        }
        /*Log.e("lookat", "pics" + picListStr);*/
    }
    //compressPrepare-2 compressfail -1 compressok 0 start 1 loading 2 success 3 fail 4 file no exist 5
    private void stateText(int state,String progress,int current,int all){
        switch (state){
            case -2:
                openTextDialog("压缩准备中...");
                break;
            case -1:
                openTextDialog("压缩失败");
                break;
            case 1:
                openTextDialog("上传准备中...");
                break;
            case 2:
                openTextDialog("上传中:"+progress+"("+current+"/"+all+")");
                break;
            case 3:
                openTextDialog("上传成功"+"("+current+"/"+all+")");
                break;
            case 4:
                openTextDialog("上传失败"+"("+current+"/"+all+")");
                break;
            case 5:
                openTextDialog("文件不存在"+"("+current+"/"+all+")");
                break;
        }
    }

    @Override
    public void urlResult(Result result) {
        closeDialog();
        if(result.isSuccess()){
            if(result.getIntent().equals("orderSubmit")){
                UrlResult urlResult = new Gson().fromJson(result.getMsg(),UrlResult.class);
                if(urlResult.success){
                    Log.e("lookat","order ok");
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new ToastSuccView(mActivity).showShort();
                        }
                    });
                    ((PreOrderActivity)mActivity).finishActivity();
                }else{
                    //fail
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mActivity,"订单提交,请重新提交！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }else{
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity,"联网失败，请联网后提交！",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class UploadFile implements Callable<Object>{
        private String saveFilePath;
        public UploadFile(String saveFilePath) {
            this.saveFilePath = saveFilePath;
        }
        @Override
        public String call() throws Exception {
            UploadFileUtil uploadFileUtil = new UploadFileUtil(mActivity,saveFilePath);
            uploadFileUtil.setUploadFileListener(new UploadFileUtil.UploadFileListener() {
                @Override
                public void uploadFile(int state, String progress, String successMsg) {
                    if(state == 3){
                        Gson gson = new Gson();
                        UrlResult urlResult = gson.fromJson(successMsg, UrlResult.class);
                        if(urlResult.success){
                            succUploadList.add(urlResult.msg);
                        }else{
                            //没有上传成功
                            failUploadList.add(saveFilePath);
                        }
                    }else if(state == 4){
                        failUploadList.add(saveFilePath);
                    }
                    stateText(state,progress,succUploadList.size(),pressedPicAmount);
                    if(succUploadList.size() == pressedPicAmount){
                        Log.e("lookat", "上传图片完成");
                        submitOrders(true);
                    }else if(succUploadList.size()+failUploadList.size() == pressedPicAmount){
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mActivity,"有图片上传失败，请重新上传!!!",Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.e("lookat","上传图片过程完成"+"succ"+succUploadList.size()+"\t"+"fail"+failUploadList.size());
                    }
                }
            });
            uploadFileUtil.start();
            return null;
        }
    }
}
