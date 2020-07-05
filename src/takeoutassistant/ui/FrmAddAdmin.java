package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAdmin;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddAdmin extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelName = new JLabel("管理员：");
    private JLabel labelPwd = new JLabel("密码：");
    private JLabel labelPwd2 = new JLabel("确认密码：");
    private JTextField edtName = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JPasswordField edtPwd2 = new JPasswordField(20);
    public FrmAddAdmin(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 260);
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            try {
                TakeoutAssistantUtil.adminManager.addAdmin(edtName.getText(), new String(edtPwd.getPassword()), new String(edtPwd2.getPassword()));
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
