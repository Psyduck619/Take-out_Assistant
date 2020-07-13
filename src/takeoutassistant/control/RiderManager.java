package takeoutassistant.control;

import takeoutassistant.itf.IRiderManager;
import takeoutassistant.model.BeanRider;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class RiderManager implements IRiderManager {

    //��������
    @Override
    public BeanRider addRider(String name, Date date, String status) throws BaseException {
        //�ж������Ƿ�Ϸ�
        if(name == null || "".equals(name)){
            throw new BusinessException("���ֲ���Ϊ��!");
        }
        if(name.length() > 20){
            throw new BusinessException("���ֲ��ܳ���20��!");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //ʵ���������
            sql = "insert into tbl_rider(rider_name,entry_date,rider_status,order_count,month_income) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
            pst.setString(3, status);
            pst.setInt(4, 0);
            pst.setDouble(5, 0);
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
    //��ʾ��������
    @Override
    public List<BeanRider> loadAll() throws BaseException {
        //��ʼ��
        List<BeanRider> result = new ArrayList<BeanRider>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�ִ����ݿ��������������Ϣ
            sql = "select rider_id,rider_name,entry_date,rider_status,order_count,month_income from tbl_rider";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                BeanRider br = new BeanRider();
                br.setRider_id(rs.getInt(1));
                br.setRider_name(rs.getString(2));
                br.setEntry_date(rs.getTimestamp(3));
                br.setRider_status(rs.getString(4));
                br.setOrder_count(rs.getInt(5));
                br.setMonth_income(rs.getDouble(6));
                result.add(br);
            }
            rs.close();
            pst.close();
            return result;
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
    //ɾ������
    @Override
    public void deleteRider(BeanRider rider) throws BaseException {
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�ִ����ݿ���ɾ��������Ϣ
            sql = "delete from tbl_rider where rider_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rider.getRider_id());
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
    //�޸��������
    @Override
    public void modifyStatus(BeanRider rider, String status) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�����ݿ����޸��������
            sql = "update tbl_rider set rider_status=? where rider_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,status);
            pst.setInt(2,rider.getRider_id());
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
    //�ж������Ƿ��͹���,���͹�,���޷�ɾ��
    public boolean ifHavingOrder(BeanRider rider) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            sql = "select * from tbl_goodsorder where rider_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,rider.getRider_id());
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
