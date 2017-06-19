package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.hospitalclient.utils.LogUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import domain.MyFamilyData;

/**
 * Created by Administrator on 2017/2/16.
 */
public class MyFamilyParse {
    public static List<MyFamilyData> myFamily(String msg){
        LogUtil.print("msg:"+msg);
        List<MyFamilyData> list = new ArrayList<>();
        try{
            Gson gson = new Gson();
            Type type = new TypeToken<List<String[]>>(){}.getType();
            List<String[]> dataList = gson.fromJson(msg,type);
            for(String[] strs:dataList){
                list.add(getMyFamilyData(strs));
                //LogUtil.print("msg strs:" + strs);
            }
        }catch (Exception e){
            LogUtil.printError(e);
        }
        return list;
    }
    private static MyFamilyData getMyFamilyData(String[] strs){
        //String id, String name, String age, String sex, String gms,String icon,boolean typeOne
        //[[\"1\",\"touxiang.jpg\",\"周小明\",\"男\",\"2000-01-01\",\"无过敏史\"],[\"12\",\"touxiang.jp
        LogUtil.print("strs:"+strs.toString());
        return new MyFamilyData(strs[0],strs[1],strs[2],strs[3],getAge(strs[4])+"",strs[5],true);
    }
    private static int getAge(String date){
        /*String []time = date.split("-");
        int year = Integer.parseInt(time[0]);*/
        int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(date.substring(0,4));
        return age;
    }
}
