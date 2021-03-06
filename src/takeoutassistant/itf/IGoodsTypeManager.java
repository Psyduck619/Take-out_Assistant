package takeoutassistant.itf;

import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IGoodsTypeManager {
    //增加商品类别
    public void addGoodsType(BeanSeller seller, String name) throws BaseException;
    //显示所有商品类别
    public List<BeanGoodsType> loadTypes(BeanSeller seller) throws BaseException;
    //删除商品类别
    public void deleteGoodsType(BeanGoodsType goodstype) throws BaseException;
    //修改商品类别名字
    public void modifyGoodsType(BeanGoodsType goodstype, String name) throws BaseException;
}
