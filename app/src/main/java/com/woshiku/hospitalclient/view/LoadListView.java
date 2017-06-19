package com.woshiku.hospitalclient.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.adapter.base.CommonAdapter;
import java.util.List;
/**
 * Created by Administrator on 2017/2/15.
 */
public class LoadListView{
    Context context;
    LinearLayout parent;
    ListView listView;
    Button reloadBt;
    Handler mHandler;
    CommonAdapter commonAdapter;
    View waitView,failView,nodataView,okView;
    private ReloadListener reloadListener;
    private ItemChoosedListener itemChoosedListener;
    private ItemLongChoosedListener itemLongChoosedListener;
    public interface ReloadListener{
        void onReload();
    }
    public interface ItemChoosedListener{
        void itemChoose(Object object,int pos);
    }
    public interface ItemLongChoosedListener{
        void itemLongChoose(Object object,int pos);
    }
    public void setReloadListener(ReloadListener reloadListener) {
        this.reloadListener = reloadListener;
    }

    public void setItemChoosedListener(ItemChoosedListener itemChoosedListener) {
        this.itemChoosedListener = itemChoosedListener;
    }

    public void setItemLongChoosedListener(ItemLongChoosedListener itemLongChoosedListener) {
        this.itemLongChoosedListener = itemLongChoosedListener;
    }

    public LoadListView(Context context, LinearLayout parent) {
        this.context = context;
        this.parent = parent;
        initHandler();
        /*//默认加载
        loadWaitView();*/
    }
    private void initHandler(){
        if(mHandler == null) {
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 0:
                            listView.setAdapter(commonAdapter);
                            break;
                        case 1:
                            commonAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            };
        }
    }
    public void loadWaitView(){
        parent.removeAllViews();
        if(waitView == null){
            waitView = View.inflate(context, R.layout.load_wait,null);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parent.addView(waitView,layoutParams);
    }

    public void loadFailView(){
        parent.removeAllViews();
        if(failView == null){
            failView = View.inflate(context,R.layout.load_fail,null);
            reloadBt = (Button)failView.findViewById(R.id.load_fail);
            reloadBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(reloadListener != null){
                        reloadListener.onReload();
                    }
                }
            });
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parent.addView(failView,layoutParams);
    }

    public void loadNodataView(){
        parent.removeAllViews();
        if(nodataView == null){
            nodataView = View.inflate(context,R.layout.load_nodata,null);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parent.addView(nodataView,layoutParams);
    }

    private void loadListView(){
        parent.removeAllViews();
        if(okView == null){
            okView = View.inflate(context,R.layout.load_listview,null);
            listView = (ListView)okView.findViewById(R.id.load_listview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (itemChoosedListener != null) {
                        itemChoosedListener.itemChoose(parent.getAdapter().getItem(position), position);
                    }
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (itemLongChoosedListener != null) {
                        itemLongChoosedListener.itemLongChoose(parent.getAdapter().getItem(position), position);
                    }
                    return true;
                }
            });
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parent.addView(okView,layoutParams);
    }
    public void setData(CommonAdapter commonAdapter){
        this.commonAdapter = commonAdapter;
        loadListView();
        mHandler.sendEmptyMessage(0);
    }
    public CommonAdapter getCommonAdapter(){
        return this.commonAdapter;
    }
    public void updateData(List<Object> mList){
        if(this.commonAdapter != null){
            if(this.commonAdapter.getCount()>0){//不改变原有界面，来改变适配合器。
                this.commonAdapter.setAdapterList(mList);
                mHandler.sendEmptyMessage(1);
            }else{//暂无数据界面
                loadListView();
                this.commonAdapter.setAdapterList(mList);
                mHandler.sendEmptyMessage(1);
            }
        }
    }
}
