package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmMain_rider extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    //总菜单选项
    private JMenu menu_Rider=new JMenu("骑手管理");
    private JMenu menu_RiderAccount=new JMenu("入帐单管理");
    //骑手菜单选项
    private JMenuItem  menuItem_AddRider=new JMenuItem("添加骑手");
    private JMenuItem  menuItem_DeleteRider=new JMenuItem("删除骑手");
    private JMenuItem  menuItem_ModifyRider=new JMenuItem("修改骑手身份");
    private JMenuItem  menuItem_ShowRider=new JMenuItem("筛选骑手");
    //入帐单菜单选项
    private JMenuItem  menuItem_DeleteRiderAccount=new JMenuItem("删除入帐单");
    private JMenuItem  menuItem_ModifyRiderAccount=new JMenuItem("修改入帐单");

    //主界面
    private JPanel statusBar = new JPanel();
    //骑手信息表构造
    private static Object tblRiderTitle[] = BeanRider.tblRiderTitle;
    private static Object tblRiderData[][];
    private static DefaultTableModel tabRiderModel = new DefaultTableModel();
    private static JTable dataTableRider = new JTable(tabRiderModel);
    //入帐单表构造
    private static Object tblRiderAccountTitle[] = BeanRiderAccount.tblRiderAccountTitle;
    private static Object tblRiderAccountData[][];
    private static DefaultTableModel tabRiderAccountModel = new DefaultTableModel();
    private static JTable dataTableRiderAccount = new JTable(tabRiderAccountModel);

    //初始化信息
    public static BeanRider curRider = null;
    public static BeanRiderAccount curRiderAccount = null;
    public static List<BeanRider> allRider = null;
    public static List<BeanRiderAccount> allRiderAccount = null;
    //实现显示所有骑手
    private void reloadRiderTable(){
        try {
            allRider = TakeoutAssistantUtil.riderManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
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
    //显示所有入帐单
    private void reloadRiderAccountTabel(int RiderIdx){
        if(RiderIdx < 0) return;
        curRider = allRider.get(RiderIdx);
        try {
            allRiderAccount = TakeoutAssistantUtil.riderAccountManager.loadAccount(curRider);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblRiderAccountData = new Object[allRiderAccount.size()][BeanRiderAccount.tblRiderAccountTitle.length];
        for(int i = 0 ; i < allRiderAccount.size() ; i++){
            for(int j = 0 ; j < BeanRiderAccount.tblRiderAccountTitle.length ; j++)
                tblRiderAccountData[i][j] = allRiderAccount.get(i).getCell(j);
        }

        tabRiderAccountModel.setDataVector(tblRiderAccountData,tblRiderAccountTitle);
        this.dataTableRiderAccount.validate();
        this.dataTableRiderAccount.repaint();
    }
    //主界面
    public FrmMain_rider(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("骑手管理");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //骑手功能菜单
        this.menu_Rider.add(this.menuItem_AddRider); this.menuItem_AddRider.addActionListener(this);
        this.menu_Rider.add(this.menuItem_DeleteRider); this.menuItem_DeleteRider.addActionListener(this);
        this.menu_Rider.add(this.menuItem_ModifyRider); this.menuItem_ModifyRider.addActionListener(this);
        //this.menu_Rider.add(this.menuItem_ShowRider); this.menuItem_ShowRider.addActionListener(this);
        //入帐单功能菜单
        this.menu_RiderAccount.add(this.menuItem_DeleteRiderAccount); this.menuItem_DeleteRiderAccount.addActionListener(this);
        this.menu_RiderAccount.add(this.menuItem_ModifyRiderAccount); this.menuItem_ModifyRiderAccount.addActionListener(this);

        menubar.add(menu_Rider);
        //menubar.add(menu_RiderAccount);
        this.setJMenuBar(menubar);

        //主界面布局
        JScrollPane js1 = new JScrollPane(this.dataTableRider);
        js1.setPreferredSize(new Dimension(750, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableRiderAccount);
        js2.setPreferredSize(new Dimension(300, 10));
        //骑手信息在左
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableRider.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain_rider.this.dataTableRider.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain_rider.this.reloadRiderAccountTabel(i);
            }
        });
        //入帐单信息在右
        this.getContentPane().add(js2, BorderLayout.CENTER);
        this.dataTableRiderAccount.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain_rider.this.dataTableRiderAccount.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curRiderAccount = allRiderAccount.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });

        this.reloadRiderTable();  //加载信息
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!管理员!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //添加骑手
        if(e.getSource() == this.menuItem_AddRider){
            FrmAddRider dlg = new FrmAddRider(this,"添加骑手",true);
            dlg.setVisible(true);
            reloadRiderTable();
        }
        //删除骑手
        else if(e.getSource() == this.menuItem_DeleteRider){
            if(this.curRider == null) {
                JOptionPane.showMessageDialog(null, "请选择骑手", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //判断骑手是否送过单,若送过,则无法删除
            try {
                if(TakeoutAssistantUtil.riderManager.ifHavingOrder(curRider)){
                    JOptionPane.showMessageDialog(null, "该骑手有配送记录,禁止删除", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            try {
                TakeoutAssistantUtil.riderManager.deleteRider(this.curRider);
                reloadRiderTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //修改骑手名称
        else if(e.getSource() == this.menuItem_ModifyRider){
            if(this.curRider == null) {
                JOptionPane.showMessageDialog(null, "请选择骑手", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyStatus dlg = new FrmModifyStatus(this,"修改骑手身份",true);
            dlg.setVisible(true);
            reloadRiderTable();
        }

        //删除入帐单
        else if(e.getSource() == this.menuItem_DeleteRiderAccount){
            if(this.curRiderAccount == null) {
                JOptionPane.showMessageDialog(null, "请选择入帐单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.riderAccountManager.deleteRiderAccount(this.curRiderAccount);
                FrmMain_rider.this.reloadRiderAccountTabel(FrmMain_rider.this.dataTableRider.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //修改入帐单归属
        else if(e.getSource() == this.menuItem_ModifyRiderAccount){
            if(this.curRider == null) {
                JOptionPane.showMessageDialog(null, "请选择入帐单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyRiderAccount dlg = new FrmModifyRiderAccount(this,"修改入帐单归属",true);
            dlg.setVisible(true);
            FrmMain_rider.this.reloadRiderAccountTabel(FrmMain_rider.this.dataTableRider.getSelectedRow());
        }

    }

}
