package takeoutassistant.control;

import takeoutassistant.itf.IRiderManager;
import takeoutassistant.model.BeanRider;
import takeoutassistant.util.BaseException;

import java.util.List;

public class RiderManager implements IRiderManager {

    //增加骑手
    @Override
    public BeanRider addRider(String name) throws BaseException {

        return null;
    }

    //显示所有骑手
    @Override
    public List<BeanRider> loadAll() throws BaseException {

        return null;
    }

    //删除骑手
    @Override
    public void deleteRider(BeanRider rider) throws BaseException {

    }

    //修改骑手身份
    @Override
    public void modifyStatus(BeanRider rider, String status) throws BaseException{

    }

}
