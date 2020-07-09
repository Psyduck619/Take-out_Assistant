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
    //主界面
    private JPanel statusBar = new JPanel();
    //我的优惠券表构造
    private static Object tblMyCouponTitle[] = BeanMyCoupon.tblMyCouponTitle2;
    private static Object tblMyCouponData[][];
    private static DefaultTableModel tabMyCouponModel = new DefaultTableModel();
    private static JTable dataTableMyCoupon = new JTable(tabMyCouponModel);

    //初始化信息
    private BeanUser curUser = currentLoginUser;
    private List<BeanMyCoupon> allMyCoupon = null;
    //显示所有我的优惠券
    private void reloadMyCouponTable(){
        try {
            allMyCoupon = TakeoutAssistantUtil.myCouponManager.loadMyCoupon2(curUser, curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
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

    //主界面
    public FrmShowMyCoupon2(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("我的优惠券");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableMyCoupon), BorderLayout.CENTER);

        this.reloadMyCouponTable();  //加载信息
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String name = curUser.getUser_name();
        JLabel label=new JLabel("您好!尊敬的"+name+"!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
