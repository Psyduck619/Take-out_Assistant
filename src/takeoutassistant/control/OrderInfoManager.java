package takeoutassistant.control;

import takeoutassistant.itf.IOrderInfoManager;
import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanMyCoupon;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.model.BeanUser;
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

    //����������Ϣ
    public void addOrderInfo(BeanUser user, BeanGoods goods, int quantity) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        boolean vip = user.isVIP(); //��ѯ�û��ǲ��ǻ�Ա
        Double price = 0.0;
        Double per_discount = 0.0;//�Żݽ��
        if(vip){
            price = goods.getDiscount_price();
            per_discount = goods.getPrice() - goods.getDiscount_price();
        }
        else{
            price = goods.getPrice();
        }
        try {
            conn = DBUtil.getConnection();
            //��ѯ���ﳵ����û����Ʒ,��û��,�����¶���,����,����ͬ��������½��в���
            sql = "select * from tbl_orderinfo where user_id=? and done=0";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            rs = pst.executeQuery();
            if(!rs.next()){  //���ﳵΪ��,�������µ����ⶩ��
                //�����ݿ�����½������ⶩ��(done==0)
                sql = "insert into tbl_orderinfo(goods_id,goods_quantity,goods_price,per_discount,user_id,done,goods_name) values(?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,goods.getGoods_id());
                pst.setInt(2,quantity);
                pst.setDouble(3,price);
                pst.setDouble(4,per_discount);
                pst.setString(5,user.getUser_id());
                pst.setBoolean(6,false);
                pst.setString(7,goods.getGoods_name());
                pst.execute();
                rs.close();
                pst.close();
            }
            else{  //���ﳵ�ǿ���ͬһ����������½������
                //�Ȳ�ѯ���������
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
                //��ѯ����Ʒ�Ƿ��Ѵ��ڹ��ﳵ��
                sql = "select * from tbl_orderinfo where order_id=? and goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,orderID);
                pst.setInt(2,goods.getGoods_id());
                rs = pst.executeQuery();
                if(rs.next()){ //��Ʒ�Ѵ��ڹ��ﳵ��,������Ʒ����
                    sql = "update tbl_orderinfo set goods_quantity=goods_quantity+? where order_id=? and goods_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1,quantity);
                    pst.setInt(2,orderID);
                    pst.setInt(3,goods.getGoods_id());
                    pst.execute();
                }
                else{  //��Ʒ���ڹ��ﳵ��,�����Ʒ
                    sql = "insert into tbl_orderinfo(order_id,goods_id,goods_quantity,goods_price," +
                            "per_discount,user_id,done,goods_name) values(?,?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1,orderID);
                    pst.setInt(2,goods.getGoods_id());
                    pst.setInt(3,quantity);
                    pst.setDouble(4,price);
                    pst.setDouble(5,per_discount);
                    pst.setString(6,user.getUser_id());
                    pst.setBoolean(7,false);
                    pst.setString(8,goods.getGoods_name());
                    pst.execute();
                }
                rs.close();
                pst.close();
            }
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
    //��ʾ������Ϣ
    public List<BeanOrderInfo> loadOrderInfo(BeanUser user) throws BaseException{
        //��ʼ��
        List<BeanOrderInfo> result = new ArrayList<BeanOrderInfo>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //��ʾ�û�Ŀǰ�Ĺ��ﳵ��Ϣ(��Ʒ��,��Ʒ����,����,ÿ���Ż�,�ܼ�)
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
    //ɾ������������Ϣ
    public void deleteOrderInfo(BeanOrderInfo orderInfo) throws BaseException{
        //�п�
        if(orderInfo == null){
            throw new BaseException("��Ʒ��Ϣ����,������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��ɾ��
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
    //�õ���ǰ���ﳵĳ��Ʒ������
    public int getQuantity(BeanUser user, BeanOrderInfo myCart) throws BaseException{
        //�п�
        if(user == null){
            try {
                throw new BusinessException("�û�Ϊ��,������");
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        if(myCart == null)
            return 0;
        //��ʼ��
        int quantity = 0;
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�õ�����
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
    //�õ���ǰ���ﳵ��ѡ�е���Ʒ�Ŀ����
    public int getGoodsQuantity(BeanOrderInfo myCart) throws BaseException{
        //�п�
        if(myCart == null)
            return 0;
        //��ʼ��
        int quantity = 0;
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�õ�����
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
    //�õ���ǰ���ﳵĳ��Ʒ������(�����Ʒ)
    public int getQuantity2(BeanUser user, BeanGoods goods) throws BaseException{
        //�п�
        if(user == null){
            try {
                throw new BusinessException("�û�Ϊ��,������");
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        if(goods == null)
            return 0;
        //��ʼ��
        int quantity = 0;
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�õ�����
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
    //�õ���ǰ���ﳵ��ѡ�е���Ʒ�Ŀ����(�����Ʒ)
    public int getGoodsQuantity2(BeanGoods goods) throws BaseException{
        //�п�
        if(goods == null)
            return 0;
        //��ʼ��
        int quantity = 0;
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�õ�����
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
    //��չ��ﳵ
    public void deleteAll(BeanUser user) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��ɾ��
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
    //���ﳵ������Ʒ����
    public void addCount(BeanOrderInfo orderInfo, int count) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����Ʒ��������
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
    //���ﳵ������Ʒ����
    public void loseCount(BeanOrderInfo orderInfo, int count) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�Ȳ�ѯĿǰ�ж��ټ���Ʒ
            int quantity = 0;
            sql = "select goods_quantity from tbl_orderinfo where order_id=? and goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderInfo.getOrder_id());
            pst.setInt(2, orderInfo.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
            }
            if(count < quantity){  //�����������С��������,��������
                sql = "update tbl_orderinfo set goods_quantity=goods_quantity-? where order_id=? and goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, count);
                pst.setInt(2, orderInfo.getOrder_id());
                pst.setInt(3, orderInfo.getGoods_id());
                pst.execute();
                pst.close();
            }
            else if(count == quantity){  //���������������������,ֱ��ɾ��������Ʒ
                sql = "delete from tbl_orderinfo where order_id=? and goods_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, orderInfo.getOrder_id());
                pst.setInt(2, orderInfo.getGoods_id());
                pst.execute();
                pst.close();
            }
            else{  //�����������С��������,���д�����ʾ
                throw new BaseException("��������С����������,����������");
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

}
