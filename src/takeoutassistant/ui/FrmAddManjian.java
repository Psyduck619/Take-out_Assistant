package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrmAddManjian extends JDialog implements ActionListener {
    BeanSeller seller = new BeanSeller();
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("ȷ��");
    private Button btnCancel = new Button("ȡ��");
    private JLabel labelFull = new JLabel("�����");
    private JLabel labelDiscount = new JLabel("�Żݽ�");
    private JTextField edtFull = new JTextField(20);
    private JTextField edtDiscount = new JTextField(20);

    public FrmAddManjian(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelFull);
        workPane.add(edtFull);
        workPane.add(labelDiscount);
        workPane.add(edtDiscount);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 220);
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
            String full = edtFull.getText();
            String discount = edtDiscount.getText();
            Pattern pattern = Pattern.compile("[0-9]*");
            if(!pattern.matcher(full).matches() || !pattern.matcher(discount).matches()){
                JOptionPane.showMessageDialog(null, "������Ϊ����", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.manjianManager.addManjian(seller, Integer.parseInt(full), Integer.parseInt(discount));
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
