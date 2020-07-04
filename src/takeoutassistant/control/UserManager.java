package takeoutassistant.control;

import takeoutassistant.itf.IUserManager;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class UserManager implements IUserManager {

    //用户注册
    public BeanUser reg(String name, String pwd, String pwd2) throws BaseException {
        //判断账号是否符合要求
        if(name == null || "".equals(name)){
            throw new BusinessException("ID can't be null!");
        }
        if(name.length() > 20){
            throw new BusinessException("登录账号不能超过20字!");
        }
        //判断密码是否符合要求
        if(pwd == null || "".equals(pwd)){
            throw new BusinessException("Password can't be null!");
        }
        if(pwd.length() > 16){
            throw new BusinessException("登录密码不能超过16字!");
        }
        if(!pwd.equals(pwd2)){
            throw new BusinessException("The two passwords are different!");
        }
        //初始化
        Connection conn = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //判断账号是否已存在
            String sql = "select * from tbl_user where user_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(!rs.next()) throw new BusinessException("登录账号已经存在!");
            rs.close();
            pst.close();
            //实现注册
            sql = "insert into tbl_user(user_name,user_pwd) values(?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, pwd);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if(conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    //用户登录
    public BeanUser login(String name, String pwd) throws BaseException {

        return null;
    }

    //用户修改密码
    public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException {

    }

    //用户点单

}
