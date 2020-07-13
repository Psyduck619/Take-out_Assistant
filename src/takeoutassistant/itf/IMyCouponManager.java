package takeoutassistant.itf;

import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanMyCoupon;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IMyCouponManager {

    public BeanMyCoupon addMyCoupon(String user_id, BeanCoupon coupon) throws BaseException;//�����Ż�ȯ
    public List<BeanMyCoupon> loadMyCoupon(BeanUser user) throws BaseException;//��ʾ�����Ż�ȯ
    public List<BeanMyCoupon> loadMyCoupon2(BeanUser user, BeanSeller seller) throws BaseException;//��ʾ�ҵ�ָ���̼ҵ��Ż�ȯ
    //��¼ʱ�ж��Ż�ȯ�Ƿ����,���ڵĻ�ֱ��ɾ��
    public void ifOVerTime(BeanUser user) throws BaseException;

}
