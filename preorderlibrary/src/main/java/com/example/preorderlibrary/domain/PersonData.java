package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/11/8.
 */

public class PersonData {
    private String id;
    private String icon;
    private String name;
    private String sex;
    private String birthday;
    private String allergicHistory;
    private boolean isTypeOne = true;

    public PersonData() {
    }

    public PersonData(String id, String icon, String name, String sex, String birthday, String allergicHistory) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.allergicHistory = allergicHistory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(String allergicHistory) {
        this.allergicHistory = allergicHistory;
    }

    public boolean isTypeOne() {
        return isTypeOne;
    }

    public void setTypeOne(boolean typeOne) {
        isTypeOne = typeOne;
    }
}
