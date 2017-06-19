package domain;

/**
 * Created by Administrator on 2017/3/19.
 */
public class CommentData {
    private String icon,name,career,direct,hospital,showPoint,doctorId;
    public CommentData(){

    }

    public CommentData(String icon, String name, String career, String direct, String hospital, String showPoint, String doctorId) {
        this.icon = icon;
        this.name = name;
        this.career = career;
        this.direct = direct;
        this.hospital = hospital;
        this.showPoint = showPoint;
        this.doctorId = doctorId;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public String getShowPoint() {
        return showPoint;
    }

    public void setShowPoint(String showPoint) {
        this.showPoint = showPoint;
    }

    @Override
    public String toString() {
        return "CommentData{" +
                "career='" + career + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", direct='" + direct + '\'' +
                ", hospital='" + hospital + '\'' +
                ", showPoint='" + showPoint + '\'' +
                ", doctorId='" + doctorId + '\'' +
                '}';
    }
}
