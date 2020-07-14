package takeoutassistant.itf;

import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IGoodsManager {
    //增加商品
    public void addGoods(BeanGoodsType type, String name, double price1, double price2, int quantity) throws BaseException;
    //显示所有商品
    public List<BeanGoods> loadGoods(BeanGoodsType type) throws BaseException;
    //删除商品
    public void deleteGoods(BeanGoods goods) throws BaseException;
    //修改商品名字.价格.优惠价
    public void modifyGoods(BeanGoods goods, String name, double price1, double price2, int quantity) throws BaseException;
    //显示热门产品(最好的三个产品)
    public List<BeanGoods> loadHGoods(BeanSeller seller) throws BaseException;
    //根据类别ID得到商家名
    public String typeToSeller_name(int type_id) throws BaseException;
    //根据商品ID得到商品名
    public String idToName(int goods_id) throws BaseException;
    //按商品名查询
    public List<BeanGoods> loadForName(String name) throws BaseException;
    //按类别名查询
    public List<BeanGoods> loadForType(String name) throws BaseException;
    //判断是否有该商品的订单存在,若有,则无法删除
    public boolean ifHavingOrder(BeanGoods goods) throws BaseException;
}
