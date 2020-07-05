package takeoutassistant.itf;

import takeoutassistant.model.BeanAdmin;
import takeoutassistant.util.BaseException;

public interface IAdminManager {
    //����Աע��
    public BeanAdmin addAdmin(String name, String pwd, String pwd2) throws BaseException;
    //����Ա��¼
    public BeanAdmin login(String name, String pwd) throws BaseException;
    //����Ա�޸�����
    public void changePwd(BeanAdmin admin, String oldPwd, String newPwd, String newPwd2) throws BaseException;
}
