package takeoutassistant.itf;

import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IMyJidanManager {

    public BeanUserJidan addMyJidan(BeanUser user, BeanSeller seller) throws BaseException;//���¼�����Ϣ
    public List<BeanUserJidan> loadMyJidan(BeanUser user) throws BaseException;//��ʾ���м������

}
