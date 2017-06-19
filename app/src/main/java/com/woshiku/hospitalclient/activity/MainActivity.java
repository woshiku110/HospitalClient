package com.woshiku.hospitalclient.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.adapter.HomeFragmentAdapter;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.fragment.impleHome.FragmentFactory;
import com.woshiku.hospitalclient.fragment.impleHome.HomeFragment;
import com.woshiku.hospitalclient.fragment.impleHome.MyFragment;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.utils.RdUtil;
import com.woshiku.tabbarlib.view.HomeBottomView;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.ButterKnife;
import butterknife.InjectView;
import common.Global;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.home_bottom_view)
    HomeBottomView homeBottomView;
    @InjectView(R.id.home_relate)
    RelativeLayout relateBg;
    ViewPager viewPager;
    private SwipeBackLayout swipeBackLayout;
    NewsReceiver newsReceiver;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initBroadCast();
        viewPager = homeBottomView.getViewPager();
        viewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager(), this));
        homeBottomView.chooseDefault(2);//默认选中页(第二页 home)
        viewPager.setCurrentItem(2, false);
        homeBottomView.setBottomViewListener(new HomeBottomView.BottomViewListener() {
            @Override
            public void chooseBottomIndex(int index) {
                switch (index){
                    case 2:
                        setBackgroundColor(R.color.titleBlue);
                        break;
                    case 4:
                        setBackgroundColor(R.color.title_my_bg);
                        break;
                }
                Log.e("lookat", "user choose" + index);
            }
        });
        swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(false);//设置可以滑动
    }
    private void initBroadCast(){
        newsReceiver = new NewsReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Global.remindAction);
        registerReceiver(newsReceiver, filter);
    }
    private void removeBroadCast(){
        if(newsReceiver != null){
            unregisterReceiver(newsReceiver);
            newsReceiver = null;
        }
    }
    @Override
    protected void allowPhoto() {//允许拍照
        if(getWho().equals("HomeFragment")){
            HomeFragment homeFragment = (HomeFragment)FragmentFactory.getFragment(2);
            homeFragment.dealClick(getFragmentView());
        }else if(getWho().equals("MyFragment")){
            MyFragment myFragment = (MyFragment)FragmentFactory.getFragment(4);
            myFragment.dealClick(getFragmentView());
        }
    }

    @Override
    protected void allowWrite() {//允许写入文件
        if(getWho().equals("HomeFragment")){
             if(isAllowPhoto(getWho(),getFragmentView())){
                HomeFragment homeFragment = (HomeFragment)FragmentFactory.getFragment(2);
                homeFragment.dealClick(getFragmentView());
             }else{
               setTakePhoto();
             }
        }else if(getWho().equals("MyFragment")){
            if(isAllowPhoto(getWho(),getFragmentView())){
                MyFragment myFragment = (MyFragment)FragmentFactory.getFragment(4);
                myFragment.dealClick(getFragmentView());
            }else{
                setTakePhoto();
            }
        }
    }

    public void setBackgroundColor(int color){
        relateBg.setBackgroundColor(getResources().getColor(color));
    }

    //用于创建本人的账户
    public void enterMyFamily(){
        Intent intent = new Intent("com.myfamily.NewFamilyActivity");
        Bundle bd = new Bundle();
        bd.putInt("action", Global.MY_NEW_USER);
        intent.putExtras(bd);
        startActivityForResult(intent, Global.CreateMyFamilyRequest);
    }

    public void reviseMyFamily(String msg){
        Intent intent = new Intent("com.myfamily.NewFamilyActivity");
        Bundle bd = new Bundle();
        bd.putInt("action", Global.REVISE_MY_USER);
        intent.putExtras(bd);
        startActivityForResult(intent,Global.CreateLookMyFamilyRequest);
    }

    //进入预约预诊活动
    public void enterPreorder(){
        Intent intent = new Intent("com.myfamily.PreorderActivity");
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10000 && resultCode==10001){
            LogUtil.print(data.getStringExtra("myfamily"));
        }else if(requestCode == Global.CreateMyFamilyRequest && resultCode == Global.CreateMyFamilyReturn){
            LogUtil.print("本人更新");
        }
    }

    class NewsReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Global.remindAction)){
                Bundle bd = intent.getExtras();
                boolean haveData = bd.getBoolean("haveData");
                final MyFragment myFragment = (MyFragment)FragmentFactory.getFragment(4);
                if(haveData){
                    if(myFragment != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myFragment.showPoint(true);
                                homeBottomView.setNewsAmount(Global.unreadNewsAmount);//未读消息数量
                            }
                        });
                    }
                    LogUtil.print("haveData","is have data:"+haveData);
                }else{

                    LogUtil.print("haveData","is have data:"+haveData);
                }
            }
        }
    }
    /**
     * @desc 底部栏的数量
     * */
    public void hideAmount(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeBottomView.hideAmount();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            exitBy2Click();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    boolean isExit;
    private void exitBy2Click(){
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {//结束应用自身
            removeBroadCast();
            closeService();
            FragmentFactory.clearFragments();
            finish();
        }
    }

    /**
     * 仅用于退出登录
     * */
    public void endApp(){
        RdUtil.saveData("login","off");//下线
        removeBroadCast();
        closeService();
        FragmentFactory.clearFragments();
        finish();
    }
    private void closeService(){
        if(Global.notifyIntent != null){
            stopService(Global.notifyIntent);
            Global.notifyIntent = null;
        }
    }
    @Override
    public void swipeBackCallback() {
        Log.e("lookat", "main swipe back");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
