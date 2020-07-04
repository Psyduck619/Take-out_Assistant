package takeoutassistant.itf;

import takeoutassistant.model.BeanRider;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IRiderManager {
    //增加骑手
    public BeanRider addRider(String name) throws BaseException;
    //显示所有骑手
    public List<BeanRider> loadAll() throws BaseException;
    //删除骑手
    public void deleteRider(BeanRider rider) throws BaseException;
    //修改骑手身份
    public void modifyStatus(BeanRider rider, String status) throws BaseException;
}
