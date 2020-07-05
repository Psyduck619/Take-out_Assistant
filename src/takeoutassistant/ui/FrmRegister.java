package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAdmin;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRegister extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ע��");
	private Button btnCancel = new Button("ȡ��");

	private JLabel labelUser = new JLabel("*�û�����");
	private JLabel labelName = new JLabel("  *������");
	private JLabel labelPwd = new JLabel("  *���룺");
	private JLabel labelGender = new JLabel("*�Ա�");
	private JLabel labelPhone = new JLabel("  *�ֻ���");
	private JLabel labelEmail = new JLabel("   ���䣺");
	private JLabel labelCity = new JLabel("*���У�");
	private JLabel labelPwd2 = new JLabel("*ȷ�����룺");
	private JTextField edtUserId = new JTextField(18);
	private JTextField edtName = new JTextField(18);
	private JTextField edtPhone = new JTextField(18);
	private JTextField edtEmail = new JTextField(18);

	private JRadioButton edtGender = new JRadioButton("��");
	private JRadioButton edtGender2 = new JRadioButton("Ů");
	private JRadioButton edtGender3 = new JRadioButton("����");
	private ButtonGroup group = new ButtonGroup();

	String items[] = {"����","�Ϻ�","����","�ɶ�","����"};
	private JComboBox edtCity = new JComboBox(items);

	private JPasswordField edtPwd = new JPasswordField(18);
	private JPasswordField edtPwd2 = new JPasswordField(18);
	public FrmRegister(Dialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		workPane.add(labelName);
		workPane.add(edtName);

		group.add(edtGender);
		group.add(edtGender2);
		group.add(edtGender3);
		workPane.add(labelGender);
		workPane.add(edtGender);
		workPane.add(edtGender2);
		workPane.add(edtGender3);

		workPane.add(labelPhone);
		workPane.add(edtPhone);
		workPane.add(labelEmail);
		workPane.add(edtEmail);
		workPane.add(labelCity);
		workPane.add(edtCity);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(240, 500);
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
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			String userid=this.edtUserId.getText();
			String name=this.edtName.getText();
			String gender=null;
			if(edtGender.isSelected()){
				gender = edtGender.getText();
			}else if(edtGender2.isSelected()){
				gender = edtGender2.getText();
			}else{
				gender = edtGender3.getText();
			}
			String phone=this.edtPhone.getText();
			String email=this.edtEmail.getText();
			String city= (String) this.edtCity.getSelectedItem();
			String pwd1=new String(this.edtPwd.getPassword());
			String pwd2=new String(this.edtPwd2.getPassword());
			try {
				BeanUser user=TakeoutAssistantUtil.userManager.reg(userid,name,gender,phone,email,city,pwd1,pwd2);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==this.btnCancel)
//			this.setVisible(false);
//		else if(e.getSource()==this.btnOk){
//			String userid=this.edtUserId.getText();
//			String name=this.edtName.getText();
//			String gender=null;
//			if(edtGender.isSelected()){
//				gender = edtGender.getText();
//			}else if(edtGender2.isSelected()){
//				gender = edtGender2.getText();
//			}else{
//				gender = edtGender3.getText();
//			}
//			String phone=this.edtPhone.getText();
//			String email=this.edtEmail.getText();
//			String city= (String) this.edtCity.getSelectedItem();
//			String pwd1=new String(this.edtPwd.getPassword());
//			String pwd2=new String(this.edtPwd2.getPassword());
//			try {
//				BeanUser user=TakeoutAssistantUtil.userManager.reg(userid,name,gender,phone,email,city,pwd1,pwd2);
//				this.setVisible(false);
//			} catch (BaseException e1) {
//				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//		}
//	}

}
