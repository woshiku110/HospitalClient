package com.woshiku.hospitalclient.fragment.impleHome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.JavascriptInterface;
import com.woshiku.hospitalclient.fragment.WebFragment;
/**
 * Created by Administrator on 2016/12/14.
 */
@SuppressLint("ValidFragment")
public class CheckFragment extends WebFragment{
    String myPid;
    public CheckFragment(FragmentActivity mActivity) {
        super(mActivity);
    }
    @Override
    public void childLoad() {
        getWebView().addJavascriptInterface(new JsInteration(), "control");
        loadLocalUrl("ExternalWeb/directoryList/directoryList.html");
    }
    /**
     * 需要js实现的方法
     * */
    public class JsInteration {
        @JavascriptInterface
        public void setMyPid(String pid){
            myPid = pid;
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent("com.woshiku.ArticleAtcivity");
                    Bundle bd = new Bundle();
                    bd.putString("title", "健康信息");
                    bd.putString("loadUrl", "ExternalWeb/directoryList/diabetes.html");
                    bd.putString("intent", "loadasset");
                    bd.putString("mypid", myPid);
                    intent.putExtras(bd);
                    mActivity.startActivity(intent);
                }
            });
        }
    }
}
