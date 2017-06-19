package com.woshiku.hospitalclient.fragment.impleHome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.example.preorderlibrary.PreOrderActivity;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.HoldDiagnsisActivity;
import com.woshiku.hospitalclient.activity.MainActivity;
import com.woshiku.hospitalclient.activity.check.CheckActivity;
import com.woshiku.hospitalclient.fragment.BaseFragment;
import com.woshiku.hospitalclient.loader.GlideImageLoader;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.utils.ThreadManage;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import java.util.Arrays;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;

/**
 * Created by Administrator on 2016/12/14.
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment{
    View mView;
    String[] urls;
    @InjectView(R.id.banner)
    Banner banner;
    public static final int ACTION_REQUEST_ADDRESS_IMAGES = 7;
    @SuppressLint("ValidFragment")
    public HomeFragment(FragmentActivity mActivity) {
        super(mActivity);
    }
    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_main_home, null);
        ButterKnife.inject(this, mView);
        urls = mActivity.getResources().getStringArray(R.array.url);
        initBanner(urls);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        LogUtil.print("onstart");
    }

    @Override
    public void onPause() {
        super.onPause();
        //banner.stopAutoPlay();
        LogUtil.print("onPause");
    }

    private void initBanner(String[] urls){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(Arrays.asList(urls));
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }
    boolean repeatClick = false;
    @OnClick({R.id.pre_order,R.id.hou_zhen,R.id.home_check_project,R.id.fragment_home_healthy_line,R.id.fragment_home_export_doctor,R.id.fragment_home_export_repeat,R.id.fragment_home_medical,R.id.fragment_home_healthy})
    void userOperate(View view){
        MainActivity mainUi = (MainActivity)mActivity;
        if(mainUi.isAllowWrite("HomeFragment",view)){
            if(mainUi.isAllowPhoto("HomeFragment",view)){
                dealClick(view);
            }else{
                mainUi.setTakePhoto();
            }
        }else{
            mainUi.setTakeWrite();
        }
    }

    public void dealClick(final View view){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                if (!repeatClick) {
                    repeatClick = true;
                    switch (view.getId()) {
                        case R.id.pre_order:
                            Intent intent = new Intent(mActivity, PreOrderActivity.class);
                            intent.putExtra("intent_string_id", Global.loginMessage.id);
                            intent.putExtra("intent_string", Global.loginMessage.token);
                            intent.putExtra("type",1);
                            startActivity(intent);
                            break;
                        case R.id.hou_zhen:
                            startActivity(new Intent(mActivity, HoldDiagnsisActivity.class));
                            break;
                        case R.id.home_check_project:
                            startActivity(new Intent(mActivity, CheckActivity.class));
                            break;
                        case R.id.fragment_home_healthy_line:
                            Intent urlIntent = new Intent("com.web.HealthyInfoActivity");
                            Bundle bd = new Bundle();
                            bd.putString("title","健康信息");
                            bd.putString("loadUrl","ExternalWeb/Healthy/healthB/health.html");
                            bd.putString("intent","loadasset");
                            urlIntent.putExtras(bd);
                            startActivity(urlIntent);
                            break;
                        case R.id.fragment_home_export_doctor:
                            Intent urlIntentOne = new Intent("com.web.ExportDoctorActivity");
                            Bundle bdOne = new Bundle();
                            bdOne.putString("title","健康信息");
                            bdOne.putString("loadUrl","ExternalWeb/docotorMessage/doctorList.html");
                            bdOne.putString("intent","loadasset");
                            urlIntentOne.putExtras(bdOne);
                            startActivity(urlIntentOne);
                            break;
                        case R.id.fragment_home_export_repeat:
                            Global.openComment = true;
                            /*Intent commentIntent = new Intent("com.comment.CommentActivity");
                            startActivity(commentIntent);*/
                            break;
                        case R.id.fragment_home_medical:
                            Intent medIntent = new Intent(mActivity, PreOrderActivity.class);
                            medIntent.putExtra("intent_string_id", Global.loginMessage.id);
                            medIntent.putExtra("intent_string", Global.loginMessage.token);
                            medIntent.putExtra("type",2);
                            startActivity(medIntent);
                            break;
                        case R.id.fragment_home_healthy:
                            Intent healIntent = new Intent(mActivity, PreOrderActivity.class);
                            healIntent.putExtra("intent_string_id", Global.loginMessage.id);
                            healIntent.putExtra("intent_string", Global.loginMessage.token);
                            healIntent.putExtra("type",3);
                            startActivity(healIntent);
                            break;
                    }
                }
                repeatClick = false;
            }
        });
    }
}
