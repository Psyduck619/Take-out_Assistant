package takeoutassistant.ui;

import com.sun.deploy.security.WIExplorerMyKeyStore;
import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
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
import static takeoutassistant.ui.FrmMain_user.curSeller;

public class FrmShowSeller extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //购买界面操作按钮
    private JPanel  menuBar1 = new JPanel();
    private JPanel  menuBar2 = new JPanel();
    private JPanel  menuBarX = new JPanel();
    private JButton btManjian = new JButton("商家满减");
    private JButton btCoupon = new JButton("商家优惠券");
    private JButton btMyCoupon = new JButton("我的优惠券");
    private JButton btAddCart = new JButton("加入购物车");
    private JButton btMyCart = new JButton("我的购物车");
    private JButton btBuy = new JButton("结算");

    //菜单栏
    private JPanel menuBar = new JPanel();
    //状态栏
    private JPanel statusBar = new JPanel();
    //商品类别表构造
    private Object tblGTypeTitle[] = BeanGoodsType.tblGTypeTitle;
    private Object tblGTypeData[][];
    DefaultTableModel tabGTypeModel = new DefaultTableModel();
    private JTable dataTableGType = new JTable(tabGTypeModel);
    //商品表构造
    private Object tblGoodsTitle[] = BeanGoods.tblGoodsTitle;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel = new DefaultTableModel();
    private JTable dataTableGoods = new JTable(tabGoodsModel);

    //初始化信息
    private BeanGoodsType curType = null;
    List<BeanGoodsType> allType = null;
    private BeanGoods curGoods = null;
    List<BeanGoods> allGoods = null;
    //显示所有商品类型
    private void reloadGTypeTabel(){
        try {
            allType = TakeoutAssistantUtil.goodsTypeManager.loadTypes(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGTypeData = new Object[allType.size()][BeanGoodsType.tblGTypeTitle.length];
        for(int i = 0 ; i < allType.size() ; i++){
            for(int j = 0 ; j < BeanGoodsType.tblGTypeTitle.length ; j++)
                tblGTypeData[i][j] = allType.get(i).getCell(j);
        }

        tabGTypeModel.setDataVector(tblGTypeData,tblGTypeTitle);
        this.dataTableGType.validate();
        this.dataTableGType.repaint();
    }
    //显示所有商品
    private void reloadGoodsTabel(int typeIdx){
        if(typeIdx <= 0) {
            while(tabGoodsModel.getRowCount() > 0){
                tabGoodsModel.removeRow(tabGoodsModel.getRowCount()-1);
            }
            return;
        }
        curType = allType.get(typeIdx);
        try {
            allGoods = TakeoutAssistantUtil.goodsManager.loadGoods(curType);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[allGoods.size()][BeanGoods.tblGoodsTitle.length];
        for(int i = 0 ; i < allGoods.size() ; i++){
            for(int j = 0 ; j < BeanGoods.tblGoodsTitle.length ; j++)
                tblGoodsData[i][j] = allGoods.get(i).getCell(j);
        }

        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    //主界面
    public FrmShowSeller(){
        //判空
        if(curSeller == null){
            JOptionPane.showMessageDialog(null, "当前选中商家错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(1000,600);
        String name = curSeller.getSeller_name();
        this.setTitle(name);
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //功能菜单1按钮设置
        menuBar1.add(btManjian);
        menuBar1.add(btCoupon);
        menuBar1.add(btMyCoupon);
        this.btManjian.addActionListener(this);
        this.btCoupon.addActionListener(this);
        this.btMyCoupon.addActionListener(this);
        //功能菜单2按钮设置
        menuBar2.add(btMyCart);
        menuBar2.add(btAddCart);
        menuBar2.add(btBuy);
        this.btMyCart.addActionListener(this);
        this.btAddCart.addActionListener(this);
        this.btBuy.addActionListener(this);
        //菜单栏
        menuBar.add(menuBar1,BorderLayout.WEST);
        menuBar.add(menuBarX,BorderLayout.CENTER);
        menuBar.add(menuBar2,BorderLayout.EAST);
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //主界面布局
        JScrollPane js1 = new JScrollPane(this.dataTableGType);
        js1.setPreferredSize(new Dimension(300, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableGoods);
        js2.setPreferredSize(new Dimension(400, 10));
        //商品类别在左
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableGType.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmShowSeller.this.dataTableGType.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmShowSeller.this.reloadGoodsTabel(i);
            }
        });
        //商品在右
        this.getContentPane().add(js2, BorderLayout.CENTER);
        this.dataTableGoods.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = FrmShowSeller.this.dataTableGoods.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curGoods = allGoods.get(i);
            }
        });

        this.reloadGTypeTabel();  //加载类别信息
        this.reloadGoodsTabel(0);  //加载首类信息
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String sellerName = curSeller.getSeller_name();
        JLabel label=new JLabel("您好!欢迎光临" + sellerName + "!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //显示商家优惠券
        if(e.getSource() == btManjian){
            new FrmShowSellerManjian();
        }
        //显示商家优惠券
        else if(e.getSource() == btCoupon){
            new FrmShowSellerCoupon();
        }
        //显示我的优惠券
        else if(e.getSource() == btMyCoupon){
            new FrmShowMyCoupon2();
        }

    }
}
