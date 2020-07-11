package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static takeoutassistant.ui.FrmLogin.loginType;

public class FrmMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    //总菜单选项
    private JMenu menu_seller=new JMenu("商家管理");
    private JMenu menu_type=new JMenu("商品类别管理");
    private JMenu menu_goods=new JMenu("商品管理");
    private JMenu menu_others=new JMenu("更多管理");
    private JMenu menu_admin=new JMenu("管理员设置");
    //商家菜单选项
    private JMenuItem  menuItem_AddSeller=new JMenuItem("添加商家");
    private JMenuItem  menuItem_DeleteSeller=new JMenuItem("删除商家");
    private JMenuItem  menuItem_ModifyName=new JMenuItem("修改名称");
    private JMenuItem  menuItem_ShowLevel=new JMenuItem("商家星级筛选");
    private JMenuItem  menuItem_ShowCoupon=new JMenuItem("满减优惠管理");
    //商品类别菜单选项
    private JMenuItem  menuItem_AddType=new JMenuItem("添加类别");
    private JMenuItem  menuItem_DeleteType=new JMenuItem("删除类别");
    private JMenuItem  menuItem_ModifyType=new JMenuItem("修改类别名称");
    //商品菜单选项
    private JMenuItem  menuItem_AddGoods=new JMenuItem("添加商品");
    private JMenuItem  menuItem_DeleteGoods=new JMenuItem("删除商品");
    private JMenuItem  menuItem_ModifyGoods=new JMenuItem("更新商品信息");

    private JMenuItem  menuItem_Rider=new JMenuItem("骑手管理");
    private JMenuItem  menuItem_Order=new JMenuItem("订单管理");

    private JMenuItem  menuItem_AddAdmin=new JMenuItem("管理员添加");
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("密码修改");

    //主界面
    private FrmLogin dlgLogin = null;
    //状态栏
    private JPanel statusBar = new JPanel();
    //商家信息表构造
    private static Object tblSellerTitle[] = BeanSeller.tableTitles;
    private static Object tblSellerData[][];
    private static DefaultTableModel tabSellerModel = new DefaultTableModel();
    private static JTable dataTableSeller = new JTable(tabSellerModel);
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

    //初始化商家信息
    public static BeanSeller curSeller = null;
    public static BeanGoodsType curType = null;
    public static BeanGoods curGoods = null;
    public static List<BeanSeller> allSeller = null;
    List<BeanGoodsType> allType = null;
    List<BeanGoods> allGoods = null;
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
    //显示所有商品类型
    private void reloadGTypeTabel(int sellerIdx){
        if(sellerIdx < 0) return;
        curSeller = allSeller.get(sellerIdx);
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
        if(typeIdx < 0) {
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
    public FrmMain(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("外卖助手管理系统");
        dlgLogin=new FrmLogin(this,"登录",true);
        dlgLogin.setVisible(true);
        //商家菜单
        this.menu_seller.add(this.menuItem_AddSeller); this.menuItem_AddSeller.addActionListener(this);
        this.menu_seller.add(this.menuItem_DeleteSeller); this.menuItem_DeleteSeller.addActionListener(this);
        this.menu_seller.add(this.menuItem_ModifyName); this.menuItem_ModifyName.addActionListener(this);
        this.menu_seller.add(this.menuItem_ShowLevel); this.menuItem_ShowLevel.addActionListener(this);
        this.menu_seller.add(this.menuItem_ShowCoupon); this.menuItem_ShowCoupon.addActionListener(this);
        //商品类别菜单
        this.menu_type.add(this.menuItem_AddType); this.menuItem_AddType.addActionListener(this);
        this.menu_type.add(this.menuItem_DeleteType); this.menuItem_DeleteType.addActionListener(this);
        this.menu_type.add(this.menuItem_ModifyType); this.menuItem_ModifyType.addActionListener(this);
        //商品菜单
        this.menu_goods.add(this.menuItem_AddGoods); this.menuItem_AddGoods.addActionListener(this);
        this.menu_goods.add(this.menuItem_DeleteGoods); this.menuItem_DeleteGoods.addActionListener(this);
        this.menu_goods.add(this.menuItem_ModifyGoods); this.menuItem_ModifyGoods.addActionListener(this);
        //其他管理菜单
        this.menu_others.add(this.menuItem_Rider); this.menuItem_Rider.addActionListener(this);
        this.menu_others.add(this.menuItem_Order); this.menuItem_Order.addActionListener(this);
        //管理员管理
        this.menu_admin.add(this.menuItem_AddAdmin); this.menuItem_AddAdmin.addActionListener(this);
        this.menu_admin.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);

        menubar.add(menu_seller);
        menubar.add(menu_type);
        menubar.add(menu_goods);
        menubar.add(menu_others);
        menubar.add(menu_admin);
        this.setJMenuBar(menubar);

        //主界面布局
        JScrollPane js1 = new JScrollPane(this.dataTableSeller);
        js1.setPreferredSize(new Dimension(500, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableGType);
        js2.setPreferredSize(new Dimension(200, 10));
        JScrollPane js3 = new JScrollPane(this.dataTableGoods);
        js3.setPreferredSize(new Dimension(400, 10));
        //商家信息在左
        //this.getContentPane().add(new JScrollPane(this.dataTableSeller), BorderLayout.WEST);
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableSeller.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain.this.dataTableSeller.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain.this.reloadGTypeTabel(i);
                FrmMain.this.reloadGoodsTabel(-1);
            }
        });
        //商品类别在中
        //this.getContentPane().add(new JScrollPane(this.dataTableGType), BorderLayout.CENTER);
        this.getContentPane().add(js2, BorderLayout.CENTER);
        this.dataTableGType.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain.this.dataTableGType.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain.this.reloadGoodsTabel(i);
            }
        });
        //商品在右
        //this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.EAST);
        this.getContentPane().add(js3, BorderLayout.EAST);
        this.dataTableGoods.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = FrmMain.this.dataTableGoods.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curGoods = allGoods.get(i);
                System.out.println(curGoods);
            }
        });

        this.reloadSellerTable();  //加载卖家信息
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!管理员!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        if(loginType == "管理员"){
            this.setVisible(true);
        }
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
        //显示指定星级商家
        else if(e.getSource() == this.menuItem_ShowLevel){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmLevel dlg = new FrmLevel(this,"选择星级商家",true);
            dlg.setVisible(true);
        }
        //商家满减优惠管理界面
        else if(e.getSource() == this.menuItem_ShowCoupon){
            new FrmMain_seller();
        }

        //添加类别
        else if(e.getSource() == this.menuItem_AddType){
            if(this.curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddType dlg = new FrmAddType(this,"添加类别",true);
            dlg.seller = curSeller;
            dlg.setVisible(true);
            FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
        }
        //删除类别
        else if(e.getSource() == this.menuItem_DeleteType){
            int i = FrmMain.this.dataTableGType.getSelectedRow();
            if(i < 0) {
                JOptionPane.showMessageDialog(null, "请选择类别", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.goodsTypeManager.deleteGoodsType(this.allType.get(i));
                FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //修改类别名称
        else if(e.getSource() == this.menuItem_ModifyType){
            if(this.curType == null) {
                JOptionPane.showMessageDialog(null, "请选择类别", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyType dlg = new FrmModifyType(this,"修改类别名称",true);
            dlg.setVisible(true);
            FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
        }

        //添加商品
        else if(e.getSource() == this.menuItem_AddGoods){
            if(this.curType == null) {
                JOptionPane.showMessageDialog(null, "请选择类别", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddGoods dlg = new FrmAddGoods(this,"添加商品",true);
            dlg.type = curType;
            dlg.setVisible(true);
            FrmMain.this.reloadGoodsTabel(FrmMain.this.dataTableGType.getSelectedRow());
            FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
        }
        //删除商品
        else if(e.getSource() == this.menuItem_DeleteGoods){
            int i = FrmMain.this.dataTableGoods.getSelectedRow();
            if(i < 0) {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.goodsManager.deleteGoods(this.allGoods.get(i));
                FrmMain.this.reloadGoodsTabel(FrmMain.this.dataTableGType.getSelectedRow());
                FrmMain.this.reloadGTypeTabel(FrmMain.this.dataTableSeller.getSelectedRow());
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //修改商品信息
        else if(e.getSource() == this.menuItem_ModifyGoods){
            if(this.curGoods == null) {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyGoods dlg = new FrmModifyGoods(this,"更新商品信息",true);
            dlg.setVisible(true);
            FrmMain.this.reloadGoodsTabel(FrmMain.this.dataTableGoods.getSelectedRow());
            curGoods = null;
        }

        //骑手管理界面
        else if(e.getSource() == this.menuItem_Rider){
            new FrmMain_rider();
        }
        //订单管理界面
        else if(e.getSource() == this.menuItem_Order){
            new FrmMain_order();
        }

        //添加管理员
        else if(e.getSource() == this.menuItem_AddAdmin) {
            FrmAddAdmin dlg = new FrmAddAdmin(this, "添加管理员", true);
            dlg.setVisible(true);
        }
        //管理员密码修改
        else if(e.getSource() == this.menuItem_modifyPwd){
            FrmModifyPwd dlg = new FrmModifyPwd(this,"密码修改",true);
            dlg.setVisible(true);
        }

    }
}
