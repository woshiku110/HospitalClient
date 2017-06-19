package com.woshiku.hospitalclient.activity.parent;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.model.LoadListViewModel;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.view.LoadListView;
import com.woshiku.urllibrary.url.base.CommonUrl;
import butterknife.ButterKnife;
import butterknife.InjectView;
import common.Global;
import domain.PreorderData;

/**
 * Created by Administrator on 2017/2/20.
 * 预约预诊
 */
public abstract class PreorderParent extends BaseActivity implements LoadListViewModel{
    @InjectView(R.id.txt_title)
    protected TextView title;
    @InjectView(R.id.concrete_bt)
    protected TextView concert_bt;
    @InjectView(R.id.load_listview_line)
    LinearLayout parentLine;
    protected LoadListView loadListView;
    protected CommonUrl commonUrl;
    protected Context context;
    protected abstract void dealUrl();
    protected abstract void dealDetailUrl(PreorderData preorderData);
    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_family);//我的预约预诊，用于列表显示。
        ButterKnife.inject(this);
        context = this;
        initControls();
        initDatas();
        init();
    }
    private void initControls(){
        loadListView = new LoadListView(context,parentLine);
        loadListView.setReloadListener(new LoadListView.ReloadListener() {
            @Override
            public void onReload() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadPage(Global.WAITTING);
                    }
                });
                dealUrl();
            }
        });
        loadListView.setItemChoosedListener(new LoadListView.ItemChoosedListener() {
            @Override
            public void itemChoose(Object object, int pos) {
                dealDetailUrl((PreorderData)object);
            }
        });
    }
}
