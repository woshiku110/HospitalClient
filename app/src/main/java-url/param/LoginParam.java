package param;

import android.os.Handler;

import com.woshiku.hospitalclient.inter.ResultCallBack;
import com.woshiku.hospitalclient.utils.RdUtil;
import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2016/12/28.
 */
public class LoginParam {
    /**
     * @param userName
     * @param passWord
     * @param handler
     * @param //intent(login)
     * @version 1.1
     * @return CommonUrlData
     * */
    public static CommonUrlData login(String userName, String passWord,Handler handler){
        String baseUrl = Global.baseUrl;
        String actionUrl = "upms/patient_login";
        Map<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("password", passWord);
        RdUtil.saveData("username", userName);
        RdUtil.saveData("password",passWord);
        String intent = "login";
        handler.sendEmptyMessage(Global.loading);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
    /**
     * @param userName
     * @param passWord
     * @param //intent(login)
     * @version 1.1
     * @return CommonUrlData
     * */
    public static CommonUrlData login(String userName, String passWord){
        String baseUrl = Global.baseUrl;
        String actionUrl = "upms/patient_login";
        Map<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("password", passWord);
        RdUtil.saveData("username", userName);
        RdUtil.saveData("password",passWord);
        String intent = "login";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
