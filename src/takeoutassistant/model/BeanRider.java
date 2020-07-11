package takeoutassistant.model;

import java.util.Date;

public class BeanRider {

    public static final String[] tblRiderTitle={"骑手编号","骑手名字","入职日期","骑手身份","单数","月收入"};
    private int rider_id;
    private String rider_name;
    private Date entry_date;
    private String rider_status;
    private int order_count;
    private double month_income;

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public double getMonth_income() {
        return month_income;
    }

    public void setMonth_income(double month_income) {
        this.month_income = month_income;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

    public String getRider_name() {
        return rider_name;
    }

    public void setRider_name(String rider_name) {
        this.rider_name = rider_name;
    }

    public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public String getRider_status() {
        return rider_status;
    }

    public void setRider_status(String rider_status) {
        this.rider_status = rider_status;
    }

    public String getCell(int col){
        if(col==0) return ""+this.rider_id;
        else if(col==1) return this.rider_name;
        else if(col==2) return ""+this.entry_date;
        else if(col==3) return this.rider_status;
        else if(col==4) return ""+this.order_count;
        else if(col==5) return ""+this.month_income;
        else return "";
    }

}
