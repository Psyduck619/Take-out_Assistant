package takeoutassistant.model;

public class BeanAddress {
    public static final String[] tblAddressTitle={"地址名称","联系人","联系电话"};
    private int add_id;
    private String user_id;
    private String address_name;
    private String linkMan;
    private String linkPhone;

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getCell(int col){
        if(col==0) return this.address_name;
        else if(col==1) return this.linkMan;
        else if(col==2) return this.linkPhone;
        else return "";
    }

}
