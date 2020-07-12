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

    //�����Ʒ����
    public void addGoodsComment(BeanOrderInfo orderInfo, String comment, String level) throws BaseException{
        //�п�
//        if(comment == null || "".equals(comment)){
//            throw new BusinessException("���۲���Ϊ��");
//        }
        if(level == null || "".equals(level)){
            throw new BusinessException("�Ǽ�����Ϊ��");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //�õ���Ʒ�����̼�ID
            int sellerID = 0;
            sql = "select a.seller_id from tbl_goodstype a,tbl_goods b where b.goods_id=? and a.type_id=b.type_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                sellerID = rs.getInt(1);
            }
            //�õ��Ǽ���double //�Ȳ�����Ʒ���۱�,�ټ��������Ʒ���Ǽ����̼ҵ��Ǽ�
            Double star = 0.0;
            if(level.equals("һ��"))
                star = 1.0;
            else if(level.equals("����"))
                star = 2.0;
            else if(level.equals("����"))
                star = 3.0;
            else if(level.equals("����"))
                star = 4.0;
            else
                star = 5.0;
            //�������۵����ݿ�
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
            //���¶�����������������
            sql = "update tbl_orderinfo set comment=true where goods_id=? and order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            pst.setInt(2,orderInfo.getOrder_id());
            pst.execute();
            //���������Ʒ���Ǽ�
            int goods_sales = 0;
            Double goods_level = 0.0;
            sql = "select goods_level from tbl_goods where goods_id=?";//�ȴ���Ʒ���в�ѯ����Ʒ�Ǽ�
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                goods_level = rs.getDouble(1);
            }
            sql = "select count(*) from tbl_orderinfo where goods_id=? and comment=1";//�Ӷ���������еõ���Ʒ�����۴���(�����۹���)
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            rs = pst.executeQuery();
            if(rs.next()){
                goods_sales = rs.getInt(1);
            }
            goods_level = (goods_level*(goods_sales-1)+star)/(goods_sales);//�����µ��Ǽ�
            sql = "update tbl_goods set goods_level=? where goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setDouble(1,goods_level);
            pst.setInt(2,orderInfo.getGoods_id());
            pst.execute();
            //�����̼ҵ��Ǽ�(��������Ʒ��������/��������Ʒ��)
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
    //�õ������û�������
    public List<BeanGoodsComment> loadAll(BeanUser user) throws BaseException{
        //��ʼ��
        List<BeanGoodsComment> result = new ArrayList<BeanGoodsComment>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //�õ�������������
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
    //�õ�������Ʒ������
    public List<BeanGoodsComment> loadGoods(BeanGoods goods) throws BaseException{
        //��ʼ��
        List<BeanGoodsComment> result = new ArrayList<BeanGoodsComment>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //�õ�������������
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
    //�ж���Ʒ�Ƿ�������
    public boolean isComment(BeanOrderInfo orderInfo) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //��ѯ
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
    //ɾ������(ɾ�겻���ٴ�����)
    public void deleteComment(BeanGoodsComment goodsComment) throws BaseException{
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //��ѯ
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
