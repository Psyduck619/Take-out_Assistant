package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAddress;
import takeoutassistant.model.BeanOrderInfo;
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
import static takeoutassistant.ui.FrmConfirm.confirm;
import static takeoutassistant.ui.FrmMain_user.curSeller;
import static takeoutassistant.ui.FrmShowSeller.curGoods;

public class FrmShowMyCart extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //状态栏和菜单栏
    private JPanel statusBar = new JPanel();
    private JPanel  menuBar = new JPanel();
    private JButton btDelete = new JButton("删除商品");
    private JButton btAdd = new JButton("增加商品数量");
    private JButton btLose = new JButton("减少商品数量");
    private JButton btClear = new JButton("清空购物车");
    //我的购物车表构造
    private static Object tblMyCartTitle[] = BeanOrderInfo.tblMyCartTitle;
    private static Object tblMyCartData[][];
    private static DefaultTableModel tabMyCartModel = new DefaultTableModel();
    private static JTable dataTableMyCart = new JTable(tabMyCartModel);

    //初始化信息
    private BeanUser curUser = currentLoginUser;
    public static BeanOrderInfo curMyCart = null;
    private List<BeanOrderInfo> allMyCart = null;
    //显示我的购物车
    private void reloadMyCartTable(){
        try {
            allMyCart = TakeoutAssistantUtil.orderInfoManager.loadOrderInfo(curUser);
            System.out.println("allCart:"+allMyCart.size());
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblMyCartData = new Object[allMyCart.size()][BeanOrderInfo.tblMyCartTitle.length];
        for(int i = 0 ; i < allMyCart.size() ; i++){
            for(int j = 0; j < BeanOrderInfo.tblMyCartTitle.length ; j++)
                tblMyCartData[i][j] = allMyCart.get(i).getCell(j);
        }
        tabMyCartModel.setDataVector(tblMyCartData,tblMyCartTitle);
        this.dataTableMyCart.validate();
        this.dataTableMyCart.repaint();
    }

    //主界面
    public FrmShowMyCart(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("我的购物车");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //设置菜单栏信息
        menuBar.add(btDelete);
        menuBar.add(btAdd);
        menuBar.add(btLose);
        menuBar.add(btClear);
        this.btDelete.addActionListener(this);
        this.btAdd.addActionListener(this);
        this.btLose.addActionListener(this);
        this.btClear.addActionListener(this);
        //菜单栏
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //主界面
        this.getContentPane().add(new JScrollPane(this.dataTableMyCart), BorderLayout.CENTER);
        this.dataTableMyCart.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //得到目前选择的商品栏
                int i = FrmShowMyCart.this.dataTableMyCart.getSelectedRow();
                if(i < 0) {
                    return;
                }
                reloadMyCartTable();
                System.out.println(i);
                System.out.println(allMyCart.size());
                curMyCart = allMyCart.get(i);
            }
        });
        //加载信息
        this.reloadMyCartTable();
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String name = curSeller.getSeller_name();
        JLabel label=new JLabel("您好!欢迎光临"+name+"!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);  //界面可见
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //删除整条商品
        if(e.getSource() == this.btDelete){
            if(this.allMyCart.size() == 0){
                JOptionPane.showMessageDialog(null, "购物车为空", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(this.curMyCart == null) {
                JOptionPane.showMessageDialog(null, "请选择一项商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.orderInfoManager.deleteOrderInfo(this.curMyCart);
                curMyCart = null;
                reloadMyCartTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //在购物车中增加商品数量
        if(e.getSource() == this.btAdd){
            if(this.allMyCart.size() == 0){
                JOptionPane.showMessageDialog(null, "购物车为空", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(this.curMyCart == null) {
                JOptionPane.showMessageDialog(null, "请选择一项商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddCartGoods dlg = new FrmAddCartGoods(this,"添加商品数量",true);
            dlg.setVisible(true);
            curMyCart = null;
            reloadMyCartTable();
        }
        //在购物车中减少商品数量
        if(e.getSource() == this.btLose){
            if(this.allMyCart.size() == 0){
                JOptionPane.showMessageDialog(null, "购物车为空", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(this.curMyCart == null) {
                JOptionPane.showMessageDialog(null, "请选择一项商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmLoseCartGoods dlg = new FrmLoseCartGoods(this,"减少商品数量",true);
            dlg.setVisible(true);
            curMyCart = null;
            reloadMyCartTable();
        }
        //清空购物车
        if(e.getSource() == this.btClear){
            if(this.allMyCart.size() == 0){
                JOptionPane.showMessageDialog(null, "购物车已空", "提示",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                FrmConfirm dlg = new FrmConfirm(this, "提示", true);
                dlg.setVisible(true);
                if(confirm){
                    TakeoutAssistantUtil.orderInfoManager.deleteAll(curUser);
                    JOptionPane.showMessageDialog(null, "购物车清空成功~", "提示", JOptionPane.INFORMATION_MESSAGE);
                    curMyCart = null;
                    reloadMyCartTable();
                }
                else
                    return;
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }
}
