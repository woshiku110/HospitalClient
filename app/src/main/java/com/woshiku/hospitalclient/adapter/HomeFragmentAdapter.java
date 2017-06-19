package com.woshiku.hospitalclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.woshiku.hospitalclient.fragment.impleHome.FragmentFactory;

/**
 * Created by Administrator on 2016/12/14.
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter{
    FragmentActivity mActivity;

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomeFragmentAdapter(FragmentManager fm,FragmentActivity mActivity){
        this(fm);
        this.mActivity = mActivity;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createContentFragment(mActivity,position);
    }

    @Override
    public int getCount(){
        return FragmentFactory.count;
    }
}
