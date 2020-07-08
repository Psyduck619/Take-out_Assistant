package takeoutassistant.itf;

import takeoutassistant.model.BeanAddress;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IAddressManager {

    //用户新增地址
    public void addAddress(BeanUser user, String name, String linkman, String linkphone) throws BaseException;
    //用户显示所有地址
    public List<BeanAddress> loadAddress(BeanUser user) throws BaseException;
    //用户删除地址
    public void deleteAddress(BeanAddress address) throws BaseException;
    //用户修改地址
    public void modifyAddress(BeanAddress address, String name, String linkman, String linkphone) throws BaseException;
}
