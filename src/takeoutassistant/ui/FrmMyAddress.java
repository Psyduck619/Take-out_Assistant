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
    //地址管理按钮
    private JPanel  menuBar = new JPanel();
    private JButton btAdd = new JButton("新增");
    private JButton btDelete = new JButton("删除");
    private JButton btModify = new JButton("修改");

    //主界面
    private JPanel statusBar = new JPanel();
    //用户地址表构造
    private static Object tblAddressTitle[] = BeanAddress.tblAddressTitle;
    private static Object tblAddressData[][];
    private static DefaultTableModel tabAddressModel = new DefaultTableModel();
    private static JTable dataTableAddress = new JTable(tabAddressModel);

    //初始化信息
    public static BeanAddress curAddress = null;
    public static List<BeanAddress> allAddress = null;
    //显示所有地址
    private void reloadAddressTable(){
        try {
            allAddress = TakeoutAssistantUtil.addressManager.loadAddress(currentLoginUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
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
    //主界面
    public FrmMyAddress(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("我的地址");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //地址功能按钮设置监听
        menuBar.add(btAdd);
        menuBar.add(btDelete);
        menuBar.add(btModify);
        this.btAdd.addActionListener(this);
        this.btDelete.addActionListener(this);
        this.btModify.addActionListener(this);
        //菜单栏
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //地址信息显示
        this.getContentPane().add(new JScrollPane(this.dataTableAddress), BorderLayout.CENTER);
        this.dataTableAddress.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //得到目前选择的地址
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
        this.reloadAddressTable();  //加载地址信息
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //新增地址
        if(e.getSource() == this.btAdd){
            FrmAddAddress dlg = new FrmAddAddress(this,"新增地址",true);
            dlg.setVisible(true);
            reloadAddressTable();
        }
        //删除地址
        else if(e.getSource() == this.btDelete){
            if(this.curAddress == null) {
                JOptionPane.showMessageDialog(null, "请选择一个地址", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try { //判断地址是否存在于订单中
                if(TakeoutAssistantUtil.addressManager.ifHavingOrder(curAddress)){
                    JOptionPane.showMessageDialog(null, "该地址已存在于订单,无法删除", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            try {
                TakeoutAssistantUtil.addressManager.deleteAddress(this.curAddress);
                reloadAddressTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //修改地址信息
        else if(e.getSource() == this.btModify){
            if(this.curAddress == null) {
                JOptionPane.showMessageDialog(null, "请选择一个地址", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyAddress dlg = new FrmModifyAddress(this,"修改地址信息",true);
            dlg.setVisible(true);
            reloadAddressTable();
            curAddress = null;
        }

    }

}
