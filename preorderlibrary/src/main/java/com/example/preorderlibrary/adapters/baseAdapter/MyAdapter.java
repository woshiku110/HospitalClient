package com.example.preorderlibrary.adapters.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by willyou on 2016/11/28.
 */

public abstract class MyAdapter<T> extends BaseAdapter {

    protected List<T> data;
    protected LayoutInflater inflater;
    protected Context context;

    public MyAdapter(Context context, List<T> data) {
        this.data = data;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
