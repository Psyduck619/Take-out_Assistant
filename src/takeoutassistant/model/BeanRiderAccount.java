package takeoutassistant.model;

import java.util.Date;

public class BeanRiderAccount {
    private String account_id;
    private Date finish_time;
    private String order_comment;
    private Double per_income;
    private String rider_id;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
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

    public String getRider_id() {
        return rider_id;
    }

    public void setRider_id(String rider_id) {
        this.rider_id = rider_id;
    }
}
