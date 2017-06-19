package com.woshiku.hospitalclient.activity.comment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lidroid.xutils.BitmapUtils;
import com.woshiku.albumlibrary.utils.StatusBarCompat;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.comment.view.CommentView;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.view.ToastSuccView;
import com.woshiku.startlib.StarRateView;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import de.hdodenhof.circleimageview.CircleImageView;
import domain.CommentData;
import param.CommentDoctorParam;
import parse.CommentInfoParse;

/**
 * Created by Administrator on 2017/3/10.
 */
public class CommentActivity extends BaseActivity implements CommentView {
    @InjectView(R.id.comment_image)
    CircleImageView userIcon;
    @InjectView(R.id.comment_name)
    TextView nameView;
    @InjectView(R.id.comment_career)
    TextView careerView;
    @InjectView(R.id.comment_direct)
    TextView directView;
    @InjectView(R.id.comment_hospital)
    TextView hospitalView;
    @InjectView(R.id.comment_show_point)
    TextView showPointView;
    @InjectView(R.id.comment_set_point)
    TextView setPointView;
    @InjectView(R.id.comment_get_star)
    StarRateView getStarView;
    @InjectView(R.id.comment_set_star)
    StarRateView setStarView;
    @InjectView(R.id.comment_submit)
    LinearLayout submitLine;
    BitmapUtils bitmapUtils;
    CommonUrl commonUrl;
    CommentData commentData;
    String mark;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_comment);
        ButterKnife.inject(this);
        bitmapUtils = new BitmapUtils(this);
        commonUrl = new CommonUrl();
        initPage();
        StatusBarCompat.compat(this, getResources().getColor(R.color.tt_gray_bg));
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void swipeBackCallback() {

    }

    @Override
    public void initPage() {
        Bundle bd = getIntent().getExtras();
        String msg = bd.getString("data");
        if(!TextUtils.isEmpty(msg)){
            commentData = CommentInfoParse.commentInfo(msg);
            bitmapUtils.display(userIcon, Global.fileAddr+commentData.getIcon());
            nameView.setText(commentData.getName());
            careerView.setText(commentData.getCareer());
            directView.setText(commentData.getDirect());
            hospitalView.setText(commentData.getHospital());
            try{
                Log.e("lookat", "fudianshu:" + commentData.getShowPoint());
                float shu = Float.parseFloat(commentData.getShowPoint());
                showPointView.setText(String.format("%.1f",shu));
            }catch(Exception e){
                e.printStackTrace();
                LogUtil.print("显示文本异常");
            }
            try{
                getStarView.setUserSetMark(Float.parseFloat(commentData.getShowPoint()));
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.print("浮点数转换异常");
            }
            setStarView.setStarRateListener(new StarRateView.StarRateListener() {
                @Override
                public void starRate(final int index) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mark = (index+1) + "";
                            setPointView.setText((index+1) + " 分");
                        }
                    });
                }
            });
        }
    }

    @OnClick({R.id.comment_top,R.id.comment_submit})
    void userClick(View view){
        switch (view.getId()){
            case R.id.comment_top:
                finish();
                break;
            case R.id.comment_submit:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        openDialog();
                        Result result = commonUrl.loadUrlAsc(CommentDoctorParam.commentDoctor(commentData.getDoctorId(), mark));
                        closeDialog();
                        if(result.isSuccess()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new ToastSuccView(CommentActivity.this).showShort();
                                }
                            });
                            finish();
                        }else{
                            toastShort(result.getMsg()+"");
                        }

                    }
                }).start();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
