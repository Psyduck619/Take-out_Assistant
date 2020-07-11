package takeoutassistant.itf;

import takeoutassistant.model.BeanGoodsOrder;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.util.BaseException;

public interface IGoodsCommentManager {

    public void addGoodsComment(BeanOrderInfo orderInfo, String comment, String level) throws BaseException;//添加商品评价

}
