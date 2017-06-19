package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/11/8.
 */

public class UrlData {
    public boolean success;
    public String code;
    public String msg;
    public UrlDataItem urlDataItem;
//    public String msg;

    @Override
    public String toString() {
        return "UrlData{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", urlDataItem=" + urlDataItem +
                '}';
    }

    public class UrlDataItem{
        public String id;
        public String token;
        public String name;
        public String username;
        public String phone;
        public String email;
        public String state;
        public String logo;

        @Override
        public String toString() {
            return "UrlDataItem{" +
                    "id='" + id + '\'' +
                    ", token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    ", username='" + username + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", state='" + state + '\'' +
                    ", logo='" + logo + '\'' +
                    '}';
        }
    }
}
