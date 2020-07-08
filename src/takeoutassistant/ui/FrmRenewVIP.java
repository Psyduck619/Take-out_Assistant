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
    private Button btnOk = new Button("ȷ��");
    private Button btnCancel = new Button("ȡ��");

    private JLabel labelMonth = new JLabel("��ѡ�����ѻ�Ա����:");
    private JRadioButton edtMonth1 = new JRadioButton("�¶Ȼ�Ա(10RMB)");
    private JRadioButton edtMonth3 = new JRadioButton("���Ȼ�Ա(27RMB)");
    private JRadioButton edtMonth12 = new JRadioButton("��Ȼ�Ա(96RMB)");
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
        // ���ھ���
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
                JOptionPane.showMessageDialog(null, "��ϲ��,���ѳɹ�!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

}
