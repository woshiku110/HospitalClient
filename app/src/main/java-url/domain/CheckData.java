package domain;

/**
 * Created by Administrator on 2017/2/25.
 */
public class CheckData {
    private String id,pic,name,age,sex,hospital,state,checkId;

    public CheckData(String id, String pic, String name, String age, String sex, String hospital, String state) {
        this.id = id;
        this.pic = pic;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.hospital = hospital;
        this.state = state;
    }

    public CheckData(String id, String pic, String name, String age, String sex, String hospital, String state,String checkId) {
        this.id = id;
        this.pic = pic;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.hospital = hospital;
        this.state = state;
        this.checkId = checkId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    @Override
    public String toString() {
        return "CheckData{" +
                "age='" + age + '\'' +
                ", id='" + id + '\'' +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", hospital='" + hospital + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
