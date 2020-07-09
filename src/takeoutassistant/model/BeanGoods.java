package takeoutassistant.model;

public class BeanGoods {
    public static final String[] tblGoodsTitle={"商品名称","单价","会员价"};
    public static final String[] tblGoodsTitle2={"热门商品","单价","会员价"};
    private int goods_id;
    private int type_id;
    private String goods_type;
    private String goods_name;
    private double price;
    private double discount_price;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(double discount_price) {
        this.discount_price = discount_price;
    }

    public String getCell(int col){
        if(col==0) return this.goods_name;
        else if(col==1) return ""+this.price;
        else if(col==2) return ""+this.discount_price;
        else return "";
    }

    public String getCell2(int col){
        if(col==0) return this.goods_name;
        else if(col==1) return ""+this.price;
        else if(col==2) return ""+this.discount_price;
        else return "";
    }

}
