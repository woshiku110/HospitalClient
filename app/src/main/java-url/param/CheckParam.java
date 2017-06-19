package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by Administrator on 2017/2/25.
 * 检查单
 */
public class CheckParam {
    public static CommonUrlData check(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_checkList";
        Map<String,String> map = new HashMap<>();
        map.put("token",Global._token);
        String intent = "check";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
