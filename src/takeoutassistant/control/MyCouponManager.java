package takeoutassistant.control;

import takeoutassistant.itf.IMyCouponManager;
import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanMyCoupon;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public class MyCouponManager implements IMyCouponManager {

    //新增优惠券
    public BeanMyCoupon addMyCoupon(BeanUser user, BeanCoupon coupon) throws BaseException{
        return null;
    }
    //显示所有优惠券
    public List<BeanMyCoupon> loadMyCoupon(BeanUser user) throws BaseException{
        return null;
    }

}
