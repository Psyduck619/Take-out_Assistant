package takeoutassistant.itf;

import takeoutassistant.model.BeanGoodsOrder;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

public interface IUserManager {
    //用户注册
    public BeanUser reg(String userid, String name, String gender, String phone, String email, String city, String pwd, String pwd2) throws BaseException;
    //用户登录
    public BeanUser login(String userid, String pwd) throws BaseException;
    //用户修改密码
    public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException;
    //用户点单
}
