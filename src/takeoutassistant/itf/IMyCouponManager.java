package takeoutassistant.itf;

import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanMyCoupon;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IMyCouponManager {

    public BeanMyCoupon addMyCoupon(String user_id, BeanCoupon coupon) throws BaseException;//新增优惠券
    public List<BeanMyCoupon> loadMyCoupon(BeanUser user) throws BaseException;//显示所有优惠券
    public List<BeanMyCoupon> loadMyCoupon2(BeanUser user, BeanSeller seller) throws BaseException;//显示我的指定商家的优惠券
    //登录时判断优惠券是否过期,过期的话直接删除
    public void ifOVerTime(BeanUser user) throws BaseException;

}
