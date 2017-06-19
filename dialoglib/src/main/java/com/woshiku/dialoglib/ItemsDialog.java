package com.woshiku.dialoglib;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.woshiku.dialoglib.utils.ManageItem;
import com.woshiku.dialoglib.utils.Utils;

/**
 * Created by Administrator on 2017/2/17.
 */
public class ItemsDialog {
    Dialog dialog;
    Context context;
    String []items;
    LinearLayout parentLine;
    ManageItem manageItem;

    public ItemsDialog(Context context, String[] items) {
        this.context = context;
        this.items = items;
        dialog = new Dialog(context,R.style.MyDialog);
        View contentView = View.inflate(context,R.layout.items_dialog_layout,null);
        parentLine = (LinearLayout)contentView.findViewById(R.id.items_dialog_layout_line);
        LinearLayout.LayoutParams lineParam = (LinearLayout.LayoutParams)parentLine.getLayoutParams();
        int gap = (int) context.getResources().getDimension(R.dimen.gap_size);
        lineParam.width = Utils.getWidth((Activity)context) - gap ;
        dialog.setContentView(contentView);
        manageItem = new ManageItem(context,parentLine,items);
    }

    public ItemsDialog show(){
        dialog.show();
        return this;
    }

    public ManageItem getManageItem(){
        return manageItem;
    }

    public void dismiss(){
        dialog.dismiss();
    }
}
