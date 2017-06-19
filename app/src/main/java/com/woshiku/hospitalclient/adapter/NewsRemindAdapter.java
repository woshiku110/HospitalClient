package com.woshiku.hospitalclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import java.util.List;
import domain.NewsRemindData;

/**
 * Created by Administrator on 2017/3/20.
 */
public class NewsRemindAdapter extends CommonAdapter{
    Context context;
    public NewsRemindAdapter(Context context, List<Object> mList) {
        this.context = context;
        super.mList = mList;
    }

    @Override
    public void setAdapterList(List<Object> mList) {
        this.mList = mList;
    }

    @Override
    public List<Object> getAdapterList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_news_remind,null);
            viewHolder.title = (TextView)convertView.findViewById(R.id.item_news_title);
            viewHolder.content = (TextView)convertView.findViewById(R.id.item_news_content);
            viewHolder.date = (TextView)convertView.findViewById(R.id.item_news_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        NewsRemindData newsRemindData = (NewsRemindData)mList.get(position);
        viewHolder.title.setText(newsRemindData.getTitle());
        viewHolder.content.setText(newsRemindData.getContent());
        if(newsRemindData.getDate().length()>9){
            viewHolder.date.setText(newsRemindData.getDate().substring(0,9));
        }else{
            viewHolder.date.setText(newsRemindData.getDate());
        }
        return convertView;
    }

    class ViewHolder{
        TextView title;
        TextView content;
        TextView date;
    }
}
