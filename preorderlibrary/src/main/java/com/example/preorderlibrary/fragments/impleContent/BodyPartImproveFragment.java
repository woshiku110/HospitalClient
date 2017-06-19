package com.example.preorderlibrary.fragments.impleContent;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.domain.BodyPartImproveData;
import com.example.preorderlibrary.fragments.base.BaseFragment;
import com.example.preorderlibrary.utils.UrlDataUtil;
import com.example.preorderlibrary.views.ManageTitleView;
import com.google.gson.Gson;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.inter.CommonUrlListener;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
@SuppressLint("ValidFragment")
public class BodyPartImproveFragment extends BaseFragment implements CommonUrlListener {
    View mView;
    LinearLayout body_part_title,body_part_content;
    ManageTitleView manageTitleView;
    LinearLayout contentLayout;
    private CommonUrl commonUrl = new CommonUrl();
    //布局的高度
    int height;
    @SuppressLint("ValidFragment")
    public BodyPartImproveFragment(FragmentActivity mActiviy) {
        super(mActiviy);
    }

    @Override
    public void setFragmentAdapter() {

    }

    @Override
    public void updateFragmentAdapter() {

    }

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_bodypart_improve,null);
        TextView titleText= (TextView) mView.findViewById(R.id.txt_title);
        contentLayout = (LinearLayout)mView.findViewById(R.id.body_part_improve_content);
        body_part_title = (LinearLayout)mView.findViewById(R.id.body_part_title);
        body_part_content = (LinearLayout)mView.findViewById(R.id.body_part_content);
        mView.findViewById(R.id.icon_return_bt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchViewPager(BODYCHOOSEFRAGMENT);
                    }
                });
        measureView(contentLayout);
        commonUrl.setCommonUrlListener(this);
        titleText.setText("病症部位");
        return mView;
    }

    private void measureView(final LinearLayout contentLayout){
        ViewTreeObserver vto = contentLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contentLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height = contentLayout.getHeight();
            }
        });
    }
    public void setData(final List<BodyPartImproveData> partList,final int index){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                manageTitleView = new ManageTitleView(mActivity,partList,index,body_part_title,body_part_content,height);
                manageTitleView.setBodyPartListener(new ManageTitleView.BodyPartListener() {
                    @Override
                    public void bodyPart(String bodyId) {
                        openDialog();
                        String[] bodies = {bodyId};
                        bodyId = new Gson().toJson(bodies);
                        Log.e("bodyId","bodyId"+bodyId);
                        ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT)).setPartsId(bodyId);
                        CommonUrlData commonUrlData= UrlDataUtil.getDiseaseMessage(bodyId, Global.token);
                        commonUrl.loadUrl(commonUrlData);
                    }
                });
            }
        });
    }

    @Override
    public void urlResult(Result result) {
        closeDialog();
        if (result.isSuccess()){
            if (result.getIntent().equals("getDiseaseMessage")){
                Log.e("BodyPartFragment->",result.getMsg());
                switchViewPager(CONCRETEFRAGMENT);
                ((ConcreteFragment)FragmentFactory.getFragment(CONCRETEFRAGMENT)).setConcreteBodyMessage(result.getMsg());
            }
        }
    }
}
