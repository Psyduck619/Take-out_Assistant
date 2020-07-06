package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static takeoutassistant.ui.FrmMain.curGoods;

public class FrmModifyGoods extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("商品名称：");
    private JLabel labelPrice = new JLabel("单价：");
    private JLabel labelPeice2 = new JLabel("会员价：");
    private JTextField edtName = new JTextField(20);
    private JTextField edtPrice = new JTextField(20);
    private JTextField edtPrice2 = new JTextField(20);

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
        workPane.add(labelPeice2);
        workPane.add(edtPrice2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 300);
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
                TakeoutAssistantUtil.goodsManager.modifyGoods(curGoods, name, Double.parseDouble(price),Double.parseDouble(price2));
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
