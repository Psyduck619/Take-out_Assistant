package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanUser;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmMain_user.curSeller;

public class FrmShowSellerCoupon extends JFrame {

    private static final long serialVersionUID = 1L;

    //������
    private JPanel statusBar = new JPanel();
    //�ҵ��Ż�ȯ����
    private static Object tblSellerCouponTitle[] = BeanCoupon.tblCouponTitle2;
    private static Object tblSellerCouponData[][];
    private static DefaultTableModel tabSellerCouponModel = new DefaultTableModel();
    private static JTable dataTableSellerCoupon = new JTable(tabSellerCouponModel);

    //��ʼ����Ϣ
    private List<BeanCoupon> allSellerCoupon = null;
    //��ʾ�����̼��Ż�ȯ
    private void reloadSellerCouponTable(){
        try {
            allSellerCoupon = TakeoutAssistantUtil.couponManager.loadCoupon(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSellerCouponData = new Object[allSellerCoupon.size()][BeanCoupon.tblCouponTitle2.length];
        for(int i = 0 ; i < allSellerCoupon.size() ; i++){
            for(int j = 0 ; j < BeanCoupon.tblCouponTitle2.length ; j++)
                tblSellerCouponData[i][j] = allSellerCoupon.get(i).getCell2(j);
        }
        tabSellerCouponModel.setDataVector(tblSellerCouponData,tblSellerCouponTitle);
        this.dataTableSellerCoupon.validate();
        this.dataTableSellerCoupon.repaint();
    }

    //������
    public FrmShowSellerCoupon(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("�̼��Ż�ȯ");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableSellerCoupon), BorderLayout.CENTER);

        this.reloadSellerCouponTable();  //������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String sellerName = curSeller.getSeller_name();
        JLabel label=new JLabel("����!��ӭ����" + sellerName + "!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
