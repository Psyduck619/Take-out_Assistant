package takeoutassistant.ui;

import takeoutassistant.model.BeanAdmin;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmLogin.loginType;

public class FrmMain_user extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_buy=new JMenu("��Ʒѡ��");
    private JMenu menu_order=new JMenu("����");
    private JMenu menu_user=new JMenu("����");
    private JMenu menu_VIP=new JMenu("��Ա");
    private JMenu menu_address=new JMenu("��ַ");

    private JMenuItem menuItem_AddPlan=new JMenuItem("�½��ƻ�");
    private JMenuItem menuItem_DeletePlan=new JMenuItem("ɾ���ƻ�");

    private JMenuItem menuItem_AddStep=new JMenuItem("�鿴����");
    private JMenuItem menuItem_DeleteStep=new JMenuItem("ɾ������");
    private JMenuItem menuItem_startStep=new JMenuItem("���۶���");
    //���˲˵����
    private JMenuItem menuItem_myCoupon=new JMenuItem("�ҵ��Ż�ȯ");
    private JMenuItem menuItem_myaddress=new JMenuItem("�ҵĵ�ַ");
    private JMenuItem menuItem_modifyUser=new JMenuItem("�޸ĸ�����Ϣ");
    private JMenuItem menuItem_modifyPwd=new JMenuItem("�޸�����");
    //��Ա�˵����
    private JMenuItem menuItem_openVIP=new JMenuItem("��ͨ��Ա");
    private JMenuItem menuItem_renewVIP=new JMenuItem("���ѻ�Ա");

    //������
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
    public FrmMain_user(){

        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("�󿪴��-��������");
        //�˵�
//        this.menu_plan.add(this.menuItem_AddPlan); this.menuItem_AddPlan.addActionListener(this);
//        this.menu_plan.add(this.menuItem_DeletePlan); this.menuItem_DeletePlan.addActionListener(this);
//        this.menu_step.add(this.menuItem_AddStep); this.menuItem_AddStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_DeleteStep); this.menuItem_DeleteStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_startStep); this.menuItem_startStep.addActionListener(this);
//        this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
        //���˲˵�
        this.menu_user.add(this.menuItem_myCoupon); this.menuItem_myCoupon.addActionListener(this);
        this.menu_user.add(this.menuItem_myaddress); this.menuItem_myaddress.addActionListener(this);
        this.menu_user.add(this.menuItem_modifyUser); this.menuItem_modifyUser.addActionListener(this);
        this.menu_user.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
        //��Ա�˵�
        this.menu_VIP.add(this.menuItem_openVIP); this.menuItem_openVIP.addActionListener(this);
        this.menu_VIP.add(this.menuItem_renewVIP); this.menuItem_renewVIP.addActionListener(this);

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
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String name = currentLoginUser.getUser_name();
        Date dt = currentLoginUser.getVIP_end_time();
        if(currentLoginUser.getVIP()){
            name = "��Ա" + name + "!      ��Ա����ʱ��:" +dt;
        }
        JLabel label=new JLabel("����!�𾴵�" + name);
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter(){  //�رմ��ڼ��˳�����
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        if(loginType == "�û�"){  //�û���¼����
            this.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //�û��޸ĸ�����Ϣ
        if(e.getSource() == this.menuItem_modifyUser){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "�û���¼����,������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyUser dlg = new FrmModifyUser(this,"�޸ĸ�����Ϣ",true);
            dlg.setVisible(true);
        }
        //�û��޸�����
        else if(e.getSource() == this.menuItem_modifyPwd){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "�û���¼����,������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyPwd_user dlg = new FrmModifyPwd_user(this,"�޸�����",true);
            dlg.setVisible(true);
        }
        //��ʾ�ҵ��Ż�ȯ��Ϣ
        else if(e.getSource() == this.menuItem_myCoupon){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "�û���¼����,������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmShowMyCoupon();
        }
        //�����û���ַ������
        else if(e.getSource() == this.menuItem_myaddress){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "�û���¼����,������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmMyAddress();
        }
        //�û���ͨ��Ա
        else if(e.getSource() == this.menuItem_openVIP){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "�û���¼����,������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(currentLoginUser.getVIP()){
                JOptionPane.showMessageDialog(null, "���������Ļ�Ա�û�!", "��ʾ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmOpenVIP dlg = new FrmOpenVIP(this,"��ͨ��Ա",true);
            dlg.setVisible(true);
            //�ı���û��ĳƺ�
            try {
                Thread.sleep(1000);
                if(currentLoginUser.getVIP()){
                    this.setVisible(false);
                    new FrmMain_user();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        //�û����ѻ�Ա
        else if(e.getSource() == this.menuItem_renewVIP){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "�û���¼����,������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!currentLoginUser.getVIP()){
                JOptionPane.showMessageDialog(null, "�������ǻ�Ա�û�,���ȿ�ͨ��Ա", "��ʾ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmRenewVIP dlg = new FrmRenewVIP(this,"���ѻ�Ա",true);
            dlg.setVisible(true);
        }

    }
}
