package com.woshiku.hospitalclient.activity.web;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import com.woshiku.hospitalclient.R;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/14.
 */
public class ArticleAtcivity extends WebActivity{
    String pid;
    @Override
    void loadChildrenMethod() {
        pid = getWebActivity().getIntent().getExtras().getString("mypid");
        webView.addJavascriptInterface(new JsInteration(), "control");
    }

    @Override
    public void swipeBackCallback() {

    }

    @OnClick({R.id.icon_return_bt})
    void userClick(View view){
        switch (view.getId()){
            case R.id.icon_return_bt:
                if(webView.canGoBack()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.goBack();
                        }
                    });
                }else{
                    scrollToFinishActivity();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            if(webView.canGoBack()){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.goBack();
                        concert_bt.setVisibility(View.INVISIBLE);
                    }
                });
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
        public String getMyPid(){
            return pid;
        }
    }
}
