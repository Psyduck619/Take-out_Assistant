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


    //增加优惠券种类
    public void addCoupon(BeanSeller seller, int amount, int request, Date begin, Date end, boolean together) throws BaseException{
        //判断优惠金额是否合理
        if(amount < 0) {
            throw new BusinessException("优惠金额必须大于0");
        }
        //判断集单数是否合理
        if(request < 0){
            throw new BusinessException("集单数必须大于0");
        }
        //判断时间是否合理
        if(begin.getTime() < System.currentTimeMillis()){
            throw new BusinessException("开始时间不能早于现在");
        }
        if(begin.getTime() > end.getTime()){
            throw new BusinessException("开始时间无法晚于结束时间");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现优惠券添加到数据库
            sql = "insert into tbl_coupon(seller_id, coupon_amount, coupon_request, begin_date, end_date, ifTogether) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            pst.setInt(2, amount);
            pst.setInt(3, request);
            pst.setTimestamp(4, new java.sql.Timestamp(begin.getTime()));
            pst.setTimestamp(5, new java.sql.Timestamp(end.getTime()));
            pst.setBoolean(6,together);
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
    //显示所有优惠券种类
    public List<BeanCoupon> loadCoupon(BeanSeller seller) throws BaseException{
        //初始化
        List<BeanCoupon> result = new ArrayList<BeanCoupon>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现显示全部优惠券功能
            sql = "select coupon_id,coupon_amount,coupon_request,begin_date,end_date,ifTogether from tbl_coupon where seller_id=? order by coupon_id";
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
                bc.setIfTogether(rs.getBoolean(6));
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
    //删除优惠券
    public void deleteCoupon(BeanCoupon coupon) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //判断是否有用户有这个优惠券,若有,则无法删除
            sql = "select * from tbl_mycoupon where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, coupon.getCoupon_id());
            rs = pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("仍有用户拥有该优惠券,无法删除");
            }
            rs.close();
            pst.close();
            //实现删除
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
    //修改优惠券
    public void modifyCoupon(BeanCoupon coupon, int amount, int request, Date begin, Date end, boolean together) throws BaseException{

        if(amount < 0) {//判断优惠金额是否合理
            throw new BusinessException("优惠金额必须大于0");
        }
        if(request < 0){//判断集单数是否合理
            throw new BusinessException("集单数必须大于0");
        }
        if(begin.getTime() > end.getTime()){//判断时间是否合理
            throw new BusinessException("开始时间无法晚于结束时间");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现商品类别修改
            sql = "update tbl_coupon set coupon_amount=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, amount);
            pst.setInt(2, coupon.getCoupon_id());
            pst.execute();
            sql = "update tbl_coupon set coupon_request=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, request);
            pst.setInt(2, coupon.getCoupon_id());
            pst.execute();
            sql = "update tbl_coupon set begin_date=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(begin.getTime()));
            pst.setInt(2, coupon.getCoupon_id());
            pst.execute();
            sql = "update tbl_coupon set end_date=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(end.getTime()));
            pst.setInt(2, coupon.getCoupon_id());
            pst.execute();
            pst.close();
            sql = "update tbl_coupon set ifTogether=? where coupon_id=?";
            pst = conn.prepareStatement(sql);
            pst.setBoolean(1, together);
            pst.setInt(2, coupon.getCoupon_id());
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
