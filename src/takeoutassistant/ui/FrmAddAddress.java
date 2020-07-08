package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmAddAddress extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("地址名称：");
    private JLabel labelLinkMan = new JLabel("联系人姓名：");
    private JLabel labelLinkPhone = new JLabel("联系人电话：");
    private JTextField edtName = new JTextField(20);
    private JTextField edtLinkMan = new JTextField(20);
    private JTextField edtLinPhone = new JTextField(20);

    public FrmAddAddress(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelLinkMan);
        workPane.add(edtLinkMan);
        workPane.add(labelLinkPhone);
        workPane.add(edtLinPhone);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 240);
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
            String name = this.edtName.getText();
            String man = this.edtLinkMan.getText();
            String phone = this.edtLinPhone.getText();
            try {
                TakeoutAssistantUtil.addressManager.addAddress(currentLoginUser, name, man, phone);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
