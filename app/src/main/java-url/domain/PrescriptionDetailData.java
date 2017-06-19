package domain;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public class PrescriptionDetailData {
    private String orderId,name,phone,receAddr,hospitalName,payNo,createTime,payTime,orderState;
    private List<MedicalDetail> medicalDetailList;
    public class MedicalDetail{
        public String medicalName,amount,price;

        @Override
        public String toString() {
            return "MedicalDetail{" +
                    "amount='" + amount + '\'' +
                    ", medicalName='" + medicalName + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
    }

    public PrescriptionDetailData() {

    }

    public PrescriptionDetailData(String orderId, String name, String phone, String receAddr, String hospitalName, List<MedicalDetail> medicalDetailList, String payNo, String createTime, String payTime, String orderState) {
        this.orderId = orderId;
        this.name = name;
        this.phone = phone;
        this.receAddr = receAddr;
        this.hospitalName = hospitalName;
        this.medicalDetailList = medicalDetailList;
        this.payNo = payNo;
        this.createTime = createTime;
        this.payTime = payTime;
        this.orderState = orderState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public List<MedicalDetail> getMedicalDetailList() {
        return medicalDetailList;
    }

    public void setMedicalDetailList(List<MedicalDetail> medicalDetailList) {
        this.medicalDetailList = medicalDetailList;
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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceAddr() {
        return receAddr;
    }

    public void setReceAddr(String receAddr) {
        this.receAddr = receAddr;
    }

    @Override
    public String toString() {
        return "PrescriptionDetailData{" +
                "createTime='" + createTime + '\'' +
                ", orderId='" + orderId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", receAddr='" + receAddr + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", payNo='" + payNo + '\'' +
                ", payTime='" + payTime + '\'' +
                ", orderState='" + orderState + '\'' +
                ", medicalDetailList=" + medicalDetailList +
                '}';
    }
}
