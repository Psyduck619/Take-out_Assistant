package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmMain_rider extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    //�ܲ˵�ѡ��
    private JMenu menu_Rider=new JMenu("���ֹ���");
    private JMenu menu_RiderAccount=new JMenu("���ʵ�����");
    //���ֲ˵�ѡ��
    private JMenuItem  menuItem_AddRider=new JMenuItem("�������");
    private JMenuItem  menuItem_DeleteRider=new JMenuItem("ɾ������");
    private JMenuItem  menuItem_ModifyRider=new JMenuItem("�޸��������");
    private JMenuItem  menuItem_ShowRider=new JMenuItem("ɸѡ����");
    //���ʵ��˵�ѡ��
    private JMenuItem  menuItem_DeleteRiderAccount=new JMenuItem("ɾ�����ʵ�");
    private JMenuItem  menuItem_ModifyRiderAccount=new JMenuItem("�޸����ʵ�");

    //������
    private JPanel statusBar = new JPanel();
    //������Ϣ����
    private static Object tblRiderTitle[] = BeanRider.tblRiderTitle;
    private static Object tblRiderData[][];
    private static DefaultTableModel tabRiderModel = new DefaultTableModel();
    private static JTable dataTableRider = new JTable(tabRiderModel);
    //���ʵ�����
    private static Object tblRiderAccountTitle[] = BeanRiderAccount.tblRiderAccountTitle;
    private static Object tblRiderAccountData[][];
    private static DefaultTableModel tabRiderAccountModel = new DefaultTableModel();
    private static JTable dataTableRiderAccount = new JTable(tabRiderAccountModel);

    //��ʼ����Ϣ
    public static BeanRider curRider = null;
    public static BeanRiderAccount curRiderAccount = null;
    public static List<BeanRider> allRider = null;
    public static List<BeanRiderAccount> allRiderAccount = null;
    //ʵ����ʾ��������
    private void reloadRiderTable(){
        try {
            allRider = TakeoutAssistantUtil.riderManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblRiderData = new Object[allRider.size()][BeanRider.tblRiderTitle.length];
        for(int i = 0 ; i < allRider.size() ; i++){
            for(int j = 0 ; j < BeanRider.tblRiderTitle.length ; j++)
                tblRiderData[i][j] = allRider.get(i).getCell(j);
        }
        tabRiderModel.setDataVector(tblRiderData,tblRiderTitle);
        this.dataTableRider.validate();
        this.dataTableRider.repaint();
    }
    //��ʾ�������ʵ�
    private void reloadRiderAccountTabel(int RiderIdx){
        if(RiderIdx < 0) return;
        curRider = allRider.get(RiderIdx);
        try {
            allRiderAccount = TakeoutAssistantUtil.riderAccountManager.loadAccount(curRider);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblRiderAccountData = new Object[allRiderAccount.size()][BeanRiderAccount.tblRiderAccountTitle.length];
        for(int i = 0 ; i < allRiderAccount.size() ; i++){
            for(int j = 0 ; j < BeanRiderAccount.tblRiderAccountTitle.length ; j++)
                tblRiderAccountData[i][j] = allRiderAccount.get(i).getCell(j);
        }

        tabRiderAccountModel.setDataVector(tblRiderAccountData,tblRiderAccountTitle);
        this.dataTableRiderAccount.validate();
        this.dataTableRiderAccount.repaint();
    }
    //������
    public FrmMain_rider(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("���ֹ���");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //���ֹ��ܲ˵�
        this.menu_Rider.add(this.menuItem_AddRider); this.menuItem_AddRider.addActionListener(this);
        this.menu_Rider.add(this.menuItem_DeleteRider); this.menuItem_DeleteRider.addActionListener(this);
        this.menu_Rider.add(this.menuItem_ModifyRider); this.menuItem_ModifyRider.addActionListener(this);
        //this.menu_Rider.add(this.menuItem_ShowRider); this.menuItem_ShowRider.addActionListener(this);
        //���ʵ����ܲ˵�
        this.menu_RiderAccount.add(this.menuItem_DeleteRiderAccount); this.menuItem_DeleteRiderAccount.addActionListener(this);
        this.menu_RiderAccount.add(this.menuItem_ModifyRiderAccount); this.menuItem_ModifyRiderAccount.addActionListener(this);

        menubar.add(menu_Rider);
        //menubar.add(menu_RiderAccount);
        this.setJMenuBar(menubar);

        //�����沼��
        JScrollPane js1 = new JScrollPane(this.dataTableRider);
        js1.setPreferredSize(new Dimension(750, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableRiderAccount);
        js2.setPreferredSize(new Dimension(300, 10));
        //������Ϣ����
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableRider.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain_rider.this.dataTableRider.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain_rider.this.reloadRiderAccountTabel(i);
            }
        });
        //���ʵ���Ϣ����
        this.getContentPane().add(js2, BorderLayout.CENTER);
        this.dataTableRiderAccount.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain_rider.this.dataTableRiderAccount.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curRiderAccount = allRiderAccount.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });

        this.reloadRiderTable();  //������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("����!����Ա!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //�������
        if(e.getSource() == this.menuItem_AddRider){
            FrmAddRider dlg = new FrmAddRider(this,"�������",true);
            dlg.setVisible(true);
            reloadRiderTable();
        }
        //ɾ������
        else if(e.getSource() == this.menuItem_DeleteRider){
            if(this.curRider == null) {
                JOptionPane.showMessageDialog(null, "��ѡ������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //�ж������Ƿ��͹���,���͹�,���޷�ɾ��
            try {
                if(TakeoutAssistantUtil.riderManager.ifHavingOrder(curRider)){
                    JOptionPane.showMessageDialog(null, "�����������ͼ�¼,��ֹɾ��", "����",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            try {
                TakeoutAssistantUtil.riderManager.deleteRider(this.curRider);
                reloadRiderTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�޸���������
        else if(e.getSource() == this.menuItem_ModifyRider){
            if(this.curRider == null) {
                JOptionPane.showMessageDialog(null, "��ѡ������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyStatus dlg = new FrmModifyStatus(this,"�޸��������",true);
            dlg.setVisible(true);
            reloadRiderTable();
        }

        //ɾ�����ʵ�
        else if(e.getSource() == this.menuItem_DeleteRiderAccount){
            if(this.curRiderAccount == null) {
                JOptionPane.showMessageDialog(null, "��ѡ�����ʵ�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.riderAccountManager.deleteRiderAccount(this.curRiderAccount);
                FrmMain_rider.this.reloadRiderAccountTabel(FrmMain_rider.this.dataTableRider.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�޸����ʵ�����
        else if(e.getSource() == this.menuItem_ModifyRiderAccount){
            if(this.curRider == null) {
                JOptionPane.showMessageDialog(null, "��ѡ�����ʵ�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyRiderAccount dlg = new FrmModifyRiderAccount(this,"�޸����ʵ�����",true);
            dlg.setVisible(true);
            FrmMain_rider.this.reloadRiderAccountTabel(FrmMain_rider.this.dataTableRider.getSelectedRow());
        }

    }

}
