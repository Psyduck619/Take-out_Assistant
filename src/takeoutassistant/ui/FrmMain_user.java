package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmLogin.loginType;

public class FrmMain_user extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_buy=new JMenu("商家与商品");
    private JMenu menu_order=new JMenu("订单管理");
    private JMenu menu_user=new JMenu("个人管理");
    private JMenu menu_VIP=new JMenu("会员管理");

    private JMenuItem menuItem_ShowSeller=new JMenuItem("进入商家");
    private JMenuItem menuItem_CheckSeller=new JMenuItem("选择星级商家");
    private JMenuItem menuItem_CheckGoods=new JMenuItem("商品名查询商品");
    private JMenuItem menuItem_CheckType=new JMenuItem("类别查询商品");
    //用户订单管理
    private JMenuItem menuItem_ShowOrder=new JMenuItem("查看订单");
    //个人菜单组件
    private JMenuItem menuItem_myCoupon=new JMenuItem("我的优惠券");
    private JMenuItem menuItem_myJidan=new JMenuItem("我的集单");
    private JMenuItem menuItem_myaddress=new JMenuItem("我的地址");
    private JMenuItem menuItem_modifyUser=new JMenuItem("修改个人信息");
    private JMenuItem menuItem_modifyPwd=new JMenuItem("修改密码");
    //会员菜单组件
    private JMenuItem menuItem_openVIP=new JMenuItem("开通会员");
    private JMenuItem menuItem_renewVIP=new JMenuItem("续费会员");

    //主界面
    private JPanel statusBar = new JPanel();
    //商家表
    private static Object[] tblSellerTitle = BeanSeller.tableTitles2;
    private static Object[][] tblSellerData;
    static DefaultTableModel tabSellerModel = new DefaultTableModel();
    private JTable dataTableSeller = new JTable(tabSellerModel);
    //满减表
    private Object tblManjianTitle[] = BeanManjian.tblManjianTitle;
    private Object tblManjianData[][];
    DefaultTableModel tabManjianModel = new DefaultTableModel();
    private JTable dataTableManjian = new JTable(tabManjianModel);
    //热门商品表
    private Object tblGoodsTitle[] = BeanGoods.tblGoodsTitle2;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel = new DefaultTableModel();
    private JTable dataTableGoods = new JTable(tabGoodsModel);

    public static BeanSeller curSeller = null;
    static List<BeanSeller> allSeller = null;
    List<BeanManjian> allManjian = null;
    List<BeanGoods> allGoods = null;

    //实现显示所有商家
    private void reloadSellerTable(){
        try {
            allSeller = TakeoutAssistantUtil.sellerManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSellerData = new Object[allSeller.size()][BeanSeller.tableTitles2.length];
        for(int i = 0 ; i < allSeller.size() ; i++){
            for(int j = 0 ; j < BeanSeller.tableTitles2.length ; j++)
                tblSellerData[i][j] = allSeller.get(i).getCell2(j);
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
                tblSellerData[i][j] = allSeller.get(i).getCell2(j);
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
    //显示经典商品
    private void reloadGoodsTabel(int sellerIdx){
        if(sellerIdx < 0) return;
        curSeller = allSeller.get(sellerIdx);
        try {
            allGoods = TakeoutAssistantUtil.goodsManager.loadHGoods(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[allGoods.size()][BeanGoods.tblGoodsTitle.length];
        for(int i = 0 ; i < allGoods.size() ; i++){
            for(int j = 0 ; j < BeanGoods.tblGoodsTitle.length ; j++)
                tblGoodsData[i][j] = allGoods.get(i).getCell2(j);
        }

        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }
    public FrmMain_user(){

        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("大开大合-外卖助手");
        //商家菜单
        this.menu_buy.add(this.menuItem_ShowSeller); this.menuItem_ShowSeller.addActionListener(this);
        this.menu_buy.add(this.menuItem_CheckSeller); this.menuItem_CheckSeller.addActionListener(this);
        this.menu_buy.add(this.menuItem_CheckGoods); this.menuItem_CheckGoods.addActionListener(this);
        this.menu_buy.add(this.menuItem_CheckType); this.menuItem_CheckType.addActionListener(this);
        //订单菜单
        this.menu_order.add(this.menuItem_ShowOrder); this.menuItem_ShowOrder.addActionListener(this);
        //个人菜单
        this.menu_user.add(this.menuItem_myCoupon); this.menuItem_myCoupon.addActionListener(this);
        this.menu_user.add(this.menuItem_myJidan); this.menuItem_myJidan.addActionListener(this);
        this.menu_user.add(this.menuItem_myaddress); this.menuItem_myaddress.addActionListener(this);
        this.menu_user.add(this.menuItem_modifyUser); this.menuItem_modifyUser.addActionListener(this);
        this.menu_user.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
        //会员菜单
        this.menu_VIP.add(this.menuItem_openVIP); this.menuItem_openVIP.addActionListener(this);
        this.menu_VIP.add(this.menuItem_renewVIP); this.menuItem_renewVIP.addActionListener(this);

        menubar.add(menu_buy);
        menubar.add(menu_order);
        menubar.add(menu_user);
        menubar.add(menu_VIP);
        this.setJMenuBar(menubar);

        //主界面布局
        JScrollPane js1 = new JScrollPane(this.dataTableSeller);
        js1.setPreferredSize(new Dimension(500, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableGoods);
        js2.setPreferredSize(new Dimension(500, 10));
        JScrollPane js3 = new JScrollPane(this.dataTableManjian);
        js3.setPreferredSize(new Dimension(300, 10));
        //商家信息在左
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableSeller.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmMain_user.this.dataTableSeller.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmMain_user.this.reloadManjianTabel(i);
                FrmMain_user.this.reloadGoodsTabel(i);
            }
        });
        //热门商品信息在右
        this.getContentPane().add(js2, BorderLayout.CENTER);
        //满减信息在中
        this.getContentPane().add(js3, BorderLayout.EAST);
        //加载初始信息
        this.reloadSellerTable();  //加载商家信息
//        this.reloadGoodsTabel(0);
//        this.reloadManjianTabel(0);
        //状态栏
        if(loginType == "用户"){  //用户登录界面
            statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
            String name = currentLoginUser.getUser_name();
            Date dt = currentLoginUser.getVIP_end_time();
            if(currentLoginUser.isVIP()){
                name = "会员" + name + "!      会员到期时间:" +dt;
            }
            JLabel label=new JLabel("您好!尊敬的" + name);
            statusBar.add(label);
            this.getContentPane().add(statusBar,BorderLayout.SOUTH);
            this.setVisible(true);
            //判断当前用户有没有过购买经历,有的话就推荐
            try {
                if(TakeoutAssistantUtil.orderManager.ifBougnt(currentLoginUser))
                new FrmShowRecommend();
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }
        this.addWindowListener(new WindowAdapter(){  //关闭窗口即退出程序
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //用户修改个人信息
        if(e.getSource() == this.menuItem_modifyUser){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyUser dlg = new FrmModifyUser(this,"修改个人信息",true);
            dlg.setVisible(true);
        }
        //用户修改密码
        else if(e.getSource() == this.menuItem_modifyPwd){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmModifyPwd_user dlg = new FrmModifyPwd_user(this,"修改密码",true);
            dlg.setVisible(true);
        }
        //显示我的优惠券信息
        else if(e.getSource() == this.menuItem_myCoupon){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmShowMyCoupon();
        }
        //显示我的集单信息
        else if(e.getSource() == this.menuItem_myJidan){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmShowMyJidan();
        }
        //显示指定商家界面
        else if(e.getSource() == this.menuItem_ShowSeller){
            if(curSeller == null) {
                JOptionPane.showMessageDialog(null, "请选择一个商家", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmShowSeller();
        }
        //筛选商家
        else if(e.getSource() == this.menuItem_CheckSeller){
            FrmLevel dlg = new FrmLevel(this,"选择星级商家",true);
            curSeller = null;
            dlg.setVisible(true);
        }
        //根据商品名进行查询
        else if(e.getSource() == this.menuItem_CheckGoods){
            FrmPutGoodsName dlg = new FrmPutGoodsName(this,"商品查询",true);
            dlg.setVisible(true);
        }
        //根据类别进行查询
        else if(e.getSource() == this.menuItem_CheckType){
            FrmPutTypeName dlg = new FrmPutTypeName(this,"商品查询",true);
            dlg.setVisible(true);
        }
        //用户地址管理
        else if(e.getSource() == this.menuItem_myaddress){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmMyAddress();
        }
        //用户开通会员
        else if(e.getSource() == this.menuItem_openVIP){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(currentLoginUser.isVIP()){
                JOptionPane.showMessageDialog(null, "您已是尊贵的会员用户!", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmOpenVIP dlg = new FrmOpenVIP(this,"开通会员",true);
            dlg.setVisible(true);
            //改变对用户的称呼
            try {
                Thread.sleep(1000);
                if(currentLoginUser.isVIP()){
                    this.setVisible(false);
                    new FrmMain_user();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        //用户续费会员
        else if(e.getSource() == this.menuItem_renewVIP){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!currentLoginUser.isVIP()){
                JOptionPane.showMessageDialog(null, "您还不是会员用户,请先开通会员", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmRenewVIP dlg = new FrmRenewVIP(this,"续费会员",true);
            dlg.setVisible(true);
        }

        //用户查看订单
        else if(e.getSource() == this.menuItem_ShowOrder){
            if(currentLoginUser == null) {
                JOptionPane.showMessageDialog(null, "用户登录错误,请重试", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmMyOrder();
        }

    }
}
