package com.woshiku.hospitalclient.activity.register;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.register.presenter.RegisterPresenter;
import com.woshiku.hospitalclient.activity.register.presenter.RegisterPresenterImple;
import com.woshiku.hospitalclient.activity.register.view.RegisterView;
import com.woshiku.hospitalclient.activity.userinfo.UserInfoActivity;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import domain.RegisterData;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/3/13.
 */
public class RegisterActivity extends BaseActivity implements RegisterView{
    @InjectView(R.id.register_phone)
    EditText phoneView;
    @InjectView(R.id.register_name)
    EditText nameView;
    @InjectView(R.id.register_pass)
    EditText passView;
    @InjectView(R.id.register_pass_correct)
    EditText repeatpassView;
    @InjectView(R.id.txt_title)
    TextView titleView;
    @InjectView(R.id.concrete_bt)
    TextView confirm_bt;
    RegisterPresenter presenter;
    String who = "register";
    View view;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        presenter = new RegisterPresenterImple(this);
        presenter.loadPage("填写账号信息");
    }
    @Override
    public void initPage(String title) {
        initTitleBar(title);
    }

    @OnClick({R.id.register_next_step,R.id.icon_return_bt})
    void userClick(View view){
        switch (view.getId()){
            case R.id.register_next_step:
                this.view = view;
                if(isAllowWrite(who,view)){
                    if(isAllowPhoto(who,view)){
                        presenter.login();
                    }else{
                        allowPhoto();
                    }
                }else{
                    allowWrite();
                }
                //startActivity(new Intent(this, UserInfoActivity.class));
                break;
            case R.id.icon_return_bt:
                scrollToFinishActivity();
                break;
        }
    }

    @Override
    public void swipeBackCallback() {

    }

    @Override
    public void initTitleBar(String title) {
        titleView.setText(title);
        confirm_bt.setVisibility(View.INVISIBLE);
        initActivity();
    }

    @Override
    public void initActivity() {
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(false);//设置可以滑动
    }

    @Override
    public void setToastError(String msg) {
        toastShort(msg);
    }

    @Override
    public void setDialogOpen() {
        openDialog();
    }

    @Override
    public void setDialogClose() {
        closeDialog();
    }

    @Override
    public void registerSuccess() {
        startActivity(new Intent(this, UserInfoActivity.class));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();//结束算账
                        //scrollToFinishActivity();//new ToastSuccView(RegisterActivity.this).showShort();
                    }
                });
            }
        });
    }

    @Override
    public RegisterData getInputData() {
        String phone = phoneView.getText().toString();
        String email = nameView.getText().toString();
        String password = passView.getText().toString();
        String repassword = repeatpassView.getText().toString();
        return new RegisterData(phone,password,repassword,email);
    }

    @Override
    protected void allowPhoto() {
        presenter.login();
    }

    @Override
    protected void allowWrite() {
        if(isAllowPhoto(who,view)){
            presenter.login();
        }else{
            if(isAllowPhoto(who,view)){
                presenter.login();
            }else{
                allowPhoto();
            }
        }
    }
}
