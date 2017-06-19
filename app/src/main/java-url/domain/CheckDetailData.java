package domain;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public class CheckDetailData {
    private String checkId,hospitalName,checkNo,printAddress,phone;
    private List<CheckItemData> checkItemDataList;
    public class CheckItemData{
        public String itemId;
        public String money;
        public String backUp;
        public String state;
    }

    public CheckDetailData(String checkId, String checkNo, String hospitalName, String phone, String printAddress, List<CheckItemData> checkItemDataList) {
        this.checkId = checkId;
        this.checkNo = checkNo;
        this.hospitalName = hospitalName;
        this.phone = phone;
        this.printAddress = printAddress;
        this.checkItemDataList = checkItemDataList;
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

    public List<CheckItemData> getCheckItemDataList() {
        return checkItemDataList;
    }

    public void setCheckItemDataList(List<CheckItemData> checkItemDataList) {
        this.checkItemDataList = checkItemDataList;
    }
}
