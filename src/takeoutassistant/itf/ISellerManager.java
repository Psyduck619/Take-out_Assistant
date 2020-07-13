package takeoutassistant.itf;

import takeoutassistant.model.BeanSeller;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface ISellerManager {
    //����̼�
    public BeanSeller addSeller(String name) throws BaseException;
    //��ʾ�����̼�
    public List<BeanSeller> loadAll() throws BaseException;
    //ɾ���̼�
    public void deleteSeller(BeanSeller seller) throws BaseException;
    //�޸��̼�����
    public void modifyName(BeanSeller seller, String name) throws BaseException;
    //��ʾĳ�Ǽ��̼�
    public List<BeanSeller> loadLevel(int level) throws BaseException;
    //�����̼ұ�ŵõ��̼���
    public String getSellerName(int seller) throws BaseException;
    //�ж��Ƿ��и��̼ҵĶ�������,����,���޷�ɾ��
    public boolean ifHavingOrder(BeanSeller seller) throws BaseException;
}
