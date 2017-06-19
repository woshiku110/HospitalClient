package com.woshiku.hospitalclient.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.woshiku.hospitalclient.R;
/**
 * Created by Administrator on 2017/2/14.
 */
public class MyItemsView {
    LinearLayout parentLayout;
    Context context;
    private String []names = {"我的家庭","我的处方","历史订单","帮助与反馈","退出登录"};
    private int []icons = {R.mipmap.ico_d_wdjt,R.mipmap.ico_d_wdcf,R.mipmap.ico_d_lsdd,R.mipmap.ico_d_bzfk,R.mipmap.ico_d_sz};
    private int []ids={R.id.fragment_my_family,R.id.fragment_my_prescrible,R.id.fragment_my_history,R.id.fragment_my_help,R.id.fragment_my_set};
    private UserChooseListener userChooseListener;
    public interface UserChooseListener{
        void userChoose(View view,int index);
    }

    public void setUserChooseListener(UserChooseListener userChooseListener) {
        this.userChooseListener = userChooseListener;
    }

    public MyItemsView(LinearLayout parentLayout,Context context) {
        this.parentLayout = parentLayout;
        this.context = context;
        initViews();
    }
    private void initViews(){
        parentLayout.removeAllViews();
        int itemHeight = (int)context.getResources().getDimension(R.dimen.item_my_height);
        int leftIconLeft = (int)context.getResources().getDimension(R.dimen.item_my_leftIcon_left);
        int leftIconRight = (int)context.getResources().getDimension(R.dimen.item_my_leftIcon_right);
        int rightIconRight = (int)context.getResources().getDimension(R.dimen.item_my_rightIcon_right);
        int top;
        for(int i=0;i<names.length;i++){
            final MyItemView myItemView = new MyItemView(context,i,ids[i]);
            myItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userChooseListener != null){
                        userChooseListener.userChoose(myItemView,myItemView.getPos());
                    }
                }
            });
            if(i==0||i==3){
                top = (int)context.getResources().getDimension(R.dimen.item_my_top);
            }else{
                top = 0;
            }
            if(i != names.length-1&& i != 2){
                myItemView.hideBottomLine();
            }
            myItemView.setText(names[i]);
            myItemView.setLayoutSize(itemHeight,top,leftIconLeft,leftIconRight,rightIconRight);
            myItemView.setLeftImage(icons[i]);
            parentLayout.addView(myItemView);
        }
    }
}
