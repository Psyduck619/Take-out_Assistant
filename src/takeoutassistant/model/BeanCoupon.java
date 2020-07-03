package takeoutassistant.model;

import java.util.Date;

public class BeanCoupon {
    private String coupon_id;
    private String seller_id;
    private int coupon_amount;
    private int coupon_request;
    private Date begin_date;
    private Date end_date;

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public int getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(int coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public int getCoupon_request() {
        return coupon_request;
    }

    public void setCoupon_request(int coupon_request) {
        this.coupon_request = coupon_request;
    }

    public Date getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(Date begin_date) {
        this.begin_date = begin_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
