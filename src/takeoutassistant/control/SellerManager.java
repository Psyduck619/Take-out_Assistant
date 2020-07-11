package takeoutassistant.control;

import takeoutassistant.itf.ISellerManager;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerManager implements ISellerManager {

    //添加商家
    @Override
    public BeanSeller addSeller(String name) throws BaseException {
        //判断商家名字是否合理
        if(name == null || "".equals(name)){
            throw new BusinessException("商家名字不能为空!");
        }
        if(name.length() > 20){
            throw new BusinessException("商家名字不能超过20字");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现商家添加到数据库
            sql = "insert into tbl_seller(seller_name,seller_level,total_sales) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setDouble(2,0);
            pst.setInt(3,0);
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
        return null;
    }

    //显示所有商家
    @Override
    public List<BeanSeller> loadAll() throws BaseException {
        //初始化
        List<BeanSeller> result = new ArrayList<BeanSeller>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现从数据库查找所有商家信息
            sql = "select seller_id,seller_name,seller_level,per_cost,total_sales from tbl_seller";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                BeanSeller bs = new BeanSeller();
                bs.setSeller_id(rs.getInt(1));
                bs.setSeller_name(rs.getString(2));
                bs.setSeller_level(rs.getDouble(3));
                bs.setPer_cost(rs.getDouble(4));
                bs.setTotal_sales(rs.getInt(5));
                result.add(bs);
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
        return result;
    }

    //删除商家
    @Override
    public void deleteSeller(BeanSeller seller) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //判断是否选中某商家
            sql = "select * from tbl_seller where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("请先选中商家~");
            }
            rs.close();
            pst.close();
            //实现从数据库删除
            sql = "delete from tbl_seller where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
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

    //修改商家名字
    @Override
    public void modifyName(BeanSeller seller, String name) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //判断是否选中某商家
            sql = "select * from tbl_seller where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,seller.getSeller_id());
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("请先选中商家~");
            }
            rs.close();
            pst.close();
            //判断商家名字是否合法
            if(name == null || "".equals(name)){
                throw new BusinessException("商家名字不能为空~");
            }
            if(name.length() > 20){
                throw new BusinessException("商家名字不能超过20字~");
            }
            //实现数据库中修改名字
            sql = "update tbl_seller set seller_name=? where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setInt(2,seller.getSeller_id());
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

    //显示某星级商家
    @Override
    public List<BeanSeller> loadLevel(int level) throws BaseException {
        if(level < 0 || level > 5){
            throw new BusinessException("请选择正确的星级~");
        }
        //初始化
        List<BeanSeller> result = new ArrayList<BeanSeller>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现从数据库查找所需商家信息
            if(level == 0){//显示所有信息
                sql = "select seller_id,seller_name,seller_level,per_cost,total_sales from tbl_seller";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    BeanSeller bs = new BeanSeller();
                    bs.setSeller_id(rs.getInt(1));
                    bs.setSeller_name(rs.getString(2));
                    bs.setSeller_level(rs.getDouble(3));
                    bs.setPer_cost(rs.getDouble(4));
                    bs.setTotal_sales(rs.getInt(5));
                    result.add(bs);
                }
                rs.close();
                pst.close();
            }
            else{//显示指定星级商家
                sql = "select seller_id,seller_name,seller_level,per_cost,total_sales from tbl_seller where seller_level=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,level);
                rs = pst.executeQuery();
                while(rs.next()){
                    BeanSeller bs = new BeanSeller();
                    bs.setSeller_id(rs.getInt(1));
                    bs.setSeller_name(rs.getString(2));
                    bs.setSeller_level(rs.getDouble(3));
                    bs.setPer_cost(rs.getDouble(4));
                    bs.setTotal_sales(rs.getInt(5));
                    result.add(bs);
                }
                rs.close();
                pst.close();
            }
        } catch (SQLException e) {
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
        return result;
    }

    //根据商家编号得到商家名
    public String getSellerName(int seller) throws BaseException{
        //判断商家ID是否合理
        if(seller <= 0){
            throw new BusinessException("商家为空");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //查询商家名
            String name = null;
            sql = "select seller_name from tbl_seller where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,seller);
            rs = pst.executeQuery();
            if(rs.next()){
                name = rs.getString(1);
                rs.close();
                pst.close();
                return name;
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
        return null;
    }

}
