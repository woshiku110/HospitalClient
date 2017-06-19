package com.woshiku.dialoglib;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Created by Administrator on 2017/3/6.
 */
public class PrescribleQosView{
    View view;
    LinearLayout parent;
    Context context;
    TextView qosText;
    public PrescribleQosView(LinearLayout parent,Context context) {
        this.parent = parent;
        this.context = context;
        initView();
    }

    private void initView(){
        parent.removeAllViews();
        view = View.inflate(context,R.layout.item_prescrible_input,null);
        qosText = (TextView)view.findViewById(R.id.item_prescrible_qos_txt);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parent.addView(view,params);
    }

    public TextView getQosText(){
        return qosText;
    }
}
