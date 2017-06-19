package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by Administrator on 2017/2/19.
 */
public class BackOrderParam {
    public static CommonUrlData backOrder(String orderId,String reason){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentProcess_patientBounce";
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("reason", reason);
        map.put("token",Global._token);
        String intent = "backOrder";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
