package com.example.preorderlibrary.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */
public class BodyPartImproveData {
    public String bodyId;
    public String bodyName;
    public String amount;
    public List<PartItem> partItemList;
    public class PartItem{
        public String partId;
        public String partDesc;
        public PartItem(String partId,String partDesc) {
            this.partId = partId;
            this.partDesc = partDesc;
        }
        @Override
        public String toString() {
            return "PartItem{" +
                    "partDesc='" + partDesc + '\'' +
                    ", partId='" + partId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BodyPartImproveData{" +
                "amount='" + amount + '\'' +
                ", bodyId='" + bodyId + '\'' +
                ", bodyName='" + bodyName + '\'' +
                ", partItemList=" + partItemList +
                '}';
    }
}
