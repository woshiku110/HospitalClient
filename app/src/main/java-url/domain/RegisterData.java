package domain;

/**
 * Created by Administrator on 2017/3/13.
 */
public class RegisterData {
    private String phone,email,password,repassword;

    public RegisterData(String phone, String password, String email) {
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public RegisterData(String phone, String password, String repassword, String email) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.repassword = repassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
