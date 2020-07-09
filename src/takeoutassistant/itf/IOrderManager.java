package takeoutassistant.itf;

import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import java.util.Date;
import java.util.List;

public interface IOrderManager {

    //��������
    public void addOrder(BeanUser user, BeanSeller seller, int addressid,
                         Date date, int manjianid, int couponid) throws BaseException;
    //��ʾ�û����ж���
    public List<BeanGoodsOrder> loadOrder(BeanUser user) throws BaseException;
    //�û�ɾ��������Ϣ
    public void deleteOrder(BeanUser user, BeanGoodsOrder order) throws BaseException;
    //�޸Ķ�������״̬
    public void modifyAddress(BeanGoodsOrder order, BeanAddress address) throws BaseException;
    //�û��޸Ķ�����ַ(������ʱ���޸�,���ּ�Ǯ)

}
