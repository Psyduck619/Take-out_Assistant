package takeoutassistant.itf;

import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IGoodsManager {
    //������Ʒ
    public void addGoods(BeanGoodsType type, String name, double price1, double price2, int quantity) throws BaseException;
    //��ʾ������Ʒ
    public List<BeanGoods> loadGoods(BeanGoodsType type) throws BaseException;
    //ɾ����Ʒ
    public void deleteGoods(BeanGoods goods) throws BaseException;
    //�޸���Ʒ����.�۸�.�Żݼ�
    public void modifyGoods(BeanGoods goods, String name, double price1, double price2, int quantity) throws BaseException;
    //��ʾ���Ų�Ʒ(��õ�������Ʒ)
    public List<BeanGoods> loadHGoods(BeanSeller seller) throws BaseException;
    //�������ID�õ��̼���
    public String typeToSeller_name(int type_id) throws BaseException;
    //������ƷID�õ���Ʒ��
    public String idToName(int goods_id) throws BaseException;
    //����Ʒ����ѯ
    public List<BeanGoods> loadForName(String name) throws BaseException;
    //���������ѯ
    public List<BeanGoods> loadForType(String name) throws BaseException;
    //�ж��Ƿ��и���Ʒ�Ķ�������,����,���޷�ɾ��
    public boolean ifHavingOrder(BeanGoods goods) throws BaseException;
}
