package takeoutassistant.itf;

import takeoutassistant.model.BeanAdmin;
import takeoutassistant.util.BaseException;

public interface IAdminManager {
    //管理员注册
    //public BeanAdmin reg(String name, String pwd, String pwd2) throws BaseException;
    //管理员登录
    public BeanAdmin login(String name, String pwd) throws BaseException;
    //管理员修改密码
    public void changePwd(BeanAdmin admin, String oldPwd, String newPwd, String newPwd2) throws BaseException;
}
