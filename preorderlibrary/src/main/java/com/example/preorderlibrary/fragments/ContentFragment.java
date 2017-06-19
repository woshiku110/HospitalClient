package com.example.preorderlibrary.fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.example.preorderlibrary.PreOrderActivity;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.fragments.base.BaseFragment;
import com.example.preorderlibrary.fragments.impleContent.FragmentFactory;

/**
 * Created by willyou on 2016/11/25.
 */

@SuppressLint("ValidFragment")
public class ContentFragment extends BaseFragment {
    View mView;
    ViewPager viewPager;
    String []titles = {"contact","bodychoose","bodypart","concrete","disease","question"
    ,"orderDate"};
    @SuppressLint("ValidFragment")
    public ContentFragment(FragmentActivity mActiviy) {
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
        if(Global.pre_type.equals("3")){//健康
            titles = new String[]{"contact","disease","orderDate"};
        }else{//中医西医
            titles = new String[]{"contact","bodychoose","bodypart","concrete","disease","question","orderDate"};
        }
        mView = View.inflate(mActivity, R.layout.fragment_pre_content,null);
        viewPager = (ViewPager)mView.findViewById(R.id.pre_content_viewpager);
        viewPager.setOffscreenPageLimit(titles.length);
        //viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new ShowAdapter(mActivity.getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PreOrderActivity mainUi = (PreOrderActivity)mActivity;
                if(position == 0){
                    mainUi.setGesture(true);//设置可以滑动
                }else{
                    mainUi.setGesture(false);//设置不可以滑动
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return mView;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    class ShowAdapter extends FragmentStatePagerAdapter {

        public ShowAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(Global.pre_type.equals("3")){//健康
                return FragmentFactory.createHealthyFragment(mActivity,position);
            }else{//中医西医
                return FragmentFactory.createContentFragment(mActivity, position);
            }
        }
        @Override
        public int getCount() {
            return titles.length;
        }
    }
    public int getCurrentPage(){
        return viewPager.getCurrentItem();
    }
    public void setCurrentPage(){
        int index = viewPager.getCurrentItem();
        if(index == 6){
            if(Global.diseaseIsEmpty){
                viewPager.setCurrentItem(4,true);
            }else{
                viewPager.setCurrentItem(5,true);
            }
            return;
        }
        if(index == 4){
            if(Global.diseaseIsEmpty){
                viewPager.setCurrentItem(2,true);
            }else{
                viewPager.setCurrentItem(3,true);
            }
            return;
        }
        viewPager.setCurrentItem(index-1,true);
    }
    public void setHealthyCurrentPage(){
        int index = viewPager.getCurrentItem();
        viewPager.setCurrentItem(index-1,true);
    }
}
