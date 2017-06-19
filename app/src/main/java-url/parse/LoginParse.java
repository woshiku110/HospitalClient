package parse;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import com.woshiku.hospitalclient.inter.ResultCallBack;
import com.woshiku.hospitalclient.utils.LogUtil;
import com.woshiku.hospitalclient.utils.RdUtil;
import com.woshiku.urllibrary.domain.Result;
import common.Global;
import domain.LoginData;

/**
 * Created by Administrator on 2016/12/28
 */
public class LoginParse {
    public static LoginData.Message login(Result result,Handler handler){
        LoginData.Message loginMessage = null;
        LogUtil.print(result.toString());
        if(result.isSuccess()){
            Gson gson = new Gson();
            try{
                LoginData loginData = gson.fromJson(result.getMsg(),LoginData.class);
                if(loginData.success){
                    LoginData.Message msg = gson.fromJson(loginData.msg, LoginData.Message.class);
                    Global.loginMessage = msg;
                    loginMessage = msg;
                    Global._token = Global.loginMessage.token;
                    Global._id = Global.loginMessage.id;
                    LogUtil.print("拿到Token"+Global._token);
                    LogUtil.print(loginData.toString());
                    RdUtil.saveData("login","done");//登录成功后的标志位
                }else{
                    Message fail = new Message();
                    fail.what = Global.parseFailWithReason;
                    Bundle bd = new Bundle();
                    bd.putString("failreason", loginData.msg);
                    fail.setData(bd);
                    handler.sendMessage(fail);
                }
            }catch (Exception e){
                loginMessage = null;
                handler.sendEmptyMessage(Global.parseFail);
            }
        }else{
            handler.sendEmptyMessage(Global.loadFail);
        }
        Message message = new Message();
        message.what = Global.loadOk;
        handler.sendMessageDelayed(message, Global.delay);
        return loginMessage;
    }
    public static LoginData.Message login(Result result,ResultCallBack resultCallBack){
        LoginData.Message loginMessage = null;
        LogUtil.print(result.toString());
        if(result.isSuccess()){
            Gson gson = new Gson();
            try{
                LoginData loginData = gson.fromJson(result.getMsg(),LoginData.class);
                if(loginData.success){
                    LoginData.Message msg = gson.fromJson(loginData.msg, LoginData.Message.class);
                    Global.loginMessage = msg;
                    loginMessage = msg;
                    Global._token = Global.loginMessage.token;
                    Global._id = Global.loginMessage.id;
                    LogUtil.print("拿到Token"+Global._token);
                    LogUtil.print(loginData.toString());
                    if(resultCallBack != null){
                        resultCallBack.parseSuccess(loginData);
                    }
                }else{
                    if(resultCallBack != null){
                        resultCallBack.parseFail(loginData.msg);
                    }
                }
            }catch (Exception e){
                if(resultCallBack != null){
                    resultCallBack.parseFail("json 解析异常");
                }
            }
        }else{
            if(resultCallBack != null){
                resultCallBack.parseFail("网络访问失败");
            }
        }
        return loginMessage;
    }
}
