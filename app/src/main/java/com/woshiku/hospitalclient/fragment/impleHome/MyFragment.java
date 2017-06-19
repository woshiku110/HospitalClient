package com.woshiku.hospitalclient.fragment.impleHome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lidroid.xutils.BitmapUtils;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.MainActivity;
import com.woshiku.hospitalclient.fragment.BaseFragment;
import com.woshiku.hospitalclient.utils.ThreadManage;
import com.woshiku.hospitalclient.view.MyItemsView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/12/14.
 */
@SuppressLint("ValidFragment")
public class MyFragment extends BaseFragment{
    @InjectView(R.id.my_scroll_line)
    LinearLayout scrollLine;
    View mView;
    @InjectView(R.id.my_image_head)
    CircleImageView iconImage;
    @InjectView(R.id.my_name_text)
    TextView nameText;
    @InjectView(R.id.fragment_my_message_point)
    View pointView;
    BitmapUtils bitmapUtils;
    @SuppressLint("ValidFragment")
    public MyFragment(FragmentActivity mActivity) {
        super(mActivity);
    }
    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_main_my, null);
        ButterKnife.inject(this, mView);
        init();
        bitmapUtils.configDefaultLoadFailedImage(getContext().getResources().getDrawable(R.mipmap.icon_head));
        bitmapUtils.display(iconImage,Global.fileAddr+Global.loginMessage.logo);
        return mView;
    }
    private void init(){
        bitmapUtils = new BitmapUtils(mActivity);
        bitmapUtils.configDefaultLoadFailedImage(getContext().getResources().getDrawable(R.mipmap.icon_head));
        if(!TextUtils.isEmpty(Global.loginMessage.logo)){
            bitmapUtils.display(iconImage,Global.fileAddr+Global.loginMessage.logo);
        }
        if(!TextUtils.isEmpty(Global.loginMessage.name)){
            nameText.setText(Global.loginMessage.name);
        }
        new MyItemsView(scrollLine,mActivity).setUserChooseListener(new MyItemsView.UserChooseListener() {
            @Override
            public void userChoose(View view,int index) {
                dealClick(view);
            }
        });
        showPoint(false);
    }
    boolean repeatClick = false;
    @OnClick({R.id.my_image_head,R.id.home_fragment_my_preorder_line,R.id.my_wallet_line,R.id.my_news_line})
    void userClick(View view){
        MainActivity mainUi = (MainActivity)mActivity;
        if(mainUi.isAllowWrite("MyFragment",view)){
            if(mainUi.isAllowPhoto("MyFragment",view)){
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
                if(!repeatClick){
                    repeatClick = true;
                    MainActivity mainUi = (MainActivity) mActivity;
                    switch (view.getId()) {
                        case R.id.my_image_head:
                            if (!TextUtils.isEmpty(Global.loginMessage.logo)) {
                                //mainUi.enterMyFamily();
                            } else {
                                //查看本人用户
                            }
                            break;
                        case R.id.home_fragment_my_preorder_line:
                            mainUi.enterPreorder();
                            break;
                        case R.id.fragment_my_family:
                            startActivity(new Intent("com.myfamily.MyFamilyActivity"));
                            break;
                        case R.id.fragment_my_prescrible:
                            startActivity(new Intent("com.prescription.PrescriptionActivity"));
                            break;
                        case R.id.fragment_my_history:
                            Intent hisIntent = new Intent("com.web.HistoryActivity");
                            Bundle hisbd = new Bundle();
                            hisbd.putString("title","健康信息");
                            hisbd.putString("loadUrl","ExternalWeb/historyOrder/historyOrder.html");
                            hisbd.putString("intent","loadasset");
                            hisIntent.putExtras(hisbd);
                            startActivity(hisIntent);
                            break;
                        case R.id.fragment_my_help:
                            Intent helpIntent = new Intent("com.web.HelpActivity");
                            Bundle helpBd = new Bundle();
                            helpBd.putString("title","健康信息");
                            helpBd.putString("loadUrl","ExternalWeb/help/help.html");
                            helpBd.putString("intent","loadasset");
                            helpIntent.putExtras(helpBd);
                            startActivity(helpIntent);
                            break;
                        case R.id.fragment_my_set:
                            mainUi.endApp();
                            break;
                        case R.id.my_wallet_line:
                            Intent urlIntent = new Intent("com.web.WalletActivity");
                            Bundle bd = new Bundle();
                            bd.putString("title","健康信息");
                            bd.putString("loadUrl","ExternalWeb/countM/myWallet.html");
                            bd.putString("intent","loadasset");
                            urlIntent.putExtras(bd);
                            startActivity(urlIntent);
                            break;
                        case R.id.my_news_line:
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showPoint(false);
                                }
                            });
                            mainUi.hideAmount();
                            Intent intent = new Intent("com.notify.NewsRemindActivity");
                            startActivity(intent);
                            break;
                    }
                }
                repeatClick = false;
            }
        });
    }

    public void showPoint(boolean isShow){
        if(isShow){
            pointView.setVisibility(View.VISIBLE);
        }else{
            pointView.setVisibility(View.GONE);
        }
    }
}
