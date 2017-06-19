package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import domain.CheckData;

/**
 * Created by Administrator on 2017/2/25.
 */
public class CheckParse {
    public static List<CheckData> check(String msg){
        Gson gson = new Gson();
        Type type = new TypeToken<List<String[]>>(){}.getType();
        List<String[]> list = gson.fromJson(msg,type);
        List<CheckData> checkList = new ArrayList<>();
        for(String[] strs:list){
            checkList.add(getCheckData(strs));
        }
        return checkList;
    }

    private static CheckData getCheckData(String[] strs){
        return new CheckData(strs[0],strs[1],strs[2],getAge(strs[3])+"",strs[4],strs[5],strs[6],strs[7]);
    }
    private static int getAge(String date){
        /*String []time = date.split("-");
        int year = Integer.parseInt(time[0]);*/
        int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(date.substring(0,4));
        return age;
    }
}
