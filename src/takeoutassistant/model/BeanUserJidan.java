package takeoutassistant.model;

public class BeanUserJidan {
    public static final String[] tblMyJidanTitle={"商家编号","优惠券金额","需要集单数","已集单数"};
    private String user_id;
    private int seller_id;
    private int coupon_request;
    private int coupon_get;
    private int coupon_amount;
    private int coupon_id;


    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(int coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public int getCoupon_id() {
        return coupon_amount;
    }

    public void setCoupon_id(int coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getCell(int col){
        if(col==0) return ""+this.seller_id;
        else if(col==1) return ""+this.coupon_amount;
        else if(col==2) return ""+this.coupon_request;
        else if(col==3) return ""+this.coupon_get;
        else return "";
    }

}
