package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/11/11.
 */

public class BodyPartData {
    private boolean success;
    private String birthplace;
    private String msg;

    @Override
    public String toString() {
        return "BodyPartData{" +
                "success=" + success +
                ", birthplace='" + birthplace + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
