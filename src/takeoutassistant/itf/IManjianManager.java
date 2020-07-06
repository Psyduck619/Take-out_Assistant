package takeoutassistant.itf;

import takeoutassistant.model.BeanManjian;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IManjianManager {

    public void addManjian(BeanSeller seller, int full, int discount, boolean together) throws BaseException;//增加满减种类
    public List<BeanManjian> loadManjian(BeanSeller seller) throws BaseException;//显示所有满减种类
    public void deleteManjian(BeanManjian manjian) throws BaseException;//删除满减种类
    public void modifyManjian(BeanManjian manjian, int full, int discount, boolean together) throws BaseException;//修改满减

}