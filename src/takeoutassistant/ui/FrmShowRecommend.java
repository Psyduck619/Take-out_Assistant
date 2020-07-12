package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanRecommend;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmShowRecommend extends JFrame {

    private static final long serialVersionUID = 1L;
    //�ҵ��Ż�ȯ����
    private static Object tblRecommendTitle[] = BeanRecommend.tblRecommendTitle;
    private static Object tblRecommendData[][];
    private static DefaultTableModel tabRecommendModel = new DefaultTableModel();
    private static JTable dataTableRecommend = new JTable(tabRecommendModel);

    //��ʼ����Ϣ
    private BeanUser curUser = currentLoginUser;
    private List<BeanRecommend> allRecommend = null;
    //��ʾ�����ҵ��Ż�ȯ
    private void reloadRecommendTable(){
        try {
            allRecommend = TakeoutAssistantUtil.orderInfoManager.Recommend(curUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblRecommendData = new Object[allRecommend.size()][BeanRecommend.tblRecommendTitle.length];
        for(int i = 0 ; i < allRecommend.size() ; i++){
            for(int j = 0 ; j < BeanRecommend.tblRecommendTitle.length ; j++)
                tblRecommendData[i][j] = allRecommend.get(i).getCell(j);
        }
        tabRecommendModel.setDataVector(tblRecommendData,tblRecommendTitle);
        this.dataTableRecommend.validate();
        this.dataTableRecommend.repaint();
    }

    //������
    public FrmShowRecommend(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("��Ʒ�Ƽ�");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableRecommend), BorderLayout.CENTER);

        this.reloadRecommendTable();  //������Ϣ
        this.setVisible(true);
    }

}
