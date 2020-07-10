package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.*;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmConfirm.confirm;

public class FrmBuy extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    //订单结算界面
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelOriginal = new JLabel("原价:");
    private JLabel labelPrice1 = new JLabel();
    private JLabel labelVIP = new JLabel("会员优惠:");
    private JLabel labelVIP_Text = new JLabel();
    private JLabel labelManjian = new JLabel("满减优惠:");
    private JLabel labelManjian_no = new JLabel("无可用满减");
    private JLabel labelManjian_Text = new JLabel();
    private JLabel labelCoupon = new JLabel("优惠券优惠:");
    private JLabel labelCoupon_no = new JLabel("无优惠券");
    private JLabel labelCoupon_Text = new JLabel();

    private JLabel labelTime = new JLabel("期望时间:");
    String items[] = {"立即送达","1小时","2小时","3小时"};
    private JComboBox edtTime = new JComboBox(items);

    private JLabel labelAddress = new JLabel("配送地址:");
    private JLabel labelAddress_Text = new JLabel();

    private JLabel labelFinal = new JLabel("               合计:");
    private JLabel labelPrice2 = new JLabel();

    private Button btCoupon = new Button("点击选择优惠券");
    private Button btTime = new Button("点击选择时间:");
    private Button btAddress = new Button("点击选择地址");

    //初始化所需信息
    private int orderID = 0;
    private double price1 = 0.0;
    double VIP_Discount = 0.0;
    private double price2 = 0.0;
    private BeanUser curUser = currentLoginUser;
    private BeanSeller curSeller = FrmMain_user.curSeller;
    private BeanManjian curManjian = null;
    private BeanMyCoupon curCoupon = null;
    private Date requestDate = null;
    private BeanAddress curAddress = null;

    //主界面
    public FrmBuy(Frame f, String s, boolean b){
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        //得到订单号
        try {
            orderID = TakeoutAssistantUtil.orderManager.getOrderID(curUser);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //得到原价
        try {
            price1 = TakeoutAssistantUtil.orderManager.getPrice1(orderID);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //显示原价
        labelPrice1.setText("" + price1 + " ");
        labelPrice1.setForeground(Color.RED);
        workPane.add(labelOriginal);
        workPane.add(labelPrice1);
        //得到会员折扣
        try {
            VIP_Discount = TakeoutAssistantUtil.orderManager.getVIP_Discount(orderID);
            //price2 = price1 - VIP_Discount;
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //显示会员折扣
        labelVIP_Text.setText("" + VIP_Discount + " ");
        labelVIP_Text.setForeground(Color.RED);
        workPane.add(labelVIP);
        workPane.add(labelVIP_Text);
        //得到满减信息
        String manjian_text = null;
        try {
            curManjian = TakeoutAssistantUtil.orderManager.getManjian(curSeller, price1);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //显示满减信息
        workPane.add(labelManjian);
        if(curManjian != null){
            manjian_text = "满" + curManjian.getManjian_amount() + "减" + curManjian.getDiscount_amount();
            labelManjian_Text.setText(manjian_text);
            labelManjian_Text.setForeground(Color.RED);
            workPane.add(labelManjian_Text);
            //price2 -= curManjian.getDiscount_amount();
        }
        else{
            labelManjian_Text.setText("无可用满减");
            workPane.add(labelManjian_Text);
        }

        //显示优惠券信息
        workPane.add(labelCoupon);
        List<BeanMyCoupon> bmg = null;
        try {  //查询用户是否有当前可用优惠券
             bmg = TakeoutAssistantUtil.myCouponManager.loadMyCoupon2(curUser, curSeller);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        if(bmg.size() == 0){ //没有则提示无优惠券信息
            workPane.add(labelCoupon_no);
        }
        else{
            curCoupon = bmg.get(0);
            //默认选择金额最大的优惠券
            for(int i = 1 ; i < bmg.size() ; i++){
                if(bmg.get(i).getCoupon_amount() > curCoupon.getCoupon_amount()){
                    curCoupon = bmg.get(i);
                }
            }
            //用户选择优惠券,设置监听
            workPane.add(btCoupon);
            this.btCoupon.addActionListener(this);
            //得到优惠券信息
            String coupon_text = null;
            coupon_text = "" + curCoupon.getCoupon_amount();
            labelCoupon_Text.setText(coupon_text);
            labelCoupon_Text.setForeground(Color.RED);
            workPane.add(labelCoupon);
            workPane.add(labelCoupon_Text);
            if(!curCoupon.getIfTogether()){
                curManjian = null;
                labelManjian_Text.setText("无可用满减");
            }
            //price2 -= curCoupon.getCoupon_amount();
        }
        //下拉框得到预期时间
        workPane.add(labelTime);
        workPane.add(edtTime);
        //显示地址信息
        List<BeanAddress> ba = null;
        try {  //查询用户是否有当前可用地址
            ba = TakeoutAssistantUtil.addressManager.loadAddress(curUser);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        if(ba.size() == 0){ //没有地址,直接报错
            JOptionPane.showMessageDialog(null, "无可用配送地址,请先设置配送地址","错误",JOptionPane.ERROR_MESSAGE);
            FrmShowSeller.flag = false;
            return;
        }
        else{
            //默认选择第一个地址
            curAddress = ba.get(0);
            //用户选择地址,设置监听
            workPane.add(btAddress);
            this.btAddress.addActionListener(this);
            //得到优惠券信息
            String address_text = null;
            address_text = curAddress.getAddress_name();
            labelAddress_Text.setText(address_text);
            workPane.add(labelAddress);
            workPane.add(labelAddress_Text);
        }
        //得到最终价格
        workPane.add(labelFinal);
        try {
            price2 = TakeoutAssistantUtil.orderManager.getPrice(price1, VIP_Discount, curManjian, curCoupon);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        labelPrice2.setText("" + price2);
        labelPrice2.setForeground(Color.RED);
        workPane.add(labelPrice2);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(220, 300);
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.btCoupon){  //得到当前选择的优惠券
            FrmSelectCoupon dlg = new FrmSelectCoupon(this,"选择优惠券",true);
            dlg.setVisible(true);
            curCoupon = FrmSelectCoupon.curMyCoupon;
            //实时刷新订单信息
            String coupon_text = "" + curCoupon.getCoupon_amount();
            labelCoupon_Text.setText(coupon_text);
            try {
                price2 = TakeoutAssistantUtil.orderManager.getPrice(price1, VIP_Discount, curManjian, curCoupon);
                labelPrice2.setText("" + price2);
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            labelPrice2.setText("" + price2);
            //如果当前优惠券不能共享满减,则更新
            if(!curCoupon.getIfTogether()){
                curManjian = null;
                labelManjian_Text.setText("无可用满减");
                try {
                    price2 = TakeoutAssistantUtil.orderManager.getPrice(price1, VIP_Discount, curManjian, curCoupon);
                    labelPrice2.setText("" + price2);
                } catch (BaseException baseException) {
                    baseException.printStackTrace();
                }
            }
            else{
                try {
                    curManjian = TakeoutAssistantUtil.orderManager.getManjian(curSeller, price1);
                    String manjian_text = "满" + curManjian.getManjian_amount() + "减" + curManjian.getDiscount_amount();
                    labelManjian_Text.setText(manjian_text);
                    price2 = TakeoutAssistantUtil.orderManager.getPrice(price1, VIP_Discount, curManjian, curCoupon);
                    labelPrice2.setText("" + price2);
                } catch (BaseException baseException) {
                    baseException.printStackTrace();
                }
            }
        }
        else if(e.getSource() == this.btAddress){  //得到当前选择的地址
            FrmSelectAddress dlg = new FrmSelectAddress(this,"选择地址",true);
            dlg.setVisible(true);
            curAddress = FrmSelectAddress.curAddress;
            //刷新地址信息
            String address_text = curAddress.getAddress_name();
            labelAddress_Text.setText(address_text);
        }
        else if(e.getSource() == btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource() == this.btnOk){
            //计算送达时间
            Long time = System.currentTimeMillis();
            String time_text = (String) this.edtTime.getSelectedItem();
            if(time_text == "立即送达")
                time += 30 * 60 * 1000;
            else if(time_text == "1小时")
                time += 60 * 60 * 1000;
            else if(time_text == "2小时")
                time += 120 * 60 * 1000;
            else
                time += 180 * 60 * 1000;
            requestDate = new Date(time);
            //
            try {
                TakeoutAssistantUtil.orderManager.addOrder(orderID, curUser, curSeller, curAddress, price1, price2, requestDate, curManjian, curCoupon);
                JOptionPane.showMessageDialog(null, "下单成功,请耐心等待您的商品,我们将尽快送达~", "提示", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

}
