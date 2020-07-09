package takeoutassistant.model;

import java.util.Date;

public class BeanMyCoupon {
    public static final String[] tblMyCouponTitle={"�̼�","�Żݽ��","����","����ʱ��","�Ƿ�ͬ������"};
    public static final String[] tblMyCouponTitle2={"�Żݽ��","����","����ʱ��","�Ƿ�ͬ������"};
    private String user_id;
    private int coupon_id;
    private int coupon_amount;
    private int coupon_count;
    private Date end_date;
    private String seller_name;
    private Boolean ifTogether;

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
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

    public Boolean getIfTogether() {
        return ifTogether;
    }

    public void setIfTogether(Boolean ifTogether) {
        this.ifTogether = ifTogether;
    }

    public String getCell(int col){
        if(col==0) return this.seller_name;
        else if(col==1) return ""+this.coupon_amount;
        else if(col==2) return ""+this.coupon_count;
        else if(col==3) return ""+this.end_date;
        else if(col==4){
            if(this.ifTogether) return "��";
            else return "��";
        }
        else return "";
    }

    public String getCell2(int col){
        if(col==0) return ""+this.coupon_amount;
        else if(col==1) return ""+this.coupon_count;
        else if(col==2) return ""+this.end_date;
        else if(col==3){
            if(this.ifTogether) return "��";
            else return "��";
        }
        else return "";
    }

}
