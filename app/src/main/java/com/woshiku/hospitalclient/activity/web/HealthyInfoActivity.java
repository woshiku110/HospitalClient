package com.woshiku.hospitalclient.activity.web;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.web.view.HealthyView;
import com.woshiku.hospitalclient.utils.LogUtil;
import butterknife.OnClick;
import common.Global;

/**
 * Created by Administrator on 2017/3/9.
 */
public class HealthyInfoActivity extends WebActivity implements HealthyView {

    /**
     * 用于初始化孩子方法
     * */
    @Override
    public void loadChildrenMethod() {
        webView.addJavascriptInterface(new JsInteration(), "control");
    }

    @Override
    public void changeTitleBarButtonState(final boolean isRevise) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isRevise){
                    concert_bt.setText("修改");
                    concert_bt.setVisibility(View.VISIBLE);
                }else{
                    concert_bt.setText("确定");
                    concert_bt.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.icon_return_bt,R.id.concrete_bt})
    void userClick(View view){
        switch (view.getId()){
            case R.id.icon_return_bt:
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
                break;
            case R.id.concrete_bt:
                final String call;
                if(concert_bt.getText().toString().equals("修改")){
                    call = "javascript:"+"correctMessage()";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            concert_bt.setText("确定");
                            webView.loadUrl(call);
                        }
                    });
                }else{
                    call = "javascript:"+"noCorrectMessage()";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl(call);
                            concert_bt.setVisibility(View.INVISIBLE);
                            webView.goBack();
                        }
                    });
                }
                break;
        }
    }

    /**
     * 需要js实现的方法
     * */
    public class JsInteration {
        @JavascriptInterface
        public String getMyParam(){
            LogUtil.print("get my parm" + Global._token);
            return Global._token;
        }
        @JavascriptInterface
        public void showTitleBarBt(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    changeTitleBarButtonState(true);
                }
            });
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

    @Override
    public void swipeBackCallback() {

    }
}
