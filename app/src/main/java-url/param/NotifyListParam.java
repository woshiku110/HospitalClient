package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by Administrator on 2017/3/17.
 */
public class NotifyListParam {

    public static CommonUrlData notifyUnreadList(){//未读消息
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/Inform_getInformList";
        Map<String,String> map = new HashMap<>();
        map.put("state","1");
        map.put("token",Global._token);
        String intent = "notifyList";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
    public static CommonUrlData notifyList(){//已读消息
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/Inform_getInformList";
        Map<String,String> map = new HashMap<>();
        map.put("state","2");
        map.put("token",Global._token);
        String intent = "notifyList";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
