package com.woshiku.hospitalclient.adapter.base;

import android.widget.BaseAdapter;
import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
public abstract class CommonAdapter extends BaseAdapter{
    public List<Object> mList;

    public abstract void setAdapterList(List<Object> mList);

    public abstract List<Object> getAdapterList();
}
