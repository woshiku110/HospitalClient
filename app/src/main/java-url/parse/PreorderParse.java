package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.hospitalclient.inter.ResultCallBack;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import common.Global;
import domain.PreorderData;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PreorderParse {
    /**
     * @desc 【0患者id 1患者姓名 2患者头像 3 性别 4患者出生日期 5医生姓名 6订单状态 7就诊时间
     *         8订单id 9医院名称 10症状id】
     * */
    public static List<PreorderData> preorder(String msg){
        Gson gson = new Gson();
        Type type = new TypeToken<List<String[]>>(){}.getType();
        List<PreorderData> list = new ArrayList<>();
        try{
            List<String[]> listDatas = gson.fromJson(msg, type);
            for(String []strs:listDatas){
                list.add(getPreorderData(strs));
            }
        }catch (Exception e){
            LogUtil.print("preorder load fail");
        }
        return list;
    }
    private static PreorderData getPreorderData(String[] strs){
        //String id,icon,name,age,sex,symbol,state
        //[\"1\",\"张小3\",\"BHACBHBHBCEBGHHAAB.png\",\"女\",\"1990年3月19日\",null,\"2\",\"2016-12-31 11:03\",\"11k6\"
        PreorderData preorderData = new PreorderData(strs[0], strs[2],strs[1],getAge(strs[4])+"",strs[3],strs[10],strs[6],strs[8]);
        return preorderData;
    }
    private static int getAge(String date){
        /*String []time = date.split("-");
        int year = Integer.parseInt(time[0]);*/
        int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(date.substring(0,4));
        return age;
    }
    public static void preorder(Result result,ResultCallBack resultCallBack){
        if(result.isSuccess()){
            Result rr = new Gson().fromJson(result.getMsg(),Result.class);
            if(rr.isSuccess()){
                resultCallBack.parseSuccess(rr.getMsg());
            }else{
                resultCallBack.parseFail("拿取接口数据失败");
            }
        }else{
            if(resultCallBack != null){
                resultCallBack.parseFail("因为网络原因失败!!!");
            }
        }
    }
}
