package takeoutassistant.model;

import java.util.Date;

public class BeanGoodsComment {
    private String goods_id;
    private String user_id;
    private String com_content;
    private Date com_date;
    private int com_level;
    private long com_picture;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
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
}
