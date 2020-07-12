package takeoutassistant.itf;

import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import java.util.List;

public interface IGoodsCommentManager {

    public void addGoodsComment(BeanOrderInfo orderInfo, String comment, String level) throws BaseException;//�����Ʒ����
    public List<BeanGoodsComment> loadAll(BeanUser user) throws BaseException;//�û��鿴���е���Ʒ����
    //�õ�������Ʒ������
    public List<BeanGoodsComment> loadGoods(BeanGoods goods) throws BaseException;
    public boolean isComment(BeanOrderInfo orderInfo) throws BaseException;//�ж���Ʒ�Ƿ�������
    public void deleteComment(BeanGoodsComment goodsComment) throws BaseException;//ɾ������

}
