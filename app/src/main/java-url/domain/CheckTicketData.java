package domain;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 * 检查单数据
 */
public class CheckTicketData {
    private String checkId,hospitalName,checkNo,printAddress,phone,state;
    private List<CheckTicketItem> itemList;
    public class CheckTicketItem{
        public CheckTicketItem(String id, String name, String price, String backUp, String state) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.backUp = backUp;
            this.state = state;
        }

        public String id;
        public String name;
        public String price;
        public String backUp;
        public String state;
    }

    public CheckTicketData() {
    }

    public CheckTicketData(String checkId, String hospitalName, String checkNo, String printAddress, String phone, List<CheckTicketItem> itemList) {
        this.checkId = checkId;
        this.hospitalName = hospitalName;
        this.checkNo = checkNo;
        this.printAddress = printAddress;
        this.phone = phone;
        this.itemList = itemList;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public List<CheckTicketItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<CheckTicketData.CheckTicketItem> itemList) {
        this.itemList = itemList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrintAddress() {
        return printAddress;
    }

    public void setPrintAddress(String printAddress) {
        this.printAddress = printAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
