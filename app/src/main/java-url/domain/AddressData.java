package domain;

/**
 * Created by Administrator on 2017/3/7.
 * 用户地址数据
 */
public class AddressData {
    private String id,name,phone,area,detailAddress;
    private boolean defaultChoosed;

    public AddressData() {
    }

    public AddressData(String id, String name,String phone, String area, String detailAddress, boolean defaultChoosed) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.area = area;
        this.detailAddress = detailAddress;
        this.defaultChoosed = defaultChoosed;
    }

    public AddressData(String name, String area, String detailAddress, boolean defaultChoosed) {
        this.name = name;
        this.area = area;
        this.detailAddress = detailAddress;
        this.defaultChoosed = defaultChoosed;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isDefaultChoosed() {
        return defaultChoosed;
    }

    public void setDefaultChoosed(boolean defaultChoosed) {
        this.defaultChoosed = defaultChoosed;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "AddressData{" +
                "area='" + area + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", defaultChoosed=" + defaultChoosed +
                '}';
    }
}
