package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/11/13.
 */

public class ConcreteBodyPartData {
    public boolean success;
    public String birthplace;
    public String msg;

    @Override
    public String toString() {
        return "ConcreteBodyPartData{" +
                "success=" + success +
                ", birthplace='" + birthplace + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
