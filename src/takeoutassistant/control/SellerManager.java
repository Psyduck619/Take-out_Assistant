package takeoutassistant.control;

import takeoutassistant.itf.ISellerManager;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public class SellerManager implements ISellerManager {

    //����̼�
    @Override
    public BeanSeller addSeller(String name) throws BaseException {

        return null;
    }

    //��ʾ�����̼�
    @Override
    public List<BeanSeller> loadAll() throws BaseException {

        return null;
    }

    //ɾ���̼�
    @Override
    public void deleteSeller(BeanSeller seller) throws BaseException{

    }

    //�޸��̼�����
    @Override
    public void modifyName(BeanSeller seller) throws BaseException{

    }

    //��ʾĳ�Ǽ��̼�
    @Override
    public List<BeanSeller> loadLevel(int level) throws BaseException {

        return null;
    }

}
