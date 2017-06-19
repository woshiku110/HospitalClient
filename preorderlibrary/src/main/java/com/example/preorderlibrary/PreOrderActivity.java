package com.example.preorderlibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import com.example.preorderlibrary.baseActivity.BaseActivity;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.common.PhotoData;
import com.example.preorderlibrary.domain.PersonData;
import com.example.preorderlibrary.fragments.ContentFragment;
import com.example.preorderlibrary.fragments.impleContent.ContactFragment;
import com.example.preorderlibrary.fragments.impleContent.DiseaseDescription;
import com.example.preorderlibrary.fragments.impleContent.FragmentFactory;
import com.example.preorderlibrary.utils.ParseDataUtil;
import com.lidroid.xutils.util.LogUtils;
import com.woshiku.waitlibrary.WaitDialog;
import java.util.ArrayList;
import java.util.List;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
public class PreOrderActivity extends BaseActivity
{
    public static String content_tag = "PreContent_Fragment";
    public static final int ACTION_TAKE_PICTURE = 0x000000;
    LinearLayout topLine;
    public WaitDialog waitDialog;
    ContentFragment contentFragment;
    @Override
    public void initViews() {
        Global.token= getIntent().getStringExtra("intent_string");
        Global.id = getIntent().getStringExtra("intent_string_id");
        Global.pre_type = getIntent().getIntExtra("type",1)+"";
        LogUtils.d("type"+Global.pre_type);
        setContentView(R.layout.activity_pre_order);
        topLine = (LinearLayout)findViewById(R.id.activity_pre_order_line);
        initFragment();
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
    }

    @Override
    public void dealBroadCast(Context context, String type, Bundle bd) {
        if(type.equals("albumResult")){
            List<String> selectedPicList = bd.getStringArrayList(IMAGE_SELECTED_ADDR);
            List<PhotoData> photoDataList = new ArrayList<>();
            for(String selePic:selectedPicList){
                photoDataList.add(new PhotoData(1,selePic));
                Log.e("lookat","user choose pic"+selePic);
            }
            if(Global.pre_type.equals("3")){//健康
                DiseaseDescription diseaseDescription = (DiseaseDescription)FragmentFactory.getFragment(1);
                diseaseDescription.addListData(photoDataList);
            }else{//中医 西医
                DiseaseDescription diseaseDescription = (DiseaseDescription)FragmentFactory.getFragment(4);
                diseaseDescription.addListData(photoDataList);
            }
        }
    }



    private void initFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fmTrans = fragmentManager.beginTransaction();
        contentFragment = new ContentFragment(this);
        fmTrans.replace(R.id.pre_content_fragment,contentFragment,content_tag);
        fmTrans.commit();
    }

    public void enterNewFamily(){
        Intent intent = new Intent("com.myfamily.NewFamilyActivity");
        Bundle bd = new Bundle();
        bd.putInt("action", Global.NEW_USER);
        intent.putExtras(bd);
        startActivityForResult(intent, Global.CreateFamilyRequest);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(Global.pre_type.equals("3")){
                if(contentFragment.getCurrentPage()==0){
                    waitDialog = null;
                    scrollToFinishActivity();
                }else{
                    contentFragment.setHealthyCurrentPage();
                }
            }else{
                if(contentFragment.getCurrentPage()==0){
                    waitDialog = null;
                    scrollToFinishActivity();
                }else{
                    contentFragment.setCurrentPage();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void swipeBackCallback() {
        finishActivity();
    }
    public void finishActivity(){
        //waitDialog = null;
        removeAllBroadCast();
        FragmentFactory.clearMap();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            DiseaseDescription diseaseDescription ;
            if(Global.pre_type.equals("3")){//健康
                diseaseDescription = (DiseaseDescription)FragmentFactory.getFragment(1);
            }else{//中医 西医
                diseaseDescription = (DiseaseDescription)FragmentFactory.getFragment(4);
            }
            switch (requestCode){
                case ACTION_TAKE_PICTURE:
                    if(resultCode == -1){
                        diseaseDescription.photoIsOk(true);
                    }else{
                        diseaseDescription.photoIsOk(false);
                    }
                    break;
            }
        }
        if(requestCode == Global.CreateFamilyRequest && resultCode==Global.CreateFamilyReturn){
            String msg = data.getExtras().getString("myfamily");
            if(!TextUtils.isEmpty(msg)){
                List<PersonData> mList = ParseDataUtil.getPersonDatas(msg);
                PersonData personData = new PersonData();
                personData.setTypeOne(false);
                mList.add(personData);
                ContactFragment contactFragment = (ContactFragment)FragmentFactory.getFragment(0);
                if(contactFragment!=null){
                    contactFragment.updateData(mList);
                }
                Log.e("lookat","info:"+"update");
                /*LogUtil.print("更新数据");*/
                //updateData(msg);
            }
        }
    }
    public void openDialog(){
        if(waitDialog == null){
            waitDialog = new WaitDialog(this,topLine);
        }
        waitDialog.showDialog();
    }
    public void openTextDialog(String text){
        if(waitDialog == null){
            waitDialog = new WaitDialog(this,topLine);
        }
        waitDialog.showTextDialog(text);
    }
    public void closeDialog(){
        waitDialog.closeDialog();
        waitDialog = null;
    }
}
