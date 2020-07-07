package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class FrmMain_seller extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    //�ܲ˵�ѡ��
    private JMenu menu_seller=new JMenu("�̼ҹ���");
    private JMenu menu_manjian=new JMenu("��������");
    private JMenu menu_coupon=new JMenu("�Ż�ȯ����");
    private JMenu menu_others=new JMenu("����");
    //�̼Ҳ˵�ѡ��
    private JMenuItem  menuItem_AddSeller=new JMenuItem("����̼�");
    private JMenuItem  menuItem_DeleteSeller=new JMenuItem("ɾ���̼�");
    private JMenuItem  menuItem_ModifyName=new JMenuItem("�޸�����");
    private JMenuItem  menuItem_ShowLevel=new JMenuItem("�̼��Ǽ�ɸѡ");
    //�̼Ҳ˵�ѡ��
    private JMenuItem  menuItem_AddManjian=new JMenuItem("�������");
    private JMenuItem  menuItem_DeleteManjian=new JMenuItem("����ɾ��");
    private JMenuItem  menuItem_ModifyManjian=new JMenuItem("�����޸�");
    //��Ʒ���˵�ѡ��
    private JMenuItem  menuItem_AddCoupon=new JMenuItem("�Ż�ȯ���");
    private JMenuItem  menuItem_DeleteCoupon=new JMenuItem("�Ż�ȯɾ��");
    private JMenuItem  menuItem_ModifyCoupon=new JMenuItem("�Ż�ȯ�޸�");
    //��Ʒ�˵�ѡ��
    private JMenuItem  menuItem_AddGoods=new JMenuItem("�����Ʒ");
    private JMenuItem  menuItem_DeleteGoods=new JMenuItem("ɾ����Ʒ");
    private JMenuItem  menuItem_ModifyGoods=new JMenuItem("������Ʒ��Ϣ");

    //������
    private JPanel statusBar = new JPanel();
    //�̼���Ϣ����
    private static Object tblSellerTitle[] = BeanSeller.tableTitles;
    private static Object tblSellerData[][];
    private static DefaultTableModel tabSellerModel = new DefaultTableModel();
    private static JTable dataTableSeller = new JTable(tabSellerModel);
    //��������
    private Object tblManjianTitle[] = BeanManjian.tblManjianTitle;
    private Object tblManjianData[][];
    DefaultTableModel tabManjianModel = new DefaultTableModel();
    private JTable dataTableManjian = new JTable(tabManjianModel);
    //�Ż�ȯ����
    private Object tblCouponTitle[] = BeanCoupon.tblCouponTitle;
    private Object tblCouponData[][];
    DefaultTableModel tabCouponModel = new DefaultTableModel();
    private JTable dataTableCoupon = new JTable(tabCouponModel);

    //��ʼ���̼���Ϣ
    public static BeanSeller curSeller = null;
    public static BeanManjian curManjian = null;
    public static BeanCoupon curCoupon = null;
    public static List<BeanSeller> allSeller = null;
    List<BeanManjian> allManjian = null;
    List<BeanCoupon> allCoupon = null;
    //ʵ����ʾ�����̼�
    private void reloadSellerTable(){
        try {
            allSeller = TakeoutAssistantUtil.sellerManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSellerData = new Object[allSeller.size()][BeanSeller.tableTitles.length];
        for(int i = 0 ; i < allSeller.size() ; i++){
            for(int j = 0 ; j < BeanSeller.tableTitles.length ; j++)
                tblSellerData[i][j] = allSeller.get(i).getCell(j);
        }
        tabSellerModel.setDataVector(tblSellerData,tblSellerTitle);
        this.dataTableSeller.validate();
        this.dataTableSeller.repaint();
    }
    //ʵ����ʾָ���̼�
    public static void reloadSLevelTable(int level){
        try {
            allSeller = TakeoutAssistantUtil.sellerManager.loadLevel(level);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSellerData = new Object[allSeller.size()][BeanSeller.tableTitles.length];
        for(int i = 0 ; i < allSeller.size() ; i++){
            for(int j = 0 ; j < BeanSeller.tableTitles.length ; j++){
                tblSellerData[i][j] = allSeller.get(i).getCell(j);
            }
        }
        tabSellerModel.setDataVector(tblSellerData,tblSellerTitle);
        //this.dataTableSeller.validate();
        //this.dataTableSeller.repaint();
    }
    //��ʾ��������
    private void reloadManjianTabel(int sellerIdx){
        if(sellerIdx < 0) return;
        curSeller = allSeller.get(sellerIdx);
        try {
            allManjian = TakeoutAssistantUtil.manjianManager.loadManjian(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblManjianData = new Object[allManjian.size()][BeanManjian.tblManjianTitle.length];
        for(int i = 0 ; i < allManjian.size() ; i++){
            for(int j = 0 ; j < BeanManjian.tblManjianTitle.length ; j++)
                tblManjianData[i][j] = allManjian.get(i).getCell(j);
        }

        tabManjianModel.setDataVector(tblManjianData,tblManjianTitle);
        this.dataTableManjian.validate();
        this.dataTableManjian.repaint();
    }
    //��ʾ�Ż�ȯ
    private void reloadCouponTabel(int sellerIdx){
        if(sellerIdx < 0) return;
        curSeller = allSeller.get(sellerIdx);
        try {
            allCoupon = TakeoutAssistantUtil.couponManager.loadCoupon(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCouponData = new Object[allCoupon.size()][BeanCoupon.tblCouponTitle.length];
        for(int i = 0 ; i < allCoupon.size() ; i++){
            for(int j = 0 ; j < BeanCoupon.tblCouponTitle.length ; j++)
                tblCouponData[i][j] = allCoupon.get(i).getCell(j);
        }

        tabCouponModel.setDataVector(tblCouponData,tblCouponTitle);
        this.dataTableCoupon.validate();
        this.dataTableCoupon.repaint();
    }
    //������
    public FrmMain_seller(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("�̼������Żݹ���");
        //�̼Ҳ˵�
        this.menu_seller.add(this.menuItem_AddSeller); this.menuItem_AddSeller.addActionListener(this);
        this.menu_seller.add(this.menuItem_DeleteSeller); this.menuItem_DeleteSeller.addActionListener(this);
        this.menu_seller.add(this.menuItem_ModifyName); this.menuItem_ModifyName.addActionListener(this);
        this.menu_seller.add(this.menuItem_ShowLevel); this.menuItem_ShowLevel.addActionListener(this);
        //��Ʒ���˵�
        this.menu_manjian.add(this.menuItem_AddManjian); this.menuItem_AddManjian.addActionListener(this);
        this.menu_manjian.add(this.menuItem_DeleteManjian); this.menuItem_DeleteManjian.addActionListener(this);
        this.menu_manjian.add(this.menuItem_ModifyManjian); this.menuItem_ModifyManjian.addActionListener(this);
        //��Ʒ�˵�
        this.menu_coupon.add(this.menuItem_AddCoupon); this.menuItem_AddCoupon.addActionListener(this);
        this.menu_coupon.add(this.menuItem_DeleteCoupon); this.menuItem_DeleteCoupon.addActionListener(this);
        this.menu_coupon.add(this.menuItem_ModifyCoupon); this.menuItem_ModifyCoupon.addActionListener(this);

        menubar.add(menu_seller);
        menubar.add(menu_manjian);
        menubar.add(menu_coupon);
        menubar.add(menu_others);
        this.setJMenuBar(menubar);

        //�����沼��
        JScrollPane js1 = new JScrollPane(this.dataTableSeller);
        js1.setPreferredSize(new Dimension(400, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableManjian);
        js2.setPreferredSize(new Dimension(200, 10));
        JScrollPane js3 = new JScrollPane(this.dataTableCoupon);
        js3.setPreferredSize(new Dimension(650, 10));
        //�̼���Ϣ����
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableSeller.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain_seller.this.dataTableSeller.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain_seller.this.reloadManjianTabel(i);
                FrmMain_seller.this.reloadCouponTabel(i);
            }
        });
        //������Ϣ����
        this.getContentPane().add(js2, BorderLayout.CENTER);
        this.dataTableManjian.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain_seller.this.dataTableManjian.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curManjian = allManjian.get(i);
                //FrmMain_seller.this.reloadManjianTabel(i);
            }
        });
        this.getContentPane().add(js3, BorderLayout.EAST);
        this.dataTableCoupon.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain_seller.this.dataTableCoupon.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curCoupon = allCoupon.get(i);
                //FrmMain_seller.this.reloadCouponTabel(i);
            }
        });

        this.reloadSellerTable();  //����������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("����!����Ա!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
