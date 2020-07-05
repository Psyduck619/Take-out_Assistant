package takeoutassistant.control;

import takeoutassistant.itf.IGoodsTypeManager;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public class GoodsTypeManager implements IGoodsTypeManager {

    //增加商品类别
    public BeanGoodsType addGoodsType(BeanSeller seller, String name, int quantity) throws BaseException {

        return null;
    }

    //显示所有商品类别
    public List<BeanGoodsType> loadTypes(BeanSeller seller) throws BaseException {

        return null;
    }

    //删除商品类别
    public void deleteGoodsType(BeanGoodsType goodstype) throws BaseException {

    }

    //修改商品类别名字
    public void modifyGoodsType(BeanGoodsType goodstype, String name) throws BaseException {

    }

}
