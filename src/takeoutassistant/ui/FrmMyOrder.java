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

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmConfirm.confirm;

public class FrmMyOrder extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //��������ť
    private JPanel menuBar = new JPanel();
    private JButton btnOrderInfo = new JButton("��������");
    private JButton btnRiderComment = new JButton("��������");
    private JButton btnCancel = new JButton("ȡ������");
    private JButton btnDelete = new JButton("ɾ������");
    private JButton btnShowGoodsComment = new JButton("�ҵ���Ʒ����");
    private JButton btnShowRiderComment = new JButton("�ҵ���������");

    //������
    private JPanel statusBar = new JPanel();
    //�û���������
    private static Object tblOrderTitle[] = BeanGoodsOrder.tblOrderTitle;
    private static Object tblOrderData[][];
    private static DefaultTableModel tabOrderModel = new DefaultTableModel();
    private static JTable dataTableOrder = new JTable(tabOrderModel);

    //��ʼ����Ϣ
    public static BeanGoodsOrder curOrder = null;
    public static List<BeanGoodsOrder> allOrder = null;
    //��ʾ����
    private void reloadOrderTable(){
        try {
            System.out.println(currentLoginUser.getUser_id());
            allOrder = TakeoutAssistantUtil.orderManager.loadUsers(currentLoginUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
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
    public FrmMyOrder(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("�ҵĶ���");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //�������ܰ�ť���.���ü���
        menuBar.add(btnOrderInfo); this.btnOrderInfo.addActionListener(this);
        menuBar.add(btnRiderComment); this.btnRiderComment.addActionListener(this);
        menuBar.add(btnCancel); this.btnCancel.addActionListener(this);
        menuBar.add(btnDelete); this.btnDelete.addActionListener(this);
        menuBar.add(btnShowGoodsComment); this.btnShowGoodsComment.addActionListener(this);
        menuBar.add(btnShowRiderComment); this.btnShowRiderComment.addActionListener(this);
        //�˵���
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //������Ϣ��ʾ
        this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
        this.dataTableOrder.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�õ�Ŀǰѡ��Ķ���
                int i = FrmMyOrder.this.dataTableOrder.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curOrder = allOrder.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });
        this.reloadOrderTable();  //���ض�����Ϣ
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //��ʾ���������
        if(e.getSource() == this.btnOrderInfo){
            if(curOrder == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmShowOrderInfo();
        }
        //ȡ������
        else if(e.getSource() == this.btnCancel){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "��ѡ��һ���", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("δ����")){
                JOptionPane.showMessageDialog(null, "�����ѽ������ͻ���ȡ��,�����޷�ȡ��", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmConfirm dlg = new FrmConfirm(this, "��ʾ", true); //���û��ٴ�ȷ���Ƿ�ȡ��
            dlg.setVisible(true);
            if(confirm){
                try {
                    TakeoutAssistantUtil.orderManager.modifyCancel(curOrder);
                    JOptionPane.showMessageDialog(null, "ȡ���ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                } catch (BaseException baseException) {
                    baseException.printStackTrace();
                }
                reloadOrderTable();
            }
            else
                return;
        }
        //ɾ������
        else if(e.getSource() == this.btnDelete){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "��ѡ��һ���", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("��ȡ��")){
                JOptionPane.showMessageDialog(null, "�ö���δȡ��,�޷�ɾ��", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.orderManager.deleteOrder(curOrder);
                JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //��������
        else if(e.getSource() == this.btnRiderComment){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "��ѡ��һ���", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("���ʹ�")){
                JOptionPane.showMessageDialog(null, "����δ���,�޷�����", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //�ж��Ƿ�������
            try {
                boolean flag = TakeoutAssistantUtil.riderAccountManager.isComment(curOrder);
                if(flag){
                    JOptionPane.showMessageDialog(null, "�ö����ѽ�������", "����",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            FrmAddRiderComment dlg = new FrmAddRiderComment(this,"��������",true);
            dlg.setVisible(true);
            reloadOrderTable();
        }
        //�鿴�����ҵ���Ʒ����
        if(e.getSource() == this.btnShowGoodsComment){
            new FrmMyGoodsComment();
        }
        //�鿴�����ҵ���������
        if(e.getSource() == this.btnShowRiderComment){
            new FrmMyRiderComment();
        }
    }

}
