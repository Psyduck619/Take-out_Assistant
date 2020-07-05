package takeoutassistant.control;

import takeoutassistant.itf.IGoodsTypeManager;
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

public class GoodsTypeManager implements IGoodsTypeManager {

    //������Ʒ���
    public void addGoodsType(BeanSeller seller, String name, int quantity) throws BaseException {
        //�ж��Ƿ�ѡ���̼�
        if(seller == null){
            throw new BusinessException("��ѡ��һ���̼�");
        }
        //�ж���������Ƿ����
        if(name == null || "".equals(name)){
            throw new BusinessException("������Ʋ���Ϊ��");
        }
        if(name.length() > 20){
            throw new BusinessException("������Ʋ��ܳ���20��");
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
            //ʵ�������ӵ����ݿ�
            sql = "insert into tbl_goodstype(seller_id,type_name,quantity) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,seller.getSeller_id());
            pst.setString(2,name);
            pst.setInt(3,quantity);
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

    //��ʾ������Ʒ���
    public List<BeanGoodsType> loadTypes(BeanSeller seller) throws BaseException {
        //��ʼ��
        List<BeanGoodsType> result = new ArrayList<BeanGoodsType>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����ʾȫ�������
            sql = "select type_id,seller_id,type_name,quantity from tbl_goodstype where seller_id=? order by type_id";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanGoodsType gt = new BeanGoodsType();
                gt.setType_id(rs.getInt(1));
                gt.setSeller_id(rs.getInt(2));
                gt.setType_name(rs.getString(3));
                gt.setQuantity(rs.getInt(4));
                result.add(gt);
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

    //ɾ����Ʒ���
    public void deleteGoodsType(BeanGoodsType goodstype) throws BaseException {
        //�ж��Ƿ�ѡ����ĳһ���
        if(goodstype == null){
            throw new BusinessException("��ѡ������һ�����");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ��ɾ��
            sql = "delete from tbl_goodstype where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goodstype.getSeller_id());
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

    //�޸���Ʒ�������
    public void modifyGoodsType(BeanGoodsType goodstype, String name) throws BaseException {
        //�ж��Ƿ�ѡ��ĳ���
        if(goodstype == null){
            throw new BusinessException("��ѡ��һ�����");
        }
        //�ж���������Ƿ����
        if(name == null || "".equals(name)){
            throw new BusinessException("������Ʋ���Ϊ��");
        }
        if(name.length() > 20){
            throw new BusinessException("������Ʋ��ܳ���20��");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //ʵ����Ʒ����޸�
            sql = "update tbl_goodstype set type_name=? where type_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setInt(2, goodstype.getType_id());
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
