package com.woshiku.tabbarlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woshiku.tabbarlib.R;


/**
 * Created by Administrator on 2016/12/13.
 */
public class HomeStateView extends LinearLayout{
    ImageView imageView;
    TextView textView;
    int selectorImageId;
    int selectorText;
    String textContent;
    public HomeStateView(Context context) {
        super(context);
    }
    public HomeStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeStateView);
        selectorImageId = typedArray.getResourceId(R.styleable.HomeStateView_imageSelector, R.drawable.inspect_selector);
        selectorText = typedArray.getResourceId(R.styleable.HomeStateView_textSelector,R.drawable.state_text_selector);
        textContent = typedArray.getString(R.styleable.HomeStateView_textContent);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_home_state, this);
        imageView = (ImageView)findViewById(R.id.imageview);
        textView = (TextView)findViewById(R.id.textview);
        textView.setText(textContent);
        imageView.setBackground(getDrawable(selectorImageId));
    }
    private Drawable getDrawable(int resId){
        return getResources().getDrawable(resId);
    }
    public void setSelected(boolean isSelected){
        if(isSelected){
            imageView.setEnabled(false);
            textView.setTextColor(getResources().getColor(R.color.textSelected));
        }else{
            imageView.setEnabled(true);
            textView.setTextColor(getResources().getColor(R.color.textUnselected));
        }
    }
}
