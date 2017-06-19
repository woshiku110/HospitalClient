package domain;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PreorderDetailData {
    private String id,name,sex,symbol,symbolDesc;
    private String []pics;
    private QosAns qosAns;
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

    public PreorderDetailData() {
    }

    public PreorderDetailData(String id, String name, String sex, String symbol, String symbolDesc, String[] pics, QosAns qosAns) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.symbol = symbol;
        this.symbolDesc = symbolDesc;
        this.pics = pics;
        this.qosAns = qosAns;
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

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public QosAns getQosAns() {
        return qosAns;
    }

    public void setQosAns(QosAns qosAns) {
        this.qosAns = qosAns;
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

    public String getSymbolDesc() {
        return symbolDesc;
    }

    public void setSymbolDesc(String symbolDesc) {
        this.symbolDesc = symbolDesc;
    }

    public List<QosAns> getQosAnsList() {
        return qosAnsList;
    }

    public void setQosAnsList(List<QosAns> qosAnsList) {
        this.qosAnsList = qosAnsList;
    }

    @Override
    public String toString() {
        return "PreorderDetailData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", symbol='" + symbol + '\'' +
                ", symbolDesc='" + symbolDesc + '\'' +
                ", pics=" + Arrays.toString(pics) +
                ", qosAns=" + qosAns +
                ", qosAnsList=" + qosAnsList +
                '}';
    }
}
