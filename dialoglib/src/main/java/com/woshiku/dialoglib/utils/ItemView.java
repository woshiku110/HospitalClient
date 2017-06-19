package com.woshiku.dialoglib.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.dialoglib.R;
/**
 * Created by Administrator on 2017/2/17.
 */
public class ItemView extends LinearLayout{
    Context context;
    String text;
    LinearLayout line;
    TextView textView;
    LinearLayout.LayoutParams param;
    public ItemView(Context context) {
        super(context);
        this.context = context;
    }
    public ItemView(Context context,String text){
        this(context);
        this.text = text;
        init();
    }
    private void init(){
        LayoutInflater.from(context).inflate(R.layout.item_dialog, this);
        line = (LinearLayout)findViewById(R.id.item_dialog_line);
        textView = (TextView)findViewById(R.id.item_dialog_txt);
        param = (LinearLayout.LayoutParams)line.getLayoutParams();
        textView.setText(text);
    }
    public void hideTop(){
        param.topMargin = 0;
    }
    public void hideBottom(){
        param.bottomMargin = 0;
    }
    public void hideAll(){
        param.topMargin = 0;
        param.bottomMargin = 0;
    }
}
