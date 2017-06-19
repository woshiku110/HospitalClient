package com.woshiku.hospitalclient.adapter;

import android.content.Context;
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
import domain.HoldDiagnosisData;

/**
 * Created by Administrator on 2017/2/15.
 */
public class HoldDiagnosisAdapter extends CommonAdapter{
    Context context;
    BitmapUtils bitmapUtils;
    boolean isChecked;
    public HoldDiagnosisAdapter(Context context, List<Object> mList) {
        this.context = context;
        super.mList = mList;
        bitmapUtils = new BitmapUtils(context);
        /*bitmapUtils.configDefaultLoadingImage(context.getResources().getDrawable(R.mipmap.icon_user));*/
        bitmapUtils.configDefaultLoadFailedImage(context.getResources().getDrawable(R.mipmap.icon_head));
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
    public void setAdapterList(List<Object> mList) {
        super.mList = mList;
    }

    @Override
    public List<Object> getAdapterList() {
        return mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_hold_diagnosis,null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (CircleImageView)convertView.findViewById(R.id.item_hold_diagnsis_icon);
            viewHolder.name = (TextView)convertView.findViewById(R.id.item_hold_diagnsis_name);
            viewHolder.sex = convertView.findViewById(R.id.item_hold_diagnsis_sex);
            viewHolder.doctor = (TextView)convertView.findViewById(R.id.item_hold_diagnsis_doctor);
            viewHolder.time = (TextView)convertView.findViewById(R.id.item_hold_diagnsis_time);
            viewHolder.sexBg = (LinearLayout)convertView.findViewById(R.id.item_hold_diagnsis_sex_bg);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        setDiagData(viewHolder,(HoldDiagnosisData)mList.get(position));
        return convertView;
    }
    private void setDiagData(ViewHolder viewHolder,HoldDiagnosisData holdDiagnosisData){
        bitmapUtils.display(viewHolder.icon, Global.fileAddr+holdDiagnosisData.getIcon());
        viewHolder.name.setText(holdDiagnosisData.getName());
        if(holdDiagnosisData.getSex().equals("男")){
            viewHolder.sex.setBackground(context.getResources().getDrawable(R.mipmap.ico_male));
            viewHolder.sexBg.setBackground(context.getResources().getDrawable(R.drawable.age_shape));
        }else{
            viewHolder.sex.setBackground(context.getResources().getDrawable(R.mipmap.ico_female));
            viewHolder.sexBg.setBackground(context.getResources().getDrawable(R.drawable.age_shape_nv));
        }
        viewHolder.doctor.setText(holdDiagnosisData.getDoctorName());
        viewHolder.time.setText(holdDiagnosisData.getIllTime());
    }
    class ViewHolder{
        public CircleImageView icon;
        public TextView name;
        public View sex;
        public TextView doctor;
        public TextView time;
        public LinearLayout sexBg;
    }
}
