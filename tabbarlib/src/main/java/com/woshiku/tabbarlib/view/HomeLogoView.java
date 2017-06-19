package com.woshiku.tabbarlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.woshiku.tabbarlib.R;

/**
 * Created by Administrator on 2016/12/13.
 */
public class HomeLogoView extends LinearLayout{
    LinearLayout bg;
    public HomeLogoView(Context context) {
        super(context);
    }

    public HomeLogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_home_bt,this);
        bg = (LinearLayout)findViewById(R.id.home_bt_bg);
    }
    public void setSelected(boolean isSelected){
        bg.setEnabled(isSelected);
    }
}
