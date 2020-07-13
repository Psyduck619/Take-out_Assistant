package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanUserCus;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmShowUser extends JFrame {

    private static final long serialVersionUID = 1L;
    //�û����ѱ���
    private static Object tblUserInfoTitle[] = BeanUserCus.tblUserCusTitle;
    private static Object tblUserInfoData[][];
    private static DefaultTableModel tabUserInfoModel = new DefaultTableModel();
    private static JTable dataTableUserInfo = new JTable(tabUserInfoModel);

    //��ʼ����Ϣ
    private BeanUser curUser = currentLoginUser;
    private List<BeanUserCus> allUserCus = null;
    //�û����������
    private void reloadUserInfoTable(){
        try {
            allUserCus = TakeoutAssistantUtil.orderManager.loadUserCus();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblUserInfoData = new Object[allUserCus.size()][BeanUserCus.tblUserCusTitle.length];
        for(int i = 0 ; i < allUserCus.size() ; i++){
            for(int j = 0; j < BeanUserCus.tblUserCusTitle.length ; j++)
                tblUserInfoData[i][j] = allUserCus.get(i).getCell(j);
        }
        tabUserInfoModel.setDataVector(tblUserInfoData,tblUserInfoTitle);
        this.dataTableUserInfo.validate();
        this.dataTableUserInfo.repaint();
    }

    //������
    public FrmShowUser(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("�û��������");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableUserInfo), BorderLayout.CENTER);

        this.reloadUserInfoTable();  //������Ϣ
        this.setVisible(true);
    }

}
