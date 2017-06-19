package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/3/20.
 */
public class CommentDoctorParam {
    /**
     * @param orderId 订单id
     * @param score 用户评价分数
     * */
    public static CommonUrlData commentDoctor(String orderId,String score){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentProcess_patientEvaluate";
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("score",score);
        map.put("describe","");
        map.put("token",Global._token);
        String intent = "commentDoctor";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
