package takeoutassistant.control;

import takeoutassistant.itf.IOrderManager;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class OrderManager implements IOrderManager {

    //得到当前的订单号
    public int getOrderID(BeanUser user) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            int ID = 0;
            //计算原价
            sql = "select order_id from tbl_orderinfo where user_id=? and done=false";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            rs = pst.executeQuery();
            if(rs.next()){
                ID = rs.getInt(1);
            }
            rs.close();
            pst.close();
            return ID;
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
    //计算原价
    public double getPrice1(int orderID) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            double price = 0.0;
            conn = DBUtil.getConnection();
            //计算原价
            sql = "select sum(goods_quantity*(goods_price+per_discount)) from tbl_orderinfo where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderID);
            rs = pst.executeQuery();
            if(rs.next()){
                price = rs.getDouble(1);
            }
            rs.close();
            pst.close();
            return price;
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
    //计算会员总优惠
    public double getVIP_Discount(int orderID) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            double price = 0.0;
            conn = DBUtil.getConnection();
            //计算会员总优惠
            sql = "select sum(goods_quantity*per_discount) from tbl_orderinfo where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderID);
            rs = pst.executeQuery();
            if(rs.next()){
                price = rs.getDouble(1);
            }
            rs.close();
            pst.close();
            return price;
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
    //计算当前适合的满减
    public BeanManjian getManjian(BeanSeller seller, double price) throws BaseException{
        //初始化
        BeanManjian manjian = new BeanManjian();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //得到所有满减值
            TreeSet<Integer> ts = new TreeSet<>();
            int[] list = new int[10];
            int k = 0;
            int amount = 0;
            sql = "select manjian_amount from tbl_manjian where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            rs = pst.executeQuery();
            while(rs.next()){
                ts.add(rs.getInt(1));
            }
            //计算得到最适合的满减值
            for(Integer x:ts){
                list[k]=x;
                k++;
            }
            if(k == 0){
                return null;
            }
            else if(price >= list[k-1]){
                amount = list[k-1];
            }
            else {
                for(int i = 0 ; i < k-1 ; i++){
                    if(price >= list[i] && price < list[i+1]){
                        amount = list[i];
                    }
                }
            }
            if(amount == 0) //没有符合的满减,直接返回null
                return null;
            //返回满减对象
            sql = "select manjian_id,seller_id,manjian_amount,discount_amount from tbl_manjian where manjian_amount=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, amount);
            rs = pst.executeQuery();
            if(rs.next()){
                manjian.setManjian_id(rs.getInt(1));
                manjian.setSeller_id(rs.getInt(2));
                manjian.setManjian_amount(rs.getInt(3));
                manjian.setDiscount_amount(rs.getInt(4));
            }
            rs.close();
            pst.close();
            return manjian;
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
    //计算最终价格
    public double getPrice(double price, double vip, BeanManjian manjian, BeanMyCoupon coupon) throws BaseException{
        double p = price - vip;
        System.out.println(p);
        if(manjian != null)
            p = p - manjian.getDiscount_amount();
        System.out.println(p);
        if(coupon != null)
            p = p - coupon.getCoupon_amount();
        System.out.println(p);
        if(p < 0)
            p = 0;
        return p;
    }
    //新增订单
    public void addOrder(int orderID, BeanUser user, BeanSeller seller, BeanAddress address, double price1, double price2,
                         Date request_date, BeanManjian manjian, BeanMyCoupon coupon) throws BaseException{
        if(orderID == 0){
            throw new BusinessException("购物车为空,无法下单");
        }
        //price2 -= coupon.getCoupon_amount();
        //处理空指针异常
        if(coupon == null){
            System.out.println("优惠券为空");
            coupon = new BeanMyCoupon();
            coupon.setCoupon_id(-1);
            coupon.setCoupon_amount(0);
        }
        if(manjian == null){
            System.out.println("满减为空");
            manjian = new BeanManjian();
            manjian.setManjian_id(-1);
            manjian.setDiscount_amount(0);
        }
        //初始化
        //int riderID = 0;//需要骑手分配算法(或骑手手动接单)
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); //事务控制
            //订单添加到数据库
            sql = "insert into tbl_goodsorder(order_id,add_id,manjian_id,coupon_id,original_price,final_price," +
                    "order_time,request_time,order_state,user_id,seller_id) values(?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderID);
            pst.setInt(2,address.getAdd_id());
            pst.setInt(3,manjian.getManjian_id());
            pst.setInt(4,coupon.getCoupon_id());
            pst.setDouble(5,price1);
            pst.setDouble(6,price2);
            pst.setTimestamp(7,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(8,new java.sql.Timestamp(request_date.getTime()));
            pst.setString(9,"未配送");
            pst.setString(10,user.getUser_id());
            pst.setInt(11,seller.getSeller_id());
            pst.execute();
            //相关商品数量减少 //先查询得到一个当前订单的各商品ID和减少数量的对应表
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            sql = "select goods_id,goods_quantity from tbl_orderinfo where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderID);
            rs = pst.executeQuery();
            while(rs.next()){
                map.put(rs.getInt(1), rs.getInt(2));
            }
            for (Integer key : map.keySet()){  //循环减少商品数据
                sql = "update tbl_goods set goods_quantity=goods_quantity-? where goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, map.get(key));
                pst.setInt(2, key);
                pst.execute();
            }
            //修改订单详情表中的虚拟订单为真实订单
            sql = "update tbl_orderinfo set done=true where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderID);
            pst.execute();
            //删除用户的优惠券,先查询,如果优惠券数量为1,则直接删除,不然就减一
            sql = "delete from tbl_mycoupon where coupon_id=? and user_id=? and coupon_count=1"; //一张优惠券直接删除
            pst = conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
            pst.setString(2,user.getUser_id());
            pst.execute();
            sql = "update tbl_mycoupon set coupon_count=coupon_count-1 where coupon_id=? and user_id=? and coupon_count>1"; //多张则减一
            pst = conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
            pst.setString(2,user.getUser_id());
            pst.execute();

//            //更新用户的集单表 //①商家有,表中无,则全都添加进去并加1②商家有,表中有,且加1后没达到要求,则直接加1
//            //③商家有,表中有,且加1后达到要求,则删除该条集单信息,对应优惠券加1④商家有表中无,但需求只有1,那么直接优惠券加1
//            List<Integer> array = new ArrayList<Integer>();
//            //先查询出商家所有的优惠券ID
//            sql = "select coupon_id from tbl_coupon where seller_id=?";
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1,seller.getSeller_id());
//            rs = pst.executeQuery();
//            while(rs.next()){ //得到所有优惠券ID
//                array.add(rs.getInt(1));
//            }
//            //开始循环查询每一个优惠券对应的情况
//            for(int i = 0 ; i < array.size() ; i++){
//                //先查询优惠券的需求数是不是1,是的话直接优惠券+1
//                sql = "select coupon_request from tbl_coupon where coupon_id=?";
//                pst = conn.prepareStatement(sql);
//                pst.setInt(1,array.get(i));
//                rs = pst.executeQuery();
//                if(rs.next()){
//                    if(rs.getInt(1) == 1){
//                        BeanCoupon c = new BeanCoupon();//创建一个优惠券对象
//                        c.setSeller_id(seller.getSeller_id());
//                        c.setCoupon_id(array.get(i));
//                        c.setCoupon_request(1);
//                        sql = "select coupon_amount,begin_date,end_date,ifTogether from tbl_coupon where coupon_id=?";
//                        pst = conn.prepareStatement(sql);
//                        pst.setInt(1,array.get(1));
//                        rs = pst.executeQuery();
//                        if(rs.next()){
//                            c.setCoupon_amount(rs.getInt(1));
//                            c.setBegin_date(rs.getTimestamp(2));
//                            c.setEnd_date(rs.getTimestamp(3));
//                            c.setIfTogether(rs.getBoolean(4));
//                        }
//                        new MyCouponManager().addMyCoupon(user, c);//调用方法加入优惠券
//                        continue;
//                    }
//                }
//                //再查询集单表中有没有该优惠券
//                sql = "select * from tbl_userjidan where coupon_id=?";
//                pst = conn.prepareStatement(sql);
//                pst.setInt(1,array.get(1));
//                rs = pst.executeQuery();
//                if(!rs.next()){  //如果表中没有的话,则添加
//                    //查询出该优惠券的需求数.优惠金额
//                    int q = 0;
//                    int p = 0;
//                    sql = "select coupon_request,coupon_amount from tbl_coupon where coupon_id=?";
//                    pst = conn.prepareStatement(sql);
//                    pst.setInt(1,array.get(1));
//                    rs = pst.executeQuery();
//                    if(rs.next()){
//                        q = rs.getInt(1);
//                        p = rs.getInt(2);
//                    }
//                    sql = "insert into tbl_userjidan(user_id,seller_id,coupon_request,coupon_get,coupon_amount,coupon_id) values(?,?,?,?,?,?)";
//                    pst = conn.prepareStatement(sql);
//                    pst.setString(1,user.getUser_id());
//                    pst.setInt(2,seller.getSeller_id());
//                    pst.setInt(3,q);
//                    pst.setInt(4,1);
//                    pst.setInt(5,p);
//                    pst.setInt(6,array.get(1));
//                    pst.execute();
//                    continue;
//                }
//                else{ //如果表中有的话,则数量加1
//                    //先查询是不是只差1
//                    sql = "select coupon_request-coupon_get from tbl_userjidan where coupon_id=?";
//                    pst = conn.prepareStatement(sql);
//                    pst.setInt(1,array.get(1));
//                    rs = pst.executeQuery();
//                    if(rs.next()){
//                        if (rs.getInt(1) == 1){ //直接删除该条集单信息,对应优惠券加1
//                            BeanCoupon c = new BeanCoupon();//创建一个优惠券对象
//                            c.setSeller_id(seller.getSeller_id());
//                            c.setCoupon_id(array.get(i));
//                            c.setCoupon_request(1);
//                            sql = "select coupon_amount,begin_date,end_date,ifTogether from tbl_coupon where coupon_id=?";
//                            pst = conn.prepareStatement(sql);
//                            pst.setInt(1,array.get(1));
//                            rs = pst.executeQuery();
//                            if(rs.next()){
//                                c.setCoupon_amount(rs.getInt(1));
//                                c.setBegin_date(rs.getTimestamp(2));
//                                c.setEnd_date(rs.getTimestamp(3));
//                                c.setIfTogether(rs.getBoolean(4));
//                            }
//                            new MyCouponManager().addMyCoupon(user, c);//调用方法加入优惠券
//                            sql = "delete from tbl_userjidan where coupon_id=?";
//                            pst = conn.prepareStatement(sql);
//                            pst.setInt(1,array.get(1));
//                            pst.execute();
//                            continue;
//                        }
//                        else{ //一般情况,则集单数加1
//                            sql = "update tbl_userjidan set coupon_get+=1 where coupon_id=?";
//                            pst = conn.prepareStatement(sql);
//                            pst.setInt(1,array.get(1));
//                            pst.execute();
//                            continue;
//                        }
//                    }
//                }
//            }
            conn.commit();
            //rs.close();
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
    //得到用户个人的订单信息
    public List<BeanGoodsOrder> loadUsers(BeanUser user) throws BaseException{
        //初始化
        List<BeanGoodsOrder> result = new ArrayList<BeanGoodsOrder>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现得到所有订单
            sql = "select order_id,add_id,manjian_id,coupon_id,rider_id,original_price,final_price,order_time," +
                    "request_time,order_state,user_id,seller_id from tbl_goodsorder where user_id=? and flag=0";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsOrder bgo = new BeanGoodsOrder();
                bgo.setOrder_id(rs.getInt(1));
                bgo.setAdd_id(rs.getInt(2));
                bgo.setManjian_id(rs.getInt(3));
                bgo.setCoupon_id(rs.getInt(4));
                bgo.setRider_id(rs.getInt(5));
                bgo.setOriginal_price(rs.getDouble(6));
                bgo.setFinal_price(rs.getDouble(7));
                bgo.setOrder_time(rs.getTimestamp(8));
                bgo.setRequest_time(rs.getTimestamp(9));
                bgo.setOrder_state(rs.getString(10));
                bgo.setUser_id(rs.getString(11));
                bgo.setSeller_id(rs.getInt(12));
                result.add(bgo);
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
    //得到所有订单
    public List<BeanGoodsOrder> loadAll() throws BaseException{
        //初始化
        List<BeanGoodsOrder> result = new ArrayList<BeanGoodsOrder>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现得到所有订单
            sql = "select order_id,add_id,manjian_id,coupon_id,rider_id,original_price,final_price,order_time," +
                    "request_time,order_state,user_id,seller_id from tbl_goodsorder";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsOrder bgo = new BeanGoodsOrder();
                bgo.setOrder_id(rs.getInt(1));
                bgo.setAdd_id(rs.getInt(2));
                bgo.setManjian_id(rs.getInt(3));
                bgo.setCoupon_id(rs.getInt(4));
                bgo.setRider_id(rs.getInt(5));
                bgo.setOriginal_price(rs.getDouble(6));
                bgo.setFinal_price(rs.getDouble(7));
                bgo.setOrder_time(rs.getTimestamp(8));
                bgo.setRequest_time(rs.getTimestamp(9));
                bgo.setOrder_state(rs.getString(10));
                bgo.setUser_id(rs.getString(11));
                bgo.setSeller_id(rs.getInt(12));
                result.add(bgo);
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
    //得到"未配送"订单
    public List<BeanGoodsOrder> loadNoDo() throws BaseException{
        //初始化
        List<BeanGoodsOrder> result = new ArrayList<BeanGoodsOrder>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现得到所有订单
            sql = "select order_id,add_id,manjian_id,coupon_id,rider_id,original_price,final_price,order_time," +
                    "request_time,order_state,user_id,seller_id from tbl_goodsorder where order_state=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,"未配送");
            rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsOrder bgo = new BeanGoodsOrder();
                bgo.setOrder_id(rs.getInt(1));
                bgo.setAdd_id(rs.getInt(2));
                bgo.setManjian_id(rs.getInt(3));
                bgo.setCoupon_id(rs.getInt(4));
                bgo.setRider_id(rs.getInt(5));
                bgo.setOriginal_price(rs.getDouble(6));
                bgo.setFinal_price(rs.getDouble(7));
                bgo.setOrder_time(rs.getTimestamp(8));
                bgo.setRequest_time(rs.getTimestamp(9));
                bgo.setOrder_state(rs.getString(10));
                bgo.setUser_id(rs.getString(11));
                bgo.setSeller_id(rs.getInt(12));
                result.add(bgo);
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
    //得到"配送中"订单
    public List<BeanGoodsOrder> loadDing() throws BaseException{
        //初始化
        List<BeanGoodsOrder> result = new ArrayList<BeanGoodsOrder>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现得到所有订单
            sql = "select order_id,add_id,manjian_id,coupon_id,rider_id,original_price,final_price,order_time," +
                    "request_time,order_state,user_id,seller_id from tbl_goodsorder where order_state=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,"配送中");
            rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsOrder bgo = new BeanGoodsOrder();
                bgo.setOrder_id(rs.getInt(1));
                bgo.setAdd_id(rs.getInt(2));
                bgo.setManjian_id(rs.getInt(3));
                bgo.setCoupon_id(rs.getInt(4));
                bgo.setRider_id(rs.getInt(5));
                bgo.setOriginal_price(rs.getDouble(6));
                bgo.setFinal_price(rs.getDouble(7));
                bgo.setOrder_time(rs.getTimestamp(8));
                bgo.setRequest_time(rs.getTimestamp(9));
                bgo.setOrder_state(rs.getString(10));
                bgo.setUser_id(rs.getString(11));
                bgo.setSeller_id(rs.getInt(12));
                result.add(bgo);
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
    //得到"确认送达"订单
    public List<BeanGoodsOrder> loadConfirm() throws BaseException{
        //初始化
        List<BeanGoodsOrder> result = new ArrayList<BeanGoodsOrder>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现得到所有订单
            sql = "select order_id,add_id,manjian_id,coupon_id,rider_id,original_price,final_price,order_time," +
                    "request_time,order_state,user_id,seller_id from tbl_goodsorder where order_state=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,"确认送达");
            rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsOrder bgo = new BeanGoodsOrder();
                bgo.setOrder_id(rs.getInt(1));
                bgo.setAdd_id(rs.getInt(2));
                bgo.setManjian_id(rs.getInt(3));
                bgo.setCoupon_id(rs.getInt(4));
                bgo.setRider_id(rs.getInt(5));
                bgo.setOriginal_price(rs.getDouble(6));
                bgo.setFinal_price(rs.getDouble(7));
                bgo.setOrder_time(rs.getTimestamp(8));
                bgo.setRequest_time(rs.getTimestamp(9));
                bgo.setOrder_state(rs.getString(10));
                bgo.setUser_id(rs.getString(11));
                bgo.setSeller_id(rs.getInt(12));
                result.add(bgo);
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
    //得到"超时"订单
    public List<BeanGoodsOrder> loadOverTime() throws BaseException{
        //初始化
        List<BeanGoodsOrder> result = new ArrayList<BeanGoodsOrder>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现得到所有订单
            sql = "select order_id,add_id,manjian_id,coupon_id,rider_id,original_price,final_price,order_time," +
                    "request_time,order_state,user_id,seller_id from tbl_goodsorder where order_state=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,"超时");
            rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsOrder bgo = new BeanGoodsOrder();
                bgo.setOrder_id(rs.getInt(1));
                bgo.setAdd_id(rs.getInt(2));
                bgo.setManjian_id(rs.getInt(3));
                bgo.setCoupon_id(rs.getInt(4));
                bgo.setRider_id(rs.getInt(5));
                bgo.setOriginal_price(rs.getDouble(6));
                bgo.setFinal_price(rs.getDouble(7));
                bgo.setOrder_time(rs.getTimestamp(8));
                bgo.setRequest_time(rs.getTimestamp(9));
                bgo.setOrder_state(rs.getString(10));
                bgo.setUser_id(rs.getString(11));
                bgo.setSeller_id(rs.getInt(12));
                result.add(bgo);
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
    //得到"已取消"订单
    public List<BeanGoodsOrder> loadCancel() throws BaseException{
        //初始化
        List<BeanGoodsOrder> result = new ArrayList<BeanGoodsOrder>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //实现得到所有订单
            sql = "select order_id,add_id,manjian_id,coupon_id,rider_id,original_price,final_price,order_time," +
                    "request_time,order_state,user_id,seller_id from tbl_goodsorder where order_state=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,"已取消");
            rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsOrder bgo = new BeanGoodsOrder();
                bgo.setOrder_id(rs.getInt(1));
                bgo.setAdd_id(rs.getInt(2));
                bgo.setManjian_id(rs.getInt(3));
                bgo.setCoupon_id(rs.getInt(4));
                bgo.setRider_id(rs.getInt(5));
                bgo.setOriginal_price(rs.getDouble(6));
                bgo.setFinal_price(rs.getDouble(7));
                bgo.setOrder_time(rs.getTimestamp(8));
                bgo.setRequest_time(rs.getTimestamp(9));
                bgo.setOrder_state(rs.getString(10));
                bgo.setUser_id(rs.getString(11));
                bgo.setSeller_id(rs.getInt(12));
                result.add(bgo);
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
    //用户删除订单信息
    public void deleteOrder(BeanGoodsOrder order) throws BaseException{
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //订单表删除记录
            sql = "update tbl_goodsorder set flag=1 where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, order.getOrder_id());
            pst.execute();
            pst.close();
            //订单详情表删除记录
            sql = "update tbl_orderinfo set flag=1 where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, order.getOrder_id());
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
    //判断订单的可用优惠券情况,返回优惠券对象组,若返回null则表示无可用优惠券
    public List<BeanMyCoupon> selectCoupon(BeanUser user, BeanSeller seller) throws BaseException{
        //初始化
        List<BeanMyCoupon> result = new ArrayList<BeanMyCoupon>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //查询是否有可用优惠券
            sql = "select a.coupon_id,a.coupon_amount,a.coupon_count,a.end_date,a.ifTogether from tbl_mycoupon a,tbl_coupon b where user_id=? and seller_id=? and b.coupon_id=a.coupon_id";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            pst.setInt(2, seller.getSeller_id());
            rs = pst.executeQuery();
            while(rs.next()){
                BeanMyCoupon bmc = new BeanMyCoupon();
                bmc.setUser_id(rs.getString(1));
                bmc.setCoupon_id(rs.getInt(2));
                bmc.setCoupon_amount(rs.getInt(3));
                bmc.setCoupon_count(rs.getInt(4));
                bmc.setEnd_date(rs.getTimestamp(5));
                bmc.setSeller_name(rs.getString(6));
                bmc.setIfTogether(rs.getBoolean(7));
                result.add(bmc);
            }
            rs.close();
            pst.close();
            if(result.size() == 0)
                return null;
            else
                return result;
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
    //修改订单配送状态
    public void modifyState(BeanGoodsOrder order, String state) throws BaseException{
        //判空
        if(state == null || "".equals(state)){
            throw new BusinessException("状态为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现状态
            sql = "update tbl_goodsorder set order_state=? where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, state);
            pst.setInt(2, order.getOrder_id());
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
    //订单确认送达
    public void confirmOrder(BeanGoodsOrder order) throws BaseException{
        //判空
        if(order == null){
            throw new BusinessException("订单为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //①改变订单的状态为"确认送达" ②骑手入帐单加入一条 ③骑手单数加1,月收入增加(判断单数来增加)
            sql = "update tbl_goodsorder set order_state=? where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "已送达");
            pst.setInt(2, order.getOrder_id());
            pst.execute();
            //新建入帐单
            //先判断是不是新的月份,如果是的话月收入清零,重新计算
            Date time = null;
            sql = "select * from tbl_rideraccount where rider_id=?" +
                    " and YEAR(finish_time)=Year(now()) and MONTH(finish_time)=MONTH(now())";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,order.getOrder_id());
            rs = pst.executeQuery();
            if(!rs.next()){
                sql = "update tbl_rider set month_income=0,order_count=0 where rider_id=?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql);
                pst2.execute();
                pst2.close();
            }
            //算这单的收入,查询该骑手的单数和身份
            Double income = 0.0;
            int count = 0;//单数
            String status = null;
            sql = "select order_count,rider_status from tbl_rider where rider_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, order.getRider_id());
            rs = pst.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
                status = rs.getString(2);
            }
            if(count < 100)
                income = 2.0;
            else if(count >= 100 && count < 300)
                income = 3.0;
            else if(count >= 300 && count < 450)
                income = 5.0;
            else if(count >= 450 && count < 550)
                income = 6.0;
            else if(count >= 550 && count < 650)
                income = 7.0;
            else
                income = 8.0;
            if(status.equals("新人") && count < 500)
                income += 0.5;
            if(status.equals("新人") && count >= 500)
                income += 1.0;
            sql = "insert into tbl_rideraccount(finish_time,per_income,rider_id,order_id,order_comment) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setDouble(2, income);
            pst.setInt(3, order.getRider_id());
            pst.setInt(4, order.getOrder_id());
            pst.setString(5,"未评价");
            pst.execute();
            //修改骑手信息
            sql = "update tbl_rider set order_count=order_count+1,month_income=month_income+? where rider_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1, income);
            pst.setInt(2, order.getRider_id());
            pst.execute();
            //如果骑手是新人,入职时间超过3个月,月单数超过450,转为正式员工;如果是正式员工,月单数超过1000,转为单王
            sql = "update tbl_rider set rider_status='正式员工' where rider_id=? and rider_status='新人' and order_count>=450";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,order.getRider_id());
            pst.execute();
            sql = "update tbl_rider set rider_status='单王' where rider_id=? and rider_status='正式员工' and order_count>=1000";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,order.getRider_id());
            pst.execute();
            //增加对应商家的销量,更改人均消费
            sql = "update tbl_seller set total_sales=total_sales+1," +
                    "per_cost=((per_cost*total_sales)+?)/total_sales where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1,order.getFinal_price());
            pst.setInt(2,order.getSeller_id());
            pst.execute();
            //更新每件商品的销量
            sql = "select goods_id,goods_quantity from tbl_orderinfo where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,order.getOrder_id());
            rs = pst.executeQuery();
            while(rs.next()){
                sql = "update tbl_goods set goods_sales=goods_sales+? where goods_id=?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql);
                pst2.setInt(1,rs.getInt(2));
                pst2.setInt(2,rs.getInt(1));
                pst2.execute();
                pst2.close();
            }
            conn.commit();
            rs.close();
            pst.close();
        }catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
    //修改骑手
    public void modifyRider(BeanGoodsOrder order, BeanRider rider) throws BaseException{
        //判空
        if(rider == null){
            throw new BusinessException("状态为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现状态
            sql = "update tbl_goodsorder set rider_id=? where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rider.getRider_id());
            pst.setInt(2, order.getOrder_id());
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
    //用户修改订单地址(配送中时可修改,骑手加钱)
    public void modifyAddress(BeanGoodsOrder order, BeanAddress address) throws BaseException{
        //判空
        if(address == null){
            throw new BusinessException("骑手为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现状态
            sql = "update tbl_goodsorder set add_id=? where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, address.getAdd_id());
            pst.setInt(2, order.getOrder_id());
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
    //修改订单状态为"配送中"
    public void modifyDoing(BeanGoodsOrder order) throws BaseException{
        //判空
        if(order == null){
            throw new BusinessException("订单为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现修改
            sql = "update tbl_goodsorder set order_state=? where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "配送中");
            pst.setInt(2, order.getOrder_id());
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
    //修改订单状态为"超时"
    public void modifyOverTime(BeanGoodsOrder order) throws BaseException{
        //判空
        if(order == null){
            throw new BusinessException("订单为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现修改
            sql = "update tbl_goodsorder set order_state=? where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "超时");
            pst.setInt(2, order.getOrder_id());
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
    //修改订单状态为"已取消"
    public void modifyCancel(BeanGoodsOrder order) throws BaseException{
        //判空
        if(order == null){
            throw new BusinessException("订单为空,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //实现修改
            sql = "update tbl_goodsorder set order_state=? where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "已取消");
            pst.setInt(2, order.getOrder_id());
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
    //判断当前用户没有过购买
    public boolean ifBougnt(BeanUser user) throws BaseException{
        //判空
        if(user == null){
            throw new BusinessException("用户信息错误,请重试");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            sql = "select * from tbl_goodsorder where user_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,user.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
                return true;
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
        return false;
    }
}
