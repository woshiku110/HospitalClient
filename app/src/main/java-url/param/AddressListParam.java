package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/3/7.
 */
public class AddressListParam {
    public static CommonUrlData addressList(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_getAddressList";
        Map<String,String> map = new HashMap<>();
        map.put("token",Global._token);
        String intent = "AddressList";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
