package com.woshiku.dialoglib.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.dialoglib.R;
import com.woshiku.dialoglib.domain.TimeLineData;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class TimeLineView extends RecyclerView{
    Context context;
    List<TimeLineData> mList;
    Handler mHandler;
    ShowAdapter showAdapter;
    public TimeLineView(Context context) {
        super(context);
    }

    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context){
        this.context = context;
        if(mHandler == null){
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 0:
                            //LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                            //layoutManager.setOrientation(HORIZONTAL);
                            setLayoutManager(new GridLayoutManager(context,mList.size()));
                            setItemAnimator(new DefaultItemAnimator());
                            setAdapter(showAdapter);
                            break;
                        case 1:
                            showAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            };
        }
    }

    public void setInitData(List<TimeLineData> mList){
        this.mList = mList;
        if(showAdapter == null){
            showAdapter = new ShowAdapter();
            mHandler.sendEmptyMessage(0);
        }
    }
    //设置选中的位置
    public void setChoosedPos(int position){
        for(int i=0;i<mList.size();i++){
            if(position == i){
                mList.get(i).setIsSelected(true);
            }else{
                mList.get(i).setIsSelected(false);
            }
        }
        if(showAdapter != null){
            mHandler.sendEmptyMessage(1);
        }
    }
    class ShowAdapter extends Adapter<ViewHolder>{
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //first
            if(viewType == 0){
                View view = View.inflate(context, R.layout.item_first,null);
                FirstHolder firstHolder = new FirstHolder(view);
                return firstHolder;
            }else if(viewType == 1){
                //middle
                View view = View.inflate(context,R.layout.item_middle,null);
                MiddlerHolder middlerHolder = new MiddlerHolder(view);
                return middlerHolder;
            }else{
                View view = View.inflate(context,R.layout.item_end,null);
                EndHolder endHolder = new EndHolder(view);
                return endHolder;
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(holder instanceof FirstHolder){
                TimeLineData timeLineData = mList.get(position);
                FirstHolder firstHolder = (FirstHolder)holder;
                firstHolder.text.setText(timeLineData.getName());
                if(timeLineData.isSelected()){
                    firstHolder.text.setTextColor(getResources().getColor(R.color.titleBlue));
                    firstHolder.image.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.common_size);
                    firstHolder.image.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.common_size);
                    firstHolder.image.setImageResource(R.mipmap.sign_blue);
                }else{
                    firstHolder.text.setTextColor(getResources().getColor(R.color.unselected_state));
                    firstHolder.image.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.small_size);
                    firstHolder.image.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.small_size);
                    firstHolder.image.setImageResource(R.mipmap.sign_ash);
                }
                firstHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
            }else if(holder instanceof MiddlerHolder){
                TimeLineData timeLineData = mList.get(position);
                MiddlerHolder middlerHolder = (MiddlerHolder)holder;
                middlerHolder.text.setText(timeLineData.getName());
                if(timeLineData.isSelected()){
                    middlerHolder.text.setTextColor(getResources().getColor(R.color.titleBlue));
                    middlerHolder.image.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.common_size);
                    middlerHolder.image.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.common_size);
                    middlerHolder.image.setImageResource(R.mipmap.sign_blue);
                }else{
                    middlerHolder.text.setTextColor(getResources().getColor(R.color.unselected_state));
                    middlerHolder.image.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.small_size);
                    middlerHolder.image.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.small_size);
                    middlerHolder.image.setImageResource(R.mipmap.sign_ash);
                }
                middlerHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
            }else{
                //endHolder
                TimeLineData timeLineData = mList.get(position);
                EndHolder endHolder = (EndHolder)holder;
                endHolder.text.setText(timeLineData.getName());
                if(timeLineData.isSelected()){
                    endHolder.text.setTextColor(getResources().getColor(R.color.titleBlue));
                    endHolder.image.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.common_size);
                    endHolder.image.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.common_size);
                    endHolder.image.setImageResource(R.mipmap.sign_blue);
                }else{
                    endHolder.text.setTextColor(getResources().getColor(R.color.unselected_state));
                    endHolder.image.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.small_size);
                    endHolder.image.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.small_size);
                    endHolder.image.setImageResource(R.mipmap.sign_ash);
                }
                endHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(mList.get(position).getType() == 0){
                return 0;
            }else if(mList.get(position).getType() == 1){
                return 1;
            }else{
                return 2;
            }
            //return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class FirstHolder extends ViewHolder{
            TextView text;
            ImageView image;
            public FirstHolder(View itemView) {
                super(itemView);
                text = (TextView)itemView.findViewById(R.id.text_first);
                image = (ImageView)itemView.findViewById(R.id.image_first);
            }
        }
        class MiddlerHolder extends ViewHolder{
            TextView text;
            ImageView image;
            public MiddlerHolder(View itemView) {
                super(itemView);
                text = (TextView)itemView.findViewById(R.id.text_middle);
                image = (ImageView)itemView.findViewById(R.id.image_middle);
            }
        }
        class EndHolder extends ViewHolder{
            TextView text;
            ImageView image;
            public EndHolder(View itemView) {
                super(itemView);
                text = (TextView)itemView.findViewById(R.id.text_end);
                image = (ImageView)itemView.findViewById(R.id.image_end);
            }
        }
    }
}
