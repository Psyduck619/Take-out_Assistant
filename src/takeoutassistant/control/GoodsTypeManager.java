package takeoutassistant.control;

import takeoutassistant.itf.IGoodsTypeManager;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public class GoodsTypeManager implements IGoodsTypeManager {

    //������Ʒ���
    public BeanGoodsType addGoodsType(BeanSeller seller, String name, int quantity) throws BaseException {

        return null;
    }

    //��ʾ������Ʒ���
    public List<BeanGoodsType> loadTypes(BeanSeller seller) throws BaseException {

        return null;
    }

    //ɾ����Ʒ���
    public void deleteGoodsType(BeanGoodsType goodstype) throws BaseException {

    }

    //�޸���Ʒ�������
    public void modifyGoodsType(BeanGoodsType goodstype, String name) throws BaseException {

    }

}
