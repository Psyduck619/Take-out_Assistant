package takeoutassistant.model;

import java.util.Date;

public class BeanUser {
    public static BeanUser currentLoginUser = null;
    private String user_id;
    private String user_name;
    private String user_gender;
    private String user_pwd;
    private String user_phone;
    private String user_email;
    private String user_city;
    private Date user_reg_time;
    private boolean VIP;
    private Date VIP_end_time;

    public boolean isVIP() {
        return VIP;
    }

    public void setVIP(boolean VIP) {
        this.VIP = VIP;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String isUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public Date getUser_reg_time() {
        return user_reg_time;
    }

    public void setUser_reg_time(Date user_reg_time) {
        this.user_reg_time = user_reg_time;
    }

    public Date getVIP_end_time() {
        return VIP_end_time;
    }

    public void setVIP_end_time(Date VIP_end_time) {
        this.VIP_end_time = VIP_end_time;
    }
}
