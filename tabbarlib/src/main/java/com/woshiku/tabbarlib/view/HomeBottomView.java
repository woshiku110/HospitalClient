package com.woshiku.tabbarlib.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woshiku.tabbarlib.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class HomeBottomView extends RelativeLayout{
    List<LinearLayout> mList;
    int selectedIndex = 2;
    private BottomViewListener bottomViewListener;
    ViewPager viewPager;
    TextView amountView;
    private String[] titles = {"check","more","home","shop","my"};
    public interface BottomViewListener{
        void chooseBottomIndex(int index);
    }

    public void setBottomViewListener(BottomViewListener bottomViewListener) {
        this.bottomViewListener = bottomViewListener;
    }

    public HomeBottomView(Context context) {
        super(context);
    }

    public HomeBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_bottom, this);
        viewPager = (ViewPager)findViewById(R.id.tab_view_pager);
        amountView = (TextView)findViewById(R.id.my_amount_txt);
        viewPager.setOffscreenPageLimit(titles.length);
        HomeStateView one = (HomeStateView)findViewById(R.id.self_check);
        HomeStateView two = (HomeStateView)findViewById(R.id.more_check);
        HomeLogoView three = (HomeLogoView)findViewById(R.id.home_check);
        HomeStateView four = (HomeStateView)findViewById(R.id.shop_check);
        HomeStateView five = (HomeStateView)findViewById(R.id.my_check);
        mList = new ArrayList<>();
        mList.add(one);
        mList.add(two);
        mList.add(three);
        mList.add(four);
        mList.add(five);
        addAllClcikEvent(mList);
        amountView.setVisibility(View.GONE);
    }
    public void chooseDefault(int index){
        selectedIndex = index;
        for(int i=0;i<mList.size();i++){
            LinearLayout view = mList.get(i);
            if(view instanceof HomeStateView){
                HomeStateView homeStateView = (HomeStateView)view;
                if(i==index){
                    homeStateView.setSelected(true);
                }else{
                    homeStateView.setSelected(false);
                }
            }else{
                HomeLogoView homeLogoView = (HomeLogoView)view;
                if(i==index){
                    homeLogoView.setSelected(true);
                }else{
                    homeLogoView.setSelected(false);
                }
            }
        }
    }
    private void addAllClcikEvent(List<LinearLayout> mList){
        for(int i=0;i<mList.size();i++){
            final int pos = i;
            mList.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseDefault(pos);
                    viewPager.setCurrentItem(pos, false);
                    if(bottomViewListener!=null){
                        bottomViewListener.chooseBottomIndex(pos);
                    }
                }
            });
        }
    }
    public ViewPager getViewPager(){
        return viewPager;
    }

    public void setNewsAmount(int amount){
        if(amount>0){
            amountView.setVisibility(View.VISIBLE);
            amountView.setText(amount+"");
        }
    }
    public void hideAmount(){
        amountView.setVisibility(View.GONE);
    }
}
