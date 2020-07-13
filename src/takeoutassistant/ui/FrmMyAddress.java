package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAddress;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmMyAddress extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //��ַ����ť
    private JPanel  menuBar = new JPanel();
    private JButton btAdd = new JButton("����");
    private JButton btDelete = new JButton("ɾ��");
    private JButton btModify = new JButton("�޸�");

    //������
    private JPanel statusBar = new JPanel();
    //�û���ַ����
    private static Object tblAddressTitle[] = BeanAddress.tblAddressTitle;
    private static Object tblAddressData[][];
    private static DefaultTableModel tabAddressModel = new DefaultTableModel();
    private static JTable dataTableAddress = new JTable(tabAddressModel);

    //��ʼ����Ϣ
    public static BeanAddress curAddress = null;
    public static List<BeanAddress> allAddress = null;
    //��ʾ���е�ַ
    private void reloadAddressTable(){
        try {
            allAddress = TakeoutAssistantUtil.addressManager.loadAddress(currentLoginUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblAddressData = new Object[allAddress.size()][BeanAddress.tblAddressTitle.length];
        for(int i = 0 ; i < allAddress.size() ; i++){
            for(int j = 0 ; j < BeanAddress.tblAddressTitle.length ; j++)
                tblAddressData[i][j] = allAddress.get(i).getCell(j);
        }
        tabAddressModel.setDataVector(tblAddressData,tblAddressTitle);
        this.dataTableAddress.validate();
        this.dataTableAddress.repaint();
    }
    //������
    public FrmMyAddress(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("�ҵĵ�ַ");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //��ַ���ܰ�ť���ü���
        menuBar.add(btAdd);
        menuBar.add(btDelete);
        menuBar.add(btModify);
        this.btAdd.addActionListener(this);
        this.btDelete.addActionListener(this);
        this.btModify.addActionListener(this);
        //�˵���
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //��ַ��Ϣ��ʾ
        this.getContentPane().add(new JScrollPane(this.dataTableAddress), BorderLayout.CENTER);
        this.dataTableAddress.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�õ�Ŀǰѡ��ĵ�ַ
                int i = FrmMyAddress.this.dataTableAddress.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curAddress = allAddress.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });
//        this.dataTableAddress.getColumnModel().getColumn(0).setPreferredWidth(50);
//        this.dataTableAddress.getColumnModel().getColumn(1).setPreferredWidth(20);
//        this.dataTableAddress.getColumnModel().getColumn(2).setPreferredWidth(30);
        this.reloadAddressTable();  //���ص�ַ��Ϣ
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //������ַ
        if(e.getSource() == this.btAdd){
            FrmAddAddress dlg = new FrmAddAddress(this,"������ַ",true);
            dlg.setVisible(true);
            reloadAddressTable();
        }
        //ɾ����ַ
        else if(e.getSource() == this.btDelete){
            if(this.curAddress == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ����ַ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try { //�жϵ�ַ�Ƿ�����ڶ�����
                if(TakeoutAssistantUtil.addressManager.ifHavingOrder(curAddress)){
                    JOptionPane.showMessageDialog(null, "�õ�ַ�Ѵ����ڶ���,�޷�ɾ��", "����",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            try {
                TakeoutAssistantUtil.addressManager.deleteAddress(this.curAddress);
                reloadAddressTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //�޸ĵ�ַ��Ϣ
        else if(e.getSource() == this.btModify){
            if(this.curAddress == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ����ַ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyAddress dlg = new FrmModifyAddress(this,"�޸ĵ�ַ��Ϣ",true);
            dlg.setVisible(true);
            reloadAddressTable();
            curAddress = null;
        }

    }

}
