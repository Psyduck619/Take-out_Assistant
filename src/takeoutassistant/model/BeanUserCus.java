package takeoutassistant.model;

public class BeanUserCus {

    public static final String[] tblUserCusTitle={"用户","累积单数","累积消费","累积优惠"};
    private String user_id;
    private String user_name;
    private int order_count;
    private Double order_amount;
    private Double order_discount;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public Double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(Double order_amount) {
        this.order_amount = order_amount;
    }

    public Double getOrder_discount() {
        return order_discount;
    }

    public void setOrder_discount(Double order_discount) {
        this.order_discount = order_discount;
    }

    public String getCell(int col){
        if(col==0) return ""+this.user_id;
        else if(col==1) return ""+this.order_count;
        else if(col==2) return ""+this.order_amount;
        else if(col==3) return ""+this.order_discount;
        else return "";
    }

}
