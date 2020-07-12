package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanOrderInfo;
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
import static takeoutassistant.ui.FrmMyOrder.curOrder;

public class FrmShowOrderInfo extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //订单管理按钮
    private JPanel menuBar = new JPanel();
    private JButton btnGoodsComment = new JButton("评价商品");

    //用户订单表构造
    private static Object tblOrderInfoTitle[] = BeanOrderInfo.tblMyCartTitle;
    private static Object tblOrderInfoData[][];
    private static DefaultTableModel tabOrderInfoModel = new DefaultTableModel();
    private static JTable dataTableOrderInfo = new JTable(tabOrderInfoModel);

    //初始化信息
    public static BeanOrderInfo curOrderInfo = null;
    public static List<BeanOrderInfo> allOrderInfo = null;
    //显示所有地址
    private void reloadOrderInfoTable(){
        try {
            allOrderInfo = TakeoutAssistantUtil.orderInfoManager.loadMyOrderInfo(curOrder);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblOrderInfoData = new Object[allOrderInfo.size()][BeanOrderInfo.tblMyCartTitle.length];
        for(int i = 0 ; i < allOrderInfo.size() ; i++){
            for(int j = 0 ; j < BeanOrderInfo.tblMyCartTitle.length ; j++)
                tblOrderInfoData[i][j] = allOrderInfo.get(i).getCell(j);
        }
        tabOrderInfoModel.setDataVector(tblOrderInfoData,tblOrderInfoTitle);
        this.dataTableOrderInfo.validate();
        this.dataTableOrderInfo.repaint();
    }
    //主界面
    public FrmShowOrderInfo(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(800,400);
        this.setTitle("订单详情表");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //详情表功能按钮添加.设置监听
        menuBar.add(btnGoodsComment); this.btnGoodsComment.addActionListener(this);
        //菜单栏
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //订单信息显示
        this.getContentPane().add(new JScrollPane(this.dataTableOrderInfo), BorderLayout.CENTER);
        this.dataTableOrderInfo.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //得到目前选择的订单详情
                int i = FrmShowOrderInfo.this.dataTableOrderInfo.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curOrderInfo = allOrderInfo.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });
        this.reloadOrderInfoTable();  //加载订单详情信息
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //评价商品
        if(e.getSource() == this.btnGoodsComment){
            if(curOrderInfo == null){
                JOptionPane.showMessageDialog(null, "请选择一件商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("已送达")){
                JOptionPane.showMessageDialog(null, "订单未完成,无法评价商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //判断是否已评价
            try {
                boolean flag = TakeoutAssistantUtil.goodsCommentManager.isComment(curOrderInfo);
                if(flag){
                    JOptionPane.showMessageDialog(null, "该商品已评价或评价已被删除", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            FrmAddGoodsComment dlg = new FrmAddGoodsComment(this,"商品评价",true);
            dlg.setVisible(true);
            reloadOrderInfoTable();
        }

    }

}
