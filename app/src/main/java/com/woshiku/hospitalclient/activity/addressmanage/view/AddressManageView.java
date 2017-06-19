package com.woshiku.hospitalclient.activity.addressmanage.view;

import android.app.Activity;

import com.nostra13.universalimageloader.utils.L;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public interface AddressManageView {
    void initTitleBar(String title);
    void initActivity( );
    void setListView(CommonAdapter commonAdapter);
    void setUpdateListView(List<Object> objectList);
    void setLoadListWait();
    void setLoadListFail();
    void setLoadListNoData();
    void setDialogWait();
    void setDialogClose();
    void setToastFail(String msg);
    Activity getActivity();
}
