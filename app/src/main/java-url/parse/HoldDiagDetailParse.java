package parse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.hospitalclient.utils.LogUtil;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import domain.HoldDiagDetailData;

/**
 * Created by Administrator on 2017/2/15.
 */
public class HoldDiagDetailParse {
    public static HoldDiagDetailData holdDiagDetail(String msg){
        HoldDiagDetailData  holdDiagDetailData = null;
        try{
            Gson gson = new Gson();
            Type listType = new TypeToken<String[]>(){}.getType();
            String[] listDatas = gson.fromJson(msg,listType);
            holdDiagDetailData = getHoldDiag(listDatas);
        }catch (Exception e){
        }
        return holdDiagDetailData;
    }
    public static String[] holdDiagDetailArray(String msg){
        try{
            Gson gson = new Gson();
            Type listType = new TypeToken<String[]>(){}.getType();
            String []listDatas = gson.fromJson(msg,listType);
            return listDatas;
        }catch (Exception e){
        }
        return null;
    }

    private static HoldDiagDetailData getHoldDiag(String[] strs){
        LogUtil.print("hold dialog data:"+Arrays.asList(strs).toString());
        //String id, String name, String sex, String symbol, String hospital, String time, String doctor, String receAddress, String holdAddress, String hospitalAddress, String symbolText,
        // String[] pics,List<QosAns> qosAnsList
        String id = getIdAndState(strs[14])[0];
        return new HoldDiagDetailData(id,strs[0],strs[1],strs[2],strs[3],strs[4],strs[5],strs[6],strs[7],strs[8],strs[9],getPics(strs[10]),getListHoldDiagDetailData(strs[11]));
    }

    private static String[] getIdAndState(String msg){
        String strs[]= {"0","1"};
        try{
            Gson gson = new Gson();
            Type type = new TypeToken<List<String[]>>(){}.getType();
            List<String[]> mList = gson.fromJson(msg, type);
            return mList.get(0);
        }catch (Exception e){
            return strs;
        }
    }

    private static String[] getPics(String msg){
        return parseArray(msg);
    }
    private static String[] parseArray(String msg){
        Gson gson = new Gson();
        Type type = new TypeToken<String[]>(){}.getType();
        String strs[] = gson.fromJson(msg, type);
        return strs;
    }

    private static List<HoldDiagDetailData.QosAns> getListHoldDiagDetailData(String msg){
        Gson gson = new Gson();
        Type type = new TypeToken<List<HoldDiagDetailData.QosAns>>(){}.getType();
        List<HoldDiagDetailData.QosAns> mList = gson.fromJson(msg, type);
        return mList;
    }
}
