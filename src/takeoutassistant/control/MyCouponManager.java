package takeoutassistant.control;

import takeoutassistant.itf.IMyCouponManager;
import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanMyCoupon;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public class MyCouponManager implements IMyCouponManager {

    //�����Ż�ȯ
    public BeanMyCoupon addMyCoupon(BeanUser user, BeanCoupon coupon) throws BaseException{
        return null;
    }
    //��ʾ�����Ż�ȯ
    public List<BeanMyCoupon> loadMyCoupon(BeanUser user) throws BaseException{
        return null;
    }

}
