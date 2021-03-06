package takeoutassistant.itf;

import takeoutassistant.control.UserManager;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IOrderInfoManager {

    //新增订单信息
    public void addOrderInfo(BeanUser user, BeanGoods goods, int quantity) throws BaseException;
    //显示订单信息(购物车)
    public List<BeanOrderInfo> loadOrderInfo(BeanUser user) throws BaseException;
    //显示订单信息(用户)
    public List<BeanOrderInfo> loadMyOrderInfo(BeanGoodsOrder order) throws BaseException;
    //删除订单信息
    public void deleteOrderInfo(BeanOrderInfo orderInfo) throws BaseException;
    //判断购物车里的商品有没有当前商家以外的
    public boolean isOnly(BeanUser user, BeanSeller curSeller) throws BaseException;
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
    //根据产品购买量为用户推荐产品
    public List<BeanRecommend> Recommend(BeanUser user) throws BaseException;

}
