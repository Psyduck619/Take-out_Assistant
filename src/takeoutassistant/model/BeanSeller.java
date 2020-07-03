package takeoutassistant.model;

public class BeanSeller {
    private String seller_id;
    private String seller_name;
    private int seller_level;
    private double per_cost;
    private int total_sales;

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public int getSeller_level() {
        return seller_level;
    }

    public void setSeller_level(int seller_level) {
        this.seller_level = seller_level;
    }

    public double getPer_cost() {
        return per_cost;
    }

    public void setPer_cost(double per_cost) {
        this.per_cost = per_cost;
    }

    public int getTotal_sales() {
        return total_sales;
    }

    public void setTotal_sales(int total_sales) {
        this.total_sales = total_sales;
    }
}
