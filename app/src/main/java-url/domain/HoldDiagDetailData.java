package domain;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
public class HoldDiagDetailData {
    private String id,name,sex,symbol,hospital,time,doctor,receAddress,holdAddress,hospitalAddress,symbolText;
    private String[] pics;
    List<QosAns> qosAnsList;
    public class QosAns{
        public String question;
        public String []answers;
        @Override
        public String toString() {
            return "QosAns{" +
                    "answers=" + Arrays.toString(answers) +
                    ", question='" + question + '\'' +
                    '}';
        }
    }

    public HoldDiagDetailData(String id, String name, String sex, String symbol, String hospital, String time, String doctor, String receAddress, String holdAddress, String hospitalAddress, String symbolText) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.symbol = symbol;
        this.hospital = hospital;
        this.time = time;
        this.doctor = doctor;
        this.receAddress = receAddress;
        this.holdAddress = holdAddress;
        this.hospitalAddress = hospitalAddress;
        this.symbolText = symbolText;
    }

    public HoldDiagDetailData(String id, String name, String sex, String symbol, String hospital, String time, String doctor, String receAddress, String holdAddress, String hospitalAddress, String symbolText,String[] pics,List<QosAns> qosAnsList) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.symbol = symbol;
        this.hospital = hospital;
        this.time = time;
        this.doctor = doctor;
        this.receAddress = receAddress;
        this.holdAddress = holdAddress;
        this.hospitalAddress = hospitalAddress;
        this.symbolText = symbolText;
        this.pics = pics;
        this.qosAnsList = qosAnsList;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHoldAddress() {
        return holdAddress;
    }

    public void setHoldAddress(String holdAddress) {
        this.holdAddress = holdAddress;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
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

    public String getReceAddress() {
        return receAddress;
    }

    public void setReceAddress(String receAddress) {
        this.receAddress = receAddress;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbolText() {
        return symbolText;
    }

    public void setSymbolText(String symbolText) {
        this.symbolText = symbolText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public List<QosAns> getQosAnsList() {
        return qosAnsList;
    }

    public void setQosAnsList(List<QosAns> qosAnsList) {
        this.qosAnsList = qosAnsList;
    }

    @Override
    public String toString() {
        return "HoldDiagDetailData{" +
                "doctor='" + doctor + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", symbol='" + symbol + '\'' +
                ", hospital='" + hospital + '\'' +
                ", time='" + time + '\'' +
                ", receAddress='" + receAddress + '\'' +
                ", holdAddress='" + holdAddress + '\'' +
                ", hospitalAddress='" + hospitalAddress + '\'' +
                ", symbolText='" + symbolText + '\'' +
                ", pics=" + Arrays.toString(pics) +
                ", qosAnsList=" + qosAnsList +
                '}';
    }
}
