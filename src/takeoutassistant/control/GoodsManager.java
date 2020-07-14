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

    //������Ʒ
    public void addGoods(BeanGoodsType type, String name, double price1, double price2, int quantity) throws BaseException {
        //�ж��Ƿ�ѡ����Ʒ���
        if (type == null) {
            throw new BusinessException("��ѡ��һ����Ʒ���");
        }
        //�ж���Ʒ�����Ƿ�Ϸ�
        if (name == null || "".equals(name)) {
            throw new BusinessException("��Ʒ���Ʋ���Ϊ��");
        }
        if (name.length() > 20) {
            throw new BusinessException("��Ʒ���Ʋ��ܳ���20��");
        }
        //�жϼ۸�
        if (price1 < 0 || price2 < 0) {
            throw new BusinessException("��Ʒ�۸���С��0");
        }
        //�ж������Ƿ����
        if(quantity < 0){
            throw new BusinessException("��Ʒ��������С��0");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //ʵ�ֶ�Ӧ���������1
            sql = "update tbl_goodstype set quantity=quantity+1 where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, type.getType_id());
            pst.execute();
            pst.close();
            //ʵ����Ʒ��ӵ����ݿ�
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
    //��ʾ������Ʒ
    public List<BeanGoods> loadGoods(BeanGoodsType type) throws BaseException {
        //��ʼ��
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����ʾ��Ӧ���ȫ����Ʒ����
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
    //ɾ����Ʒ
    public void deleteGoods(BeanGoods goods) throws BaseException {
        //�ж��Ƿ�ѡ����ĳһ��Ʒ
        if (goods == null) {
            throw new BusinessException("��ѡ��һ����Ʒ");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�ֶ�Ӧ��������Ӽ�1
            sql = "update tbl_goodstype set quantity=quantity-1 where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods.getType_id());
            pst.execute();
            pst.close();
            //ʵ��ɾ��
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
    //�޸���Ʒ����.�۸�.�Żݼ�
    public void modifyGoods(BeanGoods goods, String name, double price1, double price2, int quantity) throws BaseException {
        //�ж��Ƿ�ѡ����Ʒ
        if (goods == null) {
            throw new BusinessException("��ѡ��һ����Ʒ");
        }
        //�ж���Ʒ�����Ƿ�Ϸ�
        if (name == null || "".equals(name)) {
            throw new BusinessException("��Ʒ���Ʋ���Ϊ��");
        }
        if (name.length() > 20) {
            throw new BusinessException("��Ʒ���Ʋ��ܳ���20��");
        }
        //�жϼ۸�
        if (price1 < 0 || price2 < 0) {
            throw new BusinessException("��Ʒ�۸���С��0");
        }
        //�ж�����
        if(quantity < 0){
            throw new BusinessException("��Ʒ��������С��0");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����Ʒ��Ϣ�����ݿ�
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
    //��ʾ���Ų�Ʒ(��õ�������Ʒ)
    public List<BeanGoods> loadHGoods(BeanSeller seller) throws BaseException {
        //��ʼ��
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����ʾ��Ӧ���ȫ����Ʒ����
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
    //�������ID�õ��̼���
    public String typeToSeller_name(int type_id) throws BaseException{
        //��ʼ��
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
    //������ƷID�õ���Ʒ��
    public String idToName(int goods_id) throws BaseException{
        //��ʼ��
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
    //����Ʒ����ѯ
    public List<BeanGoods> loadForName(String name) throws BaseException{
        //��ʼ��
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��sql��ѯ
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
    //���������ѯ
    public List<BeanGoods> loadForType(String name) throws BaseException{
        //��ʼ��
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��sql��ѯ
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
    //�ж��Ƿ��и���Ʒ�Ķ�������,����,���޷�ɾ��
    public boolean ifHavingOrder(BeanGoods goods) throws BaseException{
        //��ʼ��
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