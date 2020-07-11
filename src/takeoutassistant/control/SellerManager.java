package takeoutassistant.control;

import takeoutassistant.itf.ISellerManager;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerManager implements ISellerManager {

    //����̼�
    @Override
    public BeanSeller addSeller(String name) throws BaseException {
        //�ж��̼������Ƿ����
        if(name == null || "".equals(name)){
            throw new BusinessException("�̼����ֲ���Ϊ��!");
        }
        if(name.length() > 20){
            throw new BusinessException("�̼����ֲ��ܳ���20��");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ���̼���ӵ����ݿ�
            sql = "insert into tbl_seller(seller_name,seller_level,total_sales) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setDouble(2,0);
            pst.setInt(3,0);
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
        return null;
    }

    //��ʾ�����̼�
    @Override
    public List<BeanSeller> loadAll() throws BaseException {
        //��ʼ��
        List<BeanSeller> result = new ArrayList<BeanSeller>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�ִ����ݿ���������̼���Ϣ
            sql = "select seller_id,seller_name,seller_level,per_cost,total_sales from tbl_seller";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                BeanSeller bs = new BeanSeller();
                bs.setSeller_id(rs.getInt(1));
                bs.setSeller_name(rs.getString(2));
                bs.setSeller_level(rs.getDouble(3));
                bs.setPer_cost(rs.getDouble(4));
                bs.setTotal_sales(rs.getInt(5));
                result.add(bs);
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
        return result;
    }

    //ɾ���̼�
    @Override
    public void deleteSeller(BeanSeller seller) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�ж��Ƿ�ѡ��ĳ�̼�
            sql = "select * from tbl_seller where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("����ѡ���̼�~");
            }
            rs.close();
            pst.close();
            //ʵ�ִ����ݿ�ɾ��
            sql = "delete from tbl_seller where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
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

    //�޸��̼�����
    @Override
    public void modifyName(BeanSeller seller, String name) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�ж��Ƿ�ѡ��ĳ�̼�
            sql = "select * from tbl_seller where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,seller.getSeller_id());
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("����ѡ���̼�~");
            }
            rs.close();
            pst.close();
            //�ж��̼������Ƿ�Ϸ�
            if(name == null || "".equals(name)){
                throw new BusinessException("�̼����ֲ���Ϊ��~");
            }
            if(name.length() > 20){
                throw new BusinessException("�̼����ֲ��ܳ���20��~");
            }
            //ʵ�����ݿ����޸�����
            sql = "update tbl_seller set seller_name=? where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setInt(2,seller.getSeller_id());
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

    //��ʾĳ�Ǽ��̼�
    @Override
    public List<BeanSeller> loadLevel(int level) throws BaseException {
        if(level < 0 || level > 5){
            throw new BusinessException("��ѡ����ȷ���Ǽ�~");
        }
        //��ʼ��
        List<BeanSeller> result = new ArrayList<BeanSeller>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�ִ����ݿ���������̼���Ϣ
            if(level == 0){//��ʾ������Ϣ
                sql = "select seller_id,seller_name,seller_level,per_cost,total_sales from tbl_seller";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    BeanSeller bs = new BeanSeller();
                    bs.setSeller_id(rs.getInt(1));
                    bs.setSeller_name(rs.getString(2));
                    bs.setSeller_level(rs.getDouble(3));
                    bs.setPer_cost(rs.getDouble(4));
                    bs.setTotal_sales(rs.getInt(5));
                    result.add(bs);
                }
                rs.close();
                pst.close();
            }
            else{//��ʾָ���Ǽ��̼�
                sql = "select seller_id,seller_name,seller_level,per_cost,total_sales from tbl_seller where seller_level=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,level);
                rs = pst.executeQuery();
                while(rs.next()){
                    BeanSeller bs = new BeanSeller();
                    bs.setSeller_id(rs.getInt(1));
                    bs.setSeller_name(rs.getString(2));
                    bs.setSeller_level(rs.getDouble(3));
                    bs.setPer_cost(rs.getDouble(4));
                    bs.setTotal_sales(rs.getInt(5));
                    result.add(bs);
                }
                rs.close();
                pst.close();
            }
        } catch (SQLException e) {
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
        return result;
    }

    //�����̼ұ�ŵõ��̼���
    public String getSellerName(int seller) throws BaseException{
        //�ж��̼�ID�Ƿ����
        if(seller <= 0){
            throw new BusinessException("�̼�Ϊ��");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //��ѯ�̼���
            String name = null;
            sql = "select seller_name from tbl_seller where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,seller);
            rs = pst.executeQuery();
            if(rs.next()){
                name = rs.getString(1);
                rs.close();
                pst.close();
                return name;
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
        return null;
    }

}
