package domain;

/**
 * Created by Administrator on 2017/3/21.
 */
public class UserInfoData {
    private String dateOfBirth,liveCity,bloodType,sex,iconImage,name,phone,gmy,ywjj,jwbs;


    public UserInfoData(String name, String sex, String phone, String bloodType, String dateOfBirth, String gmy, String jwbs, String liveCity, String ywjj) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.bloodType = bloodType;
        this.dateOfBirth = dateOfBirth;
        this.gmy = gmy;
        this.jwbs = jwbs;
        this.liveCity = liveCity;
        this.ywjj = ywjj;
    }

    public UserInfoData(String iconImage, String name, String sex, String phone, String bloodType, String dateOfBirth, String gmy, String jwbs, String liveCity, String ywjj) {
        this.iconImage = iconImage;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.bloodType = bloodType;
        this.dateOfBirth = dateOfBirth;
        this.gmy = gmy;
        this.jwbs = jwbs;
        this.liveCity = liveCity;
        this.ywjj = ywjj;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGmy() {
        return gmy;
    }

    public void setGmy(String gmy) {
        this.gmy = gmy;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getJwbs() {
        return jwbs;
    }

    public void setJwbs(String jwbs) {
        this.jwbs = jwbs;
    }

    public String getLiveCity() {
        return liveCity;
    }

    public void setLiveCity(String liveCity) {
        this.liveCity = liveCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYwjj() {
        return ywjj;
    }

    public void setYwjj(String ywjj) {
        this.ywjj = ywjj;
    }
}
