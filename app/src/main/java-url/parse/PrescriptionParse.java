package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.hospitalclient.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import domain.PrescriptionData;

/**
 * Created by Administrator on 2017/3/2.
 */
public class PrescriptionParse {
    public static List<PrescriptionData> prescription(String msg){
        List<PrescriptionData> dataList = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<String[]>>(){}.getType();
        try{
            List<String[]> mList = gson.fromJson(msg,type);
            dataList = generateDatas(mList);
        }catch (Exception e){
            LogUtil.printError(e);
        }
        return dataList;
    }
    private static List<PrescriptionData> generateDatas(List<String[]> list){
        List<PrescriptionData> mList = new ArrayList<>();
        for(String[] data:list){
            mList.add(parseData(data));
        }
        return mList;
    }

    private static  PrescriptionData parseData(String []strs){
        return new PrescriptionData(strs[0],strs[1],strs[2],getAge(strs[3])+"",strs[4],strs[5],strs[6]);
    }
    private static int getAge(String date){
        /*String []time = date.split("-");
        int year = Integer.parseInt(time[0]);*/
        int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(date.substring(0,4));
        return age;
    }
}
