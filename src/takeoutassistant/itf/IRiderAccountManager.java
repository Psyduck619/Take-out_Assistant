package takeoutassistant.itf;

import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IRiderAccountManager {

    public void addRiderAccount(BeanRider rider, int order_id) throws BaseException;//�������˵�
    public List<BeanRiderAccount> loadAccount(BeanRider rider) throws BaseException;//��ʾ�����������ʵ�
    public void deleteRiderAccount(BeanRiderAccount riderAccount) throws BaseException;//ɾ�����ʵ�
    public void modifyRiderAccount(int riderid, BeanRiderAccount riderAccount) throws BaseException;//�޸����ʵ��Ĺ���
    public void addRiderComment(BeanGoodsOrder order, String comment) throws BaseException;//�����������
    public boolean isComment(BeanGoodsOrder order) throws BaseException;//�ж϶����Ƿ�����������
    public List<BeanRiderAccount> loadUsers(BeanUser user) throws BaseException;//��ʾ�û�����������

}
