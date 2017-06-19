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
import domain.MyFamilyData;

/**
 * Created by Administrator on 2017/2/16.
 */
public class MyFamilyAdapter extends CommonAdapter{
    Context context;
    final int typeOne = 0;
    final int typeTwo = 1;
    BitmapUtils bitmapUtils;
    public MyFamilyAdapter(Context context,List<Object> dataList) {
        this.context = context;
        super.mList = dataList;
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadFailedImage(context.getResources().getDrawable(R.mipmap.icon_head));
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
    public int getItemViewType(int position) {
        MyFamilyData myFamilyData = (MyFamilyData)mList.get(position);
        if(myFamilyData.isTypeOne()){
            return typeOne;
        }else{
            return typeTwo;
        }
    }

    @Override
    public int getViewTypeCount() {
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
        switch (getItemViewType(position)){
            case typeOne:
                convertView = configTypeOne(convertView,(MyFamilyData)mList.get(position));
                break;
            case typeTwo:
                if(convertView == null){
                    convertView = View.inflate(context, R.layout.item_myfamily_add,null);
                }
                break;
        }
        return convertView;
    }
    private View configTypeOne(View convertView,MyFamilyData data){
        ViewHolderOne viewHolderOne;
        View myView = convertView;
        if(myView == null){
            viewHolderOne = new ViewHolderOne();
            myView = View.inflate(context, R.layout.item_myfamily,null);
            viewHolderOne.sexBg = (LinearLayout)myView.findViewById(R.id.item_myfamly_sex_bg);
            viewHolderOne.icon = (CircleImageView)myView.findViewById(R.id.item_myfamily_icon);
            viewHolderOne.name = (TextView)myView.findViewById(R.id.item_myfamily_name);
            viewHolderOne.age = (TextView)myView.findViewById(R.id.item_myfamily_age);
            viewHolderOne.sex = myView.findViewById(R.id.item_myfamily_sex);
            viewHolderOne.gms = (TextView)myView.findViewById(R.id.item_myfamily_gms);
            myView.setTag(viewHolderOne);
        }else{
            viewHolderOne = (ViewHolderOne)myView.getTag();
        }
        bitmapUtils.display(viewHolderOne.icon, Global.fileAddr+data.getIcon());
        viewHolderOne.name.setText(data.getName());
        viewHolderOne.age.setText(data.getAge());
        if(data.getSex().equals("男")){
            viewHolderOne.sex.setBackground(context.getResources().getDrawable(R.mipmap.ico_male));
            viewHolderOne.sexBg.setBackground(context.getResources().getDrawable(R.drawable.age_shape));
        }else{
            viewHolderOne.sex.setBackground(context.getResources().getDrawable(R.mipmap.ico_female));
            viewHolderOne.sexBg.setBackground(context.getResources().getDrawable(R.drawable.age_shape_nv));
        }

        viewHolderOne.gms.setText(data.getGms());
        return myView;
    }
    class ViewHolderOne{
        public CircleImageView icon;
        public TextView name;
        public TextView age;
        public View sex;
        public TextView gms;
        public LinearLayout sexBg;
    }
}
