package takeoutassistant.control;

import takeoutassistant.itf.IMyJidanManager;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyJidanManager implements IMyJidanManager {

    //更新集单信息
    public BeanUserJidan addMyJidan(BeanUser user, BeanSeller seller) throws BaseException{
        //判空
        if(user == null){
            throw new BusinessException("用户为空,请重试");
        }
        if(seller == null){
            throw new BusinessException("商家为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //查询是否已有集单信息
            boolean flag = false;
            sql = "select * from tbl_userjidan where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            rs = pst.executeQuery();
            if(rs.next()){
                flag = true;
            }
            rs.close();
            pst.close();
            //若已有该卖家的集单信息,对应集单数+1
            if(flag){
                sql = "update tbl_userjidan set coupon_get=coupon_get+1 where seller_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, seller.getSeller_id());
                pst.execute();
                pst.close();
                //如果达到集单数要求,则对应优惠券+1,并置该条集单数为0
            }
            else{
                //先查询得到商家的所有优惠券编号
                List<Integer> list = new ArrayList<>();
                sql = "select coupon_id from tbl_coupon where seller_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1 ,seller.getSeller_id());
                rs = pst.executeQuery();
                String name =null;
                while(rs.next()){
                    list.add(rs.getInt(1));
                }
                rs.close();
                pst.close();
                //添加所有商家的集单要求到集单表
                for(int i=0 ; i<list.size() ; i++){
                    //得到每个优惠券的集单需要
                    int request = 0;
                    sql = "select coupon_request from tbl_coupon where coupon_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, list.get(i));
                    rs = pst.executeQuery();
                    if(rs.next()){
                        request = rs.getInt(1);
                    }
                    rs.close();
                    pst.close();
                    //添加信息到数据库
                    sql = "insert into tbl_userjidan(user_id,seller_id,coupon_request,coupon_get,coupon_id) values(?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, user.getUser_id());
                    pst.setInt(2, seller.getSeller_id());
                    pst.setInt(3, request);
                    pst.setInt(4,1);
                    pst.setInt(5, list.get(i));
                    pst.execute();
                    pst.close();
                }
            }
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
    //显示所有集单情况
    public List<BeanUserJidan> loadMyJidan(BeanUser user) throws BaseException{

        //初始化
        List<BeanUserJidan> result = new ArrayList<BeanUserJidan>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //显示所有我的集单
            sql = "select user_id,seller_id,coupon_request,coupon_get,coupon_amount from tbl_userjidan where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanUserJidan buj = new BeanUserJidan();
                buj.setUser_id(rs.getString(1));
                buj.setSeller_id(rs.getInt(2));
                buj.setCoupon_request(rs.getInt(3));
                buj.setCoupon_get(rs.getInt(4));
                buj.setCoupon_amount(rs.getInt(5));
                result.add(buj);
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
