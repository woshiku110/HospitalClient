package com.woshiku.hospitalclient.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.addressmanage.model.AddressManageModel;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import java.util.List;
import domain.AddressData;

/**
 * Created by Administrator on 2017/3/7.
 */
public class AddressListAdapter extends CommonAdapter{
    Context context;
    private AddressManageModel.OnAddressListener onAddressListener;
    private AddressListListener addressListListener;
    public interface AddressListListener{
        void addressListEvent(String intent,AddressData object,AddressManageModel.OnAddressListener onAddressListener);
    }

    public void setAddressListListener(AddressListListener addressListListener,AddressManageModel.OnAddressListener onAddressListener) {
        this.addressListListener = addressListListener;
        this.onAddressListener = onAddressListener;
    }

    public AddressListAdapter(Context context,List<Object> mList) {
        this.context = context;
        super.mList = mList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_address,null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.address_manage_name);
            viewHolder.address = (TextView)convertView.findViewById(R.id.address_manage_address);
            viewHolder.phone = (TextView)convertView.findViewById(R.id.address_manage_phone);
            viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.address_manage_cb);
            viewHolder.editLine = (LinearLayout)convertView.findViewById(R.id.address_manage_edit);
            viewHolder.deleLine = (LinearLayout)convertView.findViewById(R.id.address_manage_dele);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        AddressData addressData = (AddressData)mList.get(position);
        if(!TextUtils.isEmpty(addressData.getName())){
            viewHolder.name.setText(addressData.getName());
        }else{
            viewHolder.name.setText("暂无数据");
        }

        viewHolder.address.setText(addressData.getArea()+addressData.getDetailAddress());
        viewHolder.phone.setText(addressData.getPhone());
        if(addressData.isDefaultChoosed()){
            viewHolder.checkBox.setBackground(context.getResources().getDrawable(R.mipmap.btn_address_s_pre));
        }else{
            viewHolder.checkBox.setBackground(context.getResources().getDrawable(R.mipmap.btn_address_s));
        }
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressListListener != null){
                    addressListListener.addressListEvent("checkbox",(AddressData)mList.get(position),onAddressListener);
                }
            }
        });
        viewHolder.editLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressListListener != null){
                    addressListListener.addressListEvent("editLine",(AddressData)mList.get(position),onAddressListener);
                }
            }
        });
        viewHolder.deleLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressListListener != null){
                    addressListListener.addressListEvent("deleLine",(AddressData)mList.get(position),onAddressListener);
                }
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView address;
        TextView phone;
        CheckBox checkBox;
        LinearLayout editLine;
        LinearLayout deleLine;
    }

}
