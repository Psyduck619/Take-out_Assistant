package takeoutassistant.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmConfirm extends JDialog implements ActionListener {

    public static boolean confirm = false;
    private JPanel toolBar = new JPanel();
    private JLabel labelSure = new JLabel("您确定吗?",JLabel.CENTER);
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    public FrmConfirm(Frame f, String s, boolean b) {
        super(f, s, b);
        labelSure.setFont(new Font("宋体1653", 1, 15));
        labelSure.setForeground(Color.RED);
        this.getContentPane().add(labelSure, BorderLayout.NORTH);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        this.setSize(280, 120);
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
            confirm = false;
            this.setVisible(false);
        }
        else if(e.getSource()==this.btnOk){
            confirm = true;
            this.setVisible(false);
        }
    }

}
