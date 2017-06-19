package com.woshiku.hospitalclient.utils;

import com.woshiku.dialoglib.domain.CheckDetailContentData;

import java.util.ArrayList;
import java.util.List;

import domain.PrescriptionDetailData;

/**
 * Created by Administrator on 2017/3/8.
 */
public class PrescribleDetailUtil {
    public static List<CheckDetailContentData> getPrescribleDetail(PrescriptionDetailData data){
        List<CheckDetailContentData> checkList = new ArrayList<>();
        List<PrescriptionDetailData.MedicalDetail> medicalDetailList = data.getMedicalDetailList();
        for(PrescriptionDetailData.MedicalDetail medicalDetail:medicalDetailList){
            checkList.add(new CheckDetailContentData(medicalDetail.medicalName,medicalDetail.price,medicalDetail.amount,true));
        }
        return checkList;
    }
}
