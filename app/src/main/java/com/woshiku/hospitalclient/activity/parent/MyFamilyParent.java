package com.woshiku.hospitalclient.activity.parent;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.activity.model.LoadListViewModel;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.view.LoadListView;
import com.woshiku.urllibrary.url.base.CommonUrl;
import butterknife.ButterKnife;
import butterknife.InjectView;
import domain.MyFamilyData;
/**
 * Created by Administrator on 2017/2/16.
 */
public abstract class MyFamilyParent extends BaseActivity implements LoadListViewModel{
    @InjectView(R.id.txt_title)
    protected TextView title;
    @InjectView(R.id.concrete_bt)
    protected TextView concert_bt;
    @InjectView(R.id.load_listview_line)
    LinearLayout parentLine;
    protected LoadListView loadListView;
    protected CommonUrl commonUrl;
    Context context;
    protected final int WAITTING=0,FAIL=1,NODATA=2,OK=3;
    protected abstract void loadUrl();
    protected abstract void dealTypeOne(MyFamilyData data);
    protected abstract void dealTypeTwo(MyFamilyData data);
    protected abstract void dealLongType(MyFamilyData data,int pos);
    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_family);
        ButterKnife.inject(this);
        context = this;
        initControl();
        initDatas();
        init();
        loadUrl();
    }
    protected void initControl(){
        loadListView = new LoadListView(context,parentLine);
        loadListView.setReloadListener(new LoadListView.ReloadListener() {
            @Override
            public void onReload() {
                loadPage(WAITTING);
                loadUrl();
            }
        });
        loadListView.setItemChoosedListener(new LoadListView.ItemChoosedListener() {
            @Override
            public void itemChoose(Object object, int pos) {
                LogUtil.print("click");
                MyFamilyData myFamilyData = (MyFamilyData) object;
                if (myFamilyData.isTypeOne()) {
                    dealTypeOne(myFamilyData);
                } else {
                    dealTypeTwo(myFamilyData);
                }
            }
        });
        loadListView.setItemLongChoosedListener(new LoadListView.ItemLongChoosedListener() {
            @Override
            public void itemLongChoose(Object object, int pos) {
                LogUtil.print("long click");
                dealLongType((MyFamilyData)object,pos);
            }
        });
    }
}
