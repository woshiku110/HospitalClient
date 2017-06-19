package com.woshiku.hospitalclient.activity.checkdetail.model;

import android.os.Bundle;
import com.woshiku.dialoglib.domain.CheckDetailContentData;
import com.woshiku.hospitalclient.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;
import domain.CheckTicketData;
import parse.CheckDetailParse;

/**
 * Created by Administrator on 2017/2/27.
 */
public class CheckDetailModelImple implements CheckDetailModel {
    @Override
    public void initPage(String title,OnCheckDetailListener onCheckDetailListener) {
        if(onCheckDetailListener != null){
            onCheckDetailListener.onTitle(title);
            Bundle bd = onCheckDetailListener.onGetActivity().getIntent().getExtras();
            String data = bd.getString("data");
            int state = bd.getInt("state");
            LogUtil.print("state" + state + "data:" + data);
            CheckTicketData checkTicketData = CheckDetailParse.getCheckTicketData(data);
            //0订单编号 1医院地址 2单据打印地点 3联系人 4电话
            String []infos = {checkTicketData.getCheckNo(),checkTicketData.getHospitalName()};
            String []keys = {"单据打印地点 : ","联系人 : ","联系电话 : "};
            String []values = {checkTicketData.getPrintAddress(),"金康晟医生",checkTicketData.getPhone()};
            onCheckDetailListener.onPage((state - 1),infos,keys,values,changeDatas(checkTicketData.getItemList()));
        }
    }

    @Override
    public void setPage(int state, OnCheckDetailListener onCheckDetailListener) {
        Bundle bd = onCheckDetailListener.onGetActivity().getIntent().getExtras();
        String data = bd.getString("data");
        CheckTicketData checkTicketData = CheckDetailParse.getCheckTicketData(data);
        //0订单编号 1医院地址 2单据打印地点 3联系人 4电话
        String []infos = {checkTicketData.getCheckNo(),checkTicketData.getHospitalName()};
        String []keys = {"单据打印地点 : ","联系人 : ","联系电话 : "};
        String []values = {"广州佛山仙水县","金康晟医生","133653786"};
        onCheckDetailListener.onPage((state - 1),infos,keys,values,changeDatas(checkTicketData.getItemList()));
    }

    private List<CheckDetailContentData> changeDatas(List<CheckTicketData.CheckTicketItem> mList){
        List<CheckDetailContentData> list = new ArrayList<>();
        for(CheckTicketData.CheckTicketItem item:mList){
            list.add(new CheckDetailContentData(item.id,item.name,item.backUp,item.price,item.state));
        }
        return list;
    }
}
