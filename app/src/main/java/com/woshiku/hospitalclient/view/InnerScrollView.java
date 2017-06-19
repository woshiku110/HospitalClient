package com.woshiku.hospitalclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/2/21.
 */
public class InnerScrollView extends ScrollView {


    public InnerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("第2个ScrollView:dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println( "第2个ScrollView: onInterceptTouchEvent: ");
//        return super.onInterceptTouchEvent(ev);
        boolean flag = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                flag = super.onInterceptTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                flag = true;
                break;
        }

        return flag;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        System.out.println("第2个ScrollView: onTouchEvent: ");
        return super.onTouchEvent(ev);
    }
}
