package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import static takeoutassistant.ui.FrmMain_rider.curRiderAccount;

public class FrmModifyRiderAccount extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("ȷ��");
    private Button btnCancel = new Button("ȡ��");
    private JLabel labelRiderid = new JLabel("���������ֱ�ţ�");
    private JTextField edtRiderid = new JTextField(20);

    public FrmModifyRiderAccount(Frame f, String s, boolean b) {

        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelRiderid);
        workPane.add(edtRiderid);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 150);
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
        if(e.getSource() == this.btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource() == this.btnOk){
            String str = this.edtRiderid.getText();
            Pattern p = Pattern.compile("[0-9]*");
            if(!p.matcher(str).matches()){
                JOptionPane.showMessageDialog(null, "��ű���Ϊ������,����������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int riderid = Integer.parseInt(str);
            try {
                TakeoutAssistantUtil.riderAccountManager.modifyRiderAccount(riderid, curRiderAccount);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
