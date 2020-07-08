package takeoutassistant.itf;

import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

public interface IVIPManager {

    public double openVIP(BeanUser user, int month) throws BaseException;//开通会员
    public  double renewVIP(BeanUser user, int month) throws BaseException;//续费会员

}
