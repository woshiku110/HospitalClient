package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;
import domain.AddressData;

/**
 * Created by Administrator on 2017/3/7.
 */
public class AddressManageParam {
    /**
     * 设为默认地址
     * */
    public static CommonUrlData defaultAddress(AddressData addressData){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_updateAddress";
        Map<String,String> map = new HashMap<>();
        map.put("id",addressData.getId());
        map.put("name",addressData.getName());
        map.put("region",addressData.getArea());
        map.put("address",addressData.getDetailAddress());
        map.put("phone",addressData.getPhone());
        map.put("defaults","1");
        map.put("state","1");
        map.put("token",Global._token);
        String intent = "defaultAddress";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
    /**
     * 删除地址
     * */
    public static CommonUrlData deleAddress(AddressData addressData){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_updateAddress";
        Map<String,String> map = new HashMap<>();
        map.put("id",addressData.getId());
        map.put("name",addressData.getName());
        map.put("region",addressData.getArea());
        map.put("address",addressData.getDetailAddress());
        map.put("phone",addressData.getPhone());
        if(addressData.isDefaultChoosed()){
            map.put("defaults","1");
        }else{
            map.put("defaults","0");
        }
        map.put("state","2");
        map.put("token",Global._token);
        String intent = "deleAddress";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
    /**
     * 更新地址
     * */
    public static CommonUrlData updateAddress(AddressData addressData){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_updateAddress";
        Map<String,String> map = new HashMap<>();
        map.put("id",addressData.getId());
        map.put("name",addressData.getName());
        map.put("region",addressData.getArea());
        map.put("address",addressData.getDetailAddress());
        map.put("phone",addressData.getPhone());
        if(addressData.isDefaultChoosed()){
            map.put("defaults","1");
        }else{
            map.put("defaults","0");
        }
        map.put("state","1");
        map.put("token",Global._token);
        String intent = "updateAddress";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
    /**
     * 添加地址
     * */
    public static CommonUrlData addAddress(AddressData addressData){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PatientPersonalCenter_updateAddress";
        Map<String,String> map = new HashMap<>();
        map.put("name",addressData.getName());
        map.put("region",addressData.getArea());
        map.put("address",addressData.getDetailAddress());
        map.put("phone",addressData.getPhone());
        if(addressData.isDefaultChoosed()){
            map.put("defaults","1");
        }else{
            map.put("defaults","0");
        }
        map.put("state","1");
        map.put("token",Global._token);
        String intent = "addAddress";
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
