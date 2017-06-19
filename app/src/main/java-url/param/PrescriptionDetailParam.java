package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/3/8.
 */
public class PrescriptionDetailParam {
    /**
     * @param id
     * @return CommonUrl
     * */
    public static CommonUrlData prescriptionDetail(String id){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/MedicinalSupport_getMedicinalAllInfo";
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("token",Global._token);
        String intent = "prescriptionDetail";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
