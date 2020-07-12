package takeoutassistant.model;

public class BeanRecommend {

    public static final String[] tblRecommendTitle={"商家名称","商品名称","近期购买量"};
    private String user_id;
    private int seller_id;
    private String seller_name;
    private int goods_id;
    private String goods_name;
    private int quantity;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCell(int col){
        if(col==0) return this.seller_name;
        else if(col==1) return this.goods_name;
        else if(col==2) return ""+this.quantity;
        else return "";
    }

}
