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
            conn.setAutoCommit(false);
            //��ѯ���ﳵ����û����Ʒ,��û��,�����¶���,����,����ͬ��������½��в���
            sql = "select * from tbl_orderinfo where user_id=? and done=0";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            rs = pst.executeQuery();
            if(!rs.next()){  //���ﳵΪ��,�������µ����ⶩ��
                //�����ݿ�����½������ⶩ��(done==0)
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
    //��ʾ������Ϣ(���ﳵ)
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
    //�жϹ��ﳵ�����Ʒ��û�е�ǰ�̼������
    public boolean isOnly(BeanUser user, BeanSeller curSeller) throws BaseException{
        //�п�
        if(curSeller == null){
            throw new BaseException("�̼���Ϣ����,������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //�Ȳ�ѯ����ǰ�û����ﳵ�����ж���
            sql = "select goods_id from tbl_orderinfo where user_id=? and done=0";
            pst = conn.prepareStatement(sql);
            pst.setString(1,user.getUser_id());
            rs = pst.executeQuery();
            while(rs.next()){
                //��ѯ��ǰ��ƷID�������̼�
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
    //��ʾ������Ϣ(�û�)
    public List<BeanOrderInfo> loadMyOrderInfo(BeanGoodsOrder order) throws BaseException{
        //��ʼ��
        List<BeanOrderInfo> result = new ArrayList<BeanOrderInfo>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //��ʾ�û�����������Ϣ(��Ʒ��,��Ʒ����,����,ÿ���Ż�,�ܼ�)
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
    //���ݲ�Ʒ������Ϊ�û��Ƽ���Ʒ
    public List<BeanRecommend> Recommend(BeanUser user) throws BaseException{
        //��ʼ��
        List<BeanRecommend> result = new ArrayList<BeanRecommend>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //���ݹ�����������Ʒ
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
