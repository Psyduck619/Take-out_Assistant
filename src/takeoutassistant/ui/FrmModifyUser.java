package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmModifyUser extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("ȷ��");
    private Button btnCancel = new Button("ȡ��");

    private JLabel labelUser = new JLabel("*�û���(�����޸�)��");
    private JLabel labelName = new JLabel("  *������");
    private JLabel labelGender = new JLabel("*�Ա�");
    private JLabel labelPhone = new JLabel("  *�ֻ���");
    private JLabel labelEmail = new JLabel("   ���䣺");
    private JLabel labelCity = new JLabel("*���У�");
    private JTextField edtUserId = new JTextField(currentLoginUser.getUser_id(),18);
    private JTextField edtName = new JTextField(currentLoginUser.getUser_name(),18);
    private JTextField edtPhone = new JTextField(currentLoginUser.getUser_phone(),18);
    private JTextField edtEmail = new JTextField(currentLoginUser.getUser_email(),18);

    private JRadioButton edtGender1 = new JRadioButton("��");
    private JRadioButton edtGender2 = new JRadioButton("Ů");
    private JRadioButton edtGender3 = new JRadioButton("����");
    private ButtonGroup group = new ButtonGroup();

    String items[] = {"����","�Ϻ�","����","�ɶ�","����"};
    private JComboBox edtCity = new JComboBox(items);

    public FrmModifyUser(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        edtUserId.setEditable(false);
        workPane.add(labelName);
        workPane.add(edtName);

        group.add(edtGender1);
        group.add(edtGender2);
        group.add(edtGender3);
        workPane.add(labelGender);
        workPane.add(edtGender1);
        workPane.add(edtGender2);
        workPane.add(edtGender3);
        if(currentLoginUser.getUser_gender().equals("��"))
            edtGender1.setSelected(true);
        else if(currentLoginUser.getUser_gender().equals("Ů"))
            edtGender2.setSelected(true);
        else if(currentLoginUser.getUser_gender().equals("����"))
            edtGender3.setSelected(true);

        workPane.add(labelPhone);
        workPane.add(edtPhone);
        workPane.add(labelEmail);
        workPane.add(edtEmail);
        workPane.add(labelCity);
        workPane.add(edtCity);
        if(currentLoginUser.getUser_city().equals("����"))
            edtCity.setSelectedIndex(0);
        else if(currentLoginUser.getUser_city().equals("�Ϻ�"))
            edtCity.setSelectedIndex(1);
        else if(currentLoginUser.getUser_city().equals("����"))
            edtCity.setSelectedIndex(2);
        else if(currentLoginUser.getUser_city().equals("�ɶ�"))
            edtCity.setSelectedIndex(3);
        else if(currentLoginUser.getUser_city().equals("����"))
            edtCity.setSelectedIndex(4);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(240, 400);
        // ��Ļ������ʾ
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
        if(e.getSource() == this.btnCancel)
            this.setVisible(false);
        else if(e.getSource() == this.btnOk){
            String name = this.edtName.getText();
            String gender = null;
            if(edtGender1.isSelected()){
                gender = edtGender1.getText();
            }else if(edtGender2.isSelected()){
                gender = edtGender2.getText();
            }else{
                gender = edtGender3.getText();
            }
            String phone = this.edtPhone.getText();
            String email =  this.edtEmail.getText();
            String city = (String) this.edtCity.getSelectedItem();
            try {
                TakeoutAssistantUtil.userManager.modifyUser(currentLoginUser,name,gender,phone,email,city);
                JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
