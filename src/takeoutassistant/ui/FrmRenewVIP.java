package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmRenewVIP extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelMonth = new JLabel("请选择续费会员类型:");
    private JRadioButton edtMonth1 = new JRadioButton("月度会员(10RMB)");
    private JRadioButton edtMonth3 = new JRadioButton("季度会员(27RMB)");
    private JRadioButton edtMonth12 = new JRadioButton("年度会员(96RMB)");
    private ButtonGroup group = new ButtonGroup();

    public FrmRenewVIP(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        group.add(edtMonth1);
        group.add(edtMonth3);
        group.add(edtMonth12);
        workPane.add(labelMonth);
        workPane.add(edtMonth1);
        workPane.add(edtMonth3);
        workPane.add(edtMonth12);
        edtMonth1.setSelected(true);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(240, 220);
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

        if(e.getSource() == this.btnCancel)
            this.setVisible(false);
        else if(e.getSource() == this.btnOk){
            int month = 0;
            if(edtMonth1.isSelected()){
                month = 1;
            }
            else if(edtMonth3.isSelected()){
                month = 3;
            }
            else{
                month = 12;
            }
            try {
                TakeoutAssistantUtil.VIPManager.renewVIP(currentLoginUser, month);
                JOptionPane.showMessageDialog(null, "恭喜您,续费成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

}
