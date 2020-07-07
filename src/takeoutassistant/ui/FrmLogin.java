package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAdmin;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;
import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class FrmLogin extends JDialog implements ActionListener {
	public static String loginType = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JPanel imagePane = new JPanel();
	private JButton btnLogin = new JButton("登陆");
	private JButton btnCancel = new JButton("退出");
	private JButton btnRegister = new JButton("用户注册");
	private JLabel lIcon = new JLabel();
	private JLabel labelUser = new JLabel("账号：");
	private JLabel labelPwd = new JLabel("密码：");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);

	private JRadioButton rbtUser1 = new JRadioButton("用户");
	private JRadioButton rbtUser2 = new JRadioButton("管理员");
	private ButtonGroup gUser = new ButtonGroup();

	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);

		gUser.add(rbtUser1);
		gUser.add(rbtUser2);
		rbtUser1.setSelected(true);
		workPane.add(rbtUser1);
		workPane.add(rbtUser2);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		Icon icon = new ImageIcon("src/PIKA.jpg");
		lIcon.setIcon(icon);
		imagePane.add(lIcon);
		imagePane.setBackground(Color.DARK_GRAY);
		this.getContentPane().add(imagePane,BorderLayout.NORTH);
		this.setSize(320, 520);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.btnRegister.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnLogin) {
			String userid = this.edtUserId.getText();
			String pwd = new String(this.edtPwd.getPassword());
			if(rbtUser1.isSelected()){
				loginType = rbtUser1.getText();
			} else {
				loginType = rbtUser2.getText();
			}
			try {
				if(loginType.equals("用户")){
					BeanUser.currentLoginUser= TakeoutAssistantUtil.userManager.login(userid, pwd);
				} else {
					BeanAdmin.currentLoginAdmin = TakeoutAssistantUtil.adminManager.login(userid, pwd);
				}
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
			
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		} else if(e.getSource()==this.btnRegister){
			FrmRegister dlg=new FrmRegister(this,"注册",true);
			dlg.setVisible(true);
		}
	}

}
