package takeoutassistant.ui;

import takeoutassistant.model.BeanAdmin;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmLogin.loginType;

public class FrmMain_user extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_buy=new JMenu("商品选购");
    private JMenu menu_order=new JMenu("订单");
    private JMenu menu_user=new JMenu("个人");
    private JMenu menu_VIP=new JMenu("会员");
    private JMenu menu_address=new JMenu("地址");

    private JMenuItem menuItem_AddPlan=new JMenuItem("新建计划");
    private JMenuItem menuItem_DeletePlan=new JMenuItem("删除计划");

    private JMenuItem menuItem_AddStep=new JMenuItem("查看订单");
    private JMenuItem menuItem_DeleteStep=new JMenuItem("删除订单");
    private JMenuItem menuItem_startStep=new JMenuItem("评价订单");
    //个人菜单组件
    private JMenuItem menuItem_myCoupon=new JMenuItem("我的优惠券");
    private JMenuItem menuItem_modifyUser=new JMenuItem("修改个人信息");
    private JMenuItem menuItem_modifyPwd=new JMenuItem("修改密码");

    //主界面
    private JPanel statusBar = new JPanel();

    //    private Object tblPlanTitle[]=BeanPlan.tableTitles;
//    private Object tblPlanData[][];
//    DefaultTableModel tabPlanModel=new DefaultTableModel();
//    private JTable dataTablePlan=new JTable(tabPlanModel);
//
//
//    private Object tblStepTitle[]=BeanStep.tblStepTitle;
//    private Object tblStepData[][];
//    DefaultTableModel tabStepModel=new DefaultTableModel();
//    private JTable dataTableStep=new JTable(tabStepModel);
//
//    private BeanPlan curPlan=null;
//    List<BeanPlan> allPlan=null;
//    List<BeanStep> planSteps=null;
//    private void reloadPlanTable(){//这是测试数据，需要用实际数替换
//        try {
//            allPlan=PersonPlanUtil.planManager.loadAll();
//        } catch (BaseException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        tblPlanData =  new Object[allPlan.size()][BeanPlan.tableTitles.length];
//        for(int i=0;i<allPlan.size();i++){
//            for(int j=0;j<BeanPlan.tableTitles.length;j++)
//                tblPlanData[i][j]=allPlan.get(i).getCell(j);
//        }
//        tabPlanModel.setDataVector(tblPlanData,tblPlanTitle);
//        this.dataTablePlan.validate();
//        this.dataTablePlan.repaint();
//    }
    public FrmMain_user(){

        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("大开大合-外卖助手");
        //菜单
//        this.menu_plan.add(this.menuItem_AddPlan); this.menuItem_AddPlan.addActionListener(this);
//        this.menu_plan.add(this.menuItem_DeletePlan); this.menuItem_DeletePlan.addActionListener(this);
//        this.menu_step.add(this.menuItem_AddStep); this.menuItem_AddStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_DeleteStep); this.menuItem_DeleteStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_startStep); this.menuItem_startStep.addActionListener(this);
//        this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
        //个人菜单
        this.menu_user.add(this.menuItem_myCoupon); this.menuItem_myCoupon.addActionListener(this);
        this.menu_user.add(this.menuItem_modifyUser); this.menuItem_modifyUser.addActionListener(this);
        this.menu_user.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);

        menubar.add(menu_buy);
        menubar.add(menu_order);
        menubar.add(menu_user);
        menubar.add(menu_VIP);
        menubar.add(menu_address);
        this.setJMenuBar(menubar);

//        this.getContentPane().add(new JScrollPane(this.dataTablePlan), BorderLayout.WEST);
//        this.dataTablePlan.addMouseListener(new MouseAdapter (){
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int i=FrmMain.this.dataTablePlan.getSelectedRow();
//                if(i<0) {
//                    return;
//                }
//                FrmMain.this.reloadPlanStepTabel(i);
//            }
//
//        });
//        this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.CENTER);
//
//        this.reloadPlanTable();
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!用户!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter(){  //关闭窗口即退出程序
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        if(loginType == "用户"){  //用户登录界面
            this.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //用户修改个人信息
        if(e.getSource() == this.menuItem_modifyUser){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyUser dlg = new FrmModifyUser(this,"修改个人信息",true);
            dlg.setVisible(true);
        }
        //用户修改密码
        if(e.getSource() == this.menuItem_modifyPwd){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyPwd_user dlg = new FrmModifyPwd_user(this,"修改密码",true);
            dlg.setVisible(true);
        }

    }
}
