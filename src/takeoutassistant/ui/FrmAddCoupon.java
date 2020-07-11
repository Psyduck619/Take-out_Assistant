package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class FrmAddCoupon extends JDialog implements ActionListener {

    BeanSeller seller = new BeanSeller();
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelDiscount = new JLabel("优惠金额：");
    private JLabel labelJidan = new JLabel("集单数：");
    private JLabel labelBegin = new JLabel("开始日期(输入整数)：                                ");
    private JLabel labelBeginYear = new JLabel("年");
    private JLabel labelBeginMonth = new JLabel("月");
    private JLabel labelBeginDay = new JLabel("日");
    private JLabel labelEnd = new JLabel("结束日期(输入整数)：                                ");
    private JLabel labelEndYear = new JLabel("年");
    private JLabel labelEndMonth = new JLabel("月");
    private JLabel labelEndDay = new JLabel("日");
    private JLabel labelTogether = new JLabel("优惠券是否可同享：");
    private JTextField edtDiscount = new JTextField(15);
    private JTextField edtJidan = new JTextField(15);
    private JTextField edtBeginYear = new JTextField(4);
    private JTextField edtBeginMonth = new JTextField(4);
    private JTextField edtBeginDay = new JTextField(4);
    private JTextField edtEndYear = new JTextField(4);
    private JTextField edtEndMonth = new JTextField(4);
    private JTextField edtEndDay = new JTextField(4);
    private JRadioButton edtYes = new JRadioButton("是");
    private JRadioButton edtNo = new JRadioButton("否");
    private ButtonGroup group = new ButtonGroup();

    public FrmAddCoupon(Frame f, String s, boolean b) {
        super(f, s, b);

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        workPane.add(labelDiscount);
        workPane.add(edtDiscount);
        workPane.add(labelJidan);
        workPane.add(edtJidan);
        workPane.add(labelBegin);
        workPane.add(edtBeginYear);
        workPane.add(labelBeginYear);
        workPane.add(edtBeginMonth);
        workPane.add(labelBeginMonth);
        workPane.add(edtBeginDay);
        workPane.add(labelBeginDay);
        workPane.add(labelEnd);
        workPane.add(edtEndYear);
        workPane.add(labelEndYear);
        workPane.add(edtEndMonth);
        workPane.add(labelEndMonth);
        workPane.add(edtEndDay);
        workPane.add(labelEndDay);
        //单选按钮设置
        group.add(edtYes);
        group.add(edtNo);
        edtYes.setSelected(true);
        workPane.add(labelTogether);
        workPane.add(edtYes);
        workPane.add(edtNo);
        this.getContentPane().add(workPane, BorderLayout.CENTER);

        this.setSize(280, 260);
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
        if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
        } else if (e.getSource() == this.btnOk) {
            Pattern pattern = Pattern.compile("[0-9]*");
            String discount = edtDiscount.getText();
            String jidan = edtJidan.getText();
            if(discount == null){
                JOptionPane.showMessageDialog(null, "优惠金额不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(jidan == null){
                JOptionPane.showMessageDialog(null, "集单数不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pattern.matcher(discount).matches()) {
                JOptionPane.showMessageDialog(null, "金额必须为整数", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pattern.matcher(jidan).matches()) {
                JOptionPane.showMessageDialog(null, "集单数必须为整数", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date dt1 = new Date();
            Date dt2 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String y1 = edtBeginYear.getText();
            String m1 = edtBeginMonth.getText();
            String d1 = edtBeginDay.getText();
            String y2 = edtEndYear.getText();
            String m2 = edtEndMonth.getText();
            String d2 = edtEndDay.getText();
            if(y1 == null || m1 == null || d1 == null || y2 == null || m2 == null || d2 == null){
                JOptionPane.showMessageDialog(null, "日期不能为空,请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(Integer.parseInt(y2) > 2021){
                JOptionPane.showMessageDialog(null, "请设置2021年以内的优惠券", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(Integer.parseInt(m1) < 1 || Integer.parseInt(m1) > 12 || Integer.parseInt(m2) < 1
                || Integer.parseInt(m2) > 12 || Integer.parseInt(d1) < 1 || Integer.parseInt(d1) > 31
                || Integer.parseInt(d2) < 0 || Integer.parseInt(d1) > 31){
                JOptionPane.showMessageDialog(null, "日期不合法,请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pattern.matcher(y1).matches() || !pattern.matcher(m1).matches() || !pattern.matcher(d1).matches()
                    || !pattern.matcher(y2).matches() || !pattern.matcher(m2).matches() || !pattern.matcher(d2).matches()) {
                JOptionPane.showMessageDialog(null, "日期信息必须为整数,请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                dt1 = sdf.parse(y1 + "-" + m1 + "-" + d1);
                dt2 = sdf.parse(y2 + "-" + m2 + "-" + d2);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            boolean flag = true;
            if(edtNo.isSelected()){
                flag = false;
            }
            try {
                TakeoutAssistantUtil.couponManager.addCoupon(seller, Integer.parseInt(discount), Integer.parseInt(jidan), dt1, dt2, flag);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
