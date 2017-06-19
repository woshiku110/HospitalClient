package com.example.preorderlibrary.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.preorderlibrary.R;

/**
 * Created by Administrator on 2017/1/5.
 */
public class BodyPartContentView extends LinearLayout{
    RelativeLayout relativeLayout;
    TextView textView;
    ImageView mark;
    int index;
    String partId;
    boolean isVisible;
    ChooseContentListener chooseContentListener;
    public interface ChooseContentListener{
        void chooseContent(String partId,String content,int index);
    }

    public void setChooseContentListener(ChooseContentListener chooseContentListener) {
        this.chooseContentListener = chooseContentListener;
    }

    public BodyPartContentView(Context context) {
        super(context);
    }

    public BodyPartContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BodyPartContentView(Context context, final String content, final String partId,final int index,boolean isVisible){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_body_part_content, this);
        relativeLayout = (RelativeLayout)findViewById(R.id.body_part_content_relate);
        textView = (TextView)findViewById(R.id.body_part_content_text);
        mark = (ImageView)findViewById(R.id.body_part_content_mark);
        textView.setText(content);
        this.index = index;
        this.partId = partId;
        this.isVisible = isVisible;
        setMarkState(isVisible);
        relativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chooseContentListener != null){
                    chooseContentListener.chooseContent(partId,content,index);
                }
            }
        });
    }
    private void setMarkState(boolean isVisible){
        if(isVisible){
            mark.setVisibility(View.VISIBLE);
        }else{
            mark.setVisibility(View.INVISIBLE);
        }
    }
}