//        this.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//                System.exit(0);
//            }
//        });

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //����̼�
        if(e.getSource() == this.menuItem_AddSeller){
            FrmAddSeller dlg = new FrmAddSeller(this,"����̼�",true);
            dlg.setVisible(true);
            reloadSellerTable();
        }
        //ɾ���̼�
        else if(e.getSource() == this.menuItem_DeleteSeller){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.sellerManager.deleteSeller(this.curSeller);
                reloadSellerTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�޸��̼�����
        else if(e.getSource() == this.menuItem_ModifyName){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifySeller dlg = new FrmModifySeller(this,"����̼�",true);
            dlg.setVisible(true);
            reloadSellerTable();
        }

        //�������
        if(e.getSource() == this.menuItem_AddManjian){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddManjian dlg = new FrmAddManjian(this,"�������",true);
            dlg.seller = curSeller;
            dlg.setVisible(true);
            FrmMain_seller.this.reloadManjianTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
        }
        //ɾ������
        else if(e.getSource() == this.menuItem_DeleteManjian){
            if(this.curManjian == null) {
                JOptionPane.showMessageDialog(null, "��ѡ������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.manjianManager.deleteManjian(this.curManjian);
                FrmMain_seller.this.reloadManjianTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�޸�����
        else if(e.getSource() == this.menuItem_ModifyManjian){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyManjian dlg = new FrmModifyManjian(this,"�����޸�",true);
            dlg.setVisible(true);
            FrmMain_seller.this.reloadManjianTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
        }

        //����Ż�ȯ
        if(e.getSource() == this.menuItem_AddCoupon){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddCoupon dlg = new FrmAddCoupon(this,"����Ż�ȯ",true);
            dlg.seller = curSeller;
            dlg.setVisible(true);
            FrmMain_seller.this.reloadCouponTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
        }
        //ɾ���Ż�ȯ
        else if(e.getSource() == this.menuItem_DeleteCoupon){
            if(this.curCoupon == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���Ż�ȯ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.couponManager.deleteCoupon(this.curCoupon);
                FrmMain_seller.this.reloadCouponTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�޸��Ż�ȯ
        else if(e.getSource() == this.menuItem_ModifyCoupon){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyCoupon dlg = new FrmModifyCoupon(this,"�Ż�ȯ�޸�",true);
            dlg.setVisible(true);
            FrmMain_seller.this.reloadCouponTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
        }
    }
}
