package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAddress;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.model.BeanUser;
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
import static takeoutassistant.ui.FrmMain_user.curSeller;
import static takeoutassistant.ui.FrmShowSeller.curGoods;

public class FrmShowMyCart extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //״̬���Ͳ˵���
    private JPanel statusBar = new JPanel();
    private JPanel  menuBar = new JPanel();
    private JButton btDelete = new JButton("ɾ����Ʒ");
    private JButton btAdd = new JButton("������Ʒ����");
    private JButton btLose = new JButton("������Ʒ����");
    private JButton btClear = new JButton("��չ��ﳵ");
    //�ҵĹ��ﳵ����
    private static Object tblMyCartTitle[] = BeanOrderInfo.tblMyCartTitle;
    private static Object tblMyCartData[][];
    private static DefaultTableModel tabMyCartModel = new DefaultTableModel();
    private static JTable dataTableMyCart = new JTable(tabMyCartModel);

    //��ʼ����Ϣ
    private BeanUser curUser = currentLoginUser;
    public static BeanOrderInfo curMyCart = null;
    private List<BeanOrderInfo> allMyCart = null;
    //��ʾ�ҵĹ��ﳵ
    private void reloadMyCartTable(){
        try {
            allMyCart = TakeoutAssistantUtil.orderInfoManager.loadOrderInfo(curUser);
            System.out.println("allCart:"+allMyCart.size());
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblMyCartData = new Object[allMyCart.size()][BeanOrderInfo.tblMyCartTitle.length];
        for(int i = 0 ; i < allMyCart.size() ; i++){
            for(int j = 0; j < BeanOrderInfo.tblMyCartTitle.length ; j++)
                tblMyCartData[i][j] = allMyCart.get(i).getCell(j);
        }
        tabMyCartModel.setDataVector(tblMyCartData,tblMyCartTitle);
        this.dataTableMyCart.validate();
        this.dataTableMyCart.repaint();
    }

    //������
    public FrmShowMyCart(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("�ҵĹ��ﳵ");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //���ò˵�����Ϣ
        menuBar.add(btDelete);
        menuBar.add(btAdd);
        menuBar.add(btLose);
        menuBar.add(btClear);
        this.btDelete.addActionListener(this);
        this.btAdd.addActionListener(this);
        this.btLose.addActionListener(this);
        this.btClear.addActionListener(this);
        //�˵���
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //������
        this.getContentPane().add(new JScrollPane(this.dataTableMyCart), BorderLayout.CENTER);
        this.dataTableMyCart.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�õ�Ŀǰѡ�����Ʒ��
                int i = FrmShowMyCart.this.dataTableMyCart.getSelectedRow();
                if(i < 0) {
                    return;
                }
                reloadMyCartTable();
                System.out.println(i);
                System.out.println(allMyCart.size());
                curMyCart = allMyCart.get(i);
            }
        });
        //������Ϣ
        this.reloadMyCartTable();
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String name = curSeller.getSeller_name();
        JLabel label=new JLabel("����!��ӭ����"+name+"!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);  //����ɼ�
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //ɾ��������Ʒ
        if(e.getSource() == this.btDelete){
            if(this.allMyCart.size() == 0){
                JOptionPane.showMessageDialog(null, "���ﳵΪ��", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(this.curMyCart == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.orderInfoManager.deleteOrderInfo(this.curMyCart);
                curMyCart = null;
                reloadMyCartTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�ڹ��ﳵ��������Ʒ����
        if(e.getSource() == this.btAdd){
            if(this.allMyCart.size() == 0){
                JOptionPane.showMessageDialog(null, "���ﳵΪ��", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(this.curMyCart == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddCartGoods dlg = new FrmAddCartGoods(this,"�����Ʒ����",true);
            dlg.setVisible(true);
            curMyCart = null;
            reloadMyCartTable();
        }
        //�ڹ��ﳵ�м�����Ʒ����
        if(e.getSource() == this.btLose){
            if(this.allMyCart.size() == 0){
                JOptionPane.showMessageDialog(null, "���ﳵΪ��", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(this.curMyCart == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmLoseCartGoods dlg = new FrmLoseCartGoods(this,"������Ʒ����",true);
            dlg.setVisible(true);
            curMyCart = null;
            reloadMyCartTable();
        }
        //��չ��ﳵ
        if(e.getSource() == this.btClear){
            if(this.allMyCart.size() == 0){
                JOptionPane.showMessageDialog(null, "���ﳵ�ѿ�", "��ʾ",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                FrmConfirm dlg = new FrmConfirm(this, "��ʾ", true);
                dlg.setVisible(true);
                if(confirm){
                    TakeoutAssistantUtil.orderInfoManager.deleteAll(curUser);
                    JOptionPane.showMessageDialog(null, "���ﳵ��ճɹ�~", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                    curMyCart = null;
                    reloadMyCartTable();
                }
                else
                    return;
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }
}
