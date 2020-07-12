package takeoutassistant.control;

import takeoutassistant.itf.IRiderAccountManager;
import takeoutassistant.model.*;
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

    //增加入账单//订单产生时生成入帐单
    public void addRiderAccount(BeanRider rider, int order_id) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现入帐单添加到数据库
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
    //显示骑手所有入帐单
    public List<BeanRiderAccount> loadAccount(BeanRider rider) throws BaseException{
        //初始化
        List<BeanRiderAccount> result = new ArrayList<BeanRiderAccount>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现显示全部入帐单功能
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
    //删除入帐单
    public void deleteRiderAccount(BeanRiderAccount riderAccount) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //若订单尚未完成,则无法删除
//            sql = "select quantity from tbl_goodstype where type_id=?";
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1,goodstype.getType_id());
//            rs = pst.executeQuery();
//            if(rs.next()){
//                if(rs.getInt(1) != 0){
//                    rs.close();
//                    pst.close();
//                    throw new BusinessException("该类别存在商品,无法删除");
//                }
//            }
            //实现删除
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
    //修改入账单的归属
    public void modifyRiderAccount(int riderid, BeanRiderAccount riderAccount) throws BaseException{
        //判空
        if(riderAccount == null){
            throw new BusinessException("入账单为空");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //判断是否是同一个人
            sql = "select rider_id from tbl_rideraccount where account_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, riderAccount.getAccount_id());
            rs = pst.executeQuery();
            if(rs.next()){
                if(rs.getInt(1) == riderid){
                    rs.close();
                    pst.close();
                    throw new BusinessException("该骑手即为原骑手");
                }
            }
            //判断骑手是否存在
            sql = "select * from tbl_rider where rider_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,riderid);
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("骑手不存在");
            }
            rs.close();
            pst.close();
            //实现商品类别修改
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
    //判断订单是否已评价骑手
    public boolean isComment(BeanGoodsOrder order) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //先查询是否已评价
            sql = "select order_comment from tbl_rideraccount where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,order.getOrder_id());
            rs = pst.executeQuery();
            if(rs.next()){
                if(!rs.getString(1).equals("未评价")){
                    rs.close();
                    pst.close();
                    return true;
                }
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
    //增加骑手评价
    public void addRiderComment(BeanGoodsOrder order, String comment) throws BaseException{
        //判空
        if(comment == null || "".equals(comment)){
            throw new BusinessException("评价不能为空");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //如果是好评,本单收入加0.5;差评减20 //更新入账表和骑手收入
            Double money = 0.0;
            if(comment.equals("好评"))
                money = 0.5;
            else if(comment.equals("差评"))
                money = -20.0;
            else
                money = 0.0;
            //实现更新评价到数据库
            sql = "update tbl_rideraccount set order_comment=?,per_income=per_income+? where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, comment);
            pst.setDouble(2, money);
            pst.setInt(3, order.getOrder_id());
            pst.execute();
            pst.close();
            //更新骑手收入(先查询出骑手ID)
            int riderID = 0;
            sql = "select rider_id from tbl_rideraccount where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,order.getOrder_id());
            rs = pst.executeQuery();
            if(rs.next()){
                riderID = rs.getInt(1);
            }
            pst.close();
            rs.close();
            sql = "update tbl_rider set month_income=month_income+? where rider_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1,money);
            pst.setInt(2,riderID);
            pst.execute();
            conn.commit();
            pst.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
    //显示用户的所有评价
    public List<BeanRiderAccount> loadUsers(BeanUser user) throws BaseException{
        //初始化
        List<BeanRiderAccount> result = new ArrayList<BeanRiderAccount>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //先查询出用户的订单号,然后再根据订单号查询对应的骑手评价
            sql = "select order_id from tbl_goodsorder where user_id=? and rider_id>=1";
            pst = conn.prepareStatement(sql);
            pst.setString(1,user.getUser_id());
            rs = pst.executeQuery();
            while(rs.next()){
                sql = "select account_id,order_id,finish_time,order_comment,per_income,rider_id from tbl_rideraccount where order_id=? order by account_id";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql);
                pst2.setInt(1, rs.getInt(1));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                while(rs2.next()){
                    BeanRiderAccount bra = new BeanRiderAccount();
                    bra.setAccount_id(rs2.getInt(1));
                    bra.setOrder_id(rs2.getInt(2));
                    bra.setFinish_time(rs2.getTimestamp(3));
                    bra.setOrder_comment(rs2.getString(4));
                    bra.setPer_income(rs2.getDouble(5));
                    bra.setRider_id(rs2.getInt(6));
                    result.add(bra);
                }
                rs2.close();
                pst2.close();
            }
            conn.commit();
            rs.close();
            pst.close();
            return result;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
