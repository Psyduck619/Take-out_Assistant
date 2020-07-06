package takeoutassistant.control;

import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsManager {
    //增加商品
    public void addGoods(BeanGoodsType type, String name, double price1, double price2) throws BaseException{
        //判断是否选择商品类别
        if(type == null){
            throw new BusinessException("请选择一个商品类别");
        }
        //判断商品名字是否合法
        if(name == null || "".equals(name)){
            throw new BusinessException("商品名称不能为空");
        }
        if(name.length() > 20){
            throw new BusinessException("商品名称不能超过20字");
        }
        //判断价格
        if(price1 < 0  || price2 < 0){
            throw new BusinessException("商品价格不能小于0");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现对应类别数量加1
            sql = "update tbl_goodstype set quantity=quantity+1 where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,type.getType_id());
            pst.execute();
            pst.close();
            //实现商品添加到数据库
            sql = "insert into tbl_goods(type_id,goods_type,goods_name,price,discount_price) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, type.getType_id());
            pst.setString(2, type.getType_name());
            pst.setString(3, name);
            pst.setDouble(4, price1);
            pst.setDouble(5, price2);
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

    //显示所有商品
    public List<BeanGoods> loadGoods(BeanGoodsType type) throws BaseException{
        //初始化
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现显示对应类别全部商品功能
            sql = "select goods_id,goods_name,goods_type,price,discount_price from tbl_goods where type_id=? order by goods_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, type.getType_id());
            rs = pst.executeQuery();
            while(rs.next()){
                BeanGoods bg = new BeanGoods();
                bg.setGoods_id(rs.getInt(1));
                bg.setGoods_name(rs.getString(2));
                bg.setGoods_type(rs.getString(3));
                bg.setPrice(rs.getDouble(4));
                bg.setDiscount_price(rs.getDouble(5));
                bg.setType_id(type.getType_id());
                result.add(bg);
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

    //删除商品
    public void deleteGoods(BeanGoods goods) throws BaseException{
        //判断是否选择了某一商品
        if(goods == null){
            throw new BusinessException("请选择一个商品");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现对应类别数量加减1
            sql = "update tbl_goodstype set quantity=quantity-1 where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,goods.getType_id());
            pst.execute();
            pst.close();
            //实现删除
            sql = "delete from tbl_goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods.getGoods_id());
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

    //修改商品名字.价格.优惠价
    public void modifyGoods(BeanGoods goods, String name, double price1, double price2) throws BaseException{
        //判断是否选择商品
        if(goods == null){
            throw new BusinessException("请选择一个商品");
        }
        //判断商品名字是否合法
        if(name == null || "".equals(name)){
            throw new BusinessException("商品名称不能为空");
        }
        if(name.length() > 20){
            throw new BusinessException("商品名称不能超过20字");
        }
        //判断价格
        if(price1 < 0  || price2 < 0){
            throw new BusinessException("商品价格不能小于0");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现商品添加到数据库
            sql = "update into tbl_goods(goods_name,price,discount_price) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setDouble(2, price1);
            pst.setDouble(3, price2);
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
}
