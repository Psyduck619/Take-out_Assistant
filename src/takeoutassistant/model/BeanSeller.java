package takeoutassistant.model;

public class BeanSeller {
    public static final String[] tableTitles={"�̼����","�̼�����","�Ǽ�","ƽ������","�ܶ�����"};
    public static final String[] tableTitles2={"�̼�����","�Ǽ�","ƽ������","�ܶ�����"};
    private int seller_id;
    private String seller_name;
    private Double seller_level;
    private double per_cost;
    private int total_sales;

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

    public Double getSeller_level() {
        return seller_level;
    }

    public void setSeller_level(Double seller_level) {
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

    public String getCell(int col){
        if(col==0) return ""+this.seller_id;
        else if(col==1) return ""+this.seller_name;
        else if(col==2) return ""+this.seller_level;
        else if(col==3) return ""+this.per_cost;
        else if(col==4) return ""+this.total_sales;
        else return "";
    }

    public String getCell2(int col){
        if(col==0) return ""+this.seller_name;
        else if(col==1) return ""+this.seller_level;
        else if(col==2) return ""+this.per_cost;
        else if(col==3) return ""+this.total_sales;
        else return "";
    }
}
