package com.woshiku.hospitalclient.fragment.impleHome.view;

import android.webkit.WebView;
import com.woshiku.progresslibrary.RoundCornerProgressBar;
/**
 * Created by Administrator on 2017/3/9.
 */
public interface CheckView {
    void initPage(String title,String url);
    void initLocalPage(String title,String url);
    void setProgress(int percent);
    void setShowProgress();
    void setHideProgress();
    WebView getWebView();
    void isShowBack(boolean isShow);
    RoundCornerProgressBar getProgressBar();
}
