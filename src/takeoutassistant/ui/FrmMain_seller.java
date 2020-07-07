package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class FrmMain_seller extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    //总菜单选项
    private JMenu menu_seller=new JMenu("商家管理");
    private JMenu menu_manjian=new JMenu("满减管理");
    private JMenu menu_coupon=new JMenu("优惠券管理");
    private JMenu menu_others=new JMenu("其他");
    //商家菜单选项
    private JMenuItem  menuItem_AddSeller=new JMenuItem("添加商家");
    private JMenuItem  menuItem_DeleteSeller=new JMenuItem("删除商家");
    private JMenuItem  menuItem_ModifyName=new JMenuItem("修改名称");
    private JMenuItem  menuItem_ShowLevel=new JMenuItem("商家星级筛选");
    //商家菜单选项
    private JMenuItem  menuItem_AddManjian=new JMenuItem("满减添加");
    private JMenuItem  menuItem_DeleteManjian=new JMenuItem("满减删除");
    private JMenuItem  menuItem_ModifyManjian=new JMenuItem("满减修改");
    //商品类别菜单选项
    private JMenuItem  menuItem_AddCoupon=new JMenuItem("优惠券添加");
    private JMenuItem  menuItem_DeleteCoupon=new JMenuItem("优惠券删除");
    private JMenuItem  menuItem_ModifyCoupon=new JMenuItem("优惠券修改");
    //商品菜单选项
    private JMenuItem  menuItem_AddGoods=new JMenuItem("添加商品");
    private JMenuItem  menuItem_DeleteGoods=new JMenuItem("删除商品");
    private JMenuItem  menuItem_ModifyGoods=new JMenuItem("更新商品信息");

    //主界面
    private JPanel statusBar = new JPanel();
    //商家信息表构造
    private static Object tblSellerTitle[] = BeanSeller.tableTitles;
    private static Object tblSellerData[][];
    private static DefaultTableModel tabSellerModel = new DefaultTableModel();
    private static JTable dataTableSeller = new JTable(tabSellerModel);
    //满减表构造
    private Object tblManjianTitle[] = BeanManjian.tblManjianTitle;
    private Object tblManjianData[][];
    DefaultTableModel tabManjianModel = new DefaultTableModel();
    private JTable dataTableManjian = new JTable(tabManjianModel);
    //优惠券表构造
    private Object tblCouponTitle[] = BeanCoupon.tblCouponTitle;
    private Object tblCouponData[][];
    DefaultTableModel tabCouponModel = new DefaultTableModel();
    private JTable dataTableCoupon = new JTable(tabCouponModel);

    //初始化商家信息
    public static BeanSeller curSeller = null;
    public static BeanManjian curManjian = null;
    public static BeanCoupon curCoupon = null;
    public static List<BeanSeller> allSeller = null;
    List<BeanManjian> allManjian = null;
    List<BeanCoupon> allCoupon = null;
    //实现显示所有商家
    private void reloadSellerTable(){
        try {
            allSeller = TakeoutAssistantUtil.sellerManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSellerData = new Object[allSeller.size()][BeanSeller.tableTitles.length];
        for(int i = 0 ; i < allSeller.size() ; i++){
            for(int j = 0 ; j < BeanSeller.tableTitles.length ; j++)
                tblSellerData[i][j] = allSeller.get(i).getCell(j);
        }
        tabSellerModel.setDataVector(tblSellerData,tblSellerTitle);
        this.dataTableSeller.validate();
        this.dataTableSeller.repaint();
    }
    //实现显示指定商家
    public static void reloadSLevelTable(int level){
        try {
            allSeller = TakeoutAssistantUtil.sellerManager.loadLevel(level);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSellerData = new Object[allSeller.size()][BeanSeller.tableTitles.length];
        for(int i = 0 ; i < allSeller.size() ; i++){
            for(int j = 0 ; j < BeanSeller.tableTitles.length ; j++){
                tblSellerData[i][j] = allSeller.get(i).getCell(j);
            }
        }
        tabSellerModel.setDataVector(tblSellerData,tblSellerTitle);
        //this.dataTableSeller.validate();
        //this.dataTableSeller.repaint();
    }
    //显示所有满减
    private void reloadManjianTabel(int sellerIdx){
        if(sellerIdx < 0) return;
        curSeller = allSeller.get(sellerIdx);
        try {
            allManjian = TakeoutAssistantUtil.manjianManager.loadManjian(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblManjianData = new Object[allManjian.size()][BeanManjian.tblManjianTitle.length];
        for(int i = 0 ; i < allManjian.size() ; i++){
            for(int j = 0 ; j < BeanManjian.tblManjianTitle.length ; j++)
                tblManjianData[i][j] = allManjian.get(i).getCell(j);
        }

        tabManjianModel.setDataVector(tblManjianData,tblManjianTitle);
        this.dataTableManjian.validate();
        this.dataTableManjian.repaint();
    }
    //显示优惠券
    private void reloadCouponTabel(int sellerIdx){
        if(sellerIdx < 0) return;
        curSeller = allSeller.get(sellerIdx);
        try {
            allCoupon = TakeoutAssistantUtil.couponManager.loadCoupon(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCouponData = new Object[allCoupon.size()][BeanCoupon.tblCouponTitle.length];
        for(int i = 0 ; i < allCoupon.size() ; i++){
            for(int j = 0 ; j < BeanCoupon.tblCouponTitle.length ; j++)
                tblCouponData[i][j] = allCoupon.get(i).getCell(j);
        }

        tabCouponModel.setDataVector(tblCouponData,tblCouponTitle);
        this.dataTableCoupon.validate();
        this.dataTableCoupon.repaint();
    }
    //主界面
    public FrmMain_seller(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("商家满减优惠管理");
        //商家菜单
        this.menu_seller.add(this.menuItem_AddSeller); this.menuItem_AddSeller.addActionListener(this);
        this.menu_seller.add(this.menuItem_DeleteSeller); this.menuItem_DeleteSeller.addActionListener(this);
        this.menu_seller.add(this.menuItem_ModifyName); this.menuItem_ModifyName.addActionListener(this);
        this.menu_seller.add(this.menuItem_ShowLevel); this.menuItem_ShowLevel.addActionListener(this);
        //商品类别菜单
        this.menu_manjian.add(this.menuItem_AddManjian); this.menuItem_AddManjian.addActionListener(this);
        this.menu_manjian.add(this.menuItem_DeleteManjian); this.menuItem_DeleteManjian.addActionListener(this);
        this.menu_manjian.add(this.menuItem_ModifyManjian); this.menuItem_ModifyManjian.addActionListener(this);
        //商品菜单
        this.menu_coupon.add(this.menuItem_AddCoupon); this.menuItem_AddCoupon.addActionListener(this);
        this.menu_coupon.add(this.menuItem_DeleteCoupon); this.menuItem_DeleteCoupon.addActionListener(this);
        this.menu_coupon.add(this.menuItem_ModifyCoupon); this.menuItem_ModifyCoupon.addActionListener(this);

        menubar.add(menu_seller);
        menubar.add(menu_manjian);
        menubar.add(menu_coupon);
        menubar.add(menu_others);
        this.setJMenuBar(menubar);

        //主界面布局
        JScrollPane js1 = new JScrollPane(this.dataTableSeller);
        js1.setPreferredSize(new Dimension(400, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableManjian);
        js2.setPreferredSize(new Dimension(200, 10));
        JScrollPane js3 = new JScrollPane(this.dataTableCoupon);
        js3.setPreferredSize(new Dimension(650, 10));
        //商家信息在左
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableSeller.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain_seller.this.dataTableSeller.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain_seller.this.reloadManjianTabel(i);
                FrmMain_seller.this.reloadCouponTabel(i);
            }
        });
        //满减信息在中
        this.getContentPane().add(js2, BorderLayout.CENTER);
        this.dataTableManjian.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain_seller.this.dataTableManjian.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curManjian = allManjian.get(i);
                //FrmMain_seller.this.reloadManjianTabel(i);
            }
        });
        this.getContentPane().add(js3, BorderLayout.EAST);
        this.dataTableCoupon.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain_seller.this.dataTableCoupon.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curCoupon = allCoupon.get(i);
                //FrmMain_seller.this.reloadCouponTabel(i);
            }
        });

        this.reloadSellerTable();  //加载卖家信息
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!管理员!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
//        this.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//                System.exit(0);
//            }
//        });

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //添加商家
        if(e.getSource() == this.menuItem_AddSeller){
            FrmAddSeller dlg = new FrmAddSeller(this,"添加商家",true);
            dlg.setVisible(true);
            reloadSellerTable();
        }
        //删除商家
        else if(e.getSource() == this.menuItem_DeleteSeller){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.sellerManager.deleteSeller(this.curSeller);
                reloadSellerTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //修改商家名称
        else if(e.getSource() == this.menuItem_ModifyName){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifySeller dlg = new FrmModifySeller(this,"添加商家",true);
            dlg.setVisible(true);
            reloadSellerTable();
        }

        //添加满减
        if(e.getSource() == this.menuItem_AddManjian){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddManjian dlg = new FrmAddManjian(this,"添加满减",true);
            dlg.seller = curSeller;
            dlg.setVisible(true);
            FrmMain_seller.this.reloadManjianTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
        }
        //删除满减
        else if(e.getSource() == this.menuItem_DeleteManjian){
            if(this.curManjian == null) {
                JOptionPane.showMessageDialog(null, "请选择满减", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.manjianManager.deleteManjian(this.curManjian);
                FrmMain_seller.this.reloadManjianTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //修改满减
        else if(e.getSource() == this.menuItem_ModifyManjian){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyManjian dlg = new FrmModifyManjian(this,"满减修改",true);
            dlg.setVisible(true);
            FrmMain_seller.this.reloadManjianTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
        }

        //添加优惠券
        if(e.getSource() == this.menuItem_AddCoupon){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddCoupon dlg = new FrmAddCoupon(this,"添加优惠券",true);
            dlg.seller = curSeller;
            dlg.setVisible(true);
            FrmMain_seller.this.reloadCouponTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
        }
        //删除优惠券
        else if(e.getSource() == this.menuItem_DeleteCoupon){
            if(this.curCoupon == null) {
                JOptionPane.showMessageDialog(null, "请选择优惠券", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.couponManager.deleteCoupon(this.curCoupon);
                FrmMain_seller.this.reloadCouponTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //修改优惠券
        else if(e.getSource() == this.menuItem_ModifyCoupon){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyCoupon dlg = new FrmModifyCoupon(this,"优惠券修改",true);
            dlg.setVisible(true);
            FrmMain_seller.this.reloadCouponTabel(FrmMain_seller.this.dataTableSeller.getSelectedRow());
        }
    }
}
