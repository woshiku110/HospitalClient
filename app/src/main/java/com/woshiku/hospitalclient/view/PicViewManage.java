package com.woshiku.hospitalclient.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.lidroid.xutils.BitmapUtils;
import com.woshiku.dialoglib.utils.Utils;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PicViewManage {
    ScrollView parentView;
    LinearLayout parentLine;
    Context context;
    String[] items;
    int pageSize = 4;
    int width,gapWidth;
    int iconWidth,iconHeight,itemHeight;
    int marginRight;
    String basePath;
    BitmapUtils bitmapUtils;
    private PicClickListener picClickListener;

    public interface PicClickListener{
        void picClickShow(int index,String selectedPic,String[] allPics);
    }

    public void setPicClickListener(PicClickListener picClickListener) {
        this.picClickListener = picClickListener;
    }

    public PicViewManage(Context context, ScrollView parentView, LinearLayout parentLine, String[] items, String basePath) {
        this.context = context;
        this.parentView = parentView;
        this.parentLine = parentLine;
        this.items = items;
        this.basePath = basePath;
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.icon_head);
        gapWidth = (int)context.getResources().getDimension(R.dimen.pre_order_detail_gap);
        width = Utils.getWidth((Activity) context) - gapWidth*2;
        /*itemHeight = (int)context.getResources().getDimension(R.dimen.item_height);*/
        iconWidth = iconHeight = (int)context.getResources().getDimension(R.dimen.pre_order_detail_pic);
        marginRight = (width - pageSize*iconWidth)/(pageSize-1);
        itemHeight = iconHeight+marginRight;
        init();
    }

    private void init(){
        LinearLayout.LayoutParams parentViewParam = (LinearLayout.LayoutParams)parentView.getLayoutParams();
        parentLine.removeAllViews();
        if(items.length > pageSize*2){//大于8个图
            generItems(items);
            parentViewParam.height = itemHeight*3;
        }else if(items.length > pageSize){//大于4个图
            generItems(items);
            parentViewParam.height = itemHeight*2;
        }else{//小于4个图
            parentViewParam.height = itemHeight;
            LogUtil.print("enter 小于4个图");
            generItems(items);
        }
    }
    private void generItems(String[] items){
        List<List<String>> dataList = makeStrToList(items);
        for(List<String> list:dataList){
            LogUtil.print("list:"+list.toString());
            parentLine.addView(generItem(list.toArray(new String[list.size()])));
        }
    }
    /**
     * 产生一个大小指定的LinearLayout
     * */
    private LinearLayout generItem(final String []images){
        //表示一块线性布局
        LinearLayout lineLayout = new LinearLayout(context);
        //lineLayout.setBackgroundColor(Color.RED);
        lineLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,itemHeight);
        lineLayout.setLayoutParams(lp);
        for(int i=0;i<images.length;i++){
            LinearLayout itemLayout = new LinearLayout(context);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(iconWidth,itemHeight);
            itemLayout.setGravity(Gravity.CENTER_VERTICAL);
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(iconWidth,iconHeight);
            imageView.setLayoutParams(imageParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final  int pos = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(picClickListener != null){
                        picClickListener.picClickShow(pos,images[pos],images);
                    }
                }
            });
            LogUtil.print(basePath+images[i]);
            bitmapUtils.display(imageView, basePath + images[i]);
            if(i!=pageSize-1){
                itemParam.rightMargin = marginRight;
            }
            itemLayout.setLayoutParams(itemParam);
            itemLayout.addView(imageView);
            lineLayout.addView(itemLayout);
        }
        return lineLayout;
    }
    private List<List<String>> makeStrToList(String []strs){
        List<List<String>> mList = new ArrayList<>();
        List<String> strList = new ArrayList<>();
        for(int i=1;i<strs.length+1;i++){
            LogUtil.print("i:" + i);
            if(i%pageSize == 0){
                strList.add(strs[i-1]);
                mList.add(strList);
                strList = new ArrayList<>();
            }else{
                strList.add(strs[i-1]);
            }
        }
        if(strList.size()>0){
            mList.add(strList);
        }
        return mList;
    }
}

