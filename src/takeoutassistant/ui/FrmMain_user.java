package takeoutassistant.ui;

import takeoutassistant.model.BeanAdmin;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import static takeoutassistant.ui.FrmLogin.loginType;

public class FrmMain_user extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_plan=new JMenu("用户计划管理");
    private JMenu menu_step=new JMenu("用户步骤管理");
    private JMenu menu_static=new JMenu("用户查询统计");
    private JMenu menu_more=new JMenu("更多");

    private JMenuItem  menuItem_AddPlan=new JMenuItem("新建计划");
    private JMenuItem  menuItem_DeletePlan=new JMenuItem("删除计划");
    private JMenuItem  menuItem_AddStep=new JMenuItem("添加步骤");
    private JMenuItem  menuItem_DeleteStep=new JMenuItem("删除步骤");
    private JMenuItem  menuItem_startStep=new JMenuItem("开始步骤");
    private JMenuItem  menuItem_finishStep=new JMenuItem("结束步骤");
    private JMenuItem  menuItem_moveUpStep=new JMenuItem("步骤上移");
    private JMenuItem  menuItem_moveDownStep=new JMenuItem("步骤下移");

    private JMenuItem  menuItem_modifyPwd=new JMenuItem("密码修改");

    private JMenuItem  menuItem_static1=new JMenuItem("统计1");


    private FrmLogin dlgLogin=null;
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
        this.setTitle("大开大合");
        //dlgLogin=new FrmLogin(this,"登录",true);
        //dlgLogin.setVisible(true);
        //菜单
        this.menu_plan.add(this.menuItem_AddPlan); this.menuItem_AddPlan.addActionListener(this);
        this.menu_plan.add(this.menuItem_DeletePlan); this.menuItem_DeletePlan.addActionListener(this);
        this.menu_step.add(this.menuItem_AddStep); this.menuItem_AddStep.addActionListener(this);
        this.menu_step.add(this.menuItem_DeleteStep); this.menuItem_DeleteStep.addActionListener(this);
        this.menu_step.add(this.menuItem_startStep); this.menuItem_startStep.addActionListener(this);
        this.menu_step.add(this.menuItem_finishStep); this.menuItem_finishStep.addActionListener(this);
        this.menu_step.add(this.menuItem_moveUpStep); this.menuItem_moveUpStep.addActionListener(this);
        this.menu_step.add(this.menuItem_moveDownStep); this.menuItem_moveDownStep.addActionListener(this);
        this.menu_static.add(this.menuItem_static1); this.menuItem_static1.addActionListener(this);
        this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);

        menubar.add(menu_plan);
        menubar.add(menu_step);
        menubar.add(menu_static);
        menubar.add(menu_more);
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
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        if(loginType == "用户"){
            this.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
