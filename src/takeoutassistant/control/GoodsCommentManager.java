package takeoutassistant.control;

import takeoutassistant.itf.IGoodsCommentManager;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class GoodsCommentManager implements IGoodsCommentManager {

    //�����Ʒ����
    public void addGoodsComment(BeanOrderInfo orderInfo, String comment, String level) throws BaseException{
        //�п�
        if(comment == null || "".equals(comment)){
            throw new BusinessException("���۲���Ϊ��");
        }
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
            sql = "insert into tbl_goodscomment(goods_id,user_id,com_content,com_date,com_level,com_picture) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orderInfo.getGoods_id());
            pst.setString(2,orderInfo.getUser_id());
            pst.setString(3,comment);
            pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setDouble(5,star);
            pst.setLong(6,0);
            pst.execute();
            pst.close();
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
