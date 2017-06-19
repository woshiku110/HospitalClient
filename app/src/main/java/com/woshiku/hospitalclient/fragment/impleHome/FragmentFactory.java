package com.woshiku.hospitalclient.fragment.impleHome;

import android.support.v4.app.FragmentActivity;
import com.woshiku.hospitalclient.fragment.BaseFragment;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/16.
 */
public class FragmentFactory {
    protected static Map<Integer,BaseFragment> map = new HashMap<>();
    public static int count = 5;
    public static BaseFragment createContentFragment(FragmentActivity mActivity,int pos){
        BaseFragment baseFragment = map.get(pos);
        if(baseFragment == null){
            switch(pos){
                case 0:
                    baseFragment = new CheckFragment(mActivity);
                    map.put(pos, baseFragment);
                    break;
                case 1:
                    baseFragment = new MoreFragment(mActivity);
                    map.put(pos, baseFragment);
                    break;
                case 2:
                    baseFragment = new HomeFragment(mActivity);
                    map.put(pos, baseFragment);
                    break;
                case 3:
                    baseFragment = new ShopFragment(mActivity);
                    map.put(pos, baseFragment);
                    break;
                case 4:
                    baseFragment = new MyFragment(mActivity);
                    map.put(pos, baseFragment);
                    break;
            }
        }
        return baseFragment;
    }

    public static BaseFragment getFragment(int pos){
        BaseFragment baseFragment = map.get(pos);
        if(baseFragment!=null){
            return baseFragment;
        }
        return null;
    }
    public static void clearFragments(){
        map = new HashMap<>();
    }
}
