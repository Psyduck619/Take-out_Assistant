package takeoutassistant.itf;

import takeoutassistant.model.BeanGoodsOrder;
import takeoutassistant.model.BeanRider;
import takeoutassistant.model.BeanRiderAccount;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IRiderAccountManager {

    public void addRiderAccount(BeanRider rider, int order_id) throws BaseException;//增加入账单
    public List<BeanRiderAccount> loadAccount(BeanRider rider) throws BaseException;//显示骑手所有入帐单
    public void deleteRiderAccount(BeanRiderAccount riderAccount) throws BaseException;//删除入帐单
    public void modifyRiderAccount(int riderid, BeanRiderAccount riderAccount) throws BaseException;//修改入帐单的归属
    public void addRiderComment(BeanGoodsOrder order, String comment) throws BaseException;//添加骑手评价
    public boolean isComment(BeanGoodsOrder order) throws BaseException;//判断订单是否已评价骑手

}
