package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by Administrator on 2017/2/17.
 */
public class DeleFamilyParam {
    public static CommonUrlData deleFamilyMember(String id) {
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_removeFamilyMember";
        Map<String, String> map = new HashMap<>();
        map.put("id",id);
        map.put("token", Global._token);
        String intent = "dele_family_member";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }
}
