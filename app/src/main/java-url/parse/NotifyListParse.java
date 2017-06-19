package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.hospitalclient.utils.LogUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import domain.NotifyData;

/**
 * Created by Administrator on 2017/3/17.
 */
public class NotifyListParse {
    public static List<NotifyData> notifyList(String msg){
        Type type = new TypeToken<List<String[]>>(){}.getType();
        Gson gson = new Gson();
        List<NotifyData> mList = new ArrayList<>();
        try{
            List<String[]> strsList = gson.fromJson(msg, type);
            for(String[] strs:strsList){
                LogUtil.print("lookat", strs[0] + strs[1] + strs[2] + strs[3] + strs[4] + strs[5]);
                mList.add(getNotifyData(strs));
            }
        }catch(Exception e){
            LogUtil.print("lookat","数据解析失败");
        }
        return mList;
    }

    private static NotifyData getNotifyData(String[] strs){
        return new NotifyData(strs[0],strs[1],strs[2],strs[3],strs[4],strs[5]);
    }
}
