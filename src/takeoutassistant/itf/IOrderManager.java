package takeoutassistant.itf;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import java.util.Date;
import java.util.List;

public interface IOrderManager {
    //�õ���ǰ�Ķ�����
    public int getOrderID(BeanUser user) throws BaseException;
    //����ԭ��
    public double getPrice1(int orderID) throws BaseException;
    //�����Ա�Ż�
    public double getVIP_Discount(int orderID) throws BaseException;
    //���㵱ǰ�ʺϵ�����,ֱ�ӷ���String��ʾ������Ϣ
    public BeanManjian getManjian(BeanSeller seller, double price) throws BaseException;
    //�������ռ۸�
    public double getPrice(double price, double vip, BeanManjian manjian, BeanMyCoupon coupon) throws BaseException;
    //��������
    public void addOrder(int orderID, BeanUser user, BeanSeller seller, BeanAddress address, double price1, double price2,
                         Date request_date, BeanManjian manjian, BeanMyCoupon coupon) throws BaseException;
    //�õ��û����˵Ķ�����Ϣ
    public List<BeanGoodsOrder> loadUsers(BeanUser user) throws BaseException;
    //�õ����ж���
    public List<BeanGoodsOrder> loadAll() throws BaseException;
    //�õ�"δ����"����
    public List<BeanGoodsOrder> loadNoDo() throws BaseException;
    //�õ�"������"����
    public List<BeanGoodsOrder> loadDing() throws BaseException;
    //�õ�"ȷ���ʹ�"����
    public List<BeanGoodsOrder> loadConfirm() throws BaseException;
    //�õ�"��ʱ"����
    public List<BeanGoodsOrder> loadOverTime() throws BaseException;
    //�õ�"��ȡ��"����
    public List<BeanGoodsOrder> loadCancel() throws BaseException;
    //�û�ɾ��������Ϣ
    public void deleteOrder(BeanGoodsOrder order) throws BaseException;
    //�ж϶����Ŀ����Ż�ȯ���,�����Ż�ȯ������,������null���ʾ�޿����Ż�ȯ
    public List<BeanMyCoupon> selectCoupon(BeanUser user, BeanSeller seller) throws BaseException;
    //�޸Ķ�������״̬
    public void modifyState(BeanGoodsOrder order, String state) throws BaseException;
    //����ȷ���ʹ�
    public void confirmOrder(BeanGoodsOrder order) throws BaseException;
    //�޸�����
    public void modifyRider(BeanGoodsOrder order, BeanRider rider) throws BaseException;
    //�û��޸Ķ�����ַ(������ʱ���޸�,���ּ�Ǯ)
    public void modifyAddress(BeanGoodsOrder order, BeanAddress address) throws BaseException;
    //�޸Ķ���״̬Ϊ"������"
    public void modifyDoing(BeanGoodsOrder order) throws BaseException;
    //�޸Ķ���״̬Ϊ"��ʱ"
    public void modifyOverTime(BeanGoodsOrder order) throws BaseException;
    //�޸Ķ���״̬Ϊ"��ȡ��"
    public void modifyCancel(BeanGoodsOrder order) throws BaseException;
    //�жϵ�ǰ�û�û�й�����
    public boolean ifBougnt(BeanUser user) throws BaseException;
    //��ѯ�û�������Ϣ
    public List<BeanUserCus> loadUserCus() throws BaseException;

}
