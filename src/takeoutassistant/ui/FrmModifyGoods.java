package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import static takeoutassistant.control.AdminManager.isDouble;
import static takeoutassistant.ui.FrmMain.curGoods;

public class FrmModifyGoods extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("商品名称：");
    private JLabel labelPrice = new JLabel("单价：");
    private JLabel labelPrice2 = new JLabel("会员价：");
    private JLabel labelQuantity = new JLabel("商品数量：");
    private JTextField edtName = new JTextField(curGoods.getGoods_name(),20);
    private JTextField edtPrice = new JTextField(Double.toString(curGoods.getPrice()),20);
    private JTextField edtPrice2 = new JTextField(Double.toString(curGoods.getDiscount_price()),20);
    private JTextField edtQuantity = new JTextField(Integer.toString(curGoods.getGoods_quantity()),20);

    public FrmModifyGoods(Frame f, String s, boolean b) {

        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelPrice);
        workPane.add(edtPrice);
        workPane.add(labelPrice2);
        workPane.add(edtPrice2);
        workPane.add(labelQuantity);
        workPane.add(edtQuantity);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 320);
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
        if(e.getSource() == this.btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource() == this.btnOk){
            try {
                String name = this.edtName.getText();
                String price = this.edtPrice.getText();
                String price2 = this.edtPrice2.getText();
                String quantity = this.edtQuantity.getText();
                if(name == null || "".equals(name)){
                    JOptionPane.showMessageDialog(null, "商品名字不能为空", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(price == null || "".equals(price) || price2 == null || "".equals(price2)){
                    JOptionPane.showMessageDialog(null, "金额不能为空", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!isDouble(price) || !isDouble(price2)){
                    JOptionPane.showMessageDialog(null, "金额格式错误", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(quantity == null || "".equals(name)){
                    JOptionPane.showMessageDialog(null, "商品数量不能为空", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Pattern p = Pattern.compile("[0-9]*");
                if(!p.matcher(quantity).matches()){
                    JOptionPane.showMessageDialog(null, "商品数量只能为正整数", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TakeoutAssistantUtil.goodsManager.modifyGoods(curGoods, name, Double.parseDouble(price),Double.parseDouble(price2), Integer.parseInt(quantity));
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
