package com.example.preorderlibrary.views;

import android.content.Context;
import android.widget.LinearLayout;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.domain.BodyPartImproveData;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2017/1/4.
 */
public class ManageTitleView {
    private String[] titles;
    private int index = 0;
    private LinearLayout titleViews;
    private LinearLayout contentViews;
    private Context context;
    private List<BodyPartImproveData> mapList;
    private int height;
    private BodyPartListener bodyPartListener;
    public ManageTitleView(Context context,List<BodyPartImproveData> mapList, int index,LinearLayout titleViews,LinearLayout contentViews,int height) {
        this.mapList = mapList;
        this.index = index;
        this.titleViews = titleViews;
        this.contentViews = contentViews;
        this.context = context;
        this.titles = getManageTitle(mapList);
        this.height = height;
        generateViews(context, titleViews, contentViews, titles, index);
    }
    /**
     * 人身体部位被选择后返回部位ID
     * */
    public interface BodyPartListener{
        void bodyPart(String bodyId);
    }

    public void setBodyPartListener(BodyPartListener bodyPartListener) {
        this.bodyPartListener = bodyPartListener;
    }

    /**
     * 拿到所有标题
     * */
    private String[] getManageTitle(List<BodyPartImproveData> mapList){
        List<String> strList = new ArrayList<>();
        for(BodyPartImproveData bodyPartImproveData:mapList){
            strList.add(bodyPartImproveData.bodyName);
        }
        return strList.toArray(new String[strList.size()]);
    }
    private void generateViews(Context context,LinearLayout titleViews,LinearLayout contentViews,String[] titles,int index){
        initTitleViews(context, titleViews, titles);
        setDefaultState(index);
    }
    private void initTitleViews(Context context,final LinearLayout titleViews, final String[] titles){
        titleViews.removeAllViews();
        for(int j = 0;j < titles.length;j++){
            BodyPartTitleView bodyPartTitleView = new BodyPartTitleView(context,titles[j],j);
            bodyPartTitleView.setChecked(false);
            bodyPartTitleView.setText(titles[j]);
            bodyPartTitleView.setChooseTitleListener(new BodyPartTitleView.ChooseTitleListener() {
                @Override
                public void chooseTitle(String title,int index) {
                    for (int i = 0; i < titles.length; i++) {
                        BodyPartTitleView view = (BodyPartTitleView) titleViews.getChildAt(i);
                        if (i==index) {
                            view.setChecked(true);
                        } else {
                            view.setChecked(false);
                        }
                    }
                    setDefaultState(index);
                }
            });
            titleViews.addView(bodyPartTitleView);
        }
    }
    private void generContentViews(List<BodyPartImproveData.PartItem> contentList,int height,LinearLayout contentViews){
        int singleSize = (int)context.getResources().getDimension(R.dimen.item_body_part_title_height);
        int itemSize = height/singleSize;
        //删除所有views
        contentViews.removeAllViews();
        List<String> strList = new ArrayList<>();
        for(int i=0;i<contentList.size();i++){
            strList.add(contentList.get(i).partDesc);
        }
        String []contents = strList.toArray(new String[strList.size()]);
        //当内容的长度小于屏幕最大要显示的个数
        if(contents.length<itemSize){
            for(int i=0;i<contents.length;i++){
                BodyPartContentView bodyPartContentView = new BodyPartContentView(context,contents[i],contentList.get(i).partId,i,true);
                bodyPartContentView.setChooseContentListener(new BodyPartContentView.ChooseContentListener() {
                    @Override
                    public void chooseContent(String partId,String content, int index) {
                        if(bodyPartListener != null){
                            bodyPartListener.bodyPart(partId);
                        }
                    }
                });
                contentViews.addView(bodyPartContentView);
            }
            int gapSize = itemSize - contents.length;
            for(int i=contents.length;i<contents.length+gapSize;i++){
                BodyPartContentView bodyPartContentView = new BodyPartContentView(context,"","",i,false);
                contentViews.addView(bodyPartContentView);
            }
        }else{
            for(int i=0;i<contents.length;i++){
                BodyPartContentView bodyPartContentView = new BodyPartContentView(context,contents[i],contentList.get(i).partId,i,true);
                bodyPartContentView.setChooseContentListener(new BodyPartContentView.ChooseContentListener() {
                    @Override
                    public void chooseContent(String partId,String content, int index) {
                        if(bodyPartListener != null){
                            bodyPartListener.bodyPart(partId);
                        }
                    }
                });
                contentViews.addView(bodyPartContentView);
            }
        }
    }
    public void setDefaultState(int index){
        BodyPartTitleView view = (BodyPartTitleView)titleViews.getChildAt(index);
        view.setChecked(true);
        //String title = view.getTitle();
        BodyPartImproveData bodyPartImproveData = mapList.get(index);
        generContentViews(bodyPartImproveData.partItemList,height,contentViews);
    }
}
