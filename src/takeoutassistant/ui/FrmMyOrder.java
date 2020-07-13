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
    //订单管理按钮
    private JPanel menuBar = new JPanel();
    private JButton btnOrderInfo = new JButton("订单详情");
    private JButton btnRiderComment = new JButton("评价骑手");
    private JButton btnCancel = new JButton("取消订单");
    private JButton btnDelete = new JButton("删除订单");
    private JButton btnShowGoodsComment = new JButton("我的商品评价");
    private JButton btnShowRiderComment = new JButton("我的骑手评价");

    //主界面
    private JPanel statusBar = new JPanel();
    //用户订单表构造
    private static Object tblOrderTitle[] = BeanGoodsOrder.tblOrderTitle;
    private static Object tblOrderData[][];
    private static DefaultTableModel tabOrderModel = new DefaultTableModel();
    private static JTable dataTableOrder = new JTable(tabOrderModel);

    //初始化信息
    public static BeanGoodsOrder curOrder = null;
    public static List<BeanGoodsOrder> allOrder = null;
    //显示所有
    private void reloadOrderTable(){
        try {
            System.out.println(currentLoginUser.getUser_id());
            allOrder = TakeoutAssistantUtil.orderManager.loadUsers(currentLoginUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
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
    //主界面
    public FrmMyOrder(){
        //设置窗口信息
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("我的订单");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //订单功能按钮添加.设置监听
        menuBar.add(btnOrderInfo); this.btnOrderInfo.addActionListener(this);
        menuBar.add(btnRiderComment); this.btnRiderComment.addActionListener(this);
        menuBar.add(btnCancel); this.btnCancel.addActionListener(this);
        menuBar.add(btnDelete); this.btnDelete.addActionListener(this);
        menuBar.add(btnShowGoodsComment); this.btnShowGoodsComment.addActionListener(this);
        menuBar.add(btnShowRiderComment); this.btnShowRiderComment.addActionListener(this);
        //菜单栏
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //订单信息显示
        this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
        this.dataTableOrder.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //得到目前选择的订单
                int i = FrmMyOrder.this.dataTableOrder.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curOrder = allOrder.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });
        this.reloadOrderTable();  //加载订单信息
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //显示订单详情表
        if(e.getSource() == this.btnOrderInfo){
            if(curOrder == null) {
                JOptionPane.showMessageDialog(null, "请选择一个订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmShowOrderInfo();
        }
        //取消订单
        else if(e.getSource() == this.btnCancel){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "请选择一项订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("未配送")){
                JOptionPane.showMessageDialog(null, "订单已进行配送或已取消,现在无法取消", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmConfirm dlg = new FrmConfirm(this, "提示", true); //让用户再次确认是否取消
            dlg.setVisible(true);
            if(confirm){
                try {
                    TakeoutAssistantUtil.orderManager.modifyCancel(curOrder);
                    JOptionPane.showMessageDialog(null, "取消成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (BaseException baseException) {
                    baseException.printStackTrace();
                }
                reloadOrderTable();
            }
            else
                return;
        }
        //删除订单
        else if(e.getSource() == this.btnDelete){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "请选择一项订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("已取消")){
                JOptionPane.showMessageDialog(null, "该订单未取消,无法删除", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.orderManager.deleteOrder(curOrder);
                JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            reloadOrderTable();
        }
        //评价骑手
        else if(e.getSource() == this.btnRiderComment){
            if(curOrder == null){
                JOptionPane.showMessageDialog(null, "请选择一项订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("已送达")){
                JOptionPane.showMessageDialog(null, "订单未完成,无法评价", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //判断是否已评价
            try {
                boolean flag = TakeoutAssistantUtil.riderAccountManager.isComment(curOrder);
                if(flag){
                    JOptionPane.showMessageDialog(null, "该订单已进行评价", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            FrmAddRiderComment dlg = new FrmAddRiderComment(this,"骑手评价",true);
            dlg.setVisible(true);
            reloadOrderTable();
        }
        //查看所有我的商品评价
        if(e.getSource() == this.btnShowGoodsComment){
            new FrmMyGoodsComment();
        }
        //查看所有我的骑手评价
        if(e.getSource() == this.btnShowRiderComment){
            new FrmMyRiderComment();
        }
    }

}
