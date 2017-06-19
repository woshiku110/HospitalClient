package com.example.preorderlibrary.adapters.impleAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.baseAdapter.MyAdapter;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.domain.PersonData;
import com.lidroid.xutils.BitmapUtils;

import java.util.Calendar;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by willyou on 2016/11/9.
 */

public class ContactListAdapter extends MyAdapter<PersonData> {

    private static final int TYPE_COUNT=2;
    private static final int TYPE_ONE=0;
    private static final int TYPE_TWO=1;
    private Calendar calendar;
    BitmapUtils bitmapUtils;
    public ContactListAdapter(Context context, List<PersonData> data){
        super(context, data);
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.icon_head);
        calendar = Calendar.getInstance();
    }
    private final class ContactWidget{
        CircleImageView contactPersonImage;
        TextView contactName;
        TextView contactAllergicHistory;
        TextView contactOld;
        LinearLayout sexBg;
        View sex;
    }

    public void setData(List<PersonData> data) {
        super.data = data;
    }

    private final class ContactWidgetadd{
        public ImageView addlist_item_addimage;
        public TextView addlist_item;
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).isTypeOne()){
            return TYPE_ONE;
        }else{
            return TYPE_TWO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactWidget contactWidget=null;
        ContactWidgetadd contactWidgetadd=null;
        if (convertView == null) {
            switch (getItemViewType(position)){
                case TYPE_ONE:
                    Log.e("getView->","TYPE_ONE");
                    contactWidget = new ContactWidget();
                    convertView = inflater.inflate(R.layout.contact_list, parent, false);
                    contactWidget.contactPersonImage = (CircleImageView) convertView.findViewById(R.id.contact_person_image);
                    contactWidget.contactName = (TextView) convertView.findViewById(R.id.contact_name);
                    contactWidget.contactAllergicHistory = (TextView) convertView.findViewById(R.id.item_myfamily_gms);
                    contactWidget.contactOld=(TextView)convertView.findViewById(R.id.item_myfamily_age);
                    contactWidget.sex = convertView.findViewById(R.id.item_myfamily_sex);
                    contactWidget.sexBg = (LinearLayout)convertView.findViewById(R.id.item_myfamly_sex_bg);
                    convertView.setTag(R.layout.contact_list,contactWidget);
                    break;
                case TYPE_TWO:
                    Log.e("getView->","TYPE_TWO");
                    contactWidgetadd=new ContactWidgetadd();
                    convertView = inflater.inflate(R.layout.contact_addlist, parent, false);
                    contactWidgetadd.addlist_item_addimage=(ImageView)convertView.findViewById(R.id.addlist_item_addimage);
                    contactWidgetadd.addlist_item=(TextView)convertView.findViewById(R.id.addlist_item);
                    convertView.setTag(R.layout.contact_addlist,contactWidgetadd);
                    break;
            }
        }
        switch (getItemViewType(position)){
            case TYPE_ONE:
                contactWidget=(ContactWidget)convertView.getTag(R.layout.contact_list);
                bitmapUtils.display(contactWidget.contactPersonImage, Global.fileAddr + data.get(position).getIcon());
                contactWidget.contactName.setText(data.get(position).getName());
                contactWidget.contactAllergicHistory.setText(data.get(position).getAllergicHistory());
                if (data.get(position).getSex().equals("男")){
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ico_male);
                    Drawable drawableBg = ContextCompat.getDrawable(context, R.drawable.age_shape);
                    contactWidget.sex.setBackground(drawable);
                    contactWidget.sexBg.setBackground(drawableBg);
                }else {
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ico_female);
                    Drawable drawableBg = ContextCompat.getDrawable(context, R.drawable.age_shape_nv);
                    contactWidget.sexBg.setBackground(drawableBg);
                    contactWidget.sex.setBackground(drawable);
                }
                contactWidget.contactOld.setText(getYearsOld(data.get(position).getBirthday())+"");
                break;
            case TYPE_TWO:
                contactWidgetadd = (ContactWidgetadd) convertView.getTag(R.layout.contact_addlist);
                contactWidgetadd.addlist_item_addimage.setImageResource(R.drawable.ico_add_uers);
                contactWidgetadd.addlist_item.setText("添加新用户");
                break;
        }
        return convertView;
    }
    private int getYearsOld(String birth){
        String split=birth.substring(0,4);
        return calendar.get(Calendar.YEAR)-Integer.parseInt(split);
    }
}