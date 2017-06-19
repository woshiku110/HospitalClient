package domain;

/**
 * Created by Administrator on 2017/3/20.
 */
public class NewsRemindData {
    private String orderId,title,content,date;
    private int categroy,state,enterActivityIndex;
    public NewsRemindData(String orderId, String title, String content) {
        this.orderId = orderId;
        this.title = title;
        this.content = content;
    }

    public NewsRemindData(String orderId, String title, String content, int categroy, int state) {
        this.orderId = orderId;
        this.title = title;
        this.content = content;
        this.categroy = categroy;
        this.state = state;
    }

    public NewsRemindData(String orderId, String title, String content, int categroy, int state,int enterActivityIndex) {
        this.orderId = orderId;
        this.title = title;
        this.content = content;
        this.categroy = categroy;
        this.state = state;
        this.enterActivityIndex = enterActivityIndex;
    }
    public NewsRemindData(String orderId, String title, String content,String date,int enterActivityIndex) {
        this.orderId = orderId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.enterActivityIndex = enterActivityIndex;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategroy() {
        return categroy;
    }

    public void setCategroy(int categroy) {
        this.categroy = categroy;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getEnterActivityIndex() {
        return enterActivityIndex;
    }

    public void setEnterActivityIndex(int enterActivityIndex) {
        this.enterActivityIndex = enterActivityIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewsRemindData{" +
                "content='" + content + '\'' +
                ", orderId='" + orderId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
