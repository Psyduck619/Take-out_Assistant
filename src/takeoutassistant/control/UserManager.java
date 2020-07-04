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

    //�û�ע��
    public BeanUser reg(String name, String pwd, String pwd2) throws BaseException {
        //�ж��˺��Ƿ����Ҫ��
        if(name == null || "".equals(name)){
            throw new BusinessException("ID can't be null!");
        }
        if(name.length() > 20){
            throw new BusinessException("��¼�˺Ų��ܳ���20��!");
        }
        //�ж������Ƿ����Ҫ��
        if(pwd == null || "".equals(pwd)){
            throw new BusinessException("Password can't be null!");
        }
        if(pwd.length() > 16){
            throw new BusinessException("��¼���벻�ܳ���16��!");
        }
        if(!pwd.equals(pwd2)){
            throw new BusinessException("The two passwords are different!");
        }
        //��ʼ��
        Connection conn = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //�ж��˺��Ƿ��Ѵ���
            String sql = "select * from tbl_user where user_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(!rs.next()) throw new BusinessException("��¼�˺��Ѿ�����!");
            rs.close();
            pst.close();
            //ʵ��ע��
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

    //�û���¼
    public BeanUser login(String name, String pwd) throws BaseException {

        return null;
    }

    //�û��޸�����
    public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException {

    }

    //�û��㵥

}
