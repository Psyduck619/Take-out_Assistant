package takeoutassistant.itf;

import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IGoodsTypeManager {
    //������Ʒ���
    public BeanGoodsType addGoodsType(BeanSeller seller, String name, int quantity) throws BaseException;
    //��ʾ������Ʒ���
    public List<BeanGoodsType> loadAll() throws BaseException;
    //ɾ����Ʒ���
    public void deleteGoodsType(BeanGoodsType goodstype) throws BaseException;
    //�޸���Ʒ�������
    public void modifyGoodsType(BeanGoodsType goodstype, String name) throws BaseException;
}
