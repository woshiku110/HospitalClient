package com.woshiku.hospitalclient.activity.parent;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.model.CommonPageModel;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.urllibrary.url.base.CommonUrl;
import butterknife.ButterKnife;
import butterknife.InjectView;
import domain.PreorderDetailData;
/**
 * Created by Administrator on 2017/2/20.
 */
public abstract class PreorderDetailParent extends BaseActivity implements CommonPageModel{

    @InjectView(R.id.txt_title)
    protected TextView title;
    @InjectView(R.id.concrete_bt)
    protected TextView concert_bt;
    protected CommonUrl commonUrl;
    @InjectView(R.id.preorder_detail_name)
    protected TextView name;
    @InjectView(R.id.preorder_detail_sex)
    protected TextView sex;
    @InjectView(R.id.preorder_detail_symbol)
    protected TextView symbol;
    @InjectView(R.id.preorder_detail_symbol_desc)
    protected TextView symolDesc;
    @InjectView(R.id.preorder_detail_scroll)
    protected ScrollView parentScrollView;
    @InjectView(R.id.preorder_detail_images_scroll)
    protected ScrollView childScrollView;
    @InjectView(R.id.preorder_detail_images_scroll_line)
    protected LinearLayout scrollViewLine;
    @InjectView(R.id.preorder_detail_symbol_qans_line)
    protected LinearLayout qosLine;
    @InjectView(R.id.preorder_detail_pics_line)
    protected LinearLayout picsLine;
    protected PreorderDetailData preorderDetailData;
    protected Context context;
    protected abstract int getPicAmount();
    protected int picAmount = 12;
    protected String orderId;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_preorder_detail);
        ButterKnife.inject(this);
        init();
        initDatas();
        initControls();
    }
    private void initControls(){
        parentScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                //LogUtil.print("PARENT TOUCH");
                childScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        childScrollView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                //LogUtil.print("CHILD TOUCH");
                if(getPicAmount()>picAmount){
                    int touchWidth = (int)context.getResources().getDimension(R.dimen.gap);
                    int startX = (int)event.getX();
                    if(startX>touchWidth){
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    }else{
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }
}
