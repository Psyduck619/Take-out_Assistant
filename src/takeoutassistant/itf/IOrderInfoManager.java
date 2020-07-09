package takeoutassistant.itf;

import takeoutassistant.control.UserManager;
import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IOrderInfoManager {

    //新增订单信息
    public void addOrderInfo(BeanUser user, BeanGoods goods, int quantity) throws BaseException;
    //显示订单信息
    public List<BeanOrderInfo> loadOrderInfo(BeanUser user) throws BaseException;
    //删除订单信息
    public void deleteOrderInfo(BeanOrderInfo orderInfo) throws BaseException;
    //清空购物车
    public void deleteAll(BeanUser user) throws BaseException;
    //购物车增加商品数量
    public void addCount(BeanOrderInfo orderInfo, int count) throws BaseException;
    //购物车减少商品数量
    public void loseCount(BeanOrderInfo orderInfo, int count) throws BaseException;

}
