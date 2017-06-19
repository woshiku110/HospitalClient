package com.woshiku.hospitalclient.activity.userinfo.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface UserInfoPresenter {
    void loadPage(String title);
    void submitFamilyInfo();
    void dealActivityReturn(int requestCode, int resultCode, Intent data);
    void dealBroadCast(Context context, String type, Bundle bd);
    void editPicDialog();
}
