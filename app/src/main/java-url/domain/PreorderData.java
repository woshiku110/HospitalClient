package domain;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PreorderData {
    private String id,icon,name,age,sex,symbol,state,orderId;

    public PreorderData(String id, String icon,String name, String age, String sex, String symbol, String state,String orderId) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.symbol = symbol;
        this.state = state;
        this.orderId = orderId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
