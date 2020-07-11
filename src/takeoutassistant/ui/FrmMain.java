package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static takeoutassistant.ui.FrmLogin.loginType;

public class FrmMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    //�ܲ˵�ѡ��
    private JMenu menu_seller=new JMenu("�̼ҹ���");
    private JMenu menu_type=new JMenu("��Ʒ������");
    private JMenu menu_goods=new JMenu("��Ʒ����");
    private JMenu menu_others=new JMenu("�������");
    private JMenu menu_admin=new JMenu("����Ա����");
    //�̼Ҳ˵�ѡ��
    private JMenuItem  menuItem_AddSeller=new JMenuItem("����̼�");
    private JMenuItem  menuItem_DeleteSeller=new JMenuItem("ɾ���̼�");
    private JMenuItem  menuItem_ModifyName=new JMenuItem("�޸�����");
    private JMenuItem  menuItem_ShowLevel=new JMenuItem("�̼��Ǽ�ɸѡ");
    private JMenuItem  menuItem_ShowCoupon=new JMenuItem("�����Żݹ���");
    //��Ʒ���˵�ѡ��
    private JMenuItem  menuItem_AddType=new JMenuItem("������");
    private JMenuItem  menuItem_DeleteType=new JMenuItem("ɾ�����");
    private JMenuItem  menuItem_ModifyType=new JMenuItem("�޸��������");
    //��Ʒ�˵�ѡ��
    private JMenuItem  menuItem_AddGoods=new JMenuItem("�����Ʒ");
    private JMenuItem  menuItem_DeleteGoods=new JMenuItem("ɾ����Ʒ");
    private JMenuItem  menuItem_ModifyGoods=new JMenuItem("������Ʒ��Ϣ");

    private JMenuItem  menuItem_Rider=new JMenuItem("���ֹ���");
    private JMenuItem  menuItem_Order=new JMenuItem("��������");

    private JMenuItem  menuItem_AddAdmin=new JMenuItem("����Ա���");
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�����޸�");

    //������
    private FrmLogin dlgLogin = null;
    //״̬��
    private JPanel statusBar = new JPanel();
    //�̼���Ϣ����
    private static Object tblSellerTitle[] = BeanSeller.tableTitles;
    private static Object tblSellerData[][];
    private static DefaultTableModel tabSellerModel = new DefaultTableModel();
    private static JTable dataTableSeller = new JTable(tabSellerModel);
    //��Ʒ������
    private Object tblGTypeTitle[] = BeanGoodsType.tblGTypeTitle;
    private Object tblGTypeData[][];
    DefaultTableModel tabGTypeModel = new DefaultTableModel();
    private JTable dataTableGType = new JTable(tabGTypeModel);
    //��Ʒ����
    private Object tblGoodsTitle[] = BeanGoods.tblGoodsTitle;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel = new DefaultTableModel();
    private JTable dataTableGoods = new JTable(tabGoodsModel);

    //��ʼ���̼���Ϣ
    public static BeanSeller curSeller = null;
    public static BeanGoodsType curType = null;
    public static BeanGoods curGoods = null;
    public static List<BeanSeller> allSeller = null;
    List<BeanGoodsType> allType = null;
    List<BeanGoods> allGoods = null;
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
    //��ʾ������Ʒ����
    private void reloadGTypeTabel(int sellerIdx){
        if(sellerIdx < 0) return;
        curSeller = allSeller.get(sellerIdx);
        try {
            allType = TakeoutAssistantUtil.goodsTypeManager.loadTypes(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGTypeData = new Object[allType.size()][BeanGoodsType.tblGTypeTitle.length];
        for(int i = 0 ; i < allType.size() ; i++){
            for(int j = 0 ; j < BeanGoodsType.tblGTypeTitle.length ; j++)
                tblGTypeData[i][j] = allType.get(i).getCell(j);
        }

        tabGTypeModel.setDataVector(tblGTypeData,tblGTypeTitle);
        this.dataTableGType.validate();
        this.dataTableGType.repaint();
    }
    //��ʾ������Ʒ
    private void reloadGoodsTabel(int typeIdx){
        if(typeIdx < 0) {
            while(tabGoodsModel.getRowCount() > 0){
                tabGoodsModel.removeRow(tabGoodsModel.getRowCount()-1);
            }
            return;
        }
        curType = allType.get(typeIdx);
        try {
            allGoods = TakeoutAssistantUtil.goodsManager.loadGoods(curType);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[allGoods.size()][BeanGoods.tblGoodsTitle.length];
        for(int i = 0 ; i < allGoods.size() ; i++){
            for(int j = 0 ; j < BeanGoods.tblGoodsTitle.length ; j++)
                tblGoodsData[i][j] = allGoods.get(i).getCell(j);
        }

        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }
    //������
    public FrmMain(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("�������ֹ���ϵͳ");
        dlgLogin=new FrmLogin(this,"��¼",true);
        dlgLogin.setVisible(true);
        //�̼Ҳ˵�
        this.menu_seller.add(this.menuItem_AddSeller); this.menuItem_AddSeller.addActionListener(this);
        this.menu_seller.add(this.menuItem_DeleteSeller); this.menuItem_DeleteSeller.addActionListener(this);
        this.menu_seller.add(this.menuItem_ModifyName); this.menuItem_ModifyName.addActionListener(this);
        this.menu_seller.add(this.menuItem_ShowLevel); this.menuItem_ShowLevel.addActionListener(this);
        this.menu_seller.add(this.menuItem_ShowCoupon); this.menuItem_ShowCoupon.addActionListener(this);
        //��Ʒ���˵�
        this.menu_type.add(this.menuItem_AddType); this.menuItem_AddType.addActionListener(this);
        this.menu_type.add(this.menuItem_DeleteType); this.menuItem_DeleteType.addActionListener(this);
        this.menu_type.add(this.menuItem_ModifyType); this.menuItem_ModifyType.addActionListener(this);
        //��Ʒ�˵�
        this.menu_goods.add(this.menuItem_AddGoods); this.menuItem_AddGoods.addActionListener(this);
        this.menu_goods.add(this.menuItem_DeleteGoods); this.menuItem_DeleteGoods.addActionListener(this);
        this.menu_goods.add(this.menuItem_ModifyGoods); this.menuItem_ModifyGoods.addActionListener(this);
        //��������˵�
        this.menu_others.add(this.menuItem_Rider); this.menuItem_Rider.addActionListener(this);
        this.menu_others.add(this.menuItem_Order); this.menuItem_Order.addActionListener(this);
        //����Ա����
        this.menu_admin.add(this.menuItem_AddAdmin); this.menuItem_AddAdmin.addActionListener(this);
        this.menu_admin.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);

        menubar.add(menu_seller);
        menubar.add(menu_type);
        menubar.add(menu_goods);
        menubar.add(menu_others);
        menubar.add(menu_admin);
        this.setJMenuBar(menubar);

        //�����沼��
        JScrollPane js1 = new JScrollPane(this.dataTableSeller);
        js1.setPreferredSize(new Dimension(500, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableGType);
        js2.setPreferredSize(new Dimension(200, 10));
        JScrollPane js3 = new JScrollPane(this.dataTableGoods);
        js3.setPreferredSize(new Dimension(400, 10));
        //�̼���Ϣ����
        //this.getContentPane().add(new JScrollPane(this.dataTableSeller), BorderLayout.WEST);
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableSeller.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain.this.dataTableSeller.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain.this.reloadGTypeTabel(i);
                FrmMain.this.reloadGoodsTabel(-1);
            }
        });
        //��Ʒ�������
        //this.getContentPane().add(new JScrollPane(this.dataTableGType), BorderLayout.CENTER);
        this.getContentPane().add(js2, BorderLayout.CENTER);
        this.dataTableGType.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain.this.dataTableGType.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain.this.reloadGoodsTabel(i);
            }
        });
        //��Ʒ����
        //this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.EAST);
        this.getContentPane().add(js3, BorderLayout.EAST);
        this.dataTableGoods.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = FrmMain.this.dataTableGoods.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curGoods = allGoods.get(i);
                System.out.println(curGoods);
            }
        });

        this.reloadSellerTable();  //����������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("����!����Ա!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        if(loginType == "����Ա"){
            this.setVisible(true);
        }
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
        //��ʾָ���Ǽ��̼�
        else if(e.getSource() == this.menuItem_ShowLevel){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmLevel dlg = new FrmLevel(this,"ѡ���Ǽ��̼�",true);
            dlg.setVisible(true);
        }
        //�̼������Żݹ������
        else if(e.getSource() == this.menuItem_ShowCoupon){
            new FrmMain_seller();
        }

        //������
        else if(e.getSource() == this.menuItem_AddType){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddType dlg = new FrmAddType(this,"������",true);
            dlg.seller = curSeller;
            dlg.setVisible(true);
            FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
        }
        //ɾ�����
        else if(e.getSource() == this.menuItem_DeleteType){
            int i = FrmMain.this.dataTableGType.getSelectedRow();
            if(i < 0) {
                JOptionPane.showMessageDialog(null, "��ѡ�����", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.goodsTypeManager.deleteGoodsType(this.allType.get(i));
                FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�޸��������
        else if(e.getSource() == this.menuItem_ModifyType){
            if(this.curType == null) {
                JOptionPane.showMessageDialog(null, "��ѡ�����", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyType dlg = new FrmModifyType(this,"�޸��������",true);
            dlg.setVisible(true);
            FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
        }

        //�����Ʒ
        else if(e.getSource() == this.menuItem_AddGoods){
            if(this.curType == null) {
                JOptionPane.showMessageDialog(null, "��ѡ�����", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddGoods dlg = new FrmAddGoods(this,"�����Ʒ",true);
            dlg.type = curType;
            dlg.setVisible(true);
            FrmMain.this.reloadGoodsTabel(FrmMain.this.dataTableGType.getSelectedRow());
            FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
        }
        //ɾ����Ʒ
        else if(e.getSource() == this.menuItem_DeleteGoods){
            int i = FrmMain.this.dataTableGoods.getSelectedRow();
            if(i < 0) {
                JOptionPane.showMessageDialog(null, "��ѡ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.goodsManager.deleteGoods(this.allGoods.get(i));
                FrmMain.this.reloadGoodsTabel(FrmMain.this.dataTableGType.getSelectedRow());
                FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�޸���Ʒ��Ϣ
        else if(e.getSource() == this.menuItem_ModifyGoods){
            if(this.curGoods == null) {
                JOptionPane.showMessageDialog(null, "��ѡ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyGoods dlg = new FrmModifyGoods(this,"������Ʒ��Ϣ",true);
            dlg.setVisible(true);
            FrmMain.this.reloadGoodsTabel(FrmMain.this.dataTableGoods.getSelectedRow());
            curGoods = null;
        }

        //���ֹ������
        else if(e.getSource() == this.menuItem_Rider){
            new FrmMain_rider();
        }
        //�����������
        else if(e.getSource() == this.menuItem_Order){
            new FrmMain_order();
        }

        //��ӹ���Ա
        else if(e.getSource() == this.menuItem_AddAdmin) {
            FrmAddAdmin dlg = new FrmAddAdmin(this, "��ӹ���Ա", true);
            dlg.setVisible(true);
        }
        //����Ա�����޸�
        else if(e.getSource() == this.menuItem_modifyPwd){
            FrmModifyPwd dlg = new FrmModifyPwd(this,"�����޸�",true);
            dlg.setVisible(true);
        }

    }
}
