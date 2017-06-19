package com.woshiku.hospitalclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;

/**
 * Created by Administrator on 2017/2/14.
 * 子选项View
 */
public class MyItemView extends LinearLayout{
    LinearLayout line;
    RelativeLayout relate;
    ImageView leftIcon;
    TextView textView;
    ImageView rightIcon;
    View topLine;
    View bottomLine;
    int pos;
    public MyItemView(Context context) {
        super(context);
        initDatas();
    }
    public MyItemView(Context context,int pos) {
        super(context);
        this.pos = pos;
        initDatas();
    }
    public MyItemView(Context context,int pos,int id) {
        super(context);
        this.pos = pos;
        setId(id);
        initDatas();
    }
    public MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDatas();
    }

    private void initDatas(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_my, this);
        line = (LinearLayout)findViewById(R.id.item_my_line_all);
        relate = (RelativeLayout)findViewById(R.id.item_my_relate);
        leftIcon = (ImageView)findViewById(R.id.item_my_icon_left);
        textView = (TextView)findViewById(R.id.item_my_txt);
        rightIcon = (ImageView)findViewById(R.id.item_my_icon_right);
        topLine = findViewById(R.id.item_top_line);
        bottomLine = findViewById(R.id.item_bottom_line);
    }

    public void setLayoutSize(int layoutheight,int marginTop,int leftMarginLeft,int leftMarginRight,int rightMarginRight){
        LinearLayout.LayoutParams lineParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lineParam.topMargin = marginTop;
        LinearLayout.LayoutParams relateParam = (LinearLayout.LayoutParams)relate.getLayoutParams();
        relateParam.height = layoutheight;
        LinearLayout.LayoutParams leftIconParam = (LinearLayout.LayoutParams)leftIcon.getLayoutParams();
        leftIconParam.leftMargin = leftMarginLeft;
        leftIconParam.rightMargin = leftMarginRight;
        LinearLayout.LayoutParams rightIconParam = (LinearLayout.LayoutParams)rightIcon.getLayoutParams();
        rightIconParam.rightMargin = rightMarginRight;
        line.setLayoutParams(lineParam);
    }
    public void setLeftImage(int icon){
        leftIcon.setImageResource(icon);
    }
    public void setText(String text){
        textView.setText(text);
    }
    public void hideTopLine(){
        topLine.setVisibility(View.GONE);
    }
    public void hideBottomLine(){
        bottomLine.setVisibility(View.GONE);
    }
    public int getPos() {
        return pos;
    }
}
