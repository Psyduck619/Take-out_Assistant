package takeoutassistant.itf;

import takeoutassistant.model.BeanGoodsOrder;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

public interface IUserManager {
    //�û�ע��
    public BeanUser reg(String userid, String name, String gender, String phone, String email, String city, String pwd, String pwd2) throws BaseException;
    //�û���¼
    public BeanUser login(String userid, String pwd) throws BaseException;
    //�û��޸�����
    public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException;
    //�û��㵥
}
