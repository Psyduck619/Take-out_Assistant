package takeoutassistant.itf;

import takeoutassistant.model.BeanAddress;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IAddressManager {

    //�û�������ַ
    public void addAddress(BeanUser user, String name, String linkman, String linkphone) throws BaseException;
    //�û���ʾ���е�ַ
    public List<BeanAddress> loadAddress(BeanUser user) throws BaseException;
    //�û�ɾ����ַ
    public void deleteAddress(BeanAddress address) throws BaseException;
    //�û��޸ĵ�ַ
    public void modifyAddress(BeanAddress address, String name, String linkman, String linkphone) throws BaseException;
}
