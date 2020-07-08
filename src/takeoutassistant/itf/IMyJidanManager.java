package takeoutassistant.itf;

import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IMyJidanManager {

    public BeanUserJidan addMyJidan(BeanUser user, BeanSeller seller) throws BaseException;//更新集单信息
    public List<BeanUserJidan> loadMyJidan(BeanUser user) throws BaseException;//显示所有集单情况

}
