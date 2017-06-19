package com.woshiku.hospitalclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.woshiku.hospitalclient.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
public class TestAdapter extends CommonAdapter{
    Context context;
    public TestAdapter(Context context,List<Object> mList) {
        this.context = context;
        super.mList = mList;
    }
    @Override
    public void setAdapterList(List<Object> mList) {

    }

    @Override
    public List<Object> getAdapterList() {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
