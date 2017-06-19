package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by Administrator on 2017/2/27.
 */
public class CheckDetailParam {
    public static CommonUrlData checkDetail(String checkId){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_getCheckInfo";
        Map<String,String> map = new HashMap<>();
        map.put("id",checkId);
        map.put("token",Global._token);
        String intent = "checkDetail";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
