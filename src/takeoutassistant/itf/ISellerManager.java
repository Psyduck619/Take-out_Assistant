package takeoutassistant.itf;

import jdk.internal.dynalink.beans.BeansLinker;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface ISellerManager {
    //添加商家
    public BeanSeller addSeller(String name) throws BaseException;
    //显示所有商家
    public List<BeanSeller> loadAll() throws BaseException;
    //删除商家
    public void deleteSeller(BeanSeller seller) throws BaseException;
    //修改商家名字
    public void modifyName(BeanSeller seller) throws BaseException;
    //显示某星级商家
    public List<BeanSeller> loadLevel(int level) throws BaseException;
}
