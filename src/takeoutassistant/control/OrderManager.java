package takeoutassistant.control;

import takeoutassistant.itf.IOrderManager;
import takeoutassistant.model.BeanAddress;
import takeoutassistant.model.BeanGoodsOrder;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.Date;
import java.util.List;

public class OrderManager implements IOrderManager {

    //新增订单
    public void addOrder(BeanUser user, BeanSeller seller, int addressid,
                         Date date, int manjianid, int couponid) throws BaseException{

    }
    //显示用户所有订单
    public List<BeanGoodsOrder> loadOrder(BeanUser user) throws BaseException{
        return null;
    }
    //用户删除订单信息
    public void deleteOrder(BeanUser user, BeanGoodsOrder order) throws BaseException{

    }
    //修改订单配送状态
    public void modifyAddress(BeanGoodsOrder order, BeanAddress address) throws BaseException{

    }
    //用户修改订单地址(配送中时可修改,骑手加钱)

}
