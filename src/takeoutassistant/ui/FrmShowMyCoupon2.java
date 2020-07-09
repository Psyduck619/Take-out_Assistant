package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanMyCoupon;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmMain_user.curSeller;

public class FrmShowMyCoupon2 extends JFrame {

    private static final long serialVersionUID = 1L;
    //������
    private JPanel statusBar = new JPanel();
    //�ҵ��Ż�ȯ����
    private static Object tblMyCouponTitle[] = BeanMyCoupon.tblMyCouponTitle2;
    private static Object tblMyCouponData[][];
    private static DefaultTableModel tabMyCouponModel = new DefaultTableModel();
    private static JTable dataTableMyCoupon = new JTable(tabMyCouponModel);

    //��ʼ����Ϣ
    private BeanUser curUser = currentLoginUser;
    private List<BeanMyCoupon> allMyCoupon = null;
    //��ʾ�����ҵ��Ż�ȯ
    private void reloadMyCouponTable(){
        try {
            allMyCoupon = TakeoutAssistantUtil.myCouponManager.loadMyCoupon2(curUser, curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblMyCouponData = new Object[allMyCoupon.size()][BeanMyCoupon.tblMyCouponTitle2.length];
        for(int i = 0 ; i < allMyCoupon.size() ; i++){
            for(int j = 0 ; j < BeanMyCoupon.tblMyCouponTitle2.length ; j++)
                tblMyCouponData[i][j] = allMyCoupon.get(i).getCell2(j);
        }
        tabMyCouponModel.setDataVector(tblMyCouponData,tblMyCouponTitle);
        this.dataTableMyCoupon.validate();
        this.dataTableMyCoupon.repaint();
    }

    //������
    public FrmShowMyCoupon2(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("�ҵ��Ż�ȯ");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableMyCoupon), BorderLayout.CENTER);

        this.reloadMyCouponTable();  //������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String name = curUser.getUser_name();
        JLabel label=new JLabel("����!�𾴵�"+name+"!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
