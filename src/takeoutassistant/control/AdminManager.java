package takeoutassistant.control;

import takeoutassistant.itf.IAdminManager;
import takeoutassistant.model.BeanAdmin;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminManager implements IAdminManager {

    //管理员注册
//    @Override
//    public BeanAdmin reg(String name, String pwd, String pwd2) throws BaseException {
//        return null;
//    }

    //管理员登录
    @Override
    public BeanAdmin login(String name, String pwd) throws BaseException {
        //初始化
        Connection conn = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //判断账号是否存在
            String sql = "select * from tbl_admin where admin_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("登录账号不存在!");
            }
            rs.close();
            pst.close();
            //判断密码正确性
            sql = "select admin_pwd from tbl_admin where admin_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(rs.next()){
                if(!pwd.equals(rs.getString(1))){
                    rs.close();
                    pst.close();
                    throw new BusinessException("The password is wrong!");
                }
            }
            rs.close();
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

    //管理员修改密码
    @Override
    public void changePwd(BeanAdmin admin, String oldPwd, String newPwd, String newPwd2) throws BaseException {

    }

}
