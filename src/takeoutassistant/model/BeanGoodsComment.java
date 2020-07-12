package takeoutassistant.model;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

import java.util.Date;

public class BeanGoodsComment {
    public static final String[] tblGoodsCommentTitle={"商家","商品","评价星级","评价内容","评价时间"};
    public static final String[] tblGoodsCommentTitle2={"商品","用户","评价星级","评价内容","评价时间"};
    private int goods_id;
    private String user_id;
    private String com_content;
    private Date com_date;
    private int com_level;
    private long com_picture;
    private int order_id;
    private int seller_id;

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCom_content() {
        return com_content;
    }

    public void setCom_content(String com_content) {
        this.com_content = com_content;
    }

    public Date getCom_date() {
        return com_date;
    }

    public void setCom_date(Date com_date) {
        this.com_date = com_date;
    }

    public int getCom_level() {
        return com_level;
    }

    public void setCom_level(int com_level) {
        this.com_level = com_level;
    }

    public long getCom_picture() {
        return com_picture;
    }

    public void setCom_picture(long com_picture) {
        this.com_picture = com_picture;
    }

    public String getCell(int col){
        if(col==0) {
            try {
                return TakeoutAssistantUtil.sellerManager.getSellerName(this.seller_id);
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }
        else if(col==1) {
            try {
                return TakeoutAssistantUtil.goodsManager.idToName(this.goods_id);
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }
        else if(col==2) return ""+this.com_level;
        else if(col==3) return ""+this.com_content;
        else if(col==4) return ""+this.com_date;
        else return "";
        return null;
    }
    public String getCell2(int col){
        if(col==0) {
            try {
                return TakeoutAssistantUtil.goodsManager.idToName(this.goods_id);
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }
        else if(col==1) return ""+this.user_id;
        else if(col==2) return ""+this.com_level;
        else if(col==3) return ""+this.com_content;
        else if(col==4) return ""+this.com_date;
        else return "";
        return null;
    }

}
