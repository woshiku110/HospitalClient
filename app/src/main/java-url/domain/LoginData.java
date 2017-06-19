package domain;

/**
 * Created by Administrator on 2016/12/28.
 */
public class LoginData {
    public boolean success;
    public String code;
    public String birthplace;
    public String msg;
    public class Message{
        public String id;
        public String email;
        public String logo;
        public String name;
        public String phone;
        public String state;
        public String token;
        public String username;

        @Override
        public String toString() {
            return "Message{" +
                    "email='" + email + '\'' +
                    ", id='" + id + '\'' +
                    ", logo='" + logo + '\'' +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", state='" + state + '\'' +
                    ", token='" + token + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "birthplace='" + birthplace + '\'' +
                ", success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
