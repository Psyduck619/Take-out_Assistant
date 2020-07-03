package takeoutassistant.model;

public class BeanManjian {
    private String manjian_id;
    private String seller_id;
    private int manjian_amount;
    private int discount_amount;
    private boolean ifTogether;

    public String getManjian_id() {
        return manjian_id;
    }

    public void setManjian_id(String manjian_id) {
        this.manjian_id = manjian_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public int getManjian_amount() {
        return manjian_amount;
    }

    public void setManjian_amount(int manjian_amount) {
        this.manjian_amount = manjian_amount;
    }

    public int getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(int discount_amount) {
        this.discount_amount = discount_amount;
    }

    public boolean isIfTogether() {
        return ifTogether;
    }

    public void setIfTogether(boolean ifTogether) {
        this.ifTogether = ifTogether;
    }
}
