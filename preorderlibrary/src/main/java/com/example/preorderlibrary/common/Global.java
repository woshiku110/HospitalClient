package com.example.preorderlibrary.common;

/**
 * Created by willyou on 2016/11/8.
 */

public class Global {
    public static String baseUrl="http://192.168.0.202/jfs1.1/";
    public static String uploadUrl = "http://192.168.0.202/File/uploadFile";
    public static String fileAddr = "http://192.168.0.202/File/filebed/";
   /* public static String baseUrl="http://211.159.188.107/jfs1.1/";
    public static String uploadUrl = "http://211.159.188.107/File/uploadFile";
    public static String fileAddr = "http://211.159.188.107/File/filebed/";*/
    public static String token;
    public static String id;
    public static String pre_type;//预约类型
    public static String part="head";
    public static boolean diseaseIsEmpty = true;
    public static final int CreateLookMyFamilyRequest = 198;
    public static final int CreateMyFamilyRequest = 199;
    public static final int CreateFamilyRequest = 200;
    public static final int CreateFamilyReturn = 201;
    public static final int ReviseFamilyReturn = 202;
    public static final int CreateMyFamilyReturn = 203;
    public static final int CreateLookMyFamilyReturn = 204;
    public static final int NEW_USER = 10;
    public static final int EDIT_USER = 11;
    public static final int LOOK_USER = 12;
    public static final int MY_NEW_USER = 13;
    public static final int REVISE_MY_USER = 14;
//    public static String submitPersonId;//用于减少viewpage的利用
//    public static String partsId;
}
