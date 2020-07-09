package takeoutassistant.itf;

import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanMyCoupon;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IMyCouponManager {

    public BeanMyCoupon addMyCoupon(BeanUser user, BeanCoupon coupon) throws BaseException;//新增优惠券
    public List<BeanMyCoupon> loadMyCoupon(BeanUser user) throws BaseException;//显示所有优惠券
    public List<BeanMyCoupon> loadMyCoupon2(BeanUser user, BeanSeller seller) throws BaseException;//显示我的指定商家的优惠券

}
