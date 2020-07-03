package takeoutassistant.model;

public class BeanUserJidan {
    private String user_id;
    private String seller_id;
    private int coupon_request;
    private int coupon_get;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public int getCoupon_request() {
        return coupon_request;
    }

    public void setCoupon_request(int coupon_request) {
        this.coupon_request = coupon_request;
    }

    public int getCoupon_get() {
        return coupon_get;
    }

    public void setCoupon_get(int coupon_get) {
        this.coupon_get = coupon_get;
    }
}
