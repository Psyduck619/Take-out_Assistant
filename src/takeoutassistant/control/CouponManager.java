package takeoutassistant.control;

import takeoutassistant.itf.ICouponManager;
import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanGoodsType;
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

public class CouponManager implements ICouponManager {


    //�����Ż�ȯ����
    public void addCoupon(BeanSeller seller, int amount, int request, Date begin, Date end) throws BaseException{
        //�ж��Żݽ���Ƿ����
        if(amount < 0) {
            throw new BusinessException("�Żݽ��������0");
        }
        //�жϼ������Ƿ����
        if(request < 0){
            throw new BusinessException("�������������0");
        }
        //�ж�ʱ���Ƿ����
        if(begin.getTime() > end.getTime()){
            throw new BusinessException("��ʼʱ���޷����ڽ���ʱ��");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ���Ż�ȯ���ӵ����ݿ�
            sql = "insert into tbl_coupon(seller_id, coupon_amount, coupon_request, begin_date, end_date) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            pst.setInt(2, amount);
            pst.setInt(3, request);
            pst.setTimestamp(4, new java.sql.Timestamp(begin.getTime()));
            pst.setTimestamp(5, new java.sql.Timestamp(end.getTime()));
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
    //��ʾ�����Ż�ȯ����
    public List<BeanCoupon> loadCoupon(BeanSeller seller) throws BaseException{
        //��ʼ��
        List<BeanCoupon> result = new ArrayList<BeanCoupon>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����ʾȫ���Ż�ȯ����
            sql = "select coupon_id,coupon_amount,coupon_request,begin_date,end_date from tbl_coupon where seller_id=? order by coupon_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanCoupon bc = new BeanCoupon();
                bc.setCoupon_id(rs.getInt(1));
                bc.setSeller_id(seller.getSeller_id());
                bc.setCoupon_amount(rs.getInt(2));
                bc.setCoupon_request(rs.getInt(3));
                bc.setBegin_date(rs.getTimestamp(4));
                bc.setEnd_date(rs.getTimestamp(5));
                result.add(bc);
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
    //ɾ���Ż�ȯ
    public void deleteCoupon(BeanCoupon coupon) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�ж��Ƿ����û�������Ż�ȯ,����,���޷�ɾ��
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
            sql = "delete from tbl_coupon where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, coupon.getCoupon_id());
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
    //�޸��Ż�ȯ
    public void modifyCoupon(BeanCoupon coupon, int amount, int request, Date begin, Date end) throws BaseException{

        if(amount < 0) {//�ж��Żݽ���Ƿ����
            throw new BusinessException("�Żݽ��������0");
        }
        if(request < 0){//�жϼ������Ƿ����
            throw new BusinessException("�������������0");
        }
        if(begin.getTime() > end.getTime()){//�ж�ʱ���Ƿ����
            throw new BusinessException("��ʼʱ���޷����ڽ���ʱ��");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����Ʒ����޸�
            sql = "update tbl_coupon set coupon_amount=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, amount);
            sql = "update tbl_coupon set coupon_request=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, request);
            sql = "update tbl_coupon set begin=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(begin.getTime()));
            sql = "update tbl_coupon set end=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(end.getTime()));
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