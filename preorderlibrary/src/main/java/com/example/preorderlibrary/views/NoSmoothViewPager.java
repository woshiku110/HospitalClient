package com.example.preorderlibrary.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.preorderlibrary.R;


/**
 * Created by Administrator on 2016/12/14.
 */
public class NoSmoothViewPager extends ViewPager{
    int edgeSize = (int)getContext().getResources().getDimension(R.dimen.pre_order_edge_size);//20个px
    public NoSmoothViewPager(Context context) {
        super(context);
    }

    public NoSmoothViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("testkankan","按下");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("testkankan","移动"+ev.getRawX());
                break;
            case MotionEvent.ACTION_UP:
                Log.e("testkankan","松开");
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(ev.getRawX()<edgeSize&&getCurrentItem()!=0){
                    Log.e("testkankan","Intercept按下");
                    return super.onInterceptTouchEvent(ev);
                }else {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                Log.e("testkankan","Intercept移动"+ev.getRawX());
                break;
            case MotionEvent.ACTION_UP:
                Log.e("testkankan","Intercept松开");
                break;
        }
        return super.onInterceptTouchEvent(ev);
        /*return false;*/
    }
}
