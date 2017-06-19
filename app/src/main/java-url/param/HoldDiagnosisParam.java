package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/2/15.
 */
public class HoldDiagnosisParam {
    /**
     * @param state
     * @return CommonUrl
     * */
    public static CommonUrlData holdDiagnosis(String state){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_waitList";
        Map<String,String> map = new HashMap<>();
        map.put("state", state);
        map.put("token",Global._token);
        String intent = "holdDiagnosis";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
