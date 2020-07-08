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

    //主界面
    private JPanel statusBar = new JPanel();
    //我的优惠券表构造
    private static Object tblMyJidanTitle[] = BeanUserJidan.tblMyJidanTitle;
    private static Object tblMyJidanData[][];
    private static DefaultTableModel tabMyJidanModel = new DefaultTableModel();
    private static JTable dataTableMyJidan = new JTable(tabMyJidanModel);

    //初始化信息
    public static BeanUser curUser = currentLoginUser;
    public static List<BeanUserJidan> allMyJidan = null;
    //显示我的集单情况
    private void reloadMyJidanTable(){
        try {
            allMyJidan = TakeoutAssistantUtil.myJidanManager.loadMyJidan(curUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
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
    //主界面
    public FrmShowMyJidan(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("我的集单情况");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableMyJidan), BorderLayout.CENTER);

        this.reloadMyJidanTable();  //加载信息
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String name = curUser.getUser_name();
        JLabel label=new JLabel("您好!尊敬的"+name+"!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    
}
