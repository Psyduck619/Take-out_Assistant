package takeoutassistant.control;

import takeoutassistant.itf.IManjianManager;
import takeoutassistant.model.BeanManjian;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManjianManager implements IManjianManager {

    //增加满减种类
    public void addManjian(BeanSeller seller, int full, int discount, boolean together) throws BaseException{
        //满减是否合法
        if(full < discount){
            throw new BusinessException("满足金额不能小于优惠金额");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现类别添加到数据库
            sql = "insert into tbl_manjian(seller_id,manjian_amount,discount_amount,ifTogether) values(?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,seller.getSeller_id());
            pst.setInt(2,full);
            pst.setInt(3,discount);
            pst.setBoolean(4,together);
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
    //显示所有满减种类
    public List<BeanManjian> loadManjian(BeanSeller seller) throws BaseException{
        //初始化
        List<BeanManjian> result = new ArrayList<BeanManjian>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现显示全部满减功能
            sql = "select manjian_id, manjian_amount, discount_amount, ifTogether from tbl_manjian where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanManjian bm = new BeanManjian();
                bm.setManjian_id(rs.getInt(1));
                bm.setSeller_id(seller.getSeller_id());
                bm.setManjian_amount(rs.getInt(2));
                bm.setDiscount_amount(rs.getInt(3));
                bm.setIfTogether(rs.getBoolean(4));
                result.add(bm);
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
    //删除满减种类
    public void deleteManjian(BeanManjian manjian) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现删除
            sql = "delete from tbl_manjian where manjian_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, manjian.getManjian_id());
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
    //修改满减
    public void modifyManjian(BeanManjian manjian, int full, int discount, boolean together) throws BaseException{
        //判断满减是否合理
        if(full < discount) {
            throw new BusinessException("满足金额不能小于优惠金额");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现满减修改
            sql = "update tbl_manjian set manjian_amount=? where manjian_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, full);
            sql = "update tbl_manjian set discount_amount=? where manjian_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, discount);
            sql = "update tbl_manjian set ifTogether=? where manjian_id=?";
            pst = conn.prepareStatement(sql);
            pst.setBoolean(1, together);
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
