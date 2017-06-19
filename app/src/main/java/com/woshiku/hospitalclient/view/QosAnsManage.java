package com.woshiku.hospitalclient.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woshiku.hospitalclient.R;

import java.util.List;

import domain.HoldDiagDetailData;
import domain.PreorderDetailData;

/**
 * Created by Administrator on 2017/2/21.
 */
public class QosAnsManage {
    LinearLayout parentLine;
    Context context;
    List<PreorderDetailData.QosAns> qosAnsList;
    List<HoldDiagDetailData.QosAns> qosAnsListOne;
    public QosAnsManage(Context context, LinearLayout parentLine,List<PreorderDetailData.QosAns> qosAnsList) {
        this.context = context;
        this.parentLine = parentLine;
        this.qosAnsList = qosAnsList;
        parentLine.removeAllViews();
        initView();
    }
    public QosAnsManage(Context context, LinearLayout parentLine,List<HoldDiagDetailData.QosAns> qosAnsList,String mark) {
        this.context = context;
        this.parentLine = parentLine;
        this.qosAnsListOne = qosAnsList;
        parentLine.removeAllViews();
        initViewOne();
    }
    private void initView(){
        for(int i=0;i<qosAnsList.size();i++){
            parentLine.addView(generItem(qosAnsList.get(i),i+1));
            parentLine.setPadding(0,0,0,(int)context.getResources().getDimension(R.dimen.qos_ans_top));
        }
    }
    private void initViewOne(){
        for(int i=0;i<qosAnsListOne.size();i++){
            parentLine.addView(generItem(qosAnsListOne.get(i),i+1));
            parentLine.setPadding(0,0,0,(int)context.getResources().getDimension(R.dimen.qos_ans_top));
        }
    }
    private LinearLayout generItem(Object obj,int index){
        LinearLayout itemLayout = new LinearLayout(context);
        LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParam.topMargin = (int)context.getResources().getDimension(R.dimen.qos_ans_top);
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        TextView titleView = new TextView(context);
        if(obj instanceof PreorderDetailData.QosAns){
            titleView.setText(index+". "+((PreorderDetailData.QosAns)obj).question);
        }else{
            titleView.setText(index+". "+((HoldDiagDetailData.QosAns)obj).question);
        }
        titleView.setTextAppearance(context,R.style.common_text_content);
        LinearLayout.LayoutParams titleParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParam.leftMargin = (int)context.getResources().getDimension(R.dimen.qos_ans_title);
        titleView.setLayoutParams(titleParam);
        itemLayout.addView(titleView);
        String []answers = null;
        if(obj instanceof PreorderDetailData.QosAns){
            answers = ((PreorderDetailData.QosAns)obj).answers;
        }else{
            answers = ((HoldDiagDetailData.QosAns)obj).answers;
        }
        for(String content:answers){
            LinearLayout.LayoutParams contentLineParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout contentLine = new LinearLayout(context);
            contentLine.setOrientation(LinearLayout.HORIZONTAL);
            contentLineParam.leftMargin = (int)context.getResources().getDimension(R.dimen.qos_ans_title);
            contentLineParam.topMargin = (int)context.getResources().getDimension(R.dimen.qos_ans_top);
            TextView markView = new TextView(context);
            markView.setText(index+".");
            markView.setVisibility(View.INVISIBLE);
            TextView contentView = new TextView(context);
            contentView.setText(content);
            contentView.setTextAppearance(context, R.style.common_text_blue_content);
            contentLine.addView(markView);
            contentLine.addView(contentView);
            contentLine.setLayoutParams(contentLineParam);
            itemLayout.addView(contentLine);
        }
        itemLayout.setLayoutParams(itemParam);
        return itemLayout;
    }
}
