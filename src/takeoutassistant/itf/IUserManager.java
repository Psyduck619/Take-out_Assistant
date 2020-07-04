package takeoutassistant.itf;

import takeoutassistant.model.BeanGoodsOrder;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

public interface IUserManager {
    //�û�ע��
    public BeanUser reg(String name, String pwd, String pwd2) throws BaseException;
    //�û���¼
    public BeanUser login(String name, String pwd) throws BaseException;
    //�û��޸�����
    public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException;
    //�û��㵥
}
