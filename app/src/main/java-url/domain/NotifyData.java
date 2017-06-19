package domain;

/**
 * Created by Administrator on 2017/3/17.
 */
public class NotifyData {
    public String recordId,orderId,sender,sendTime,categroy,state;

    public NotifyData(String recordId, String orderId, String sender, String sendTime, String categroy, String state) {
        this.recordId = recordId;
        this.orderId = orderId;
        this.sender = sender;
        this.sendTime = sendTime;
        this.categroy = categroy;
        this.state = state;
    }

    public String getCategroy() {
        return categroy;
    }

    public void setCategroy(String categroy) {
        this.categroy = categroy;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "NotifyData{" +
                "categroy='" + categroy + '\'' +
                ", recordId='" + recordId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", sender='" + sender + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
