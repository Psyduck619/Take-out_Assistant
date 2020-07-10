package takeoutassistant.control;

import takeoutassistant.itf.IMyCouponManager;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyCouponManager implements IMyCouponManager {

    //�����Ż�ȯ
    public BeanMyCoupon addMyCoupon(BeanUser user, BeanCoupon coupon) throws BaseException{
        //�п�
        if(user == null){
            throw new BusinessException("�û�Ϊ��,������");
        }
        if(coupon == null){
            throw new BusinessException("�Ż�ȯΪ��,������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //��ѯ�Ƿ����и��Ż�ȯ
            boolean flag = false;
            sql = "select * from tbl_mycoupon where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
            rs = pst.executeQuery();
            if(rs.next()){
                flag = true;
            }
            rs.close();
            pst.close();
            //�����и��Ż�ȯ,�Ż�ȯ+1
            if(flag){
                sql = "update tbl_mycoupon set coupon_count=coupon_count+1 where coupon_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, coupon.getCoupon_id());
                pst.execute();
                pst.close();
            }
            else{
                //�Ȳ�ѯ�Ż�ȯ�����̼�����
                sql = "select a.seller_name from tbl_seller a,tbl_coupon b where a.seller_id=b.seller_id and b.coupon_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,coupon.getCoupon_id());
                rs = pst.executeQuery();
                String name =null;
                if(rs.next()){
                    name = rs.getString(1);
                }
                rs.close();
                pst.close();
                //����ҵ��Ż�ȯ
                sql = "insert into tbl_mycoupon(user_id,coupon_id,coupon_amount,coupon_count,end_date,seller_name,ifTogether) values(?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, user.getUser_id());
                pst.setInt(2, coupon.getCoupon_id());
                pst.setInt(3, coupon.getCoupon_amount());
                pst.setInt(4,1);
                pst.setTimestamp(5, new java.sql.Timestamp(coupon.getEnd_date().getTime()));
                pst.setString(6,name);
                pst.setBoolean(7, coupon.isIfTogether());
                conn.commit();
                pst.execute();
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
    //��ʾ�����Ż�ȯ
    public List<BeanMyCoupon> loadMyCoupon(BeanUser user) throws BaseException{
        //��ʼ��
        List<BeanMyCoupon> result = new ArrayList<BeanMyCoupon>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //��ʾ�����ҵ��Ż�ȯ
            sql = "select user_id,coupon_id,coupon_amount,coupon_count,end_date,seller_name,ifTogether from tbl_mycoupon where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanMyCoupon bgc = new BeanMyCoupon();
                bgc.setUser_id(rs.getString(1));
                bgc.setCoupon_id(rs.getInt(2));
                bgc.setCoupon_amount(rs.getInt(3));
                bgc.setCoupon_count(rs.getInt(4));
                bgc.setEnd_date(rs.getTimestamp(5));
                bgc.setSeller_name(rs.getString(6));
                bgc.setIfTogether(rs.getBoolean(7));
                result.add(bgc);
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
    //��ʾ�ҵ�ָ���̼ҵ��Ż�ȯ
    public List<BeanMyCoupon> loadMyCoupon2(BeanUser user, BeanSeller seller) throws BaseException{
        //��ʼ��
        List<BeanMyCoupon> result = new ArrayList<BeanMyCoupon>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //��ʾ�����ҵ��Ż�ȯ
            sql = "select a.user_id,a.coupon_id,a.coupon_amount,a.coupon_count,a.end_date,a.seller_name,a.ifTogether" +
                    " from tbl_mycoupon a,tbl_coupon b where a.coupon_id=b.coupon_id and user_id=? and seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            pst.setInt(2, seller.getSeller_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanMyCoupon bgc = new BeanMyCoupon();
                bgc.setUser_id(rs.getString(1));
                bgc.setCoupon_id(rs.getInt(2));
                bgc.setCoupon_amount(rs.getInt(3));
                bgc.setCoupon_count(rs.getInt(4));
                bgc.setEnd_date(rs.getTimestamp(5));
                bgc.setSeller_name(rs.getString(6));
                bgc.setIfTogether(rs.getBoolean(7));
                result.add(bgc);
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

}
