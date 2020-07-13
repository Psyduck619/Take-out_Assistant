package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmLogin.loginType;

public class FrmMain_user extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_buy=new JMenu("�̼�����Ʒ");
    private JMenu menu_order=new JMenu("��������");
    private JMenu menu_user=new JMenu("���˹���");
    private JMenu menu_VIP=new JMenu("��Ա����");

    private JMenuItem menuItem_ShowSeller=new JMenuItem("�����̼�");
    private JMenuItem menuItem_CheckSeller=new JMenuItem("ѡ���Ǽ��̼�");
    private JMenuItem menuItem_CheckGoods=new JMenuItem("��Ʒ����ѯ��Ʒ");
    private JMenuItem menuItem_CheckType=new JMenuItem("����ѯ��Ʒ");
    //�û���������
    private JMenuItem menuItem_ShowOrder=new JMenuItem("�鿴����");
    //���˲˵����
    private JMenuItem menuItem_myCoupon=new JMenuItem("�ҵ��Ż�ȯ");
    private JMenuItem menuItem_myJidan=new JMenuItem("�ҵļ���");
    private JMenuItem menuItem_myaddress=new JMenuItem("�ҵĵ�ַ");
    private JMenuItem menuItem_modifyUser=new JMenuItem("�޸ĸ�����Ϣ");
    private JMenuItem menuItem_modifyPwd=new JMenuItem("�޸�����");
    //��Ա�˵����
    private JMenuItem menuItem_openVIP=new JMenuItem("��ͨ��Ա");
    private JMenuItem menuItem_renewVIP=new JMenuItem("���ѻ�Ա");

    //������
    private JPanel statusBar = new JPanel();
    //�̼ұ�
    private static Object[] tblSellerTitle = BeanSeller.tableTitles2;
    private static Object[][] tblSellerData;
    static DefaultTableModel tabSellerModel = new DefaultTableModel();
    private JTable dataTableSeller = new JTable(tabSellerModel);
    //������
    private Object tblManjianTitle[] = BeanManjian.tblManjianTitle;
    private Object tblManjianData[][];
    DefaultTableModel tabManjianModel = new DefaultTableModel();
    private JTable dataTableManjian = new JTable(tabManjianModel);
    //������Ʒ��
    private Object tblGoodsTitle[] = BeanGoods.tblGoodsTitle2;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel = new DefaultTableModel();
    private JTable dataTableGoods = new JTable(tabGoodsModel);

    public static BeanSeller curSeller = null;
    static List<BeanSeller> allSeller = null;
    List<BeanManjian> allManjian = null;
    List<BeanGoods> allGoods = null;

    //ʵ����ʾ�����̼�
    private void reloadSellerTable(){
        try {
            allSeller = TakeoutAssistantUtil.sellerManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSellerData = new Object[allSeller.size()][BeanSeller.tableTitles2.length];
        for(int i = 0 ; i < allSeller.size() ; i++){
            for(int j = 0 ; j < BeanSeller.tableTitles2.length ; j++)
                tblSellerData[i][j] = allSeller.get(i).getCell2(j);
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
                tblSellerData[i][j] = allSeller.get(i).getCell2(j);
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
    //��ʾ������Ʒ
    private void reloadGoodsTabel(int sellerIdx){
        if(sellerIdx < 0) return;
        curSeller = allSeller.get(sellerIdx);
        try {
            allGoods = TakeoutAssistantUtil.goodsManager.loadHGoods(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[allGoods.size()][BeanGoods.tblGoodsTitle.length];
        for(int i = 0 ; i < allGoods.size() ; i++){
            for(int j = 0 ; j < BeanGoods.tblGoodsTitle.length ; j++)
                tblGoodsData[i][j] = allGoods.get(i).getCell2(j);
        }

        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }
    public FrmMain_user(){

        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("�󿪴��-��������");
        //�̼Ҳ˵�
        this.menu_buy.add(this.menuItem_ShowSeller); this.menuItem_ShowSeller.addActionListener(this);
        this.menu_buy.add(this.menuItem_CheckSeller); this.menuItem_CheckSeller.addActionListener(this);
        this.menu_buy.add(this.menuItem_CheckGoods); this.menuItem_CheckGoods.addActionListener(this);
        this.menu_buy.add(this.menuItem_CheckType); this.menuItem_CheckType.addActionListener(this);
        //�����˵�
        this.menu_order.add(this.menuItem_ShowOrder); this.menuItem_ShowOrder.addActionListener(this);
        //���˲˵�
        this.menu_user.add(this.menuItem_myCoupon); this.menuItem_myCoupon.addActionListener(this);
        this.menu_user.add(this.menuItem_myJidan); this.menuItem_myJidan.addActionListener(this);
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
        this.setJMenuBar(menubar);

        //�����沼��
        JScrollPane js1 = new JScrollPane(this.dataTableSeller);
        js1.setPreferredSize(new Dimension(500, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableGoods);
        js2.setPreferredSize(new Dimension(500, 10));
        JScrollPane js3 = new JScrollPane(this.dataTableManjian);
        js3.setPreferredSize(new Dimension(300, 10));
        //�̼���Ϣ����
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableSeller.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain_user.this.dataTableSeller.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain_user.this.reloadManjianTabel(i);
                FrmMain_user.this.reloadGoodsTabel(i);
            }
        });
        //������Ʒ��Ϣ����
        this.getContentPane().add(js2, BorderLayout.CENTER);
        //������Ϣ����
        this.getContentPane().add(js3, BorderLayout.EAST);
        //���س�ʼ��Ϣ
        this.reloadSellerTable();  //�����̼���Ϣ
//        this.reloadGoodsTabel(0);
//        this.reloadManjianTabel(0);
        //״̬��
        if(loginType == "�û�"){  //�û���¼����
            statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
            String name = currentLoginUser.getUser_name();
            Date dt = currentLoginUser.getVIP_end_time();
            if(currentLoginUser.isVIP()){
                name = "��Ա" + name + "!      ��Ա����ʱ��:" +dt;
            }
            JLabel label=new JLabel("����!�𾴵�" + name);
            statusBar.add(label);
            this.getContentPane().add(statusBar,BorderLayout.SOUTH);
            this.setVisible(true);
            //�жϵ�ǰ�û���û�й�������,�еĻ����Ƽ�
            try {
                if(TakeoutAssistantUtil.orderManager.ifBougnt(currentLoginUser))
                new FrmShowRecommend();
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }
        this.addWindowListener(new WindowAdapter(){  //�رմ��ڼ��˳�����
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
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
        //��ʾ�ҵļ�����Ϣ
        else if(e.getSource() == this.menuItem_myJidan){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "�û���¼����,������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmShowMyJidan();
        }
        //��ʾָ���̼ҽ���
        else if(e.getSource() == this.menuItem_ShowSeller){
            if(curSeller == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ���̼�", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmShowSeller();
        }
        //ɸѡ�̼�
        else if(e.getSource() == this.menuItem_CheckSeller){
            FrmLevel dlg = new FrmLevel(this,"ѡ���Ǽ��̼�",true);
            curSeller = null;
            dlg.setVisible(true);
        }
        //������Ʒ�����в�ѯ
        else if(e.getSource() == this.menuItem_CheckGoods){
            FrmPutGoodsName dlg = new FrmPutGoodsName(this,"��Ʒ��ѯ",true);
            dlg.setVisible(true);
        }
        //���������в�ѯ
        else if(e.getSource() == this.menuItem_CheckType){
            FrmPutTypeName dlg = new FrmPutTypeName(this,"��Ʒ��ѯ",true);
            dlg.setVisible(true);
        }
        //�û���ַ����
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
            if(currentLoginUser.isVIP()){
                JOptionPane.showMessageDialog(null, "���������Ļ�Ա�û�!", "��ʾ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmOpenVIP dlg = new FrmOpenVIP(this,"��ͨ��Ա",true);
            dlg.setVisible(true);
            //�ı���û��ĳƺ�
            try {
                Thread.sleep(1000);
                if(currentLoginUser.isVIP()){
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
            if(!currentLoginUser.isVIP()){
                JOptionPane.showMessageDialog(null, "�������ǻ�Ա�û�,���ȿ�ͨ��Ա", "��ʾ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmRenewVIP dlg = new FrmRenewVIP(this,"���ѻ�Ա",true);
            dlg.setVisible(true);
        }

        //�û��鿴����
        else if(e.getSource() == this.menuItem_ShowOrder){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "�û���¼����,������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmMyOrder();
        }

    }
}
