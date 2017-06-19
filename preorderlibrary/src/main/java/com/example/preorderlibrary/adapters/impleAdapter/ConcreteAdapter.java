package com.example.preorderlibrary.adapters.impleAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.domain.ConcreteBodyPartMessage;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by willyou on 2016/11/16.
 */

public class ConcreteAdapter extends BaseAdapter {
    private List<ConcreteBodyPartMessage>data;
    private LayoutInflater layoutInflater;
    private Button button;
    private List<Map<String,Object>>isSelected=new ArrayList<>();
    private List<String>id=new ArrayList<>();
    private ChooseSymbolListener chooseSymbolListener;
    BitmapUtils bitmapUtils;
    public interface ChooseSymbolListener{
        void symbolChoose(String userSelected);
    }

    public void setChooseSymbolListener(ChooseSymbolListener chooseSymbolListener) {
        this.chooseSymbolListener = chooseSymbolListener;
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
    public ConcreteAdapter(Context context, List<ConcreteBodyPartMessage>data,Button button){
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
        this.button=button;
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.ic_launcher);
    }


    private final class ViewHolder{
        private ImageView concrete_image;
        private TextView concrete_text;
        public ImageView concrete_checkbox;
    }
    public void refresh(List<ConcreteBodyPartMessage>data){
        this.data=data;
        initState();
        notifyDataSetChanged();
    }
    public void initState(){
        isSelected.clear();
        for (int i=0;i<data.size();i++){
            Map<String,Object>map=new HashMap<>(2);
            map.put("checkState",false);
            map.put("resource", R.drawable.btn_yes_s);
            map.put("ID",data.get(i).getDiseaseId());
            isSelected.add(map);
        }
    }
    public String getIsSelectedTrue(){
        id.clear();
        for (int i=0;i<isSelected.size();i++){
            if ((Boolean) isSelected.get(i).get("checkState")){
                id.add("\'"+isSelected.get(i).get("ID")+"\'");
            }
        }
        return id.size()==0?null : id.toString();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.concrete_item,parent,false);
            holder.concrete_image=(ImageView)convertView.findViewById(R.id.concrete_image);
            holder.concrete_text=(TextView)convertView.findViewById(R.id.concrete_text);
            holder.concrete_checkbox=(ImageView)convertView.findViewById(R.id.concrete_checkbox);
            convertView.setTag(R.layout.concrete_item,holder);
        }else{
            holder = (ViewHolder) convertView.getTag(R.layout.concrete_item);
        }
        holder.concrete_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bitmapUtils.display(holder.concrete_image, Global.fileAddr+data.get(position).getDiseasePic());
        holder.concrete_text.setText(data.get(position).getDiseaseTitle());
        try {
            holder.concrete_checkbox.setImageResource((int)isSelected.get(position).get("resource"));
        } catch (Exception e) {
            Log.d("tag", "getView: " + position);
        }

        final ViewHolder finalHolder = holder;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //button.setVisibility(View.VISIBLE);
                if (isSelected.get(position).containsValue(false)){
                    isSelected.get(position).put("checkState",true);
                    Log.e("选中checkbox",isSelected.get(position).values().toString());
                    finalHolder.concrete_checkbox.setImageResource(R.drawable.btn_yes_n);
                }else {
                    isSelected.get(position).put("checkState",false);
                    Log.e("选中checkbox",isSelected.get(position).values().toString());
                    finalHolder.concrete_checkbox.setImageResource(R.drawable.btn_yes_s);
                }
                if(chooseSymbolListener != null){
                    chooseSymbolListener.symbolChoose(getIsSelectedTrue());
                }
            }
        });
        return convertView;
    }
}
