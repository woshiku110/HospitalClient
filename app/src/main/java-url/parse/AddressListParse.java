package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import domain.AddressData;

/**
 * Created by Administrator on 2017/3/7.
 */
public class AddressListParse {

    public static List<AddressData> addressList(String msg){
        List<AddressData> mList = new ArrayList<>();
        try{
            Type type = new TypeToken<List<String[]>>(){}.getType();
            Gson gson = new Gson();
            List<String[]> strsList = gson.fromJson(msg,type);
            for(String[] strs:strsList){
                mList.add(getAddressData(strs));
            }
        }catch(Exception e){

        }
        return mList;
    }

    private static AddressData getAddressData(String[] strs){
        int flag = Integer.parseInt(strs[5]);
        if(flag == 0){
            return new AddressData(strs[0],strs[1],strs[2],strs[3],strs[4],false);
        }else{
            return new AddressData(strs[0],strs[1],strs[2],strs[3],strs[4],true);
        }
    }
}
