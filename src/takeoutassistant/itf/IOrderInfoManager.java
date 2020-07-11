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
    //得到当前购物车某商品的数量(增加商品)
    public int getQuantity(BeanUser user, BeanOrderInfo myCart) throws BaseException;
    //得到当前购物车中选中的商品的库存数(增加商品)
    public int getGoodsQuantity(BeanOrderInfo myCart) throws BaseException;
    //得到当前购物车某商品的数量(添加商品)
    public int getQuantity2(BeanUser user, BeanGoods goods) throws BaseException;
    //得到当前购物车中选中的商品的库存数(添加商品)
    public int getGoodsQuantity2(BeanGoods goods) throws BaseException;
    //购物车增加商品数量
    public void addCount(BeanOrderInfo orderInfo, int count) throws BaseException;
    //购物车减少商品数量
    public void loseCount(BeanOrderInfo orderInfo, int count) throws BaseException;

}
