package com.example.preorderlibrary.views;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.preorderlibrary.R;

/**
 * Created by Administrator on 2016/12/30.
 */
public class PicEditView  extends PopupWindow {
    Context context;
    View parent;
    View view;
    RelativeLayout pop_desc_relate;
    LinearLayout pop_desc_choose;
    LinearLayout pop_desc_takephoto;
    LinearLayout pop_desc_cancel;
    AlbumPreviewListener albumPreviewListener;
    AlbumDeleListener albumDeleListener;
    public interface AlbumPreviewListener{
        void previewPhoto();
    }
    public interface AlbumDeleListener{
        void delePhoto();
    }
    public PicEditView(Context context,View parent) {
        this.context = context;
        this.parent = parent;
        view = View.inflate(context, R.layout.pop_preview_photo, null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        pop_desc_relate = (RelativeLayout)view.findViewById(R.id.pop_desc_relate);
        pop_desc_choose = (LinearLayout)view.findViewById(R.id.pop_desc_choose);
        pop_desc_takephoto = (LinearLayout)view.findViewById(R.id.pop_desc_takephoto);
        pop_desc_cancel = (LinearLayout)view.findViewById(R.id.pop_desc_cancel);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        pop_desc_relate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        pop_desc_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(albumPreviewListener != null){
                    albumPreviewListener.previewPhoto();
                }
                dismiss();
            }
        });
        pop_desc_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(albumDeleListener != null){
                    albumDeleListener.delePhoto();
                }
                dismiss();
            }
        });
        pop_desc_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void showDialog(){
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
    }
    public void closeDialog(){
        dismiss();
    }
    public PicEditView addAlbumPreviewListener(AlbumPreviewListener albumPreviewListener){
        this.albumPreviewListener = albumPreviewListener;
        return this;
    }
    public PicEditView addAlbumDeleListener(AlbumDeleListener albumDeleListener){
        this.albumDeleListener = albumDeleListener;
        return this;
    }
}
