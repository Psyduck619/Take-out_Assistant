package takeoutassistant.model;

public class BeanGoodsType {
    public static final String[] tblGTypeTitle={"商品类别名称","商品数"};
    private int type_id;
    private int seller_id;
    private String type_name;
    private int quantity;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCell(int col){
        if(col==0) return this.type_name;
        else if(col==1) return ""+this.quantity;
        else return "";
    }
}
