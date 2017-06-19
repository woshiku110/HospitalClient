package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import domain.PrescriptionDetailData;

/**
 * Created by Administrator on 2017/3/8.
 */
public class PrescriptionDetailParse {
    public static PrescriptionDetailData prescriptionDetail(String msg){
        Type type = new TypeToken<String[]>(){}.getType();
        try{
            Gson gson = new Gson();
            String []strs = gson.fromJson(msg, type);
            return new PrescriptionDetailData(strs[0],strs[1],strs[2],strs[3],strs[4],parseMedical(strs[5]),strs[6],strs[7],strs[8],strs[9]);
        }catch (Exception e){

        }
        return null;
    }
    private static List<PrescriptionDetailData.MedicalDetail> parseMedical(String msg){
        List<PrescriptionDetailData.MedicalDetail> list = new ArrayList<>();
        try{
            Type type = new TypeToken<List<String[]>>(){}.getType();
            Gson gson = new Gson();
            List<String[]> mList = gson.fromJson(msg, type);
            for(String[] strs:mList){
                list.add(getMedical(strs));
            }
        }catch(Exception e){

        }
        return list;
    }

    private static PrescriptionDetailData.MedicalDetail getMedical(String[] strs){
        PrescriptionDetailData.MedicalDetail medicalDetail = new PrescriptionDetailData().new MedicalDetail();
        medicalDetail.medicalName = strs[0];
        medicalDetail.amount = strs[1];
        medicalDetail.price = strs[2];
        return medicalDetail;
    }
}
