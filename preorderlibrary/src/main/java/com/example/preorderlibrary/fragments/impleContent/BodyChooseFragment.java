package com.example.preorderlibrary.fragments.impleContent;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.domain.BodyPartImproveData;
import com.example.preorderlibrary.fragments.base.BaseFragment;
import com.example.preorderlibrary.utils.CommonUtil;
import com.example.preorderlibrary.utils.ParseDataUtil;
import com.example.preorderlibrary.utils.UrlDataUtil;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.inter.CommonUrlListener;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;

/**
 * Created by willyou on 2016/11/25.
 */
@SuppressLint("ValidFragment")
public class BodyChooseFragment extends BaseFragment implements CommonUrlListener{
    private CommonUrl commonUrl;
    ProgressBar pb;
    WebView webView;
    private int index;
    boolean isMan = true;
    @SuppressLint("ValidFragment")
    public BodyChooseFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_bodychoose,null);
        fm = (FrameLayout)mView.findViewById(R.id.fm_layout);
        commonUrl = new CommonUrl();
        commonUrl.setCommonUrlListener(this);
        TextView textView= (TextView) mView.findViewById(R.id.txt_title);
        pb = (ProgressBar) mView.findViewById(R.id.body_choose_pb);
        webView = (WebView) mView.findViewById(R.id.body_choose_webview);
        textView.setText("身体选择");
        mView.findViewById(R.id.icon_return_bt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchViewPager(CONTACTFRAGMENT);
                    }
                });
        initDatas();
        return mView;
    }
    @Override
    public void initDatas() {
        initWebView(webView, pb);
        loadUrl(true);
        sendMessage(SHOW_CONTENT, null);
    }
    public void loadUrl(boolean isMan){
        this.isMan = isMan;
        if(isMan){
            webView.loadUrl("file:///android_asset/html/nan.html");
        }else{
            webView.loadUrl("file:///android_asset/html/nv.html");
        }
    }
    @Override
    public void setFragmentAdapter() {}

    @Override
    public void updateFragmentAdapter() {}
    /**
     * 此方法的监听器在父类已经被调用
     * @param isZhen
     * @param part There is only "1" can catch the message
     */
    public void bodyChooseResult(boolean isZhen, String part) {
        if(isZhen){
            Log.e("lookat","isZhen:"+part);
        }else{
            Log.e("lookat","isFan:"+part);
        }
        choosePartIndex(CommonUtil.getBodyIndex(part));
        /*if (part.equals("头")) {
            choosePartIndex(0);
        }else if(part.equals("颈")){
            choosePartIndex(1);
        }*/
    }

    private void choosePartIndex(int index){
        this.index = index;
        openDialog();
        CommonUrlData commonUrlData = UrlDataUtil.getBodyAllParts(Global.id,Global.token);
        commonUrl.loadUrl(commonUrlData);
    }
    public void urlResult(Result result) {
        closeDialog();
        if (result.isSuccess()) {
            if (result.getIntent().equals("bodychoose_allparts")) {
                Log.e("lookat",result.getMsg());
                try {
                    List<BodyPartImproveData> bodyPartImproveDataList = ParseDataUtil.parseAllBodyParts(result.getMsg());
                    int bodyPos = 0;
                    for(int i=0;i<bodyPartImproveDataList.size();i++){
                        BodyPartImproveData bodyPartImproveData = bodyPartImproveDataList.get(i);
                        if(Integer.parseInt(bodyPartImproveData.bodyId) == index){
                            bodyPos = i;
                            break;
                        }
                    }
                    if(bodyPartImproveDataList != null){
                        ((BodyPartImproveFragment) FragmentFactory.getFragment(BODYPARTFRAGMENT)).setData(bodyPartImproveDataList,bodyPos);
                        switchViewPager(BODYPARTFRAGMENT);
                    }
                }catch (Exception e){
                    Log.e("setBodyPartMessage->","Bodychoose message has some question!",e);
                }
            }
        }else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
