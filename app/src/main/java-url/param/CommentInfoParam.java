package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by Administrator on 2017/3/19.
 * 评价信息参数
 */
public class CommentInfoParam {
    public static CommonUrlData commentInfo(String id){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_getDoctorEvaluate";
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("token",Global._token);
        String intent = "comment";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
