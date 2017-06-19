package com.example.preorderlibrary.adapters.impleAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.common.PhotoData;
import com.lidroid.xutils.BitmapUtils;
import java.util.List;
/**
 * Created by Administrator on 2016/12/28.
 */
public class PhotoAdapter extends BaseAdapter{
    private List<PhotoData> mList;
    private Context context;
    private BitmapUtils bitmapUtils;
    public PhotoAdapter(Context context,List<PhotoData> mList) {
        this.context = context;
        this.mList = mList;
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadFailedImage(context.getResources().getDrawable(R.mipmap.ic_launcher));
    }
    public void setData(List<PhotoData> mList){
        this.mList = mList;
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
            convertView = View.inflate(context, R.layout.item_pre_pic,null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.item_pre_pic_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if(mList.get(position).type==0){
            viewHolder.imageView.setImageResource(R.mipmap.ico_add_uers);
        }else{
            bitmapUtils.display(viewHolder.imageView,mList.get(position).imageAddr);
        }
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
    }
    public BitmapUtils getBitmapUtils(){
        return bitmapUtils;
    }
}
