package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAddress;
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

public class FrmSelectAddress extends JDialog implements ActionListener {

    private JPanel  menuBar = new JPanel();
    private JButton btSelect = new JButton("ѡ��");//ѡ��ť
    //�ҵ��Ż�ȯ����
    private static Object tblAddressTitle[] = BeanAddress.tblAddressTitle;
    private static Object tblAddressData[][];
    private static DefaultTableModel tabAddressModel = new DefaultTableModel();
    private static JTable dataTableAddress = new JTable(tabAddressModel);

    //��ʼ����Ϣ
    private BeanUser curUser = currentLoginUser;
    private List<BeanAddress> allAddress = null;
    public static BeanAddress curAddress = null;
    //��ʾ�����ҵ��Ż�ȯ
    private void reloadAddressTable(){
        try {
            allAddress = TakeoutAssistantUtil.addressManager.loadAddress(curUser);
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
    public FrmSelectAddress(FrmBuy f,String s, boolean b){
        //���ô�����Ϣ
        super(f,s,b);
        this.setSize(700,400);
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        menuBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        menuBar.add(btSelect);
        this.btSelect.addActionListener(this);
        this.getContentPane().add(menuBar, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(this.dataTableAddress), BorderLayout.CENTER);
        this.dataTableAddress.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmSelectAddress.this.dataTableAddress.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curAddress = allAddress.get(i);
            }
        });

        this.reloadAddressTable();  //������Ϣ
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btSelect){
            if(this.curAddress == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ���ַ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.setVisible(false);
        }
    }

}
