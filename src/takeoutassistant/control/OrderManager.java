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

    //��������
    public void addOrder(BeanUser user, BeanSeller seller, int addressid,
                         Date date, int manjianid, int couponid) throws BaseException{

    }
    //��ʾ�û����ж���
    public List<BeanGoodsOrder> loadOrder(BeanUser user) throws BaseException{
        return null;
    }
    //�û�ɾ��������Ϣ
    public void deleteOrder(BeanUser user, BeanGoodsOrder order) throws BaseException{

    }
    //�޸Ķ�������״̬
    public void modifyAddress(BeanGoodsOrder order, BeanAddress address) throws BaseException{

    }
    //�û��޸Ķ�����ַ(������ʱ���޸�,���ּ�Ǯ)

}
