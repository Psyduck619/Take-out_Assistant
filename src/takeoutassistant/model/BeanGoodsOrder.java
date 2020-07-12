package takeoutassistant.model;

import java.util.Date;

public class BeanGoodsOrder {
    public static final String[] tblOrderTitle={"订单编号","用户编号","商家编号","地址编号","骑手编号","原价","实际价格","满减编号","优惠券编号","下单时间","期望送达时间","配送状态"};
    //public static final String[] tblOrderTitle2={"订单编号","用户编号","商家编号","地址编号","骑手编号","下单时间","期望送达时间","配送状态"};
    private int order_id;
    private int seller_id;
    private int rider_id;
    private String  user_id;
    private int add_id;
    private int manjian_id;
    private int coupon_id;
    private double original_price;
    private double final_price;
    private Date order_time;
    private Date request_time;
    private String order_state;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public int getManjian_id() {
        return manjian_id;
    }

    public void setManjian_id(int manjian_id) {
        this.manjian_id = manjian_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public double getFinal_price() {
        return final_price;
    }

    public void setFinal_price(double final_price) {
        this.final_price = final_price;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Date getRequest_time() {
        return request_time;
    }

    public void setRequest_time(Date request_time) {
        this.request_time = request_time;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getCell(int col){
        if(col==0) return ""+this.order_id;
        else if(col==1) return ""+this.user_id;
        else if(col==2) return ""+this.seller_id;
        else if(col==3) return ""+this.add_id;
        else if(col==4) return ""+this.rider_id;
        else if(col==5) return ""+this.original_price;
        else if(col==6) return ""+this.final_price;
        else if(col==7) return ""+this.manjian_id;
        else if(col==8) return ""+this.coupon_id;
        else if(col==9) return ""+this.order_time;
        else if(col==10) return ""+this.request_time;
        else if(col==11) return ""+this.order_state;
        else return "";
    }

}
