package takeoutassistant.itf;

import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.Date;
import java.util.List;

public interface ICouponManager {

    public void addCoupon(BeanSeller seller, int amount, int request, Date begin, Date end) throws BaseException;//�����Ż�ȯ����
    public List<BeanCoupon> loadCoupon(BeanSeller seller) throws BaseException;//��ʾ�����Ż�ȯ����
    public void deleteCoupon(BeanCoupon coupon) throws BaseException;//ɾ���Ż�ȯ
    public void modifyCoupon(BeanCoupon coupon, int amount, int request, Date begin, Date end) throws BaseException;//�޸��Ż�ȯ

}
