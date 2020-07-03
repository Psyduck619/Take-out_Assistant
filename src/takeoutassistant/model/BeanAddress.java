package takeoutassistant.model;

public class BeanAddress {
    private String add_id;
    private String user_id;
    private String address;
    private String linkMan;
    private int linkPhone;

    public String getAdd_id() {
        return add_id;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public int getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(int linkPhone) {
        this.linkPhone = linkPhone;
    }
}
