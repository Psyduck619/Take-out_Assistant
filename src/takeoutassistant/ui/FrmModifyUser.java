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
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelUser = new JLabel("*用户名(不可修改)：");
    private JLabel labelName = new JLabel("  *姓名：");
    private JLabel labelGender = new JLabel("*性别：");
    private JLabel labelPhone = new JLabel("  *手机：");
    private JLabel labelEmail = new JLabel("   邮箱：");
    private JLabel labelCity = new JLabel("*城市：");
    private JTextField edtUserId = new JTextField(currentLoginUser.getUser_id(),18);
    private JTextField edtName = new JTextField(currentLoginUser.getUser_name(),18);
    private JTextField edtPhone = new JTextField(currentLoginUser.getUser_phone(),18);
    private JTextField edtEmail = new JTextField(currentLoginUser.getUser_email(),18);

    private JRadioButton edtGender1 = new JRadioButton("男");
    private JRadioButton edtGender2 = new JRadioButton("女");
    private JRadioButton edtGender3 = new JRadioButton("保密");
    private ButtonGroup group = new ButtonGroup();

    String items[] = {"杭州","上海","北京","成都","广州"};
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
        if(currentLoginUser.getUser_gender().equals("男"))
            edtGender1.setSelected(true);
        else if(currentLoginUser.getUser_gender().equals("女"))
            edtGender2.setSelected(true);
        else if(currentLoginUser.getUser_gender().equals("保密"))
            edtGender3.setSelected(true);

        workPane.add(labelPhone);
        workPane.add(edtPhone);
        workPane.add(labelEmail);
        workPane.add(edtEmail);
        workPane.add(labelCity);
        workPane.add(edtCity);
        if(currentLoginUser.getUser_city().equals("杭州"))
            edtCity.setSelectedIndex(0);
        else if(currentLoginUser.getUser_city().equals("上海"))
            edtCity.setSelectedIndex(1);
        else if(currentLoginUser.getUser_city().equals("北京"))
            edtCity.setSelectedIndex(2);
        else if(currentLoginUser.getUser_city().equals("成都"))
            edtCity.setSelectedIndex(3);
        else if(currentLoginUser.getUser_city().equals("广州"))
            edtCity.setSelectedIndex(4);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(240, 400);
        // 屏幕居中显示
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
                JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
