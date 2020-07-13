package takeoutassistant.control;

import takeoutassistant.itf.IAddressManager;
import takeoutassistant.model.BeanAddress;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddressManager implements IAddressManager {

    //�û�������ַ
    public void addAddress(BeanUser user, String name, String linkman, String linkphone) throws BaseException{
        //�жϵ�ַ�����Ƿ����
        if(name == null || "".equals(name)){
            throw new BusinessException("��ַ���Ʋ���Ϊ��");
        }
        if(name.length() > 20){
            throw new BusinessException("��ַ���Ʋ��ܳ���50��");
        }
        //�ж���ϵ���Ƿ����
        if(linkman == null || "".equals(linkman)){
            throw new BusinessException("��ϵ�˲���Ϊ��");
        }
        if(linkman.length() > 20){
            throw new BusinessException("��ϵ�����ֲ��ܳ���20��");
        }
        //�ж���ϵ�绰�Ƿ����
        Pattern p = Pattern.compile("[0-9]*");
        if(!p.matcher(linkphone).matches()){
            throw new BusinessException("��ϵ�绰ֻ��Ϊ����");
        }
        if(linkphone.length() != 11 && linkphone.length() != 8){
            throw new BusinessException("��ϵ�绰����Ϊ8λ��11λ");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //��ַ��ӵ����ݿ�
            sql = "insert into tbl_address(user_id,address_name,linkman,linkphone) values(?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            pst.setString(2,name);
            pst.setString(3,linkman);
            pst.setString(4,linkphone);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //�û���ʾ���е�ַ
    public List<BeanAddress> loadAddress(BeanUser user) throws BaseException{
        //��ʼ��
        List<BeanAddress> result = new ArrayList<BeanAddress>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //��ʾ�û����е�ַ
            sql = "select add_id,address_name,linkman,linkphone from tbl_address where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanAddress ba = new BeanAddress();
                ba.setAdd_id(rs.getInt(1));
                ba.setUser_id(user.getUser_id());
                ba.setAddress_name(rs.getString(2));
                ba.setLinkMan(rs.getString(3));
                ba.setLinkPhone(rs.getString(4));
                result.add(ba);
            }
            rs.close();
            pst.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
    //�û�ɾ����ַ
    public void deleteAddress(BeanAddress address) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��ɾ��
            sql = "delete from tbl_address where add_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, address.getAdd_id());
            pst.execute();
            pst.close();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //�û��޸ĵ�ַ
    public void modifyAddress(BeanAddress address, String name, String linkman, String linkphone) throws BaseException{
        //�жϵ�ַ�����Ƿ����
        if(name == null || "".equals(name)){
            throw new BusinessException("��ַ���Ʋ���Ϊ��");
        }
        if(name.length() > 20){
            throw new BusinessException("��ַ���Ʋ��ܳ���50��");
        }
        //�ж���ϵ���Ƿ����
        if(linkman == null || "".equals(linkman)){
            throw new BusinessException("��ϵ�˲���Ϊ��");
        }
        if(linkman.length() > 20){
            throw new BusinessException("��ϵ�����ֲ��ܳ���20��");
        }
        //�ж���ϵ�绰�Ƿ����
        Pattern p = Pattern.compile("[0-9]*");
        if(!p.matcher(linkphone).matches()){
            throw new BusinessException("��ϵ�绰ֻ��Ϊ����");
        }
        if(linkphone.length() != 11 && linkphone.length() != 8){
            throw new BusinessException("��ϵ�绰����Ϊ8λ��11λ");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�ֵ�ַ�޸�
            sql = "update tbl_address set address_name=?,linkman=?,linkphone=? where add_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, linkman);
            pst.setString(3, linkphone);
            pst.setInt(4, address.getAdd_id());
            pst.execute();
            pst.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //�жϵ�ַ�Ƿ����
    public boolean ifHavingOrder(BeanAddress address) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            sql = "select * from tbl_goodsorder where add_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,address.getAdd_id());
            rs = pst.executeQuery();
            if(rs.next()){
                return true;
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
