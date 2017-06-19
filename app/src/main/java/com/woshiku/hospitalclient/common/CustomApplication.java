package com.woshiku.hospitalclient.common;

import android.app.Activity;
import android.app.Application;
import android.view.View;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by fhf11991 on 2016/7/18.
 */

public class CustomApplication extends Application{

    private ActivityLifecycleHelper mActivityLifecycleHelper;
    private static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        //x.Ext.init(this);
        mApplication = this;
        Fresco.initialize(this);
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());
    }
    public static Application getApplication(){
        return mApplication;
    }
    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

    public void onSlideBack(boolean isReset, float distance) {
        if(mActivityLifecycleHelper != null) {
            Activity lastActivity = mActivityLifecycleHelper.getPreActivity();
            if(lastActivity != null) {
                View contentView = lastActivity.findViewById(android.R.id.content);
                if(isReset) {
                    contentView.setX(contentView.getLeft());
                } else {
                    final int width = getResources().getDisplayMetrics().widthPixels;
                    contentView.setX(-width / 3 + distance / 3);
                }
            }
        }
    }
}
