package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import domain.HoldDiagnosisData;

/**
 * Created by Administrator on 2017/2/15.
 */
public class HoldDiagnosisParse {
    public static List<HoldDiagnosisData> holdDiagnosis(String msg){
        List<HoldDiagnosisData> mList = new ArrayList<>();
        try{
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String[]>>(){}.getType();
            List<String[]> listDatas = gson.fromJson(msg,listType);
            for(String[] strs:listDatas){
                mList.add(getHoldDiag(strs));
            }
        }catch (Exception e){

        }
        return mList;
    }
    private static HoldDiagnosisData getHoldDiag(String[] strs){
        return new HoldDiagnosisData(strs[0],strs[1],strs[2],strs[3],strs[4],strs[5],strs[6],strs[7],strs[8]);
    }
}
