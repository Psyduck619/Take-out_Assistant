package takeoutassistant.control;

import takeoutassistant.itf.IMyJidanManager;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyJidanManager implements IMyJidanManager {

    //���¼�����Ϣ
    public BeanUserJidan addMyJidan(BeanUser user, BeanSeller seller) throws BaseException{
        //�п�
        if(user == null){
            throw new BusinessException("�û�Ϊ��,������");
        }
        if(seller == null){
            throw new BusinessException("�̼�Ϊ��,������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            //��ѯ�Ƿ����м�����Ϣ
            boolean flag = false;
            sql = "select * from tbl_userjidan where seller_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, seller.getSeller_id());
            rs = pst.executeQuery();
            if(rs.next()){
                flag = true;
            }
            rs.close();
            pst.close();
            //�����и����ҵļ�����Ϣ,��Ӧ������+1
            if(flag){
                sql = "update tbl_userjidan set coupon_get=coupon_get+1 where seller_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, seller.getSeller_id());
                pst.execute();
                pst.close();
                //����ﵽ������Ҫ��,���Ӧ�Ż�ȯ+1,���ø���������Ϊ0
            }
            else{
                //�Ȳ�ѯ�õ��̼ҵ������Ż�ȯ���
                List<Integer> list = new ArrayList<>();
                sql = "select coupon_id from tbl_coupon where seller_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1 ,seller.getSeller_id());
                rs = pst.executeQuery();
                String name =null;
                while(rs.next()){
                    list.add(rs.getInt(1));
                }
                rs.close();
                pst.close();
                //��������̼ҵļ���Ҫ�󵽼�����
                for(int i=0 ; i<list.size() ; i++){
                    //�õ�ÿ���Ż�ȯ�ļ�����Ҫ
                    int request = 0;
                    sql = "select coupon_request from tbl_coupon where coupon_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, list.get(i));
                    rs = pst.executeQuery();
                    if(rs.next()){
                        request = rs.getInt(1);
                    }
                    rs.close();
                    pst.close();
                    //�����Ϣ�����ݿ�
                    sql = "insert into tbl_userjidan(user_id,seller_id,coupon_request,coupon_get,coupon_id) values(?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, user.getUser_id());
                    pst.setInt(2, seller.getSeller_id());
                    pst.setInt(3, request);
                    pst.setInt(4,1);
                    pst.setInt(5, list.get(i));
                    pst.execute();
                    pst.close();
                }
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
        return null;
    }
    //��ʾ���м������
    public List<BeanUserJidan> loadMyJidan(BeanUser user) throws BaseException{

        //��ʼ��
        List<BeanUserJidan> result = new ArrayList<BeanUserJidan>();
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            //��ʾ�����ҵļ���
            sql = "select user_id,seller_id,coupon_request,coupon_get,coupon_amount from tbl_userjidan where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanUserJidan buj = new BeanUserJidan();
                buj.setUser_id(rs.getString(1));
                buj.setSeller_id(rs.getInt(2));
                buj.setCoupon_request(rs.getInt(3));
                buj.setCoupon_get(rs.getInt(4));
                buj.setCoupon_amount(rs.getInt(5));
                result.add(buj);
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
