package com.example.preorderlibrary.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/16.
 */
public class CommonUtil {

    static class BodyPart{
        public String part;
        public int index;

        public BodyPart(int index, String part) {
            this.index = index;
            this.part = part;
        }
    }
    public static int getBodyIndex(String part){
        List<BodyPart> bodyPartList = getDatas();
        for(int i=0;i<bodyPartList.size();i++){
            if(bodyPartList.get(i).part.equals(part)){
                return bodyPartList.get(i).index;
            }
        }
        return 0;
    }

    private static List<BodyPart> getDatas(){
        List<BodyPart> mList = new ArrayList<>();
        mList.add(new BodyPart(1001,"头"));
        mList.add(new BodyPart(1002,"胸部"));
        mList.add(new BodyPart(1003,"颈"));
        mList.add(new BodyPart(1004,"腹部"));
        mList.add(new BodyPart(1005,"腰部"));
        mList.add(new BodyPart(1006,"左臂"));
        mList.add(new BodyPart(1007,"右臂"));
        mList.add(new BodyPart(1008,"左腿"));
        mList.add(new BodyPart(1009,"右腿"));
        mList.add(new BodyPart(1501,"背部"));
        mList.add(new BodyPart(1504,"臀部"));
        return mList;
    }
}
