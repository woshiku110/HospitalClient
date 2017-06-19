package com.woshiku.dialoglib.utils;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.woshiku.dialoglib.R;

/**
 * Created by Administrator on 2017/2/17.
 */
public class ManageItem {
    LinearLayout parentLine;
    Context context;
    String []strs;
    private ItemClickListener itemClickListener;
    public interface ItemClickListener{
        void itemClick(int pos);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ManageItem(Context context, LinearLayout parentLine,String []strs) {
        this.context = context;
        this.parentLine = parentLine;
        this.strs = strs;
        init();
    }
    private void init(){
        parentLine.removeAllViews();
        for(int i=0;i<strs.length;i++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ItemView itemView = new ItemView(context,strs[i]);
            itemView.setBackground(context.getResources().getDrawable(R.drawable.bt_shape_bg_selector_item));
            final int pos = i;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null){
                        itemClickListener.itemClick(pos);
                    }
                }
            });
            parentLine.addView(itemView,layoutParams);
            if(strs.length >= 2){
                if(i == 0){
                    itemView.hideTop();
                }else if(i == strs.length - 1){
                    itemView.hideBottom();
                }
            }else{
                itemView.hideAll();
            }
        }
    }
}
