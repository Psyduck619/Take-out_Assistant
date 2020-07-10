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
    //�����������
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("ȷ��");
    private Button btnCancel = new Button("ȡ��");
    private JLabel labelOriginal = new JLabel("ԭ��:");
    private JLabel labelPrice1 = new JLabel();
    private JLabel labelVIP = new JLabel("��Ա�Ż�:");
    private JLabel labelVIP_Text = new JLabel();
    private JLabel labelManjian = new JLabel("�����Ż�:");
    private JLabel labelManjian_no = new JLabel("�޿�������");
    private JLabel labelManjian_Text = new JLabel();
    private JLabel labelCoupon = new JLabel("�Ż�ȯ�Ż�:");
    private JLabel labelCoupon_no = new JLabel("���Ż�ȯ");
    private JLabel labelCoupon_Text = new JLabel();

    private JLabel labelTime = new JLabel("����ʱ��:");
    String items[] = {"�����ʹ�","1Сʱ","2Сʱ","3Сʱ"};
    private JComboBox edtTime = new JComboBox(items);

    private JLabel labelAddress = new JLabel("���͵�ַ:");
    private JLabel labelAddress_Text = new JLabel();

    private JLabel labelFinal = new JLabel("               �ϼ�:");
    private JLabel labelPrice2 = new JLabel();

    private Button btCoupon = new Button("���ѡ���Ż�ȯ");
    private Button btTime = new Button("���ѡ��ʱ��:");
    private Button btAddress = new Button("���ѡ���ַ");

    //��ʼ��������Ϣ
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

    //������
    public FrmBuy(Frame f, String s, boolean b){
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        //�õ�������
        try {
            orderID = TakeoutAssistantUtil.orderManager.getOrderID(curUser);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //�õ�ԭ��
        try {
            price1 = TakeoutAssistantUtil.orderManager.getPrice1(orderID);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //��ʾԭ��
        labelPrice1.setText("" + price1 + " ");
        labelPrice1.setForeground(Color.RED);
        workPane.add(labelOriginal);
        workPane.add(labelPrice1);
        //�õ���Ա�ۿ�
        try {
            VIP_Discount = TakeoutAssistantUtil.orderManager.getVIP_Discount(orderID);
            //price2 = price1 - VIP_Discount;
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //��ʾ��Ա�ۿ�
        labelVIP_Text.setText("" + VIP_Discount + " ");
        labelVIP_Text.setForeground(Color.RED);
        workPane.add(labelVIP);
        workPane.add(labelVIP_Text);
        //�õ�������Ϣ
        String manjian_text = null;
        try {
            curManjian = TakeoutAssistantUtil.orderManager.getManjian(curSeller, price1);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //��ʾ������Ϣ
        workPane.add(labelManjian);
        if(curManjian != null){
            manjian_text = "��" + curManjian.getManjian_amount() + "��" + curManjian.getDiscount_amount();
            labelManjian_Text.setText(manjian_text);
            labelManjian_Text.setForeground(Color.RED);
            workPane.add(labelManjian_Text);
            //price2 -= curManjian.getDiscount_amount();
        }
        else{
            labelManjian_Text.setText("�޿�������");
            workPane.add(labelManjian_Text);
        }

        //��ʾ�Ż�ȯ��Ϣ
        workPane.add(labelCoupon);
        List<BeanMyCoupon> bmg = null;
        try {  //��ѯ�û��Ƿ��е�ǰ�����Ż�ȯ
             bmg = TakeoutAssistantUtil.myCouponManager.loadMyCoupon2(curUser, curSeller);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        if(bmg.size() == 0){ //û������ʾ���Ż�ȯ��Ϣ
            workPane.add(labelCoupon_no);
        }
        else{
            curCoupon = bmg.get(0);
            //Ĭ��ѡ���������Ż�ȯ
            for(int i = 1 ; i < bmg.size() ; i++){
                if(bmg.get(i).getCoupon_amount() > curCoupon.getCoupon_amount()){
                    curCoupon = bmg.get(i);
                }
            }
            //�û�ѡ���Ż�ȯ,���ü���
            workPane.add(btCoupon);
            this.btCoupon.addActionListener(this);
            //�õ��Ż�ȯ��Ϣ
            String coupon_text = null;
            coupon_text = "" + curCoupon.getCoupon_amount();
            labelCoupon_Text.setText(coupon_text);
            labelCoupon_Text.setForeground(Color.RED);
            workPane.add(labelCoupon);
            workPane.add(labelCoupon_Text);
            if(!curCoupon.getIfTogether()){
                curManjian = null;
                labelManjian_Text.setText("�޿�������");
            }
            //price2 -= curCoupon.getCoupon_amount();
        }
        //������õ�Ԥ��ʱ��
        workPane.add(labelTime);
        workPane.add(edtTime);
        //��ʾ��ַ��Ϣ
        List<BeanAddress> ba = null;
        try {  //��ѯ�û��Ƿ��е�ǰ���õ�ַ
            ba = TakeoutAssistantUtil.addressManager.loadAddress(curUser);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        if(ba.size() == 0){ //û�е�ַ,ֱ�ӱ���
            JOptionPane.showMessageDialog(null, "�޿������͵�ַ,�����������͵�ַ","����",JOptionPane.ERROR_MESSAGE);
            FrmShowSeller.flag = false;
            return;
        }
        else{
            //Ĭ��ѡ���һ����ַ
            curAddress = ba.get(0);
            //�û�ѡ���ַ,���ü���
            workPane.add(btAddress);
            this.btAddress.addActionListener(this);
            //�õ��Ż�ȯ��Ϣ
            String address_text = null;
            address_text = curAddress.getAddress_name();
            labelAddress_Text.setText(address_text);
            workPane.add(labelAddress);
            workPane.add(labelAddress_Text);
        }
        //�õ����ռ۸�
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
        // ���ھ���
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

        if(e.getSource() == this.btCoupon){  //�õ���ǰѡ����Ż�ȯ
            FrmSelectCoupon dlg = new FrmSelectCoupon(this,"ѡ���Ż�ȯ",true);
            dlg.setVisible(true);
            curCoupon = FrmSelectCoupon.curMyCoupon;
            //ʵʱˢ�¶�����Ϣ
            String coupon_text = "" + curCoupon.getCoupon_amount();
            labelCoupon_Text.setText(coupon_text);
            try {
                price2 = TakeoutAssistantUtil.orderManager.getPrice(price1, VIP_Discount, curManjian, curCoupon);
                labelPrice2.setText("" + price2);
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            labelPrice2.setText("" + price2);
            //�����ǰ�Ż�ȯ���ܹ�������,�����
            if(!curCoupon.getIfTogether()){
                curManjian = null;
                labelManjian_Text.setText("�޿�������");
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
                    String manjian_text = "��" + curManjian.getManjian_amount() + "��" + curManjian.getDiscount_amount();
                    labelManjian_Text.setText(manjian_text);
                    price2 = TakeoutAssistantUtil.orderManager.getPrice(price1, VIP_Discount, curManjian, curCoupon);
                    labelPrice2.setText("" + price2);
                } catch (BaseException baseException) {
                    baseException.printStackTrace();
                }
            }
        }
        else if(e.getSource() == this.btAddress){  //�õ���ǰѡ��ĵ�ַ
            FrmSelectAddress dlg = new FrmSelectAddress(this,"ѡ���ַ",true);
            dlg.setVisible(true);
            curAddress = FrmSelectAddress.curAddress;
            //ˢ�µ�ַ��Ϣ
            String address_text = curAddress.getAddress_name();
            labelAddress_Text.setText(address_text);
        }
        else if(e.getSource() == btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource() == this.btnOk){
            //�����ʹ�ʱ��
            Long time = System.currentTimeMillis();
            String time_text = (String) this.edtTime.getSelectedItem();
            if(time_text == "�����ʹ�")
                time += 30 * 60 * 1000;
            else if(time_text == "1Сʱ")
                time += 60 * 60 * 1000;
            else if(time_text == "2Сʱ")
                time += 120 * 60 * 1000;
            else
                time += 180 * 60 * 1000;
            requestDate = new Date(time);
            //
            try {
                TakeoutAssistantUtil.orderManager.addOrder(orderID, curUser, curSeller, curAddress, price1, price2, requestDate, curManjian, curCoupon);
                JOptionPane.showMessageDialog(null, "�µ��ɹ�,�����ĵȴ�������Ʒ,���ǽ������ʹ�~", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

}
