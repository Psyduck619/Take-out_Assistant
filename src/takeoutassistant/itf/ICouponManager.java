package takeoutassistant.itf;

import takeoutassistant.model.BeanAddress;
import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.Date;
import java.util.List;

public interface ICouponManager {

    public void addCoupon(BeanSeller seller, int amount, int request, Date begin, Date end, boolean together) throws BaseException;//增加优惠券种类
    public List<BeanCoupon> loadCoupon(BeanSeller seller) throws BaseException;//显示所有优惠券种类
    public void deleteCoupon(BeanCoupon coupon) throws BaseException;//删除优惠券
    public void modifyCoupon(BeanCoupon coupon, int amount, int request, Date begin, Date end, boolean together) throws BaseException;//修改优惠券
    //判断优惠券是否存在于订单.鸡蛋表或用户优惠券表中
    public boolean ifHavingOrder(BeanCoupon coupon) throws BaseException;

}
