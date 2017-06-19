package com.woshiku.startlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/3/15.
 */
public class StarRateView extends RelativeLayout{
    private int starSize,marginSize,starNum,markNum;
    private float step;
    private boolean isEdit = true;
    private float userSetMark;
    Drawable unselectedPic;
    Drawable selectedPic;
    LinearLayout outLayout,innerLayout;
    StarRateListener starRateListener;
    public interface StarRateListener{
        void starRate(int index);
    }

    public void setStarRateListener(StarRateListener starRateListener) {
        this.starRateListener = starRateListener;
    }

    public StarRateView(Context context) {
        super(context);
    }
    public StarRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.StarRateView);
        starSize = (int)a.getDimension(R.styleable.StarRateView_starSize,(int)getResources().getDimension(R.dimen.star_size));
        marginSize = (int)a.getDimension(R.styleable.StarRateView_marginSize,(int)getResources().getDimension(R.dimen.margin_size));
        unselectedPic = a.getDrawable(R.styleable.StarRateView_unChoosedPic);
        selectedPic = a.getDrawable(R.styleable.StarRateView_choosedPic);
        starNum = a.getInteger(R.styleable.StarRateView_starNum,5);
        markNum = a.getInteger(R.styleable.StarRateView_markNum,3);
        step = a.getFloat(R.styleable.StarRateView_step,1.0f);//默认大小
        if(step < 1.0f){//小于1.0f按照切成十等份处理
            step = 0.1f;
        }else if(step > 1.0f){
            step = 1.0f;
        }
        isEdit = a.getBoolean(R.styleable.StarRateView_isEdit, true);
        Log.e("lookat", "startSize:" + starSize + "\t" + "starNum:" + starNum + "markNum:" + markNum + "\t" + "isEdit:" + isEdit + "\t marginsize" + marginSize);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
        Log.e("lookat", "onin flate startSize:" + starSize + "\t" + "starNum:" + starNum + "markNum:" + markNum + "\t" + "isEdit:" + isEdit);
    }
    /**
     * 初始化外层星布局
     * */
    private void initOutLayout(){
        outLayout = new LinearLayout(getContext());
        RelativeLayout.LayoutParams outParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        outLayout.setLayoutParams(outParam);
        outLayout.setOrientation(LinearLayout.HORIZONTAL);
        outLayout.setLayoutParams(outParam);
        outLayout.removeAllViews();
    }
    /**
     * 初始化内层星布局
     * */
    private void initInnerLayout(){
        innerLayout = new LinearLayout(getContext());
        RelativeLayout.LayoutParams innerParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        innerLayout.setLayoutParams(innerParam);
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        innerLayout.setLayoutParams(innerParam);
        innerLayout.removeAllViews();
    }
    /**
     * 初始化视图
     * */
    private void initViews() {
        initOutLayout();
        initInnerLayout();
        drawStarBg();
        addView(outLayout);
        drawStars();
        addView(innerLayout);
    }
    /**
     * 绘制星星背景
     * */
    private void drawStarBg(){
        for (int i = 0; i < starNum; i++) {
            final int choose = i;
            View view = new View(getContext());
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isEdit){//可以编辑的
                        reDrawStars(choose);
                        if(starRateListener != null){
                            starRateListener.starRate(choose);
                        }
                    }
                }
            });
            if (i != (starNum - 1)) {
                view.setBackground(unselectedPic);
                param.width = starSize;
                param.height = starSize;
                param.rightMargin = marginSize;
                outLayout.addView(view,param);
            }else{
                view.setBackground(unselectedPic);
                param.width = starSize;
                param.height = starSize;
                outLayout.addView(view,param);
            }
        }
    }
    /**
     * 绘制星星
     * */
    private void drawStars(){
        for(int i=0;i<markNum;i++){
            if(i != (markNum - 1)){
                View view = new View(getContext());
                view.setBackground(selectedPic);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.width = starSize;
                param.height = starSize;
                param.rightMargin = marginSize;
                innerLayout.addView(view, param);
            }else{
                View view = new View(getContext());
                view.setBackground(selectedPic);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.width = starSize;
                param.height = starSize;
                innerLayout.addView(view,param);
            }
        }
    }
    /**
     * 重绘星星
     * */
    private void reDrawStars(int index){
        int amount = index + 1;
        markNum = amount;
        innerLayout.removeAllViews();//移除有所选中星星
        drawStars();
    }

    /**
     * 设置用户设置mark 可以是带小数的
     * */
    private void drawUserStars(){
        int userStars = (int)Math.ceil(userSetMark);
        int minValue = (int)userSetMark;
        Log.e("lookat","userstart:"+userStars+"\t"+"minValue"+minValue);
        float offSet = userSetMark - minValue;
        Log.e("lookat","offSet:"+offSet);
        for(int i=0;i<userStars;i++){
            if(i != (userStars - 1)){
                View view = new View(getContext());
                view.setBackground(selectedPic);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.width = starSize;
                param.height = starSize;
                param.rightMargin = marginSize;
                innerLayout.addView(view, param);
            }else{
                View view = new View(getContext());
                view.setBackground(selectedPic);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.width = starSize;
                param.height = starSize;
                innerLayout.addView(view,param);
            }
        }
        int offDis = (int)((starSize*1.0f)*offSet);
        int width = (userStars - 1)*starSize + (userStars - 1)*marginSize + offDis;
        innerLayout.getLayoutParams().width = width;
        Log.e("lookat","width:"+width+"\t"+"offDis:"+offDis);
    }
    public void setUserSetMark(float userSetMark) {
        this.userSetMark = userSetMark;
        innerLayout.removeAllViews();//移除有所选中星星
        drawUserStars();
    }
}
