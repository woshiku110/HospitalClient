package com.woshiku.hospitalclient.activity.comment.model;

import android.app.Activity;

/**
 * Created by Administrator on 2017/3/16.
 */
public interface CommentModel {
    void initPage(CommentListener commentListener);
    interface CommentListener{
        void loadPage();
        Activity getActivity();
    }
}
