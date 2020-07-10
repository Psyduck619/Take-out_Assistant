package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanMyCoupon;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmMain_user.curSeller;

public class FrmSelectCoupon extends JDialog implements ActionListener {

    private JPanel  menuBar = new JPanel();
    private JButton btSelect = new JButton("选择");//选择按钮
    //我的优惠券表构造
    private static Object tblMyCouponTitle[] = BeanMyCoupon.tblMyCouponTitle2;
    private static Object tblMyCouponData[][];
    private static DefaultTableModel tabMyCouponModel = new DefaultTableModel();
    private static JTable dataTableMyCoupon = new JTable(tabMyCouponModel);

    //初始化信息
    private BeanUser curUser = currentLoginUser;
    private List<BeanMyCoupon> allMyCoupon = null;
    public static BeanMyCoupon curMyCoupon = null;
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
    public FrmSelectCoupon(FrmBuy f,String s, boolean b){
        //设置窗口信息
        super(f,s,b);
        this.setSize(700,400);
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        menuBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        menuBar.add(btSelect);
        this.btSelect.addActionListener(this);
        this.getContentPane().add(menuBar, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(this.dataTableMyCoupon), BorderLayout.CENTER);
        this.dataTableMyCoupon.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmSelectCoupon.this.dataTableMyCoupon.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curMyCoupon = allMyCoupon.get(i);
            }
        });

        this.reloadMyCouponTable();  //加载信息
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btSelect){
            if(this.curMyCoupon == null) {
                JOptionPane.showMessageDialog(null, "请选择一项优惠券", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.setVisible(false);
        }
    }
}
