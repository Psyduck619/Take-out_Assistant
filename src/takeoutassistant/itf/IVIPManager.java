package takeoutassistant.itf;

import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

public interface IVIPManager {

    public double openVIP(BeanUser user, int month) throws BaseException;//��ͨ��Ա
    public  double renewVIP(BeanUser user, int month) throws BaseException;//���ѻ�Ա

}
