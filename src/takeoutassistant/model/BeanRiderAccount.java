package takeoutassistant.model;

import java.util.Date;

public class BeanRiderAccount {

    public static final String[] tblRiderAccountTitle={"订单编号","订单完成时间","订单评价","本单收入"};
    public static final String[] tblRiderCommentTitle={"骑手","评价","订单完成时间"};
    private int account_id;
    private Date finish_time;
    private String order_comment;
    private Double per_income;
    private int rider_id;
    private int order_id;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public Date getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Date finish_time) {
        this.finish_time = finish_time;
    }

    public String getOrder_comment() {
        return order_comment;
    }

    public void setOrder_comment(String order_comment) {
        this.order_comment = order_comment;
    }

    public Double getPer_income() {
        return per_income;
    }

    public void setPer_income(Double per_income) {
        this.per_income = per_income;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCell(int col){
        if(col==0) return ""+this.order_id;
        else if(col==1) return ""+this.finish_time;
        else if(col==2) return ""+this.order_comment;
        else if(col==3) return ""+this.per_income;
        else return "";
    }

    public String getCell2(int col){
        if(col==0) return ""+this.rider_id;
        else if(col==1) return ""+this.order_comment;
        else if(col==2) return ""+this.finish_time;
        else return "";
    }

}
