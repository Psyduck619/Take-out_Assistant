package takeoutassistant.control;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.itf.IOrderInfoManager;
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

public class OrderInfoManager implements IOrderInfoManager {

    //新增订单信息
    public void addOrderInfo(BeanUser user, BeanGoods goods, int quantity) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        boolean vip = user.isVIP(); //查询用户是不是会员
        Double price = 0.0;
        Double per_discount = 0.0;//优惠金额
        if(vip){
            price = goods.getDiscount_price();
            per_discount = goods.getPrice() - goods.getDiscount_price();
        }
        else{
            price = goods.getPrice();
        }
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //查询购物车中有没有商品,若没有,增加新订单,若有,则在同订单编号下进行操作
            sql = "select * from tbl_orderinfo where user_id=? and done=0";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            rs = pst.executeQuery();
            if(!rs.next()){  //购物车为空,则增加新的虚拟订单
                //向数据库插入新建的虚拟订单(done==0)
                sql = "insert into tbl_orderinfo(goods_id,goods_quantity,goods_price,per_discount,user_id,done,goods_name,flag,comment) values(?,?,?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,goods.getGoods_id());
                pst.setInt(2,quantity);
                pst.setDouble(3,price);
                pst.setDouble(4,per_discount);
                pst.setString(5,user.getUser_id());
                pst.setBoolean(6,false);
                pst.setString(7,goods.getGoods_name());
                pst.setBoolean(8,false);
                pst.setBoolean(9,false);
                pst.execute();
                System.out.println(goods.getGoods_id());
            }
            else{  //购物车非空在同一个订单编号下进行添加
                //先查询出订单编号
                int orderID = 0;
                sql = "select order_id from tbl_orderinfo where user_id=? and done=0";
                pst = conn.prepareStatement(sql);
                pst.setString(1,user.getUser_id());
                rs = pst.executeQuery();
                if(rs.next()){
                    orderID = rs.getInt(1);
                }
                rs.close();
                pst.close();
                //查询该商品是否已存在购物车中
                sql = "select * from tbl_orderinfo where order_id=? and goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,orderID);
                pst.setInt(2,goods.getGoods_id());
                rs = pst.executeQuery();
                if(rs.next()){ //商品已存在购物车中,更新商品数量
                    sql = "update tbl_orderinfo set goods_quantity=goods_quantity+? where order_id=? and goods_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1,quantity);
                    pst.setInt(2,orderID);
                    pst.setInt(3,goods.getGoods_id());
                    pst.execute();
                }
                else{  //商品不在购物车中,添加商品
                    sql = "insert into tbl_orderinfo(order_id,goods_id,goods_quantity,goods_price," +
                            "per_discount,user_id,done,goods_name,flag,comment) values(?,?,?,?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1,orderID);
                    pst.setInt(2,goods.getGoods_id());
                    pst.setInt(3,quantity);
                    pst.setDouble(4,price);
                    pst.setDouble(5,per_discount);
                    pst.setString(6,user.getUser_id());
                    pst.setBoolean(7,false);
                    pst.setString(8,goods.getGoods_name());
                    pst.setBoolean(9,false);
                    pst.setBoolean(10,false);
                    pst.execute();
                }

            }
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
    //显示订单信息(购物车)
    public List<BeanOrderInfo> loadOrderInfo(BeanUser user) throws BaseException{
        //初始化
        List<BeanOrderInfo> result = new ArrayList<BeanOrderInfo>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //显示用户目前的购物车信息(商品名,商品数量,单价,每件优惠,总价)
            sql = "select goods_id,goods_name,goods_quantity,goods_price,per_discount,order_id from tbl_orderinfo where user_id=? and done=false";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanOrderInfo boi = new BeanOrderInfo();
                boi.setGoods_id(rs.getInt(1));
                boi.setGoods_name(rs.getString(2));
                boi.setGoods_quantity(rs.getInt(3));
                boi.setOrder_price(rs.getDouble(4));
                boi.setPer_discount(rs.getDouble(5));
                boi.setOrder_id(rs.getInt(6));
                result.add(boi);
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
    //判断购物车里的商品有没有当前商家以外的
    public boolean isOnly(BeanUser user, BeanSeller curSeller) throws BaseException{
        //判空
        if(curSeller == null){
            throw new BaseException("商家信息错误,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //先查询出当前用户购物车订单有多少
            sql = "select goods_id from tbl_orderinfo where user_id=? and done=0";
            pst = conn.prepareStatement(sql);
            pst.setString(1,user.getUser_id());
            rs = pst.executeQuery();
            while(rs.next()){
                //查询当前商品ID所属的商家
                sql = "select b.seller_id from tbl_goods a,tbl_goodstype b where a.goods_id=? and a.type_id=b.type_id";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql);
                pst2.setInt(1,rs.getInt(1));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    if(rs2.getInt(1) != curSeller.getSeller_id()){
                        rs2.close();
                        pst2.close();
                        rs.close();
                        pst.close();
                        return false;
                    }
                }
                rs2.close();
                pst2.close();
            }
            rs.close();
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
        return true;
    }
    //显示订单信息(用户)
    public List<BeanOrderInfo> loadMyOrderInfo(BeanGoodsOrder order) throws BaseException{
        //初始化
        List<BeanOrderInfo> result = new ArrayList<BeanOrderInfo>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //显示用户订单详情信息(商品名,商品数量,单价,每件优惠,总价)
            sql = "select goods_id,goods_name,goods_quantity,goods_price,per_discount,order_id,user_id from tbl_orderinfo where order_id=? and flag=0";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, order.getOrder_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanOrderInfo boi = new BeanOrderInfo();
                boi.setGoods_id(rs.getInt(1));
                boi.setGoods_name(rs.getString(2));
                boi.setGoods_quantity(rs.getInt(3));
                boi.setOrder_price(rs.getDouble(4));
                boi.setPer_discount(rs.getDouble(5));
                boi.setOrder_id(rs.getInt(6));
                boi.setUser_id(rs.getString(7));
                result.add(boi);
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
    //删除单条订单信息
    public void deleteOrderInfo(BeanOrderInfo orderInfo) throws BaseException{
        //判空
        if(orderInfo == null){
            throw new BaseException("商品信息错误,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现删除
            sql = "delete from tbl_orderinfo where order_id=? and goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderInfo.getOrder_id());
            pst.setInt(2, orderInfo.getGoods_id());
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
    //得到当前购物车某商品的数量
    public int getQuantity(BeanUser user, BeanOrderInfo myCart) throws BaseException{
        //判空
        if(user == null){
            try {
                throw new BusinessException("用户为空,请重试");
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        if(myCart == null)
            return 0;
        //初始化
        int quantity = 0;
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //得到数量
            sql = "select goods_quantity from tbl_orderinfo where user_id=? and goods_id=? and done=false";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            pst.setInt(2, myCart.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
            }
            rs.close();
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
        return quantity;
    }
    //得到当前购物车中选中的商品的库存数
    public int getGoodsQuantity(BeanOrderInfo myCart) throws BaseException{
        //判空
        if(myCart == null)
            return 0;
        //初始化
        int quantity = 0;
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //得到数量
            sql = "select goods_quantity from tbl_goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            System.out.println("1:"+myCart.getGoods_id());
            pst.setInt(1, myCart.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
            }
            rs.close();
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
        return quantity;
    }
    //得到当前购物车某商品的数量(添加商品)
    public int getQuantity2(BeanUser user, BeanGoods goods) throws BaseException{
        //判空
        if(user == null){
            try {
                throw new BusinessException("用户为空,请重试");
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        if(goods == null)
            return 0;
        //初始化
        int quantity = 0;
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //得到数量
            sql = "select goods_quantity from tbl_orderinfo where user_id=? and goods_id=? and done=false";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            pst.setInt(2, goods.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
            }
            rs.close();
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
        return quantity;
    }
    //得到当前购物车中选中的商品的库存数(添加商品)
    public int getGoodsQuantity2(BeanGoods goods) throws BaseException{
        //判空
        if(goods == null)
            return 0;
        //初始化
        int quantity = 0;
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //得到数量
            sql = "select goods_quantity from tbl_goods where goods_id=?";
            pst = conn.prepareStatement(sql);
            System.out.println("1:"+goods.getGoods_id());
            pst.setInt(1, goods.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
            }
            rs.close();
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
        return quantity;
    }
    //清空购物车
    public void deleteAll(BeanUser user) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现删除
            sql = "delete from tbl_orderinfo where user_id=? and done=false";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
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
    //购物车增加商品数量
    public void addCount(BeanOrderInfo orderInfo, int count) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现商品数量增加
            sql = "update tbl_orderinfo set goods_quantity=goods_quantity+? where order_id=? and goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, count);
            pst.setInt(2, orderInfo.getOrder_id());
            pst.setInt(3, orderInfo.getGoods_id());
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
    //购物车减少商品数量
    public void loseCount(BeanOrderInfo orderInfo, int count) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //先查询目前有多少件商品
            int quantity = 0;
            sql = "select goods_quantity from tbl_orderinfo where order_id=? and goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderInfo.getOrder_id());
            pst.setInt(2, orderInfo.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
            }
            if(count < quantity){  //如果减少数量小于已有数,正常减少
                sql = "update tbl_orderinfo set goods_quantity=goods_quantity-? where order_id=? and goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, count);
                pst.setInt(2, orderInfo.getOrder_id());
                pst.setInt(3, orderInfo.getGoods_id());
                pst.execute();
                pst.close();
            }
            else if(count == quantity){  //如果减少数量等于已有数,直接删除整条商品
                sql = "delete from tbl_orderinfo where order_id=? and goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, orderInfo.getOrder_id());
                pst.setInt(2, orderInfo.getGoods_id());
                pst.execute();
                pst.close();
            }
            else{  //如果减少数量小于已有数,进行错误提示
                throw new BaseException("减少数量小于已有数量,请重新输入");
            }

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
    //根据产品购买量为用户推荐产品
    public List<BeanRecommend> Recommend(BeanUser user) throws BaseException{
        //初始化
        List<BeanRecommend> result = new ArrayList<BeanRecommend>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //根据购买量排序商品
            sql = "select goods_name,order_id,count(goods_quantity) count,goods_id from tbl_orderinfo " +
                    "where done=1 and flag=0 and user_id=? group by goods_id order by count DESC LIMIT 5";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanRecommend br = new BeanRecommend();
                br.setUser_id(user.getUser_id());
                br.setGoods_name(rs.getString(1));
                br.setQuantity(rs.getInt(3));
                br.setGoods_id(rs.getInt(4));
                int sellerID = 0;
                sql = "select seller_id from tbl_goodsorder where order_id=?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql);
                pst2.setInt(1,rs.getInt(2));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    sellerID = rs2.getInt(1);
                }
                String sellerName = TakeoutAssistantUtil.sellerManager.getSellerName(sellerID);
                br.setSeller_id(sellerID);
                br.setSeller_name(sellerName);
                result.add(br);
                pst2.close();
                rs2.close();
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
