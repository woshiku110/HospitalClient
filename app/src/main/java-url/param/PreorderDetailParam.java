package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PreorderDetailParam {
    /**
     * @param id
     * @return CommonUrl
     * */
    public static CommonUrlData preorderDetail(String id){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getOrderInfo";
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("token",Global._token);
        String intent = "preorderDetail";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
