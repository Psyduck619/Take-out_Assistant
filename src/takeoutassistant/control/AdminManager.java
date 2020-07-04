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

    //����Աע��
//    @Override
//    public BeanAdmin reg(String name, String pwd, String pwd2) throws BaseException {
//        return null;
//    }

    //����Ա��¼
    @Override
    public BeanAdmin login(String name, String pwd) throws BaseException {
        //��ʼ��
        Connection conn = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //�ж��˺��Ƿ����
            String sql = "select * from tbl_admin where admin_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("��¼�˺Ų�����!");
            }
            rs.close();
            pst.close();
            //�ж�������ȷ��
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

    //����Ա�޸�����
    @Override
    public void changePwd(BeanAdmin admin, String oldPwd, String newPwd, String newPwd2) throws BaseException {

    }

}
