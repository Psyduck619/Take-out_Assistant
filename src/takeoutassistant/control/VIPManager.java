package takeoutassistant.control;

import takeoutassistant.itf.IVIPManager;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Pattern;

public class VIPManager implements IVIPManager {

    //��ͨ��Ա(һ���°�30����)//3���¾���//12����8��
    public double openVIP(BeanUser user, int month) throws BaseException{
        //�ж��Ƿ����ǻ�Ա
        if(user.isVIP()){
            throw new BusinessException("���������Ļ�Ա�û�");
        }
        //�п�
        if(user == null){
            throw new BusinessException("�û�Ϊ��,������");
        }
        //�ж������Ƿ����
        if(month < 0){
            throw new BusinessException("��������������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        double money = 10 * month;
        if(month == 3)
            money = money * 0.9;
        else if(month == 12)
            money = money * 0.8;
        try {
            conn = DBUtil.getConnection();
            //���»�Ա��Ϣ
            sql = "update tbl_user set VIP=?,VIP_end_time=? where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setBoolean(1, true);
            pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis() + (3600 * 24 * 30 * month * 1000L)));
            pst.setString(3, user.getUser_id());
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
        return money;
    }
    //���ѻ�Ա(һ���°�30����)//3���¾���//12����8��
    public double renewVIP(BeanUser user, int month) throws BaseException{
        //�ж��Ƿ����ǻ�Ա
        if(!user.isVIP()){
            throw new BusinessException("�������ǻ�Ա,����ѡ��ͨ��Ա");
        }
        //�п�
        if(user == null){
            throw new BusinessException("�û�Ϊ��,������");
        }
        //�ж������Ƿ����
        if(month < 0){
            throw new BusinessException("��������������");
        }
        //��ʼ��
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        double money = 10 * month;
        if(month == 3)
            money = money * 0.9;
        else if(month == 12)
            money = money * 0.8;
        try {
            conn = DBUtil.getConnection();
            //�õ�Ŀǰ�Ļ�Ա����ʱ��
            long d = 0;
            sql = "select VIP_end_time from tbl_user where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUser_id());
            rs = pst.executeQuery();
            if(rs.next()){
                d = rs.getTimestamp(1).getTime();
            }
            //���»�Ա��Ϣ
            sql = "update tbl_user set VIP_end_time=? where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(d + (3600 * 24 * 30 * month * 1000L)));
            pst.setString(2, user.getUser_id());
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
        return money;
    }

}
