package takeoutassistant.model;

import java.util.Date;

public class BeanCoupon {
    public static final String[] tblCouponTitle={"优惠券序号","优惠金额","集单数","开始时间","结束时间","是否同享"};
    private int coupon_id;
    private int seller_id;
    private int coupon_amount;
    private int coupon_request;
    private Date begin_date;
    private Date end_date;
    private boolean ifTogether;

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

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

    public boolean isIfTogether() {
        return ifTogether;
    }

    public void setIfTogether(boolean ifTogether) {
        this.ifTogether = ifTogether;
    }

    public String getCell(int col){
        if(col==0) return  ""+this.coupon_id;
        else if(col==1) return ""+this.coupon_amount;
        else if(col==2) return ""+this.coupon_request;
        else if(col==3) return  ""+this.begin_date;
        else if(col==4) return  ""+this.end_date;
        else if(col==5){
            if(this.ifTogether) return "是";
            else return "否";
        }
        else return "";
    }
}
