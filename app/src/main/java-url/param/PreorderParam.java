package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PreorderParam {
    /**
     * @param state
     * @return CommonUrl
     * */
    public static CommonUrlData preorder(String state){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_waitList";
        Map<String,String> map = new HashMap<>();
        map.put("state", state);
        map.put("token",Global._token);
        String intent = "preorder";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
