package takeoutassistant.itf;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
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
    //得到用户个人的订单信息
    public List<BeanGoodsOrder> loadUsers(BeanUser user) throws BaseException;
    //得到所有订单
    public List<BeanGoodsOrder> loadAll() throws BaseException;
    //得到"未配送"订单
    public List<BeanGoodsOrder> loadNoDo() throws BaseException;
    //得到"配送中"订单
    public List<BeanGoodsOrder> loadDing() throws BaseException;
    //得到"确认送达"订单
    public List<BeanGoodsOrder> loadConfirm() throws BaseException;
    //得到"超时"订单
    public List<BeanGoodsOrder> loadOverTime() throws BaseException;
    //得到"已取消"订单
    public List<BeanGoodsOrder> loadCancel() throws BaseException;
    //用户删除订单信息
    public void deleteOrder(BeanGoodsOrder order) throws BaseException;
    //判断订单的可用优惠券情况,返回优惠券对象组,若返回null则表示无可用优惠券
    public List<BeanMyCoupon> selectCoupon(BeanUser user, BeanSeller seller) throws BaseException;
    //修改订单配送状态
    public void modifyState(BeanGoodsOrder order, String state) throws BaseException;
    //订单确认送达
    public void confirmOrder(BeanGoodsOrder order) throws BaseException;
    //修改骑手
    public void modifyRider(BeanGoodsOrder order, BeanRider rider) throws BaseException;
    //用户修改订单地址(配送中时可修改,骑手加钱)
    public void modifyAddress(BeanGoodsOrder order, BeanAddress address) throws BaseException;
    //修改订单状态为"配送中"
    public void modifyDoing(BeanGoodsOrder order) throws BaseException;
    //修改订单状态为"超时"
    public void modifyOverTime(BeanGoodsOrder order) throws BaseException;
    //修改订单状态为"已取消"
    public void modifyCancel(BeanGoodsOrder order) throws BaseException;
    //判断当前用户没有过购买
    public boolean ifBougnt(BeanUser user) throws BaseException;
    //查询用户消费信息
    public List<BeanUserCus> loadUserCus() throws BaseException;

}
