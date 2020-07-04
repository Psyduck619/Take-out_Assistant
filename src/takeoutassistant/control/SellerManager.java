package takeoutassistant.control;

import takeoutassistant.itf.ISellerManager;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public class SellerManager implements ISellerManager {

    //添加商家
    @Override
    public BeanSeller addSeller(String name) throws BaseException {

        return null;
    }

    //显示所有商家
    @Override
    public List<BeanSeller> loadAll() throws BaseException {

        return null;
    }

    //删除商家
    @Override
    public void deleteSeller(BeanSeller seller) throws BaseException{

    }

    //修改商家名字
    @Override
    public void modifyName(BeanSeller seller) throws BaseException{

    }

    //显示某星级商家
    @Override
    public List<BeanSeller> loadLevel(int level) throws BaseException {

        return null;
    }

}
