package domain;

/**
 * Created by Administrator on 2017/2/15.
 */
public class HoldDiagnosisData {
    private String illId,name,icon,sex,illDate,doctorName,state,illTime,orderId;

    public HoldDiagnosisData(String illId, String name, String icon, String sex, String illDate, String doctorName, String state, String illTime, String orderId) {
        this.illId = illId;
        this.name = name;
        this.icon = icon;
        this.sex = sex;
        this.illDate = illDate;
        this.doctorName = doctorName;
        this.state = state;
        this.illTime = illTime;
        this.orderId = orderId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIllDate() {
        return illDate;
    }

    public void setIllDate(String illDate) {
        this.illDate = illDate;
    }

    public String getIllId() {
        return illId;
    }

    public void setIllId(String illId) {
        this.illId = illId;
    }

    public String getIllTime() {
        return illTime;
    }

    public void setIllTime(String illTime) {
        this.illTime = illTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "HoldDiagnosisData{" +
                "doctorName='" + doctorName + '\'' +
                ", illId='" + illId + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", sex='" + sex + '\'' +
                ", illDate='" + illDate + '\'' +
                ", state='" + state + '\'' +
                ", illTime='" + illTime + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
