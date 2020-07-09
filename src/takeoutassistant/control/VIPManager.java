package takeoutassistant.control;

import takeoutassistant.itf.IVIPManager;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Pattern;

public class VIPManager implements IVIPManager {

    //开通会员(一个月按30天算)//3个月九折//12个月8折
    public double openVIP(BeanUser user, int month) throws BaseException{
        //判断是否已是会员
        if(user.isVIP()){
            throw new BusinessException("您已是尊贵的会员用户");
        }
        //判空
        if(user == null){
            throw new BusinessException("用户为空,请重试");
        }
        //判断月数是否合理
        if(month < 0){
            throw new BusinessException("请输入合理的数量");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        double money = 10 * month;
        if(month == 3)
            money = money * 0.9;
        else if(month == 12)
            money = money * 0.8;
        try {
            conn = DBUtil.getConnection();
            //更新会员信息
            sql = "update tbl_user set VIP=?,VIP_end_time=? where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setBoolean(1, true);
            pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis() + (3600 * 24 * 30 * month * 1000L)));
            pst.setString(3, user.getUser_id());
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
        return money;
    }
    //续费会员(一个月按30天算)//3个月九折//12个月8折
    public double renewVIP(BeanUser user, int month) throws BaseException{
        //判断是否已是会员
        if(!user.isVIP()){
            throw new BusinessException("您还不是会员,请先选择开通会员");
        }
        //判空
        if(user == null){
            throw new BusinessException("用户为空,请重试");
        }
        //判断月数是否合理
        if(month < 0){
            throw new BusinessException("请输入合理的数量");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        double money = 10 * month;
        if(month == 3)
            money = money * 0.9;
        else if(month == 12)
            money = money * 0.8;
        try {
            conn = DBUtil.getConnection();
            //得到目前的会员到期时间
            long d = 0;
            sql = "select VIP_end_time from tbl_user where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            rs = pst.executeQuery();
            if(rs.next()){
                d = rs.getTimestamp(1).getTime();
            }
            //更新会员信息
            sql = "update tbl_user set VIP_end_time=? where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(d + (3600 * 24 * 30 * month * 1000L)));
            pst.setString(2, user.getUser_id());
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
        return money;
    }

}
