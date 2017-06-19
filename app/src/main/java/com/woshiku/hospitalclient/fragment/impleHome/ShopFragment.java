package com.woshiku.hospitalclient.fragment.impleHome;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import com.woshiku.hospitalclient.fragment.WebFragment;

/**
 * Created by Administrator on 2016/12/14.
 */
@SuppressLint("ValidFragment")
public class ShopFragment extends WebFragment{

    public ShopFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public void childLoad() {
        loadUrl("https://weidian.com/?userid=261062599&spider=w95jw2.46bh1gow.0.0&hd_back=1");
    }
}
