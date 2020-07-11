package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanRider;
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

import static takeoutassistant.ui.FrmMain_order.curOrder;


public class FrmSelectRider extends JDialog implements ActionListener {

    private JPanel  menuBar = new JPanel();
    private JButton btSelect = new JButton("ѡ��");//ѡ��ť
    //������Ϣ����
    private static Object tblRiderTitle[] = BeanRider.tblRiderTitle;
    private static Object tblRiderData[][];
    private static DefaultTableModel tabRiderModel = new DefaultTableModel();
    private static JTable dataTableRider = new JTable(tabRiderModel);

    //��ʼ����Ϣ
    private List<BeanRider> allRider = null;
    public static BeanRider curRider = null;
    //��ʾ��������
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

    //������
    public FrmSelectRider(FrmMain_order f,String s, boolean b){
        //���ô�����Ϣ
        super(f,s,b);
        this.setSize(1000,600);
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        menuBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        menuBar.add(btSelect);
        this.btSelect.addActionListener(this);
        this.getContentPane().add(menuBar, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(this.dataTableRider), BorderLayout.CENTER);
        this.dataTableRider.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmSelectRider.this.dataTableRider.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curRider = allRider.get(i);
            }
        });

        this.reloadRiderTable();  //������Ϣ
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btSelect){
            if(this.curRider == null) {
                JOptionPane.showMessageDialog(null, "��ѡ��һ������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.orderManager.modifyRider(curOrder,curRider);//����������Ϣ
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            this.setVisible(false);
        }
    }

}
