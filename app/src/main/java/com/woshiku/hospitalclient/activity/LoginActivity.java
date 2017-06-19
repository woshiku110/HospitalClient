package com.woshiku.hospitalclient.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.service.NotifyService;
import com.woshiku.inputlib.LeftIconInput;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import domain.LoginData;
import param.LoginParam;
import parse.LoginParse;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.input_user_name)
    LeftIconInput user_name;
    @InjectView(R.id.input_user_pass)
    LeftIconInput user_pass;
    @InjectView(R.id.scroll_parent)
    LinearLayout lineTop;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        user_name.setPasswordType();
        /*user_name.setText("1");
        user_pass.setText("1");*/
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @OnClick({R.id.login_bt,R.id.fast_register_txt})
    void userClick(View view){
        switch (view.getId()){
            case R.id.login_bt:
                final String userName = user_name.getText();
                final String userPass = user_pass.getText();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        LoginData.Message loginMessage = LoginParse.login(hpUtil.loadUrlAsc(LoginParam.login(userName, userPass, mHandler)), mHandler);
                        if(loginMessage!=null){
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            beginService();//开启通知服务
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    }
                }.start();
                break;
            case R.id.fast_register_txt:
                Intent intent = new Intent("com.woshiku.RegisterActivity");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void swipeBackCallback() {

    }

    /**
     * 开启服务
     * */
    private void beginService(){
        if(Global.notifyIntent == null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Global.notifyIntent = new Intent(LoginActivity.this,NotifyService.class);
                    startService(Global.notifyIntent);
                }
            }).start();
        }
    }
}
