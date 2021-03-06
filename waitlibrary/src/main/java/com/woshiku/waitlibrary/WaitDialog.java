package com.woshiku.waitlibrary;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
/**
 * Created by Administrator on 2016/12/27.
 */
public class WaitDialog extends PopupWindow{
    View parent;
    View view;
    LinearLayout gif_line;
    Context context;
    GifView gifView;
    TextView desc;
    Handler mHandler;
    public WaitDialog(Context context,View parent){
        this.context = context;
        this.parent = parent;
        view = View.inflate(context,R.layout.dialog_widget_wait,null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        gif_line = (LinearLayout)view.findViewById(R.id.gif_line);
        desc = (TextView)view.findViewById(R.id.wait_text);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        initGif(gif_line);
        if(mHandler == null){
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 0:
                            desc.setText(msg.getData().getString("desc"));
                            break;
                    }
                }
            };
        }
        //update();
    }

    private void initGif(LinearLayout gif_line){
        gifView = new GifView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        gifView.setGifResource(R.mipmap.loading);
        gifView.setLayoutParams(params);
        gif_line.addView(gifView);
    }
    private void sendMessage(int what,String msg){
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString("desc", msg);
        message.setData(bundle);
        mHandler.sendMessage(message);
    }
    public void showDialog(){
        if(!gifView.isPlaying()){
            gifView.play();
        }
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
        sendMessage(0,"");
    }
    public void showTextDialog(String text){
        if(!gifView.isPlaying()){
            gifView.play();
        }
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
        sendMessage(0, text);
    }
    public void closeDialog(){
        try{
            if(!gifView.isPaused()){
                gifView.pause();
            }
            dismiss();
        }catch (Exception e){
            Log.e("lookat","dialog error",e);
        }

    }
    public void closeAnim(){
        if(!gifView.isPaused()){
            gifView.pause();
        }
    }
}
