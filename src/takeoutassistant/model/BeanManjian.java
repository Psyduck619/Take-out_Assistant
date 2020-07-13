package takeoutassistant.model;

public class BeanManjian {
    public static final String[] tblManjianTitle={"Âú¼õ±àºÅ","Âú×ã½ð¶î","ÓÅ»Ý½ð¶î"};
    private int manjian_id;
    private int seller_id;
    private int manjian_amount;
    private int discount_amount;

    public int getManjian_id() {
        return manjian_id;
    }

    public void setManjian_id(int manjian_id) {
        this.manjian_id = manjian_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
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

    public String getCell(int col){
        if(col==0) return ""+this.manjian_id;
        else if(col==1) return ""+this.manjian_amount;
        else if(col==2) return ""+this.discount_amount;
        else return "";
    }
}
