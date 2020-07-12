package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanRiderAccount;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmMyRiderComment extends JFrame {

    private static final long serialVersionUID = 1L;

    //��Ʒ���۱���
    private static Object tblRiderCommentTitle[] = BeanRiderAccount.tblRiderCommentTitle;
    private static Object tblRiderCommentData[][];
    private static DefaultTableModel tabRiderCommentModel = new DefaultTableModel();
    private static JTable dataTableRiderComment = new JTable(tabRiderCommentModel);

    //��ʼ����Ϣ
    public static BeanRiderAccount curRiderComment = null;
    public static List<BeanRiderAccount> allRiderComment = null;
    //��ʾ��������
    private void reloadRiderCommentTable(){
        try {
            allRiderComment = TakeoutAssistantUtil.riderAccountManager.loadUsers(currentLoginUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblRiderCommentData = new Object[allRiderComment.size()][BeanRiderAccount.tblRiderCommentTitle.length];
        for(int i = 0 ; i < allRiderComment.size() ; i++){
            for(int j = 0 ; j < BeanRiderAccount.tblRiderCommentTitle.length ; j++)
                tblRiderCommentData[i][j] = allRiderComment.get(i).getCell2(j);
        }
        tabRiderCommentModel.setDataVector(tblRiderCommentData,tblRiderCommentTitle);
        this.dataTableRiderComment.validate();
        this.dataTableRiderComment.repaint();
    }
    //������
    public FrmMyRiderComment(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(800,400);
        this.setTitle("�ҵ���������");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //������Ϣ��ʾ
        this.getContentPane().add(new JScrollPane(this.dataTableRiderComment), BorderLayout.CENTER);
        this.reloadRiderCommentTable();  //����������Ϣ
        this.setVisible(true);
    }

}
