package takeoutassistant.itf;

import takeoutassistant.model.BeanRider;
import takeoutassistant.util.BaseException;

import java.util.Date;
import java.util.List;

public interface IRiderManager {
    //增加骑手
    public BeanRider addRider(String name, Date entryDate, String status) throws BaseException;
    //显示所有骑手
    public List<BeanRider> loadAll() throws BaseException;
    //删除骑手
    public void deleteRider(BeanRider rider) throws BaseException;
    //修改骑手身份
    public void modifyStatus(BeanRider rider, String status) throws BaseException;
    //判断骑手是否送过单,若送过,则无法删除
    public boolean ifHavingOrder(BeanRider rider) throws BaseException;
}
