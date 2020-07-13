package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanGoodsOrder;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmMain_order extends JFrame implements ActionListener {

    boolean flag = true;
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    //�ܲ˵�ѡ��
    private JMenu menu_Order=new JMenu("����ɸѡ");
    private JMenu menu_OrderAccount=new JMenu("��������");
    //����ɸѡѡ��
    private JMenuItem  menuItem_NoDo=new JMenuItem("δ����");
    private JMenuItem  menuItem_Doing=new JMenuItem("������");
    private JMenuItem  menuItem_OverTime=new JMenuItem("��ʱ");
    private JMenuItem  menuItem_Done=new JMenuItem("���ʹ�");
    private JMenuItem  menuItem_Cancel=new JMenuItem("��ȡ��");
    private JMenuItem  menuItem_All=new JMenuItem("��ʾȫ������");
    //��������ѡ��
    private JMenuItem  menuItem_SelectRider=new JMenuItem("��������");
    private JMenuItem  menuItem_ChangeRider=new JMenuItem("��������");
    private JMenuItem  menuItem_Confirm=new JMenuItem("ȷ���ʹ�");

    //״̬��
    private JPanel statusBar = new JPanel();
    //��������
    private static Object tblOrderTitle[] = BeanGoodsOrder.tblOrderTitle;
    private static Object tblOrderData[][];
    private static DefaultTableModel tabOrderModel = new DefaultTableModel();
    private static JTable dataTableOrder = new JTable(tabOrderModel);

    //��ʼ����Ϣ
    public static List<BeanGoodsOrder> allOrder = null;
    public static BeanGoodsOrder curOrder = null;
    //ʵ����ʾ���ж���
    private void reloadOrderTable(){
        if(flag){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        flag = false;
        //�жϳ�ʱ
        for(int i = 0 ; i < allOrder.size() ; i++){
            if(allOrder.get(i).getRequest_time().getTime() < System.currentTimeMillis() &&
                    allOrder.get(i).getOrder_state().equals("������")) {
                try {
                    TakeoutAssistantUtil.orderManager.modifyOverTime(allOrder.get(i));
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }
        tblOrderData = new Object[allOrder.size()][BeanGoodsOrder.tblOrderTitle.length];
        for(int i = 0 ; i < allOrder.size() ; i++){
            for(int j = 0 ; j < BeanGoodsOrder.tblOrderTitle.length ; j++)
                tblOrderData[i][j] = allOrder.get(i).getCell(j);
        }
        tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);
        this.dataTableOrder.validate();
        this.dataTableOrder.repaint();
    }
    //������
    public FrmMain_order(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("��������");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //����ɸѡ�˵�
        this.menu_Order.add(this.menuItem_NoDo); this.menuItem_NoDo.addActionListener(this);
        this.menu_Order.add(this.menuItem_Doing); this.menuItem_Doing.addActionListener(this);
        this.menu_Order.add(this.menuItem_OverTime); this.menuItem_OverTime.addActionListener(this);
        this.menu_Order.add(this.menuItem_Done); this.menuItem_Done.addActionListener(this);
        this.menu_Order.add(this.menuItem_Cancel); this.menuItem_Cancel.addActionListener(this);
        this.menu_Order.add(this.menuItem_All); this.menuItem_All.addActionListener(this);
        //���������˵�
        this.menu_OrderAccount.add(this.menuItem_SelectRider); this.menuItem_SelectRider.addActionListener(this);
        this.menu_OrderAccount.add(this.menuItem_ChangeRider); this.menuItem_ChangeRider.addActionListener(this);
        this.menu_OrderAccount.add(this.menuItem_Confirm); this.menuItem_Confirm.addActionListener(this);

        menubar.add(menu_Order);
        menubar.add(menu_OrderAccount);
        this.setJMenuBar(menubar);

        //��������
        this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
        this.dataTableOrder.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmMain_order.this.dataTableOrder.getSelectedRow();
                if(i < 0) {
                    return;
                }
                //FrmMain_order.this.reloadOrderTable2();
                curOrder = allOrder.get(i);
            }
        });

        this.reloadOrderTable();  //������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("����!����Ա!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //ɸѡ"δ����"����
        if(e.getSource() == this.menuItem_NoDo){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadNoDo();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //ɸѡ"������"����
        else if(e.getSource() == this.menuItem_Doing){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadDing();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //ɸѡ"���ʹ�"����
        else if(e.getSource() == this.menuItem_Done){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadConfirm();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //ɸѡ"��ʱ"����
        else if(e.getSource() == this.menuItem_OverTime){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadOverTime();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //ɸѡ"��ȡ��"����
        if(e.getSource() == this.menuItem_Cancel){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadCancel();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //ɸѡ���ж���
        else if(e.getSource() == this.menuItem_All){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadAll();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //��������
        else if(e.getSource() == this.menuItem_SelectRider){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "��ѡ��һ���", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("δ����")){
                JOptionPane.showMessageDialog(null, "�ö���������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmSelectRider dlg = new FrmSelectRider(this,"ѡ������",true);
            dlg.setVisible(true);
            try {
                TakeoutAssistantUtil.orderManager.modifyDoing(curOrder);//���¶���״̬
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }finally {
                reloadOrderTable();
            }
        }
        //��������
        else if(e.getSource() == this.menuItem_ChangeRider){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "��ѡ��һ���", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("������")){
                JOptionPane.showMessageDialog(null, "δ����������״̬�Ķ����޷��޸�����", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmChangeRider dlg = new FrmChangeRider(this,"��������",true);
            dlg.setVisible(true);
            FrmMain_order.this.reloadOrderTable();
        }
        //ȷ���ʹ�
        else if(e.getSource() == this.menuItem_Confirm){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "��ѡ��һ���", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("������") && !curOrder.getOrder_state().equals("��ʱ")){
                JOptionPane.showMessageDialog(null, "����δ��������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.orderManager.confirmOrder(curOrder);
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
    }

}
