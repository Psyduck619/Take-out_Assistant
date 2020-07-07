package takeoutassistant.itf;

import takeoutassistant.model.BeanRider;
import takeoutassistant.model.BeanRiderAccount;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IRiderAccountManager {

    public void addRiderAccount(BeanRider rider, int order_id) throws BaseException;//�������˵�
    public List<BeanRiderAccount> loadAccount(BeanRider rider) throws BaseException;//��ʾ�����������ʵ�
    public void deleteRiderAccount(BeanRiderAccount riderAccount) throws BaseException;//ɾ�����ʵ�
    public void modifyRiderAccount(int riderid, BeanRiderAccount riderAccount) throws BaseException;//�޸����ʵ��Ĺ���

}
