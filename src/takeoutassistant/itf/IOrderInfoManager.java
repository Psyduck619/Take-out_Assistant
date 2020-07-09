package takeoutassistant.itf;

import takeoutassistant.control.UserManager;
import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IOrderInfoManager {

    //����������Ϣ
    public void addOrderInfo(BeanUser user, BeanGoods goods, int quantity) throws BaseException;
    //��ʾ������Ϣ
    public List<BeanOrderInfo> loadOrderInfo(BeanUser user) throws BaseException;
    //ɾ��������Ϣ
    public void deleteOrderInfo(BeanOrderInfo orderInfo) throws BaseException;
    //��չ��ﳵ
    public void deleteAll(BeanUser user) throws BaseException;
    //���ﳵ������Ʒ����
    public void addCount(BeanOrderInfo orderInfo, int count) throws BaseException;
    //���ﳵ������Ʒ����
    public void loseCount(BeanOrderInfo orderInfo, int count) throws BaseException;

}
