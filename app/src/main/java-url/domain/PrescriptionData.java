package domain;

/**
 * Created by Administrator on 2017/3/2.
 * 处方数据
 */
public class PrescriptionData {
    private String id,icon,name,date,doctor,state,sex="男";

    public PrescriptionData(String id, String icon, String name, String date, String doctor, String state) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.date = date;
        this.doctor = doctor;
        this.state = state;
    }

    public PrescriptionData(String id, String icon, String name, String date, String doctor, String state,String sex) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.date = date;
        this.doctor = doctor;
        this.state = state;
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
