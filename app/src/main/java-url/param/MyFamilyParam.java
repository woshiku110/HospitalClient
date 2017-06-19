package param;


import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/2/16.
 */
public class MyFamilyParam {
    //获取家庭成员信息的方法，参数只需要id
    public static CommonUrlData myFamily() {
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_listMyFamily";
        Map<String, String> map = new HashMap<>();
        map.put("token", Global._token);
        String intent = "contact_data";
        return new CommonUrlData(baseUrl, actionUrl, map, intent);
    }
}
