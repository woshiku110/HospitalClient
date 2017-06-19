package com.woshiku.hospitalclient.activity.web;

import android.view.KeyEvent;
import com.woshiku.hospitalclient.R;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/9.
 */
public class QuestionActivity extends WebActivity{
    @Override
    public void loadChildrenMethod() {

    }

    @Override
    public void swipeBackCallback() {

    }

    @OnClick({R.id.icon_return_bt})
    void userClick(){
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            scrollToFinishActivity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            if(webView.canGoBack()){
                webView.goBack();
            }else{
                scrollToFinishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
