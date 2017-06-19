package com.example.preorderlibrary.fragments.impleContent;

import android.support.v4.app.FragmentActivity;
import com.example.preorderlibrary.fragments.base.BaseFragment;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/9.
 */
public class FragmentFactory {
    protected static Map<Integer,BaseFragment> map = new HashMap<>();
    public static BaseFragment createContentFragment(FragmentActivity mActivity, int index){
        BaseFragment baseFragment = map.get(index);
        if(baseFragment == null){
            switch(index){
                case 0:
                    baseFragment = new ContactFragment(mActivity);
                    map.put(index, baseFragment);
                    break;
                case 1:
                    baseFragment = new BodyChooseFragment(mActivity);
                    map.put(index, baseFragment);
                    break;
                case 2:
                    baseFragment = new BodyPartImproveFragment(mActivity);
                    map.put(index, baseFragment);
                    break;
                case 3:
                    baseFragment = new ConcreteFragment(mActivity);
                    map.put(index,baseFragment);
                    break;
                case 4:
                    baseFragment=new DiseaseDescription(mActivity);
                    map.put(index,baseFragment);
                    break;
                case 5:
                    baseFragment=new DiseaseQuestion(mActivity);
                    map.put(index,baseFragment);
                    break;
                case 6:
                    baseFragment=new OrderDataFragment(mActivity);
                    map.put(index,baseFragment);
                    break;
            }
        }
        return baseFragment;
    }
    /*创建健康fragment*/
    public static BaseFragment createHealthyFragment(FragmentActivity mActivity, int index){
        BaseFragment baseFragment = map.get(index);
        if(baseFragment == null){
            switch(index){
                case 0:
                    baseFragment = new ContactFragment(mActivity);
                    map.put(index, baseFragment);
                    break;
                case 1:
                    baseFragment=new DiseaseDescription(mActivity);
                    map.put(index,baseFragment);
                    break;
                case 2:
                    baseFragment=new OrderDataFragment(mActivity);
                    map.put(index,baseFragment);
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
    public static void clearMap(){
        map = new HashMap<>();
    }
}
