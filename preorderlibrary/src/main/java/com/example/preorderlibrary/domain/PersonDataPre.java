package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/11/28.
 */

public class PersonDataPre {
    public boolean success;
    public String birthplace;
    public String msg;

    @Override
    public String toString() {
        return "PersonDataPre{" +
                "success=" + success +
                ", birthplace='" + birthplace + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
