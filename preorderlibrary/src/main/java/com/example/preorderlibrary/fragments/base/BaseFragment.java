package com.example.preorderlibrary.fragments.base;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.example.preorderlibrary.PreOrderActivity;
import com.example.preorderlibrary.fragments.ContentFragment;
import com.example.preorderlibrary.fragments.impleContent.BodyChooseFragment;
import com.example.preorderlibrary.fragments.impleContent.FragmentFactory;
/**
 * Created by willyou on 2016/11/25.
 */

public abstract class BaseFragment extends Fragment{
    public static final int SHOW_FAIL = 2;
    public static final int SHOW_WAIT = 3;
    public static final int SHOW_CONTENT = 4;
    public static final int SHOW_PROGRESS = 5;
    public static final int CONTACTFRAGMENT=0;
    public static final int BODYCHOOSEFRAGMENT=1;
    public static final int BODYPARTFRAGMENT=2;
    public static final int CONCRETEFRAGMENT=3;
    public static final int DISEASEDESCROPTION=4;
    public static final int DISEASEQUESTION=5;
    public static final int ORDERDATEFRAGMENT=6;
    public int position;
    public View mView;
    public FrameLayout fm;
    public FragmentActivity mActivity;
    private Handler mHandler;
    private WebView webView;
    private ProgressBar pb;
    private Object mList;

    @SuppressLint("ValidFragment")
    public BaseFragment(FragmentActivity mActiviy){
        this.mActivity = mActiviy;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mHandler == null){
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 0:
                            setFragmentAdapter();
                            break;
                        case 1:
                            updateFragmentAdapter();
                            break;
                        case SHOW_FAIL:
                            showFail();
                            break;
                        case SHOW_CONTENT:
                            showContent();
                            break;
                        case SHOW_WAIT:
                            showWait();
                            break;
                        case SHOW_PROGRESS: //在bodychoose界面加载网络数据的时候用到的progressbar
                            int progress = Integer.parseInt(msg.getData().getString("msg"));
                            pb.setProgress(progress);
                            break;
                        case 8:
                            try{
                                ((PreOrderActivity)mActivity).openDialog();
                            }catch (Exception e){
                                Log.e("lookat","handle opendialog",e);
                            }
                            break;
                        case 9:
                            try{
                                ((PreOrderActivity)mActivity).closeDialog();
                            }catch(Exception e){
                                Log.e("lookat","handle closedialog",e);
                            }
                            break;
                        case 10:
                            try{
                                ((PreOrderActivity)mActivity).openTextDialog(msg.getData().getString("msg"));
                            }catch (Exception e){
                                Log.e("lookat","handle opentextdialog",e);
                            }
                            break;
                    }
                }
            };
        }
    }
    public void sendMessage(int what,String msg){
        Message message = new Message();
        message.what = what;
        Bundle bd = new Bundle();
        bd.putString("msg",msg);
        message.setData(bd);
        mHandler.sendMessage(message);
    }

    public abstract void setFragmentAdapter();
    public abstract void updateFragmentAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //初始化界面
        return initViews();
    }
    public abstract View initViews();

    public void initDatas(){
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void showWait(){
        fm.getChildAt(0).setVisibility(View.VISIBLE);
        fm.getChildAt(1).setVisibility(View.GONE);
        fm.getChildAt(2).setVisibility(View.GONE);
    }
    public void showFail(){
        fm.getChildAt(0).setVisibility(View.GONE);
        fm.getChildAt(1).setVisibility(View.VISIBLE);
        fm.getChildAt(2).setVisibility(View.GONE);
    };
    public void showContent(){
        fm.getChildAt(0).setVisibility(View.GONE);
        fm.getChildAt(1).setVisibility(View.GONE);
        fm.getChildAt(2).setVisibility(View.VISIBLE);
    }
    public void initWebView(WebView webView, final ProgressBar pb){
        this.webView = webView;
        this.pb = pb;
        //能够的调用JavaScript代码
        WebSettings webSettings = webView.getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setSupportZoom(true);//设定支持缩放
        webSettings.setLoadWithOverviewMode(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.loadUrl("file:///android_asset/111.html");
        webView.addJavascriptInterface(new JsInteration(), "control");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //开始加载
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                sendMessage(SHOW_PROGRESS,newProgress+"");
            }
        });

    }


    public class JsInteration {
        @JavascriptInterface
        public void onChooseResult(boolean male,String part){
            BodyChooseFragment bodyChooseFragment = (BodyChooseFragment) FragmentFactory.getFragment(1);
            if(bodyChooseFragment!=null){
                bodyChooseFragment.bodyChooseResult(male,part);
            }
        }
    }

    /**
     * 通过position传值跳转到相应的fragment
     * @param position
     */
    public void switchViewPager(final int position){
        this.position=position;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ContentFragment contentFragment = (ContentFragment) mActivity.getSupportFragmentManager()
                        .findFragmentByTag(PreOrderActivity.content_tag);
                ViewPager viewPager = contentFragment.getViewPager();
                switch (position){
                    case CONTACTFRAGMENT:
                        viewPager.setCurrentItem(position);
                        break;
                    case BODYCHOOSEFRAGMENT:
                        viewPager.setCurrentItem(position);
                        break;
                    case BODYPARTFRAGMENT:
                        viewPager.setCurrentItem(position);
                        break;
                    case CONCRETEFRAGMENT:
                        viewPager.setCurrentItem(position);
                        break;
                    case DISEASEDESCROPTION:
                        viewPager.setCurrentItem(position);
                        break;
                    case DISEASEQUESTION:
                        viewPager.setCurrentItem(position);
                        break;
                    case ORDERDATEFRAGMENT:
                        viewPager.setCurrentItem(position);
                        break;
                }
            }
        });
    }

    /**
     * 通过position传值跳转到相应的fragment 健康的viewpager
     * @param position
     */
    public void switchHealthyViewPager(final int position){
        this.position = position;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ContentFragment contentFragment = (ContentFragment) mActivity.getSupportFragmentManager()
                        .findFragmentByTag(PreOrderActivity.content_tag);
                ViewPager viewPager = contentFragment.getViewPager();
                switch (position){
                    case 0:
                        viewPager.setCurrentItem(position);//人员列表
                        break;
                    case 1:
                        viewPager.setCurrentItem(position);//图文描述
                        break;
                    case 2:
                        viewPager.setCurrentItem(position);//提交答案
                        break;
                }
            }
        });
    }

    public void openDialog(){
        sendMessage(8, null);
    }
    public void openTextDialog(String text){
        Log.e("lookat","text:"+text);
        sendMessage(10, text);
    }
    public void closeDialog(){
        sendMessage(9,null);
    }

}
