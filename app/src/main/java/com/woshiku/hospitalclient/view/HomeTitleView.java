package com.woshiku.hospitalclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.woshiku.hospitalclient.R;
/**
 * Created by Administrator on 2016/12/12.
 */
public class HomeTitleView extends LinearLayout{
    public HomeTitleView(Context context) {
        super(context);
    }

    public HomeTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_home_title,this);
    }
}
