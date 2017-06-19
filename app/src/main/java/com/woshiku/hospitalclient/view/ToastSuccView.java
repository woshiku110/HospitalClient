package com.woshiku.hospitalclient.view;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.woshiku.hospitalclient.R;

/**
 * Created by Administrator on 2017/1/17.
 */
public class ToastSuccView {
    Context context;
    Toast toast;
    public ToastSuccView(Context context) {
        this.context = context;
        toast = new Toast(context);
        LinearLayout lineView = new LinearLayout(context);
        LinearLayout.LayoutParams lineParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.ico_tjcg);
        imageView.setLayoutParams(lineParam);
        lineView.addView(imageView);
        lineView.setGravity(Gravity.CENTER);
        toast.setView(lineView);
    }

    public void showShort(){
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
