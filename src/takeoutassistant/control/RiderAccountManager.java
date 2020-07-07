package takeoutassistant.control;

import takeoutassistant.itf.IRiderAccountManager;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanRider;
import takeoutassistant.model.BeanRiderAccount;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RiderAccountManager implements IRiderAccountManager {

    //�������˵�//��������ʱ�������ʵ�
    public void addRiderAccount(BeanRider rider, int order_id) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�����ʵ���ӵ����ݿ�
            sql = "insert into tbl_rideraccount(rider_id,order_id) values(?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rider.getRider_id());
            pst.setInt(2, order_id);
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
    //��ʾ�����������ʵ�
    public List<BeanRiderAccount> loadAccount(BeanRider rider) throws BaseException{
        //��ʼ��
        List<BeanRiderAccount> result = new ArrayList<BeanRiderAccount>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����ʾȫ�����ʵ�����
            sql = "select account_id,order_id,finish_time,order_comment,per_income from tbl_rideraccount where rider_id=? order by account_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rider.getRider_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanRiderAccount bra = new BeanRiderAccount();
                bra.setAccount_id(rs.getInt(1));
                bra.setOrder_id((rs.getInt(2)));
                bra.setFinish_time(rs.getTimestamp(3));
                bra.setOrder_comment(rs.getString(4));
                bra.setPer_income(rs.getDouble(5));
                result.add(bra);
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
    //ɾ�����ʵ�
    public void deleteRiderAccount(BeanRiderAccount riderAccount) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //��������δ���,���޷�ɾ��
//            sql = "select quantity from tbl_goodstype where type_id=?";
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1,goodstype.getType_id());
//            rs = pst.executeQuery();
//            if(rs.next()){
//                if(rs.getInt(1) != 0){
//                    rs.close();
//                    pst.close();
//                    throw new BusinessException("����������Ʒ,�޷�ɾ��");
//                }
//            }
            //ʵ��ɾ��
            sql = "delete from tbl_rideraccount where account_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, riderAccount.getAccount_id());
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
    //�޸����˵��Ĺ���
    public void modifyRiderAccount(int riderid, BeanRiderAccount riderAccount) throws BaseException{
        //�п�
        if(riderAccount == null){
            throw new BusinessException("���˵�Ϊ��");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�ж��Ƿ���ͬһ����
            sql = "select rider_id from tbl_rideraccount where account_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, riderAccount.getAccount_id());
            rs = pst.executeQuery();
            if(rs.next()){
                if(rs.getInt(1) == riderid){
                    rs.close();
                    pst.close();
                    throw new BusinessException("�����ּ�Ϊԭ����");
                }
            }
            //�ж������Ƿ����
            sql = "select * from tbl_rider where rider_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,riderid);
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("���ֲ�����");
            }
            rs.close();
            pst.close();
            //ʵ����Ʒ����޸�
            sql = "update tbl_rideraccount set rider_id=? where account_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, riderid);
            pst.setInt(2, riderAccount.getAccount_id());
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

}
