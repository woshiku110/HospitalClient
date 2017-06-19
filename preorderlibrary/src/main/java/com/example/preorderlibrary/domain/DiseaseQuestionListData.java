package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/12/7.
 */

public class DiseaseQuestionListData {
    private boolean success;
    private String birthplace;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    @Override
    public String toString() {

        return "DiseaseQuestionListData{" +
                "success=" + success +
                ", birthplace='" + birthplace + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
