package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/3/1.
 */
public class PrescriptionParam {
    /**
     * @return CommonUrl
     * */
    public static CommonUrlData prescription(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_getRecipeList";
        Map<String,String> map = new HashMap<>();
        map.put("token",Global._token);
        String intent = "prescription";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
