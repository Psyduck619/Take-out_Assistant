package takeoutassistant.control;

import takeoutassistant.itf.IRiderManager;
import takeoutassistant.model.BeanRider;
import takeoutassistant.util.BaseException;

import java.util.List;

public class RiderManager implements IRiderManager {

    //��������
    @Override
    public BeanRider addRider(String name) throws BaseException {

        return null;
    }

    //��ʾ��������
    @Override
    public List<BeanRider> loadAll() throws BaseException {

        return null;
    }

    //ɾ������
    @Override
    public void deleteRider(BeanRider rider) throws BaseException {

    }

    //�޸��������
    @Override
    public void modifyStatus(BeanRider rider, String status) throws BaseException{

    }

}
