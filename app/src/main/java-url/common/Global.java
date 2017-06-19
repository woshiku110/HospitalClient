package common;

import android.content.Intent;
import java.util.List;
import domain.LoginData;
import domain.NewsRemindData;

/**
 * Created by Administrator on 2016/12/28.
 */
public class Global {
    public static String baseUrl = "http://192.168.0.202/jfs1.1/";
    public static String fileAddr = "http://192.168.0.202/File/filebed/";
//    public static String baseUrl = "http://211.159.188.107/jfs1.1/";
//    public static String fileAddr = "http://211.159.188.107/File/filebed/";
    public static final int loading = 0;
    public static final int loadOk = 1;
    public static final int loadFail = 2;
    public static final int parseFail = 3;
    public static final int parseFailWithReason = 4;
    public static final int shortToast = 5;
    public static final int submitOk = 6;
    public static final long delay = 200;
    public static LoginData.Message loginMessage;
    public static String _token;
    public static String _id;
    public static final int EnterPreorderDetailRequest = 193;
    public static final int EnterManageAddressRequest = 194;
    public static final int EnterEditAddressRequest = 195;
    public static final int EnterAddNewAddressRequest = 196;
    public static final int EnterCheckDetailRequest = 197;
    public static final int CreateLookMyFamilyRequest = 198;
    public static final int CreateMyFamilyRequest = 199;
    public static final int CreateFamilyRequest = 200;
    public static final int CreateFamilyReturn = 201;
    public static final int ReviseFamilyReturn = 202;
    public static final int CreateMyFamilyReturn = 203;
    public static final int CreateLookMyFamilyReturn = 204;
    public static final int CloseAddNewAddressReturn = 205;
    public static final int CloseEditAddressReturn = 206;
    public static final int CloseManageAddressReturn = 207;
    public static final int ClosePreorderDetailReturn = 208;
    public static final int NEW_USER = 10;
    public static final int EDIT_USER = 11;
    public static final int LOOK_USER = 12;
    public static final int MY_NEW_USER = 13;
    public static final int REVISE_MY_USER = 14;
    public static final int WAITTING=0,FAIL=1,NODATA=2,OK=3;
    public static Intent notifyIntent;
    public static boolean openComment = false;
    public static List<NewsRemindData> newsRemindUnreadList ;//用于消息提醒
    public static List<NewsRemindData> newsRemindReadedList;//用于消息提醒
    public static int unreadNewsAmount = 0;
    public static String remindAction = "com.message.remind";
}
