package com.woshiku.dialoglib;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.dialoglib.utils.Utils;

/**
 * Created by Administrator on 2017/2/19.
 */
public class TitleContentDialog {
    Context context;
    String title,content;
    Dialog dialog;
    TextView titleText;
    EditText contentText;
    LinearLayout okLine,cancelLine;
    LinearLayout parentLine;
    private TitleContentListener titleContentListener;
    public interface TitleContentListener{
        void titleContentClick(boolean isOk);
    }

    public void setTitleContentListener(TitleContentListener titleContentListener) {
        this.titleContentListener = titleContentListener;
    }

    public TitleContentDialog(Context context) {
        this.context = context;
        initView();
    }

    public TitleContentDialog(Context context, String title) {
        this(context);
        this.title = title;
    }

    public TitleContentDialog(Context context, String title, String content) {
        this(context,title);
        this.content = content;
    }

    private void initView(){
        dialog = new Dialog(context,R.style.MyDialog);
        View contentView = View.inflate(context,R.layout.title_content_layout,null);
        parentLine = (LinearLayout)contentView.findViewById(R.id.item_parent_line);
        LinearLayout.LayoutParams lineParam = (LinearLayout.LayoutParams)parentLine.getLayoutParams();
        int gap = (int) context.getResources().getDimension(R.dimen.gap_size);
        lineParam.width = Utils.getWidth((Activity)context) - gap ;
        titleText = (TextView)contentView.findViewById(R.id.item_title);
        titleText.setText("退单原因");
        contentText = (EditText)contentView.findViewById(R.id.item_content);
        okLine = (LinearLayout)contentView.findViewById(R.id.item_ok);
        cancelLine = (LinearLayout)contentView.findViewById(R.id.item_cancel);
        okLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleContentListener != null) {
                    titleContentListener.titleContentClick(true);
                }
            }
        });
        cancelLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleContentListener != null){
                    titleContentListener.titleContentClick(false);
                }
            }
        });
        dialog.setContentView(contentView);
    }
    public TitleContentDialog show(){
        dialog.show();
        return this;
    }
    public void cancel(){
        dialog.dismiss();
    }
    public void setTitle(String title) {
        this.title = title;
        titleText.setText(title);
    }

    public String getContent() {
        return contentText.getText().toString();
    }
}
