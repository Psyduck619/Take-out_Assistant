package takeoutassistant.control;

import takeoutassistant.itf.IOrderManager;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class OrderManager implements IOrderManager {

    //�õ���ǰ�Ķ�����
    public int getOrderID(BeanUser user) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            int ID = 0;
            //����ԭ��
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
    //����ԭ��
    public double getPrice1(int orderID) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            double price = 0.0;
            conn = DBUtil.getConnection();
            //����ԭ��
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
    //�����Ա���Ż�
    public double getVIP_Discount(int orderID) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            double price = 0.0;
            conn = DBUtil.getConnection();
            //�����Ա���Ż�
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
    //���㵱ǰ�ʺϵ�����
    public BeanManjian getManjian(BeanSeller seller, double price) throws BaseException{
        //��ʼ��
        BeanManjian manjian = new BeanManjian();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�õ���������ֵ
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
            //����õ����ʺϵ�����ֵ
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
            if(amount == 0) //û�з��ϵ�����,ֱ�ӷ���null
                return null;
            //������������
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
    //�������ռ۸�
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
    //��������
    public void addOrder(int orderID, BeanUser user, BeanSeller seller, BeanAddress address, double price1, double price2,
                         Date request_date, BeanManjian manjian, BeanMyCoupon coupon) throws BaseException{
        if(orderID == 0){
            throw new BusinessException("���ﳵΪ��,�޷��µ�");
        }
        //price2 -= coupon.getCoupon_amount();
        //�����ָ���쳣
        System.out.println(coupon.getCoupon_amount());
        if(coupon == null){
            System.out.println("�Ż�ȯΪ��");
            coupon = new BeanMyCoupon();
            coupon.setCoupon_id(-1);
            coupon.setCoupon_amount(0);
        }
        if(manjian == null){
            System.out.println("����Ϊ��");
            manjian = new BeanManjian();
            manjian.setManjian_id(-1);
            manjian.setDiscount_amount(0);
        }
        //��ʼ��
        //int riderID = 0;//��Ҫ���ַ����㷨(�������ֶ��ӵ�)
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); //�������
            //������ӵ����ݿ�
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
            pst.setString(9,"������");
            pst.setString(10,user.getUser_id());
            pst.setInt(11,seller.getSeller_id());
            pst.execute();
            //�޸Ķ���������е����ⶩ��Ϊ��ʵ����
            sql = "update tbl_orderinfo set done=true where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderID);
            pst.execute();
            //ɾ���û����Ż�ȯ,�Ȳ�ѯ,����Ż�ȯ����Ϊ1,��ֱ��ɾ��,��Ȼ�ͼ�һ
            sql = "delete from tbl_mycoupon where coupon_id=? and user_id=? and coupon_count=1"; //һ���Ż�ȯֱ��ɾ��
            pst = conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
            pst.setString(2,user.getUser_id());
            pst.execute();
            sql = "update tbl_mycoupon set coupon_count=coupon_count-1 where coupon_id=? and user_id=? and coupon_count>1"; //�������һ
            pst = conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
            pst.setString(2,user.getUser_id());
            pst.execute();
            //���Ӷ�Ӧ�̼ҵ�����,�����˾�����
            sql = "update tbl_seller set total_sales=total_sales+1,per_cost=((per_cost*total_sales)+?)/(total_sales+1) where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1,price2);
            pst.setInt(2,seller.getSeller_id());
            pst.execute();

//            //�����û��ļ����� //���̼���,������,��ȫ����ӽ�ȥ����1���̼���,������,�Ҽ�1��û�ﵽҪ��,��ֱ�Ӽ�1
//            //���̼���,������,�Ҽ�1��ﵽҪ��,��ɾ������������Ϣ,��Ӧ�Ż�ȯ��1���̼��б�����,������ֻ��1,��ôֱ���Ż�ȯ��1
//            List<Integer> array = new ArrayList<Integer>();
//            //�Ȳ�ѯ���̼����е��Ż�ȯID
//            sql = "select coupon_id from tbl_coupon where seller_id=?";
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1,seller.getSeller_id());
//            rs = pst.executeQuery();
//            while(rs.next()){ //�õ������Ż�ȯID
//                array.add(rs.getInt(1));
//            }
//            //��ʼѭ����ѯÿһ���Ż�ȯ��Ӧ�����
//            for(int i = 0 ; i < array.size() ; i++){
//                //�Ȳ�ѯ�Ż�ȯ���������ǲ���1,�ǵĻ�ֱ���Ż�ȯ+1
//                sql = "select coupon_request from tbl_coupon where coupon_id=?";
//                pst = conn.prepareStatement(sql);
//                pst.setInt(1,array.get(i));
//                rs = pst.executeQuery();
//                if(rs.next()){
//                    if(rs.getInt(1) == 1){
//                        BeanCoupon c = new BeanCoupon();//����һ���Ż�ȯ����
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
//                        new MyCouponManager().addMyCoupon(user, c);//���÷��������Ż�ȯ
//                        continue;
//                    }
//                }
//                //�ٲ�ѯ����������û�и��Ż�ȯ
//                sql = "select * from tbl_userjidan where coupon_id=?";
//                pst = conn.prepareStatement(sql);
//                pst.setInt(1,array.get(1));
//                rs = pst.executeQuery();
//                if(!rs.next()){  //�������û�еĻ�,�����
//                    //��ѯ�����Ż�ȯ��������.�Żݽ��
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
//                else{ //��������еĻ�,��������1
//                    //�Ȳ�ѯ�ǲ���ֻ��1
//                    sql = "select coupon_request-coupon_get from tbl_userjidan where coupon_id=?";
//                    pst = conn.prepareStatement(sql);
//                    pst.setInt(1,array.get(1));
//                    rs = pst.executeQuery();
//                    if(rs.next()){
//                        if (rs.getInt(1) == 1){ //ֱ��ɾ������������Ϣ,��Ӧ�Ż�ȯ��1
//                            BeanCoupon c = new BeanCoupon();//����һ���Ż�ȯ����
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
//                            new MyCouponManager().addMyCoupon(user, c);//���÷��������Ż�ȯ
//                            sql = "delete from tbl_userjidan where coupon_id=?";
//                            pst = conn.prepareStatement(sql);
//                            pst.setInt(1,array.get(1));
//                            pst.execute();
//                            continue;
//                        }
//                        else{ //һ�����,�򼯵�����1
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
            e.printStackTrace();
        } finally {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //��ʾ�û����ж���
    public List<BeanGoodsOrder> loadOrder(BeanUser user) throws BaseException{
        return null;
    }
    //�û�ɾ��������Ϣ
    public void deleteOrder(BeanGoodsOrder order) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //������ɾ����¼
            sql = "delete from tbl_goodsorder where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, order.getOrder_id());
            pst.execute();
            pst.close();
            //���������ɾ����¼
            sql = "delete from tbl_orderinfo where order_id=?";
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
    //�ж϶����Ŀ����Ż�ȯ���,�����Ż�ȯ������,������null���ʾ�޿����Ż�ȯ
    public List<BeanMyCoupon> selectCoupon(BeanUser user, BeanSeller seller) throws BaseException{
        //��ʼ��
        List<BeanMyCoupon> result = new ArrayList<BeanMyCoupon>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //��ѯ�Ƿ��п����Ż�ȯ
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
    //�޸Ķ�������״̬
    public void modifyState(BeanGoodsOrder order, String state) throws BaseException{
        //�п�
        if(state == null || "".equals(state)){
            throw new BusinessException("״̬Ϊ��,������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��״̬
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
    //�޸�����
    public void modifyRider(BeanGoodsOrder order, BeanRider rider) throws BaseException{
        //�п�
        if(rider == null){
            throw new BusinessException("״̬Ϊ��,������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��״̬
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
    //�û��޸Ķ�����ַ(������ʱ���޸�,���ּ�Ǯ)
    public void modifyAddress(BeanGoodsOrder order, BeanAddress address) throws BaseException{
        //�п�
        if(address == null){
            throw new BusinessException("����Ϊ��,������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��״̬
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
}
