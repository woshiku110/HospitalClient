package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import domain.CheckTicketData;

/**
 * Created by Administrator on 2017/3/1.
 */
public class CheckDetailParse {
    public static CheckTicketData getCheckTicketData(String msg){
        Type type = new TypeToken<String[]>(){}.getType();
        Gson gson = new Gson();
        String[] strs = gson.fromJson(msg, type);
        CheckTicketData checkTicketData = new CheckTicketData();
        checkTicketData.setCheckId(strs[0]);
        checkTicketData.setHospitalName(strs[1]);
        checkTicketData.setCheckNo(strs[2]);
        checkTicketData.setPrintAddress(strs[3]);
        checkTicketData.setPhone(strs[4]);
        checkTicketData.setItemList(getListItem(strs[5]));
        checkTicketData.setState(strs[6]);
        return checkTicketData;
    }

    private static List<CheckTicketData.CheckTicketItem> getListItem(String msg){
        Type type = new TypeToken<List<String[]>>(){}.getType();
        Gson gson = new Gson();
        List<String[]> mList = gson.fromJson(msg,type);
        List<CheckTicketData.CheckTicketItem> checkList = new ArrayList<>();
        for(String[] param:mList){
            checkList.add(getCheckTicketItem(param));
        }
        return checkList;
    }
    private static CheckTicketData.CheckTicketItem getCheckTicketItem(String []params){
        return new CheckTicketData().new CheckTicketItem(params[0],params[1],params[2],params[3],params[4]);
    }
}
