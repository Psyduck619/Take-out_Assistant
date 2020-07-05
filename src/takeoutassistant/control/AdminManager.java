package takeoutassistant.control;

import takeoutassistant.itf.IAdminManager;
import takeoutassistant.model.BeanAdmin;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AdminManager implements IAdminManager {

    //����Ա���
    @Override
    public BeanAdmin addAdmin(String name, String pwd, String pwd2) throws BaseException {
        Pattern p = null;
        //�ж��˺��Ƿ�Ϸ�
        if(name == null || "".equals(name)){
            throw new BusinessException("����Ա���Ʋ���Ϊ��~");
        }
        if(name.length() > 20){
            throw new BusinessException("����Ա���Ʋ��ܳ���20��~");
        }
        //�ж������Ƿ�Ϸ�
        if(pwd == null || "".equals(pwd)){
            throw new BusinessException("���벻��Ϊ��~");
        }
        if(pwd.length() > 16){
            throw new BusinessException("��¼���벻�ܳ���16��~");
        }
        if(!pwd.equals(pwd2)){
            throw new BusinessException("���벻��ͬ~");
        }
        //��ʼ��
        Connection conn = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //�ж��˺��Ƿ��Ѵ���
            String sql = "select * from tbl_admin where admin_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(rs.next()) throw new BusinessException("�ù���Ա�����Ѿ�����~");
            rs.close();
            pst.close();
            //ʵ�����
            sql = "insert into tbl_admin(admin_name,admin_pwd) values(?,?)";
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
                throw new BusinessException("��¼�˺Ų�����~");
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
                    throw new BusinessException("�������~");
                }
            }
            rs.close();
            pst.close();
            //���ص�ǰ��¼����Ա��Ϣ
            sql = "select admin_id from tbl_admin where admin_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            rs = pst.executeQuery();
            BeanAdmin ba = new BeanAdmin();
            if(rs.next()){
                ba.setAdmin_id(rs.getInt(1));
                ba.setAdmin_name(name);
                ba.setAdmin_pwd(pwd);
            }
            rs.close();
            pst.close();
            return ba;
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
    }

    //����Ա�޸�����
    @Override
    public void changePwd(BeanAdmin admin, String oldPwd, String newPwd, String newPwd2) throws BaseException {
        //�ж������Ƿ�Ϸ�
        if(oldPwd == null || "".equals(oldPwd) || newPwd == null || "".equals(newPwd) || newPwd2 == null || "".equals(newPwd2)){
            throw new BusinessException("���벻��Ϊ��~");
        }
        if(oldPwd.length() > 16 || newPwd.length() > 16 || newPwd2.length() > 16){
            throw new BusinessException("���벻�ܳ���16��~");
        }
        if(!newPwd.equals(newPwd2)){
            throw new BusinessException("�������벻��ͬ~");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //�ж������Ƿ���ȷ
            sql = "select admin_pwd from tbl_admin where admin_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,admin.getAdmin_name());
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("�������~");
            }
            rs.close();
            pst.close();
            //ʵ�������޸�
            sql = "update tbl_admin set admin_pwd=? where admin_name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,newPwd);
            pst.setString(2,admin.getAdmin_name());
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
    }

}
