package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by Administrator on 2017/3/13.
 */
public class RegisterParam {
    /**
     * @param phone
     * @param email
     * @param passWord
     * @param //intent(login)
     * @version 1.1
     * @return CommonUrlData
     * */
    public static CommonUrlData register(String phone, String email,String passWord){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_patientEnroll";
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("email",email);
        map.put("password", passWord);
        /*RdUtil.saveData("username", phone);
        RdUtil.saveData("password",passWord);*/
        String intent = "register";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
