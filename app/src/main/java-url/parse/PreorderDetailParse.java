package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.hospitalclient.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.List;

import domain.PreorderDetailData;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PreorderDetailParse {
    public static PreorderDetailData preorderDetail(String msg){
        PreorderDetailData preorderDetailData = new PreorderDetailData();
        try {
            Type type = new TypeToken<String[]>(){}.getType();
            Gson gson = new Gson();
            String[] strs = gson.fromJson(msg, type);
            preorderDetailData = getData(strs);
        }catch (Exception e){
            LogUtil.printError(e);
        }

        return preorderDetailData;
    }
    private static PreorderDetailData getData(String[] strs){
        PreorderDetailData preorderDetailData = new PreorderDetailData();
        preorderDetailData.setId(strs[0]);
        preorderDetailData.setName(strs[1]);
        preorderDetailData.setSex(strs[2]);
        preorderDetailData.setSymbol(strs[3]);
        preorderDetailData.setSymbolDesc(strs[10]);
        preorderDetailData.setPics(getPics(strs[11]));
        preorderDetailData.setQosAnsList(getQosAns(strs[13]));
        return preorderDetailData;
    }
    private static String[] getPics(String msg){
        Gson gson = new Gson();
        Type type = new TypeToken<String[]>(){}.getType();
        String []pics = gson.fromJson(msg, type);
        return pics;
    }
    private static List<PreorderDetailData.QosAns> getQosAns(String msg){
        Gson gson = new Gson();
        Type type = new TypeToken<List<PreorderDetailData.QosAns>>(){}.getType();
        List<PreorderDetailData.QosAns> list = gson.fromJson(msg, type);
        return list;
    }
}
