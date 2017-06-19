package com.example.preorderlibrary.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.preorderlibrary.R;

/**
 * Created by Administrator on 2017/1/4.
 */
public class BodyPartTitleView extends LinearLayout{
    LinearLayout linearLayout,lineMark;
    TextView textView;
    String title;
    boolean isChoosed = false;
    int index;
    ChooseTitleListener chooseTitleListener;
    Context context;
    public interface ChooseTitleListener{
        void chooseTitle(String title,int index);
    }
    public BodyPartTitleView(Context context) {
        super(context);
    }

    public BodyPartTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BodyPartTitleView(Context context,final String title,final int index) {
        super(context);
        this.title = title;
        this.index = index;
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_body_part_title, this);
        linearLayout = (LinearLayout)findViewById(R.id.body_part_title_line);
        lineMark = (LinearLayout)findViewById(R.id.body_part_title_mark);
        textView = (TextView)findViewById(R.id.body_part_title_txt);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChoosed) {
                    if (chooseTitleListener != null) {
                        chooseTitleListener.chooseTitle(title,index);
                    }
                }
            }
        });
    }
    private void setTextColor(boolean isSelected){
        if(isSelected){
            textView.setTextColor(getContext().getResources().getColor(R.color.body_part_title_txt_selected));
            lineMark.setVisibility(View.VISIBLE);
        }else{
            textView.setTextColor(getContext().getResources().getColor(R.color.body_part_title_txt_unselect));
            lineMark.setVisibility(View.INVISIBLE);
        }
    }
    private void setBgColor(boolean isSelected){
        if(isSelected){
            linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.body_part_title_bg_selected));
        }else{
            linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.body_part_title_bg_unselect));
        }
    }
    public void setChecked(boolean isChoosed){
        setTextColor(isChoosed);
        setBgColor(isChoosed);
    }
    public void setText(String title){
        this.title = title;
        textView.setText(title);
    }

    public String getTitle() {
        return title;
    }

    public void setChooseTitleListener(ChooseTitleListener chooseTitleListener) {
        this.chooseTitleListener = chooseTitleListener;
    }
}
