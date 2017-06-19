package com.woshiku.hospitalclient.activity.web;

import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import com.woshiku.hospitalclient.R;
import butterknife.OnClick;
import common.Global;

/**
 * Created by Administrator on 2017/3/14.
 */
public class HistoryActivity extends WebActivity{
    @Override
    void loadChildrenMethod() {
        webView.addJavascriptInterface(new JsInteration(), "control");
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
    /**
     * 需要js实现的方法
     * */
    public class JsInteration {
        @JavascriptInterface
        public String getToken(){
            return Global._token;
        }
    }
}
