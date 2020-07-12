package takeoutassistant.control;

import takeoutassistant.itf.IGoodsCommentManager;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsCommentManager implements IGoodsCommentManager {

    //添加商品评价
    public void addGoodsComment(BeanOrderInfo orderInfo, String comment, String level) throws BaseException{
        //判空
//        if(comment == null || "".equals(comment)){
//            throw new BusinessException("评价不能为空");
//        }
        if(level == null || "".equals(level)){
            throw new BusinessException("星级不能为空");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //得到商品所属商家ID
            int sellerID = 0;
            sql = "select a.seller_id from tbl_goodstype a,tbl_goods b where b.goods_id=? and a.type_id=b.type_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                sellerID = rs.getInt(1);
            }
            //得到星级的double //先插入商品评价表,再计算更新商品的星级和商家的星级
            Double star = 0.0;
            if(level.equals("一星"))
                star = 1.0;
            else if(level.equals("二星"))
                star = 2.0;
            else if(level.equals("三星"))
                star = 3.0;
            else if(level.equals("四星"))
                star = 4.0;
            else
                star = 5.0;
            //插入评价到数据库
            sql = "insert into tbl_goodscomment(goods_id,user_id,com_content,com_date,com_level,com_picture,order_id,seller_id) values(?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            pst.setString(2,orderInfo.getUser_id());
            pst.setString(3,comment);
            pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setDouble(5,star);
            pst.setLong(6,0);
            pst.setInt(7,orderInfo.getOrder_id());
            pst.setInt(8,sellerID);
            pst.execute();
            //更新订单详情表里的已评论
            sql = "update tbl_orderinfo set comment=true where goods_id=? and order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            pst.setInt(2,orderInfo.getOrder_id());
            pst.execute();
            //计算更新商品的星级
            int goods_sales = 0;
            Double goods_level = 0.0;
            sql = "select goods_level from tbl_goods where goods_id=?";//先从商品表中查询出商品星级
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                goods_level = rs.getDouble(1);
            }
            sql = "select count(*) from tbl_orderinfo where goods_id=? and comment=1";//从订单详情表中得到商品的销售次数(已评价过的)
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                goods_sales = rs.getInt(1);
            }
            goods_level = (goods_level*(goods_sales-1)+star)/(goods_sales);//计算新的星级
            sql = "update tbl_goods set goods_level=? where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1,goods_level);
            pst.setInt(2,orderInfo.getGoods_id());
            pst.execute();
            //更新商家的星级(有销量商品的总星数/有销量商品数)
            int goods_count = 0;
            Double all_level = 0.0;
            sql = "select count(*) from tbl_goods a,tbl_goodstype b where b.seller_id=? and a.type_id=b.type_id and a.goods_sales>0";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,sellerID);
            rs = pst.executeQuery();
            if(rs.next()){
                goods_count = rs.getInt(1);
            }
            sql = "select a.goods_level from tbl_goods a,tbl_goodstype b where b.seller_id=? and a.type_id=b.type_id and a.goods_sales>0";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,sellerID);
            rs = pst.executeQuery();
            while(rs.next()){
                all_level += rs.getDouble(1);
            }
            sql = "update tbl_seller set seller_level=? where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1,all_level/goods_count);
            pst.setInt(2,sellerID);
            pst.execute();
            conn.commit();
            rs.close();
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
    //得到所有用户的评价
    public List<BeanGoodsComment> loadAll(BeanUser user) throws BaseException{
        //初始化
        List<BeanGoodsComment> result = new ArrayList<BeanGoodsComment>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //得到所有评价数据
            sql = "select order_id,user_id,seller_id,goods_id,com_content,com_level,com_picture,com_date " +
                    "from tbl_goodscomment where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsComment bgc = new BeanGoodsComment();
                bgc.setOrder_id(rs.getInt(1));
                bgc.setUser_id(rs.getString(2));
                bgc.setSeller_id(rs.getInt(3));
                bgc.setGoods_id(rs.getInt(4));
                bgc.setCom_content(rs.getString(5));
                bgc.setCom_level(rs.getShort(6));
                bgc.setCom_picture(rs.getLong(7));
                bgc.setCom_date(rs.getTimestamp(8));
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
    //得到所有商品的评价
    public List<BeanGoodsComment> loadGoods(BeanGoods goods) throws BaseException{
        //初始化
        List<BeanGoodsComment> result = new ArrayList<BeanGoodsComment>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //得到所有评价数据
            sql = "select order_id,user_id,seller_id,goods_id,com_content,com_level,com_picture,com_date " +
                    "from tbl_goodscomment where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods.getGoods_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsComment bgc = new BeanGoodsComment();
                bgc.setOrder_id(rs.getInt(1));
                bgc.setUser_id(rs.getString(2));
                bgc.setSeller_id(rs.getInt(3));
                bgc.setGoods_id(rs.getInt(4));
                bgc.setCom_content(rs.getString(5));
                bgc.setCom_level(rs.getShort(6));
                bgc.setCom_picture(rs.getLong(7));
                bgc.setCom_date(rs.getTimestamp(8));
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
    //判断商品是否已评价
    public boolean isComment(BeanOrderInfo orderInfo) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //查询
            sql = "select * from tbl_goodscomment where goods_id=? and order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            pst.setInt(2,orderInfo.getOrder_id());
            rs = pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
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
    //删除评价(删完不能再次评价)
    public void deleteComment(BeanGoodsComment goodsComment) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //查询
            sql = "delete from tbl_goodscomment where goods_id=? and order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,goodsComment.getGoods_id());
            pst.setInt(2,goodsComment.getOrder_id());
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
