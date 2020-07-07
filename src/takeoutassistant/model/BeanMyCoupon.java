package takeoutassistant.model;

import java.util.Date;

public class BeanMyCoupon {
    private int user_id;
    private String conpon_id;
    private int coupon_amount;
    private int coupon_count;
    private Date end_date;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getConpon_id() {
        return conpon_id;
    }

    public void setConpon_id(String conpon_id) {
        this.conpon_id = conpon_id;
    }

    public int getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(int coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public int getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(int coupon_count) {
        this.coupon_count = coupon_count;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
