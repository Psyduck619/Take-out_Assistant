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

    //主界面
    private JPanel statusBar = new JPanel();
    //我的优惠券表构造
    private static Object tblSellerCouponTitle[] = BeanCoupon.tblCouponTitle2;
    private static Object tblSellerCouponData[][];
    private static DefaultTableModel tabSellerCouponModel = new DefaultTableModel();
    private static JTable dataTableSellerCoupon = new JTable(tabSellerCouponModel);

    //初始化信息
    private List<BeanCoupon> allSellerCoupon = null;
    //显示所有商家优惠券
    private void reloadSellerCouponTable(){
        try {
            allSellerCoupon = TakeoutAssistantUtil.couponManager.loadCoupon(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
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

    //主界面
    public FrmShowSellerCoupon(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("商家优惠券");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableSellerCoupon), BorderLayout.CENTER);

        this.reloadSellerCouponTable();  //加载信息
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String sellerName = curSeller.getSeller_name();
        JLabel label=new JLabel("您好!欢迎光临" + sellerName + "!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
