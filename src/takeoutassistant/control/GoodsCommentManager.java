package takeoutassistant.control;

import takeoutassistant.itf.IGoodsCommentManager;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class GoodsCommentManager implements IGoodsCommentManager {

    //添加商品评价
    public void addGoodsComment(BeanOrderInfo orderInfo, String comment, String level) throws BaseException{
        //判空
        if(comment == null || "".equals(comment)){
            throw new BusinessException("评价不能为空");
        }
        if(level == null || "".equals(level)){
            throw new BusinessException("星级不能为空");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            //得到星级的double //先插入商品评价表,再计算更新商品的星级和商家的星级
            Double star = 0.0;
            if(level.equals("一星"))
                star = 1.0;
            else if(level.equals("二星"))
                star = 2.0;
            else if(level.equals("三星"))
                star = 3.0;
            else if(level.equals("四星"))
                star = 4.0;
            else
                star = 5.0;
            //插入评价到数据库
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
