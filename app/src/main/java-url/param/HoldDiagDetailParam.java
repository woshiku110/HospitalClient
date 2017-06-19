package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/2/15.
 */
public class HoldDiagDetailParam {
    /**
     * @param id
     * @return CommonUrl
     * */
    public static CommonUrlData holdDiagnosis(String id){
        String baseUrl = Global.baseUrl;
        /*String actionUrl = "yuyue/AppointmentSupport_getOrderInfo";*/
        String actionUrl = "yuyue/PatientPersonalCenter_waitDetails";
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("token",Global._token);
        String intent = "holdDiagnosisDetail";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
    /**
     * @param orderId
     * @return CommonUrl
     * */
    public static CommonUrlData illHold(String orderId){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentProcess_patientWaiting";
        Map<String,String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("token",Global._token);
        String intent = "illHold";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
