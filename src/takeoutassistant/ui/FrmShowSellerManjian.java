package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanCoupon;
import takeoutassistant.model.BeanManjian;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmMain_user.curSeller;

public class FrmShowSellerManjian extends JFrame {

    private static final long serialVersionUID = 1L;

    //������
    private JPanel statusBar = new JPanel();
    //��������
    private Object tblSellerManjianTitle[] = BeanManjian.tblManjianTitle;
    private Object tblSellerManjianData[][];
    DefaultTableModel tabSellerManjianModel = new DefaultTableModel();
    private JTable dataTableSellerManjian = new JTable(tabSellerManjianModel);

    //��ʼ����Ϣ
    private List<BeanManjian> allSellerManjian = null;
    //��ʾ�����̼��Ż�ȯ
    private void reloadSellerManjianTable(){
        try {
            allSellerManjian = TakeoutAssistantUtil.manjianManager.loadManjian(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSellerManjianData = new Object[allSellerManjian.size()][BeanCoupon.tblCouponTitle2.length];
        for(int i = 0 ; i < allSellerManjian.size() ; i++){
            for(int j = 0 ; j < BeanCoupon.tblCouponTitle2.length ; j++)
                tblSellerManjianData[i][j] = allSellerManjian.get(i).getCell(j);
        }
        tabSellerManjianModel.setDataVector(tblSellerManjianData,tblSellerManjianTitle);
        this.dataTableSellerManjian.validate();
        this.dataTableSellerManjian.repaint();
    }

    //������
    public FrmShowSellerManjian(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(400,300);
        this.setTitle("�̼�������Ϣ");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableSellerManjian), BorderLayout.CENTER);

        this.reloadSellerManjianTable();  //������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String sellerName = curSeller.getSeller_name();
        JLabel label=new JLabel("����!��ӭ����" + sellerName + "!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
