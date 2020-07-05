package takeoutassistant.control;

import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.util.BaseException;

import java.util.List;

public class GoodsManager {
    //增加商品
    public void addGoods(BeanGoodsType type, String name, double price1, double price2) throws BaseException{

    }

    //显示所有商品
    public List<BeanGoods> loadGoods(BeanGoodsType type) throws BaseException{
        return null;
    }

    //删除商品
    public void deleteGoods(BeanGoods goods) throws BaseException{

    }

    //修改商品名字.价格.优惠价
    public void modifyGoods(BeanGoods goods, String name, double price1, double price2) throws BaseException{

    }
}
