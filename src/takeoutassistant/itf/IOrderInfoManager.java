package takeoutassistant.itf;

import takeoutassistant.control.UserManager;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IOrderInfoManager {

    //����������Ϣ
    public void addOrderInfo(BeanUser user, BeanGoods goods, int quantity) throws BaseException;
    //��ʾ������Ϣ(���ﳵ)
    public List<BeanOrderInfo> loadOrderInfo(BeanUser user) throws BaseException;
    //��ʾ������Ϣ(�û�)
    public List<BeanOrderInfo> loadMyOrderInfo(BeanGoodsOrder order) throws BaseException;
    //ɾ��������Ϣ
    public void deleteOrderInfo(BeanOrderInfo orderInfo) throws BaseException;
    //�жϹ��ﳵ�����Ʒ��û�е�ǰ�̼������
    public boolean isOnly(BeanUser user, BeanSeller curSeller) throws BaseException;
    //��չ��ﳵ
    public void deleteAll(BeanUser user) throws BaseException;
    //�õ���ǰ���ﳵĳ��Ʒ������(������Ʒ)
    public int getQuantity(BeanUser user, BeanOrderInfo myCart) throws BaseException;
    //�õ���ǰ���ﳵ��ѡ�е���Ʒ�Ŀ����(������Ʒ)
    public int getGoodsQuantity(BeanOrderInfo myCart) throws BaseException;
    //�õ���ǰ���ﳵĳ��Ʒ������(�����Ʒ)
    public int getQuantity2(BeanUser user, BeanGoods goods) throws BaseException;
    //�õ���ǰ���ﳵ��ѡ�е���Ʒ�Ŀ����(�����Ʒ)
    public int getGoodsQuantity2(BeanGoods goods) throws BaseException;
    //���ﳵ������Ʒ����
    public void addCount(BeanOrderInfo orderInfo, int count) throws BaseException;
    //���ﳵ������Ʒ����
    public void loseCount(BeanOrderInfo orderInfo, int count) throws BaseException;
    //���ݲ�Ʒ������Ϊ�û��Ƽ���Ʒ
    public List<BeanRecommend> Recommend(BeanUser user) throws BaseException;

}
