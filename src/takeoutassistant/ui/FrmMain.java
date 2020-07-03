//package takeoutassistant.ui;
//
//import takeoutassistant.util.BaseException;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.*;
//
//public class FrmMain extends JFrame implements ActionListener {
//    private static final long serialVersionUID = 1L;
//    private JMenuBar menubar=new JMenuBar(); ;
//    private JMenu menu_plan=new JMenu("�ƻ�����");
//    private JMenu menu_step=new JMenu("�������");
//    private JMenu menu_static=new JMenu("��ѯͳ��");
//    private JMenu menu_more=new JMenu("����");
//
//    private JMenuItem  menuItem_AddPlan=new JMenuItem("�½��ƻ�");
//    private JMenuItem  menuItem_DeletePlan=new JMenuItem("ɾ���ƻ�");
//    private JMenuItem  menuItem_AddStep=new JMenuItem("��Ӳ���");
//    private JMenuItem  menuItem_DeleteStep=new JMenuItem("ɾ������");
//    private JMenuItem  menuItem_startStep=new JMenuItem("��ʼ����");
//    private JMenuItem  menuItem_finishStep=new JMenuItem("��������");
//    private JMenuItem  menuItem_moveUpStep=new JMenuItem("��������");
//    private JMenuItem  menuItem_moveDownStep=new JMenuItem("��������");
//
//    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�����޸�");
//
//    private JMenuItem  menuItem_static1=new JMenuItem("ͳ��1");
//
//
//    private FrmLogin dlgLogin=null;
//    private JPanel statusBar = new JPanel();
//
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
//    private void reloadPlanTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
//        try {
//            allPlan=PersonPlanUtil.planManager.loadAll();
//        } catch (BaseException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
//    private void reloadPlanStepTabel(int planIdx){
//        if(planIdx<0) return;
//        curPlan=allPlan.get(planIdx);
//        try {
//            planSteps=PersonPlanUtil.stepManager.loadSteps(curPlan);
//        } catch (BaseException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        tblStepData =new Object[planSteps.size()][BeanStep.tblStepTitle.length];
//        for(int i=0;i<planSteps.size();i++){
//            for(int j=0;j<BeanStep.tblStepTitle.length;j++)
//                tblStepData[i][j]=planSteps.get(i).getCell(j);
//        }
//
//        tabStepModel.setDataVector(tblStepData,tblStepTitle);
//        this.dataTableStep.validate();
//        this.dataTableStep.repaint();
//    }
//    public FrmMain(){
//
//        this.setExtendedState(Frame.MAXIMIZED_BOTH);
//        this.setTitle("���˼ƻ�����ϵͳ");
//        dlgLogin=new FrmLogin(this,"��½",true);
//        dlgLogin.setVisible(true);
//        //�˵�
//        this.menu_plan.add(this.menuItem_AddPlan); this.menuItem_AddPlan.addActionListener(this);
//        this.menu_plan.add(this.menuItem_DeletePlan); this.menuItem_DeletePlan.addActionListener(this);
//        this.menu_step.add(this.menuItem_AddStep); this.menuItem_AddStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_DeleteStep); this.menuItem_DeleteStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_startStep); this.menuItem_startStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_finishStep); this.menuItem_finishStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_moveUpStep); this.menuItem_moveUpStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_moveDownStep); this.menuItem_moveDownStep.addActionListener(this);
//        this.menu_static.add(this.menuItem_static1); this.menuItem_static1.addActionListener(this);
//        this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
//
//        menubar.add(menu_plan);
//        menubar.add(menu_step);
//        menubar.add(menu_static);
//        menubar.add(menu_more);
//        this.setJMenuBar(menubar);
//
//        this.getContentPane().add(new JScrollPane(this.dataTablePlan), BorderLayout.WEST);
//        this.dataTablePlan.addMouseListener(new MouseAdapter(){
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
//        //״̬��
//        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//        JLabel label=new JLabel("����!");//�޸ĳ�   ���ã�+��½�û���
//        statusBar.add(label);
//        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
//        this.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//                System.exit(0);
//            }
//        });
//        this.setVisible(true);
//    }
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource()==this.menuItem_AddPlan){
//            FrmAddPlan dlg=new FrmAddPlan(this,"��Ӽƻ�",true);
//            dlg.setVisible(true);
//            reloadPlanTable();
//        }
//        else if(e.getSource()==this.menuItem_DeletePlan){
//            if(this.curPlan==null) {
//                JOptionPane.showMessageDialog(null, "��ѡ��ƻ�", "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.planManager.deletePlan(this.curPlan);
//                reloadPlanTable();
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_AddStep){
//            if(this.curPlan==null){
//                JOptionPane.showMessageDialog(null, "Select a plan please", "����",JOptionPane.ERROR_MESSAGE);
//                return ;
//            }
//            FrmAddStep dlg=new FrmAddStep(this,"��Ӳ���",true);
//            dlg.plan=curPlan;
//            dlg.setVisible(true);
//            reloadPlanStepTabel(FrmMain.this.dataTablePlan.getSelectedRow());
//        }
//        else if(e.getSource()==this.menuItem_DeleteStep){
//            int i=FrmMain.this.dataTableStep.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.stepManager.deleteStep(this.planSteps.get(i));
//                reloadPlanStepTabel(FrmMain.this.dataTablePlan.getSelectedRow());
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_startStep){
//            int i=FrmMain.this.dataTableStep.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.stepManager.startStep(this.planSteps.get(i));
//                reloadPlanStepTabel(FrmMain.this.dataTablePlan.getSelectedRow());
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_finishStep){
//            int i=FrmMain.this.dataTableStep.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.stepManager.finishStep(this.planSteps.get(i));
//                reloadPlanStepTabel(FrmMain.this.dataTablePlan.getSelectedRow());
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_moveUpStep){
//            int i=FrmMain.this.dataTableStep.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.stepManager.moveUp(this.planSteps.get(i));
//                reloadPlanStepTabel(FrmMain.this.dataTablePlan.getSelectedRow());
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_moveDownStep){
//            int i=FrmMain.this.dataTableStep.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.stepManager.moveDown(this.planSteps.get(i));
//                reloadPlanStepTabel(FrmMain.this.dataTablePlan.getSelectedRow());
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_static1){
//
//        }
//        else if(e.getSource()==this.menuItem_modifyPwd){
//            FrmModifyPwd dlg=new FrmModifyPwd(this,"�����޸�",true);
//            dlg.setVisible(true);
//        }
//    }
//}
