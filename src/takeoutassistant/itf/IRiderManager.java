package takeoutassistant.itf;

import takeoutassistant.model.BeanRider;
import takeoutassistant.util.BaseException;

import java.util.Date;
import java.util.List;

public interface IRiderManager {
    //��������
    public BeanRider addRider(String name, Date entryDate, String status) throws BaseException;
    //��ʾ��������
    public List<BeanRider> loadAll() throws BaseException;
    //ɾ������
    public void deleteRider(BeanRider rider) throws BaseException;
    //�޸��������
    public void modifyStatus(BeanRider rider, String status) throws BaseException;
}
