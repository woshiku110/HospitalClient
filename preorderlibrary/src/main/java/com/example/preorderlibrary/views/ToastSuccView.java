package com.example.preorderlibrary.views;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.preorderlibrary.R;

/**
 * Created by Administrator on 2017/1/17.
 */
public class ToastSuccView {
    Context context;
    Toast toast;
    public ToastSuccView(Context context) {
        this.context = context;
        toast = new Toast(context);
        ViewGroup.LayoutParams viewParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(viewParam);
        imageView.setImageResource(R.mipmap.ico_tjcg);
        toast.setView(imageView);
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    public void showShort(){
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
