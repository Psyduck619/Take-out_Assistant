package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanGoodsComment;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmShowSeller.curGoods;

public class FrmShowGoodsComment extends JFrame {

    private static final long serialVersionUID = 1L;
    //��Ʒ���۱���
    private static Object tblGoodsCommentTitle[] = BeanGoodsComment.tblGoodsCommentTitle2;
    private static Object tblGoodsCommentData[][];
    private static DefaultTableModel tabGoodsCommentModel = new DefaultTableModel();
    private static JTable dataTableGoodsComment = new JTable(tabGoodsCommentModel);

    //��ʼ����Ϣ
    private BeanUser curUser = currentLoginUser;
    private List<BeanGoodsComment> allGoodsComment = null;
    //��ʾ������Ʒ����
    private void reloadGoodsCommentTable(){
        try {
            allGoodsComment = TakeoutAssistantUtil.goodsCommentManager.loadGoods(curGoods);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsCommentData = new Object[allGoodsComment.size()][BeanGoodsComment.tblGoodsCommentTitle2.length];
        for(int i = 0 ; i < allGoodsComment.size() ; i++){
            for(int j = 0 ; j < BeanGoodsComment.tblGoodsCommentTitle2.length ; j++)
                tblGoodsCommentData[i][j] = allGoodsComment.get(i).getCell2(j);
        }
        tabGoodsCommentModel.setDataVector(tblGoodsCommentData,tblGoodsCommentTitle);
        this.dataTableGoodsComment.validate();
        this.dataTableGoodsComment.repaint();
    }

    //������
    public FrmShowGoodsComment(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("��Ʒ����");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableGoodsComment), BorderLayout.CENTER);

        this.reloadGoodsCommentTable();  //������Ϣ
        this.setVisible(true);
    }

}
