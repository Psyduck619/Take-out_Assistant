package takeoutassistant.itf;

import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import java.util.Date;
import java.util.List;

public interface IOrderManager {
    //得到当前的订单号
    public int getOrderID(BeanUser user) throws BaseException;
    //计算原价
    public double getPrice1(int orderID) throws BaseException;
    //计算会员优惠
    public double getVIP_Discount(int orderID) throws BaseException;
    //计算当前适合的满减,直接返回String显示满减信息
    public BeanManjian getManjian(BeanSeller seller, double price) throws BaseException;
    //计算最终价格
    public double getPrice(double price, double vip, BeanManjian manjian, BeanMyCoupon coupon) throws BaseException;
    //新增订单
    public void addOrder(int orderID, BeanUser user, BeanSeller seller, BeanAddress address, double price1, double price2,
                         Date request_date, BeanManjian manjian, BeanMyCoupon coupon) throws BaseException;
    //显示用户所有订单
    public List<BeanGoodsOrder> loadOrder(BeanUser user) throws BaseException;
    //用户删除订单信息
    public void deleteOrder(BeanGoodsOrder order) throws BaseException;
    //判断订单的可用优惠券情况,返回优惠券对象组,若返回null则表示无可用优惠券
    public List<BeanMyCoupon> selectCoupon(BeanUser user, BeanSeller seller) throws BaseException;
    //修改订单配送状态
    public void modifyState(BeanGoodsOrder order, String state) throws BaseException;
    //修改骑手
    public void modifyRider(BeanGoodsOrder order, BeanRider rider) throws BaseException;
    //用户修改订单地址(配送中时可修改,骑手加钱)
    public void modifyAddress(BeanGoodsOrder order, BeanAddress address) throws BaseException;

}
