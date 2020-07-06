package takeoutassistant.itf;

import takeoutassistant.model.BeanManjian;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IManjianManager {

    public void addManjian(BeanSeller seller, int full, int discount, boolean together) throws BaseException;//������������
    public List<BeanManjian> loadManjian(BeanSeller seller) throws BaseException;//��ʾ������������
    public void deleteManjian(BeanManjian manjian) throws BaseException;//ɾ����������
    public void modifyManjian(BeanManjian manjian, int full, int discount, boolean together) throws BaseException;//�޸�����

}