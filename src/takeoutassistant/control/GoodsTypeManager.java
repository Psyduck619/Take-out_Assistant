package takeoutassistant.control;

import takeoutassistant.itf.IGoodsTypeManager;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsTypeManager implements IGoodsTypeManager {

    //增加商品类别
    public void addGoodsType(BeanSeller seller, String name, int quantity) throws BaseException {
        //判断是否选择商家
        if(seller == null){
            throw new BusinessException("请选择一个商家");
        }
        //判断类别名字是否合理
        if(name == null || "".equals(name)){
            throw new BusinessException("类别名称不能为空");
        }
        if(name.length() > 20){
            throw new BusinessException("类别名称不能超过20字");
        }
        //判断数量是否合理
        if(quantity < 0){
            throw new BusinessException("商品数量不能小于0");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现类别添加到数据库
            sql = "insert into tbl_goodstype(seller_id,type_name,quantity) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,seller.getSeller_id());
            pst.setString(2,name);
            pst.setInt(3,quantity);
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

    //显示所有商品类别
    public List<BeanGoodsType> loadTypes(BeanSeller seller) throws BaseException {
        //初始化
        List<BeanGoodsType> result = new ArrayList<BeanGoodsType>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现显示全部类别功能
            sql = "select type_id,seller_id,type_name,quantity from tbl_goodstype where seller_id=? order by type_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsType gt = new BeanGoodsType();
                gt.setType_id(rs.getInt(1));
                gt.setSeller_id(rs.getInt(2));
                gt.setType_name(rs.getString(3));
                gt.setQuantity(rs.getInt(4));
                result.add(gt);
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

    //删除商品类别
    public void deleteGoodsType(BeanGoodsType goodstype) throws BaseException {
        //判断是否选择了某一类别
        if(goodstype == null){
            throw new BusinessException("请选择其中一个类别");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现删除
            sql = "delete from tbl_goodstype where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goodstype.getSeller_id());
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

    //修改商品类别名字
    public void modifyGoodsType(BeanGoodsType goodstype, String name) throws BaseException {
        //判断是否选择某类别
        if(goodstype == null){
            throw new BusinessException("请选择一个类别");
        }
        //判断类别名字是否合理
        if(name == null || "".equals(name)){
            throw new BusinessException("类别名称不能为空");
        }
        if(name.length() > 20){
            throw new BusinessException("类别名称不能超过20字");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现商品类别修改
            sql = "update tbl_goodstype set type_name=? where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setInt(2, goodstype.getType_id());
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
