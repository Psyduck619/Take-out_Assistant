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
    //������Ʒ
    public void addGoods(BeanGoodsType type, String name, double price1, double price2) throws BaseException{
        //�ж��Ƿ�ѡ����Ʒ���
        if(type == null){
            throw new BusinessException("��ѡ��һ����Ʒ���");
        }
        //�ж���Ʒ�����Ƿ�Ϸ�
        if(name == null || "".equals(name)){
            throw new BusinessException("��Ʒ���Ʋ���Ϊ��");
        }
        if(name.length() > 20){
            throw new BusinessException("��Ʒ���Ʋ��ܳ���20��");
        }
        //�жϼ۸�
        if(price1 < 0  || price2 < 0){
            throw new BusinessException("��Ʒ�۸���С��0");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ�ֶ�Ӧ���������1
            sql = "update tbl_goodstype set quantity=quantity+1 where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,type.getType_id());
            pst.execute();
            pst.close();
            //ʵ����Ʒ��ӵ����ݿ�
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

    //��ʾ������Ʒ
    public List<BeanGoods> loadGoods(BeanGoodsType type) throws BaseException{
        //��ʼ��
        List<BeanGoods> result = new ArrayList<BeanGoods>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����ʾ��Ӧ���ȫ����Ʒ����
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

    //ɾ����Ʒ
    public void deleteGoods(BeanGoods goods) throws BaseException{
        //�ж��Ƿ�ѡ����ĳһ��Ʒ
        if(goods == null){
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
            pst.setInt(1,goods.getType_id());
            pst.execute();
            pst.close();
            //ʵ��ɾ��
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

    //�޸���Ʒ����.�۸�.�Żݼ�
    public void modifyGoods(BeanGoods goods, String name, double price1, double price2) throws BaseException{
        //�ж��Ƿ�ѡ����Ʒ
        if(goods == null){
            throw new BusinessException("��ѡ��һ����Ʒ");
        }
        //�ж���Ʒ�����Ƿ�Ϸ�
        if(name == null || "".equals(name)){
            throw new BusinessException("��Ʒ���Ʋ���Ϊ��");
        }
        if(name.length() > 20){
            throw new BusinessException("��Ʒ���Ʋ��ܳ���20��");
        }
        //�жϼ۸�
        if(price1 < 0  || price2 < 0){
            throw new BusinessException("��Ʒ�۸���С��0");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����Ʒ��ӵ����ݿ�
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
