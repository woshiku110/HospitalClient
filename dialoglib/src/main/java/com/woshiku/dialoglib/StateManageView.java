package com.woshiku.dialoglib;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woshiku.dialoglib.domain.TimeLineData;
import com.woshiku.dialoglib.view.TimeLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public abstract class StateManageView {
    Context context;
    View view;
    LinearLayout parentLayout;
    ImageView stateIcon;
    TextView stateDesc;
    TextView stateNo;
    TimeLineView timeLineView;
    String[] stateText;
    String no;
    boolean isHave;
    int selectedPos = 0;
    int []statePics;
    abstract String[] getTextState();
    abstract int[] getStatePics();
    public StateManageView(Context context, LinearLayout parent,int selectedPos,boolean isHave,String no) {
        this.context = context;
        this.parentLayout = parent;
        this.selectedPos = selectedPos;
        this.no = no;
        this.isHave = isHave;
        view = View.inflate(context,R.layout.timeline_layout,null);
        initView(view);
        parentLayout.removeAllViews();
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        parentLayout.addView(view,param);
    }

    private void initView(View view){
        stateIcon = (ImageView)view.findViewById(R.id.item_icon_check);
        stateDesc = (TextView)view.findViewById(R.id.item_desc_txt);
        stateNo = (TextView)view.findViewById(R.id.item_no_txt);
        timeLineView = (TimeLineView)view.findViewById(R.id.time_line_view);
    }

    protected void initDatas(int selectedPos){
        this.statePics = getStatePics();
        this.stateText = getTextState();
        List<TimeLineData> timeLineDataList = new ArrayList<>();
        for(int i=0;i<stateText.length;i++){
            TimeLineData timeLineData = new TimeLineData();
            String name =stateText[i];
            timeLineData.setName(name);
            if(i==0){
                timeLineData.setType(0);
            }else if(i == (stateText.length-1)){
                timeLineData.setType(2);
            }else{
                timeLineData.setType(1);
            }
            if(i == selectedPos){
                timeLineData.setIsSelected(true);
            }else{
                timeLineData.setIsSelected(false);
            }
            timeLineDataList.add(timeLineData);
        }
        timeLineView.setInitData(timeLineDataList);
        timeLineView.setChoosedPos(selectedPos);
        stateDesc.setText(stateText[selectedPos]);
        stateIcon.setImageResource(statePics[selectedPos]);
        if(isHave){
            stateNo.setText("编号 : "+no);
            stateNo.setVisibility(View.VISIBLE);
        }
    }
}
