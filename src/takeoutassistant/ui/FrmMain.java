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
    private JMenu menu_seller=new JMenu("�̼ҹ���");
    private JMenu menu_rider=new JMenu("���ֹ���");
    private JMenu menu_user=new JMenu("�û�����");
    private JMenu menu_admin=new JMenu("����Ա����");

    private JMenuItem  menuItem_AddSeller=new JMenuItem("����̼�");
    private JMenuItem  menuItem_DeleteSeller=new JMenuItem("ɾ���̼�");
    private JMenuItem  menuItem_ModifyName=new JMenuItem("�޸�����");
    private JMenuItem  menuItem_ShowLevel=new JMenuItem("�̼��Ǽ�ɸѡ");

    private JMenuItem  menuItem_AddStep=new JMenuItem("��Ӳ���");
    private JMenuItem  menuItem_DeleteStep=new JMenuItem("ɾ������");
    private JMenuItem  menuItem_startStep=new JMenuItem("��ʼ����");
    private JMenuItem  menuItem_finishStep=new JMenuItem("��������");
    private JMenuItem  menuItem_moveUpStep=new JMenuItem("��������");
    private JMenuItem  menuItem_moveDownStep=new JMenuItem("��������");

    private JMenuItem  menuItem_AddAdmin=new JMenuItem("����Ա���");
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�����޸�");

    private JMenuItem  menuItem_static1=new JMenuItem("ͳ��1");

    //������
    private FrmLogin dlgLogin = null;
    //״̬��
    private JPanel statusBar = new JPanel();
    //�̼���Ϣ����
    private static Object tblSellerTitle[] = BeanSeller.tableTitles;
    private static Object tblSellerData[][];
    private static DefaultTableModel tabSellerModel = new DefaultTableModel();
    private static JTable dataTableSeller = new JTable(tabSellerModel);

    private Object tblGTypeTitle[] = BeanGoodsType.tblGTypeTitle;
    private Object tblGTypeData[][];
    DefaultTableModel tabGTypeModel = new DefaultTableModel();
    private JTable dataTableGType = new JTable(tabGTypeModel);

    //��ʼ���̼���Ϣ
    public static BeanSeller curSeller = null;
    public static List<BeanSeller> allSeller = null;
    List<BeanGoodsType> goodsTypes = null;
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
            goodsTypes = TakeoutAssistantUtil.goodsTypeManager.loadTypes(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
//        tblGTypeData = new Object[goodsTypes.size()][BeanGoodsType.tblGTypeTitle.length];
//        for(int i = 0 ; i < goodsTypes.size() ; i++){
//            for(int j = 0 ; j < BeanGoodsType.tblGTypeTitle.length ; j++)
//                tblGTypeData[i][j] = goodsTypes.get(i).getCell(j);
//        }
//
//        tabGTypeModel.setDataVector(tblGTypeData,tblGTypeTitle);
//        this.dataTableGType.validate();
//        this.dataTableGType.repaint();
    }
    //������
    public FrmMain(){
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.setTitle("�󿪴��");
            dlgLogin=new FrmLogin(this,"��¼",true);
            dlgLogin.setVisible(true);
            //�̼Ҳ˵�
            this.menu_seller.add(this.menuItem_AddSeller); this.menuItem_AddSeller.addActionListener(this);
            this.menu_seller.add(this.menuItem_DeleteSeller); this.menuItem_DeleteSeller.addActionListener(this);
            this.menu_seller.add(this.menuItem_ModifyName); this.menuItem_ModifyName.addActionListener(this);
            this.menu_seller.add(this.menuItem_ShowLevel); this.menuItem_ShowLevel.addActionListener(this);
            //
            this.menu_rider.add(this.menuItem_AddStep); this.menuItem_AddStep.addActionListener(this);
            this.menu_rider.add(this.menuItem_DeleteStep); this.menuItem_DeleteStep.addActionListener(this);
            this.menu_rider.add(this.menuItem_startStep); this.menuItem_startStep.addActionListener(this);
            this.menu_rider.add(this.menuItem_finishStep); this.menuItem_finishStep.addActionListener(this);
            this.menu_rider.add(this.menuItem_moveUpStep); this.menuItem_moveUpStep.addActionListener(this);
            this.menu_rider.add(this.menuItem_moveDownStep); this.menuItem_moveDownStep.addActionListener(this);
            this.menu_user.add(this.menuItem_static1); this.menuItem_static1.addActionListener(this);
            this.menu_admin.add(this.menuItem_AddAdmin); this.menuItem_AddAdmin.addActionListener(this);
            this.menu_admin.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);

            menubar.add(menu_seller);
            menubar.add(menu_rider);
            menubar.add(menu_user);
            menubar.add(menu_admin);
            this.setJMenuBar(menubar);

            //�����沼��
            //�̼���Ϣ����
            this.getContentPane().add(new JScrollPane(this.dataTableSeller), BorderLayout.WEST);
            //�����ʾѡ����Ϣ
            this.dataTableSeller.addMouseListener(new MouseAdapter (){
                @Override
                public void mouseClicked(MouseEvent e) {
                    int i = FrmMain.this.dataTableSeller.getSelectedRow();
                    if(i < 0) {
                        return;
                    }
                    FrmMain.this.reloadGTypeTabel(i);
                }
            });
            this.getContentPane().add(new JScrollPane(this.dataTableGType), BorderLayout.CENTER);

            this.reloadSellerTable();
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
        if(e.getSource() == this.menuItem_ShowLevel){
            FrmLevel dlg = new FrmLevel(this,"ѡ���Ǽ��̼�",true);
            dlg.setVisible(true);
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
