package takeoutassistant.model;

public class BeanOrderInfo {
    public static final String[] tblMyCartTitle={"商品名称","商品数量","单价","总价","单品优惠"};
    private int order_id;
    private int goods_id;
    private String goods_name;
    private int goods_quantity;
    private double goods_price;
    private double per_discount;
    private String user_id;
    private boolean done;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(int goods_quantity) {
        this.goods_quantity = goods_quantity;
    }

    public double getOrder_price() {
        return goods_price;
    }

    public void setOrder_price(double order_price) {
        this.goods_price = order_price;
    }

    public double getPer_discount() {
        return per_discount;
    }

    public void setPer_discount(double per_discount) {
        this.per_discount = per_discount;
    }

    public String getCell(int col){
        if(col==0) return this.goods_name;
        else if(col==1) return ""+this.goods_quantity;
        else if(col==2) return ""+this.goods_price;
        else if(col==3) return ""+(this.goods_price * this.goods_quantity);
        else if(col==4) return ""+this.per_discount;
        else return "";
    }

}
