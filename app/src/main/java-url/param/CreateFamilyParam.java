package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/2/13.
 */
public class CreateFamilyParam {
    /**
     * @param pic
     * @param name
     * @param sex
     * @param //intent(login)
     * @version 1.1
     * @return CommonUrlData
     * */
    public static CommonUrlData createFamily(String pic,String name,String sex,String phone,String birth,String city,String xx,String gmy,String ywjj,String jwbs,boolean isBenRen){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_createFamilyMember";
        Map<String,String> map = new HashMap<>();
        map.put("pic",pic);
        map.put("name", name);
        map.put("sex",sex);
        map.put("phone",phone);
        map.put("birth",birth);
        map.put("city",city);
        map.put("xx",xx);
        map.put("gmy",gmy);
        map.put("ywjj",ywjj);
        map.put("jwbs", jwbs);
        if(isBenRen){
            map.put("gx","本人");
        }
        map.put("token",Global._token);
        String intent = "createFamily";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
