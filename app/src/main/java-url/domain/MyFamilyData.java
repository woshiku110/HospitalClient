package domain;

/**
 * Created by Administrator on 2017/2/16.
 */
public class MyFamilyData {
    private String id,name,sex,age,gms,icon;
    private boolean typeOne;
    //[\"1\",\"touxiang.jpg\",\"周小明\",\"男\",\"2000-01-01\",\"无过敏史\"]

    public MyFamilyData(boolean typeOne) {
        this.typeOne = typeOne;
    }

    public MyFamilyData(String id, String icon, String name, String sex, String age, String gms, boolean typeOne) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.gms = gms;
        this.typeOne = typeOne;
    }


    /*public MyFamilyData(String id, String name, String age, String sex, String gms,String icon,boolean typeOne) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.gms = gms;
        this.icon = icon;
        this.typeOne = typeOne;
    }*/

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGms() {
        return gms;
    }

    public void setGms(String gms) {
        this.gms = gms;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isTypeOne() {
        return typeOne;
    }

    public void setTypeOne(boolean typeOne) {
        this.typeOne = typeOne;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "MyFamilyData{" +
                "age='" + age + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", gms='" + gms + '\'' +
                ", icon='" + icon + '\'' +
                ", typeOne=" + typeOne +
                '}';
    }
}
