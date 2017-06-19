package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/2/17.
 */
public class LookFamilyDetail {
    public static CommonUrlData lookFamily(String id){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_getMyFamilyDetailed";
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", Global._token);
        String intent = "look_family";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }
}
