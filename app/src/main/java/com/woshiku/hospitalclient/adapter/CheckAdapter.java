package com.woshiku.hospitalclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lidroid.xutils.BitmapUtils;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import java.util.List;
import common.Global;
import de.hdodenhof.circleimageview.CircleImageView;
import domain.CheckData;

/**
 * Created by Administrator on 2017/2/25.
 */
public class CheckAdapter extends CommonAdapter{
    BitmapUtils bitmapUtils;
    Context context;
    public CheckAdapter(Context context,List<Object> mList){
        this.context = context;
        super.mList = mList;
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadFailedImage(context.getResources().getDrawable(R.mipmap.icon_head));
    }

    @Override
    public void setAdapterList(List<Object> mList) {

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
            convertView = View.inflate(context, R.layout.item_hold_diagnosis,null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (CircleImageView)convertView.findViewById(R.id.item_hold_diagnsis_icon);
            viewHolder.name = (TextView)convertView.findViewById(R.id.item_hold_diagnsis_name);
            viewHolder.age = (TextView)convertView.findViewById(R.id.item_hold_diagnsis_age);
            viewHolder.sex = convertView.findViewById(R.id.item_hold_diagnsis_sex);
            viewHolder.sexBg = (LinearLayout)convertView.findViewById(R.id.item_hold_diagnsis_sex_bg);
            viewHolder.descOne = (TextView)convertView.findViewById(R.id.item_holde_diag_check_desc_one);
            viewHolder.descTwo = (TextView)convertView.findViewById(R.id.item_holde_diag_check_desc_two);
            viewHolder.state = (TextView)convertView.findViewById(R.id.item_hold_diagnsis_time);
            viewHolder.symbol = (TextView)convertView.findViewById(R.id.item_hold_diagnsis_doctor);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        dealData(viewHolder, (CheckData) mList.get(position));
        return convertView;
    }
    private void dealData(ViewHolder viewHolder, CheckData preorderData){
        bitmapUtils.display(viewHolder.icon, Global.fileAddr + preorderData.getPic());
        viewHolder.name.setText(preorderData.getName());
        viewHolder.age.setText(preorderData.getAge());
        viewHolder.descOne.setText("就诊医院 : ");
        viewHolder.descTwo.setText("当前状态 : ");
        if(preorderData.getSex().equals("男")){
            Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.ico_male);
            Drawable drawableBg = ContextCompat.getDrawable(context, R.drawable.age_shape);
            viewHolder.sex.setBackground(drawable);
            viewHolder.sexBg.setBackground(drawableBg);
        }else{
            Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.ico_female);
            Drawable drawableBg = ContextCompat.getDrawable(context, R.drawable.age_shape_nv);
            viewHolder.sex.setBackground(drawable);
            viewHolder.sexBg.setBackground(drawableBg);
        }
        viewHolder.symbol.setText(preorderData.getHospital());
        if(preorderData.getState().equals("1")){
            viewHolder.state.setText("待缴费");
        }else if(preorderData.getState().equals("2")){
            viewHolder.state.setText("待检查");
        }else{
            viewHolder.state.setText("待打印");
        }
    }

    class ViewHolder{
        public CircleImageView icon;
        public TextView name;
        public TextView age;
        public View sex;
        public LinearLayout sexBg;
        public TextView descOne;
        public TextView descTwo;
        public TextView symbol;
        public TextView state;
    }
}
