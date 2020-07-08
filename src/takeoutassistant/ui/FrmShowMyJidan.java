package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanUser;
import takeoutassistant.model.BeanUserJidan;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmShowMyJidan extends JFrame {

    private static final long serialVersionUID = 1L;

    //������
    private JPanel statusBar = new JPanel();
    //�ҵ��Ż�ȯ����
    private static Object tblMyJidanTitle[] = BeanUserJidan.tblMyJidanTitle;
    private static Object tblMyJidanData[][];
    private static DefaultTableModel tabMyJidanModel = new DefaultTableModel();
    private static JTable dataTableMyJidan = new JTable(tabMyJidanModel);

    //��ʼ����Ϣ
    public static BeanUser curUser = currentLoginUser;
    public static List<BeanUserJidan> allMyJidan = null;
    //��ʾ�ҵļ������
    private void reloadMyJidanTable(){
        try {
            allMyJidan = TakeoutAssistantUtil.myJidanManager.loadMyJidan(curUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblMyJidanData = new Object[allMyJidan.size()][BeanUserJidan.tblMyJidanTitle.length];
        for(int i = 0 ; i < allMyJidan.size() ; i++){
            for(int j = 0 ; j < BeanUserJidan.tblMyJidanTitle.length ; j++)
                tblMyJidanData[i][j] = allMyJidan.get(i).getCell(j);
        }
        tabMyJidanModel.setDataVector(tblMyJidanData,tblMyJidanTitle);
        this.dataTableMyJidan.validate();
        this.dataTableMyJidan.repaint();
    }
    //������
    public FrmShowMyJidan(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("�ҵļ������");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableMyJidan), BorderLayout.CENTER);

        this.reloadMyJidanTable();  //������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String name = curUser.getUser_name();
        JLabel label=new JLabel("����!�𾴵�"+name+"!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    
}
