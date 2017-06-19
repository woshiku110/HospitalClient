/*
package com.woshiku.picshowlib;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lidroid.xutils.BitmapUtils;
import com.woshiku.picshowlib.utils.Utils;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Administrator on 2017/2/20.
 *//*

public class PicViewManage {
    ScrollView parentView;
    LinearLayout parentLine;
    Context context;
    String[] items;
    int pageSize = 4;
    int width;
    int itemWidth,itemHeight,iconWidth,iconHeight;
    String basePath;
    BitmapUtils bitmapUtils;
    public PicViewManage(Context context, ScrollView parentView) {
        this.context = context;
        this.parentView = parentView;
        width = Utils.getWidth((Activity)context);
        itemWidth = width/4;
        itemHeight = (int)context.getResources().getDimension(R.dimen.item_height);
        iconWidth = iconHeight = itemHeight - (int)context.getResources().getDimension(R.dimen.gap);
    }

    public PicViewManage(Context context, ScrollView parentView,LinearLayout parentLine,String[] items,String basePath) {
        this.context = context;
        this.parentView = parentView;
        this.parentLine = parentLine;
        this.items = items;
        this.basePath = basePath;
        bitmapUtils = new BitmapUtils(context);
        init();
    }

    private void init(){
        LinearLayout.LayoutParams parentViewParam = (LinearLayout.LayoutParams)parentView.getLayoutParams();
        parentLine.removeAllViews();
        if(items.length > pageSize*2){//大于8个图
            generItems(items);
            parentViewParam.width = width;
            parentViewParam.height = itemHeight*3;
        }else if(items.length > pageSize){//大于4个图
            generItems(items);
            parentViewParam.width = width;
            parentViewParam.height = itemHeight*2;
        }else{//小于4个图
            parentViewParam.width = width;
            parentViewParam.height = itemHeight;
            generItems(items);
        }
    }
    private void generItems(String[] items){
        List<List<String>> dataList = makeStrToList(items);
        for(List<String> list:dataList){
            parentLine.addView(generItem(list.toArray(new String[list.size()])));
        }
    }
    */
/**
     * 产生一个大小指定的LinearLayout
     * *//*

    private LinearLayout generItem(String []images){
        //表示一块线性布局
        LinearLayout lineLayout = new LinearLayout(context);
        lineLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,iconHeight);
        lineLayout.setLayoutParams(lp);
        for(String image:images){
            LinearLayout itemLayout = new LinearLayout(context);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(itemWidth,itemHeight);
            itemLayout.setGravity(Gravity.CENTER);
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(iconWidth,iconHeight);
            imageView.setLayoutParams(imageParams);
            bitmapUtils.display(imageView,basePath+image);
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
            if(i%4 == 0){
                strList.add(strs[i-1]);
                mList.add(strList);
                strList = new ArrayList<>();
            }else{
                strList.add(strs[i-1]);
            }
        }
        return mList;
    }
}

*/
