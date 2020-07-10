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

    //新增优惠券
    public BeanMyCoupon addMyCoupon(BeanUser user, BeanCoupon coupon) throws BaseException{
        //判空
        if(user == null){
            throw new BusinessException("用户为空,请重试");
        }
        if(coupon == null){
            throw new BusinessException("优惠券为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //查询是否已有该优惠券
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
            //若已有该优惠券,优惠券+1
            if(flag){
                sql = "update tbl_mycoupon set coupon_count=coupon_count+1 where coupon_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, coupon.getCoupon_id());
                pst.execute();
                pst.close();
            }
            else{
                //先查询优惠券所属商家名字
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
                //添加我的优惠券
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
    //显示所有优惠券
    public List<BeanMyCoupon> loadMyCoupon(BeanUser user) throws BaseException{
        //初始化
        List<BeanMyCoupon> result = new ArrayList<BeanMyCoupon>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //显示所有我的优惠券
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
    //显示我的指定商家的优惠券
    public List<BeanMyCoupon> loadMyCoupon2(BeanUser user, BeanSeller seller) throws BaseException{
        //初始化
        List<BeanMyCoupon> result = new ArrayList<BeanMyCoupon>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //显示所有我的优惠券
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
