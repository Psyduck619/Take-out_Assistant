package takeoutassistant.itf;

import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IGoodsCommentManager {

    public void addGoodsComment(BeanOrderInfo orderInfo, String comment, String level) throws BaseException;//添加商品评价
    public List<BeanGoodsComment> loadAll(BeanUser user) throws BaseException;//用户查看所有的商品评价
    //得到所有商品的评价
    public List<BeanGoodsComment> loadGoods(BeanGoods goods) throws BaseException;
    public boolean isComment(BeanOrderInfo orderInfo) throws BaseException;//判断商品是否已评价
    public void deleteComment(BeanGoodsComment goodsComment) throws BaseException;//删除评价

}
