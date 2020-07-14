package takeoutassistant.control;

import takeoutassistant.model.BeanGoods;
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

public class GoodsManager {

    //增加商品
    public void addGoods(BeanGoodsType type, String name, double price1, double price2, int quantity) throws BaseException {
        //判断是否选择商品类别
        if (type == null) {
            throw new BusinessException("请选择一个商品类别");
        }
        //判断商品名字是否合法
        if (name == null || "".equals(name)) {
            throw new BusinessException("商品名称不能为空");
        }
        if (name.length() > 20) {
            throw new BusinessException("商品名称不能超过20字");
        }
        //判断价格
        if (price1 < 0 || price2 < 0) {
            throw new BusinessException("商品价格不能小于0");
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
            conn.setAutoCommit(false);
            //实现对应类别数量加1
            sql = "update tbl_goodstype set quantity=quantity+1 where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, type.getType_id());
            pst.execute();
            pst.close();
            //实现商品添加到数据库
            sql = "insert into tbl_goods(type_id,goods_type,goods_name,price,discount_price,goods_quantity,goods_sales,goods_level) values(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, type.getType_id());
            pst.setString(2, type.getType_name());
            pst.setString(3, name);
            pst.setDouble(4, price1);
            pst.setDouble(5, price2);
            pst.setInt(6, quantity);
            pst.setInt(7,0);
            pst.setDouble(8,0.0);
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //显示所有商品
    public List<BeanGoods> loadGoods(BeanGoodsType type) throws BaseException {
        //初始化
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现显示对应类别全部商品功能
            sql = "select goods_id,goods_name,goods_type,price,discount_price,goods_quantity,goods_sales,goods_level from tbl_goods where type_id=? order by goods_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, type.getType_id());
            rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoods bg = new BeanGoods();
                bg.setGoods_id(rs.getInt(1));
                bg.setGoods_name(rs.getString(2));
                bg.setGoods_type(rs.getString(3));
                bg.setPrice(rs.getDouble(4));
                bg.setDiscount_price(rs.getDouble(5));
                bg.setType_id(type.getType_id());
                bg.setGoods_quantity(rs.getInt(6));
                bg.setGoods_sales(rs.getInt(7));
                bg.setGoods_level(rs.getDouble(8));
                result.add(bg);
            }
            rs.close();
            pst.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
    //删除商品
    public void deleteGoods(BeanGoods goods) throws BaseException {
        //判断是否选择了某一商品
        if (goods == null) {
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
            pst.setInt(1, goods.getType_id());
            pst.execute();
            pst.close();
            //实现删除
            sql = "delete from tbl_goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods.getGoods_id());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //修改商品名字.价格.优惠价
    public void modifyGoods(BeanGoods goods, String name, double price1, double price2, int quantity) throws BaseException {
        //判断是否选择商品
        if (goods == null) {
            throw new BusinessException("请选择一个商品");
        }
        //判断商品名字是否合法
        if (name == null || "".equals(name)) {
            throw new BusinessException("商品名称不能为空");
        }
        if (name.length() > 20) {
            throw new BusinessException("商品名称不能超过20字");
        }
        //判断价格
        if (price1 < 0 || price2 < 0) {
            throw new BusinessException("商品价格不能小于0");
        }
        //判断数量
        if(quantity < 0){
            throw new BusinessException("商品数量不能小于0");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现商品信息到数据库
            sql = "update tbl_goods set goods_name=? where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setInt(2, goods.getGoods_id());
            pst.execute();
            sql = "update tbl_goods set price=? where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1, price1);
            pst.setInt(2, goods.getGoods_id());
            pst.execute();
            sql = "update tbl_goods set discount_price=?,goods_quantity=? where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1, price2);
            pst.setInt(2, quantity);
            pst.setInt(3, goods.getGoods_id());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //显示热门产品(最好的三个产品)
    public List<BeanGoods> loadHGoods(BeanSeller seller) throws BaseException {
        //初始化
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现显示对应类别全部商品功能
            sql = "select a.goods_id,a.goods_name,a.goods_type,a.price,a.discount_price,a.goods_quantity,a.goods_sales top from tbl_goods a,tbl_goodstype b" +
                    " where b.type_id=a.type_id and b.seller_id=? order by top DESC LIMIT 3";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoods bg = new BeanGoods();
                bg.setGoods_id(rs.getInt(1));
                bg.setGoods_name(rs.getString(2));
                bg.setGoods_type(rs.getString(3));
                bg.setPrice(rs.getDouble(4));
                bg.setDiscount_price(rs.getDouble(5));
                bg.setGoods_quantity(rs.getInt(6));
                bg.setGoods_sales(rs.getInt(7));
                result.add(bg);
            }
            rs.close();
            pst.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
    //根据类别ID得到商家名
    public String typeToSeller_name(int type_id) throws BaseException{
        //初始化
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //sql
            sql = "select b.seller_name from tbl_goodstype a,tbl_seller b where a.type_id=? and a.seller_id=b.seller_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, type_id);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
    //根据商品ID得到商品名
    public String idToName(int goods_id) throws BaseException{
        //初始化
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //sql
            sql = "select goods_name from tbl_goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods_id);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
    //按商品名查询
    public List<BeanGoods> loadForName(String name) throws BaseException{
        //初始化
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现sql查询
            sql = "select goods_id,goods_name,goods_type,price,discount_price,goods_quantity,type_id,goods_sales,goods_level from tbl_goods where goods_name like ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+name+"%");
            rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoods bg = new BeanGoods();
                bg.setGoods_id(rs.getInt(1));
                bg.setGoods_name(rs.getString(2));
                bg.setGoods_type(rs.getString(3));
                bg.setPrice(rs.getDouble(4));
                bg.setDiscount_price(rs.getDouble(5));
                bg.setGoods_quantity(rs.getInt(6));
                bg.setType_id(rs.getInt(7));
                bg.setGoods_sales(rs.getInt(8));
                bg.setGoods_level(rs.getDouble(9));
                result.add(bg);
            }
            rs.close();
            pst.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
    //按类别名查询
    public List<BeanGoods> loadForType(String name) throws BaseException{
        //初始化
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现sql查询
            sql = "select goods_id,goods_name,goods_type,price,discount_price,goods_quantity,type_id,goods_sales,goods_level from tbl_goods where goods_type like ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%"+name+"%");
            rs = pst.executeQuery();
            while (rs.next()) {
                BeanGoods bg = new BeanGoods();
                bg.setGoods_id(rs.getInt(1));
                bg.setGoods_name(rs.getString(2));
                bg.setGoods_type(rs.getString(3));
                bg.setPrice(rs.getDouble(4));
                bg.setDiscount_price(rs.getDouble(5));
                bg.setGoods_quantity(rs.getInt(6));
                bg.setType_id(rs.getInt(7));
                bg.setGoods_sales(rs.getInt(8));
                bg.setGoods_level(rs.getDouble(9));
                result.add(bg);
            }
            rs.close();
            pst.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
    //判断是否有该商品的订单存在,若有,则无法删除
    public boolean ifHavingOrder(BeanGoods goods) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            sql = "select * from tbl_orderinfo where goods_id=? and done=1";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,goods.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                return true;
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

}