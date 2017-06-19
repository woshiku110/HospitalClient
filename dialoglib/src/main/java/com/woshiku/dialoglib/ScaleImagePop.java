package com.woshiku.dialoglib;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;
import com.woshiku.dialoglib.view.TouchImageView;

/**
 * Created by Administrator on 2017/2/23.
 */
public class ScaleImagePop extends PopupWindow{
    View view,parent;
    Context context;
    BitmapUtils bitmapUtils;
    TouchImageView touchImageView;
    String path;
    LinearLayout scaleLineLayout;
    public ScaleImagePop(Context context,View parent,String path){
        this.context = context;
        this.parent = parent;
        this.path = path;
        view = View.inflate(context,R.layout.scale_image_layout,null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        scaleLineLayout = (LinearLayout)view.findViewById(R.id.scale_relate);
        touchImageView = (TouchImageView)view.findViewById(R.id.scale_touch_view);
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(touchImageView, path);
        scaleLineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
    }
    public void show(){
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
    }
}
