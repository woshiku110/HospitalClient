package com.example.preorderlibrary.adapters.impleAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.baseAdapter.MyAdapter;
import com.example.preorderlibrary.domain.BodyPartMessage;

import java.util.List;

/**
 * Created by willyou on 2016/11/29.
 */

public class BodypartListAdapter extends MyAdapter<BodyPartMessage> {
    public BodypartListAdapter(Context context, List<BodyPartMessage> data) {
        super(context, data);
    }
    private final class ViewHolder{
        TextView bodypartListText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView = inflater.inflate(R.layout.bodypart_list, parent, false);
            viewHolder.bodypartListText= (TextView) convertView.findViewById(R.id.bodypart_list_text);
            convertView.setTag(R.id.bodypart_list,viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag(R.id.bodypart_list);
        }
        viewHolder.bodypartListText.setText(data.get(position).getSecondTitle());
        return convertView;
    }
}
