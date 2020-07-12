package takeoutassistant.model;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

public class BeanGoods {
    public static final String[] tblGoodsTitle={"商品编号","商品名称","单价","会员价","销量","库存","星级"};
    public static final String[] tblGoodsTitle2={"热门商品","单价","会员价","销量"};
    public static final String[] tblGoodsTitle3={"商家名称","商品类别","商品名称","单价","会员价","销量","库存","星级"};
    private int goods_id;
    private int type_id;
    private String goods_type;
    private String goods_name;
    private double price;
    private double discount_price;
    private int goods_quantity;
    private int goods_sales;
    private double goods_level;

    public int getGoods_sales() {
        return goods_sales;
    }

    public void setGoods_sales(int goods_sales) {
        this.goods_sales = goods_sales;
    }

    public double getGoods_level() {
        return goods_level;
    }

    public void setGoods_level(double goods_level) {
        this.goods_level = goods_level;
    }

    public int getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(int goods_quantity) {
        this.goods_quantity = goods_quantity;
    }

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
        if(col==0) return ""+this.goods_id;
        else if(col==1) return this.goods_name;
        else if(col==2) return ""+this.price;
        else if(col==3) return ""+this.discount_price;
        else if(col==4) return ""+this.goods_sales;
        else if(col==5) return ""+this.goods_quantity;
        else if(col==6) return ""+this.goods_level;
        else return "";
    }

    public String getCell2(int col){
        if(col==0) return this.goods_name;
        else if(col==1) return ""+this.price;
        else if(col==2) return ""+this.discount_price;
        else if(col==3) return ""+this.goods_sales;
        else return "";
    }

    public String getCell3(int col){
        if(col==0) {
            try {
                return TakeoutAssistantUtil.goodsManager.typeToSeller_name(this.type_id);
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }
        else if(col==1) return ""+this.goods_type;
        else if(col==2) return ""+this.goods_name;
        else if(col==3) return ""+this.price;
        else if(col==4) return ""+this.discount_price;
        else if(col==5) return ""+this.goods_sales;
        else if(col==6) return ""+this.goods_quantity;
        else if(col==7) return ""+this.goods_level;
        else return "";
        return null;
    }

}
