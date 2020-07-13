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
    //总菜单选项
    private JMenu menu_Order=new JMenu("订单筛选");
    private JMenu menu_OrderAccount=new JMenu("订单操作");
    //订单筛选选项
    private JMenuItem  menuItem_NoDo=new JMenuItem("未配送");
    private JMenuItem  menuItem_Doing=new JMenuItem("配送中");
    private JMenuItem  menuItem_OverTime=new JMenuItem("超时");
    private JMenuItem  menuItem_Done=new JMenuItem("已送达");
    private JMenuItem  menuItem_Cancel=new JMenuItem("已取消");
    private JMenuItem  menuItem_All=new JMenuItem("显示全部订单");
    //订单操作选项
    private JMenuItem  menuItem_SelectRider=new JMenuItem("分配骑手");
    private JMenuItem  menuItem_ChangeRider=new JMenuItem("更换骑手");
    private JMenuItem  menuItem_Confirm=new JMenuItem("确认送达");

    //状态栏
    private JPanel statusBar = new JPanel();
    //订单表构造
    private static Object tblOrderTitle[] = BeanGoodsOrder.tblOrderTitle;
    private static Object tblOrderData[][];
    private static DefaultTableModel tabOrderModel = new DefaultTableModel();
    private static JTable dataTableOrder = new JTable(tabOrderModel);

    //初始化信息
    public static List<BeanGoodsOrder> allOrder = null;
    public static BeanGoodsOrder curOrder = null;
    //实现显示所有订单
    private void reloadOrderTable(){
        if(flag){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        flag = false;
        //判断超时
        for(int i = 0 ; i < allOrder.size() ; i++){
            if(allOrder.get(i).getRequest_time().getTime() < System.currentTimeMillis() &&
                    allOrder.get(i).getOrder_state().equals("配送中")) {
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
    //主界面
    public FrmMain_order(){
        //设置窗口信息
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("订单管理");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //订单筛选菜单
        this.menu_Order.add(this.menuItem_NoDo); this.menuItem_NoDo.addActionListener(this);
        this.menu_Order.add(this.menuItem_Doing); this.menuItem_Doing.addActionListener(this);
        this.menu_Order.add(this.menuItem_OverTime); this.menuItem_OverTime.addActionListener(this);
        this.menu_Order.add(this.menuItem_Done); this.menuItem_Done.addActionListener(this);
        this.menu_Order.add(this.menuItem_Cancel); this.menuItem_Cancel.addActionListener(this);
        this.menu_Order.add(this.menuItem_All); this.menuItem_All.addActionListener(this);
        //订单操作菜单
        this.menu_OrderAccount.add(this.menuItem_SelectRider); this.menuItem_SelectRider.addActionListener(this);
        this.menu_OrderAccount.add(this.menuItem_ChangeRider); this.menuItem_ChangeRider.addActionListener(this);
        this.menu_OrderAccount.add(this.menuItem_Confirm); this.menuItem_Confirm.addActionListener(this);

        menubar.add(menu_Order);
        menubar.add(menu_OrderAccount);
        this.setJMenuBar(menubar);

        //订单界面
        this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
        this.dataTableOrder.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain_order.this.dataTableOrder.getSelectedRow();
                if(i < 0) {
                    return;
                }
                //FrmMain_order.this.reloadOrderTable2();
                curOrder = allOrder.get(i);
            }
        });

        this.reloadOrderTable();  //加载信息
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!管理员!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //筛选"未配送"订单
        if(e.getSource() == this.menuItem_NoDo){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadNoDo();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //筛选"配送中"订单
        else if(e.getSource() == this.menuItem_Doing){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadDing();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //筛选"已送达"订单
        else if(e.getSource() == this.menuItem_Done){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadConfirm();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //筛选"超时"订单
        else if(e.getSource() == this.menuItem_OverTime){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadOverTime();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //筛选"已取消"订单
        if(e.getSource() == this.menuItem_Cancel){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadCancel();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //筛选所有订单
        else if(e.getSource() == this.menuItem_All){
            try {
                allOrder = TakeoutAssistantUtil.orderManager.loadAll();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //分配骑手
        else if(e.getSource() == this.menuItem_SelectRider){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "请选择一项订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("未配送")){
                JOptionPane.showMessageDialog(null, "该订单已配送", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmSelectRider dlg = new FrmSelectRider(this,"选择骑手",true);
            dlg.setVisible(true);
            try {
                TakeoutAssistantUtil.orderManager.modifyDoing(curOrder);//更新订单状态
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }finally {
                reloadOrderTable();
            }
        }
        //更换骑手
        else if(e.getSource() == this.menuItem_ChangeRider){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "请选择一项订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("配送中")){
                JOptionPane.showMessageDialog(null, "未处于配送中状态的订单无法修改骑手", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmChangeRider dlg = new FrmChangeRider(this,"更改骑手",true);
            dlg.setVisible(true);
            FrmMain_order.this.reloadOrderTable();
        }
        //确认送达
        else if(e.getSource() == this.menuItem_Confirm){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "请选择一项订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("配送中") && !curOrder.getOrder_state().equals("超时")){
                JOptionPane.showMessageDialog(null, "订单未在配送中", "错误",JOptionPane.ERROR_MESSAGE);
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
