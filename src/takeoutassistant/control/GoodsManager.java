package takeoutassistant.control;

import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.util.BaseException;

import java.util.List;

public class GoodsManager {
    //������Ʒ
    public void addGoods(BeanGoodsType type, String name, double price1, double price2) throws BaseException{

    }

    //��ʾ������Ʒ
    public List<BeanGoods> loadGoods(BeanGoodsType type) throws BaseException{
        return null;
    }

    //ɾ����Ʒ
    public void deleteGoods(BeanGoods goods) throws BaseException{

    }

    //�޸���Ʒ����.�۸�.�Żݼ�
    public void modifyGoods(BeanGoods goods, String name, double price1, double price2) throws BaseException{

    }
}
