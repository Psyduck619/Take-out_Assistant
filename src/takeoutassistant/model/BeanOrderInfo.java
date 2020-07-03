package takeoutassistant.model;

public class BeanOrderInfo {
    private String order_id;
    private String goods_id;
    private int goods_quantity;
    private double order_price;
    private double per_discount;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(int goods_quantity) {
        this.goods_quantity = goods_quantity;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public double getPer_discount() {
        return per_discount;
    }

    public void setPer_discount(double per_discount) {
        this.per_discount = per_discount;
    }
}
