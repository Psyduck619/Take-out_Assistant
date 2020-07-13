package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;
import takeoutassistant.ui.FrmMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmLevel extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelLevel = new JLabel("请选择星级：");
    private JRadioButton edtLevel1 = new JRadioButton("一星");
    private JRadioButton edtLevel2 = new JRadioButton("二星");
    private JRadioButton edtLevel3 = new JRadioButton("三星");
    private JRadioButton edtLevel4 = new JRadioButton("四星");
    private JRadioButton edtLevel5 = new JRadioButton("五星");
    private JRadioButton edtLevel = new JRadioButton("显示所有商家");
    private ButtonGroup group = new ButtonGroup();

    public FrmLevel(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        workPane.add(labelLevel);
        group.add(edtLevel1);
        group.add(edtLevel2);
        group.add(edtLevel3);
        group.add(edtLevel4);
        group.add(edtLevel5);
        group.add(edtLevel);
        edtLevel1.setSelected(true);
        workPane.add(edtLevel1);
        workPane.add(edtLevel2);
        workPane.add(edtLevel3);
        workPane.add(edtLevel4);
        workPane.add(edtLevel5);
        workPane.add(edtLevel);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 300);
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
        int level = 0;
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            if(edtLevel1.isSelected()){
                level = 1;
            }else if(edtLevel2.isSelected()){
                level = 2;
            }else if(edtLevel3.isSelected()){
                level = 3;
            }else if(edtLevel4.isSelected()){
                level = 4;
            }else if(edtLevel5.isSelected()){
                level = 5;
            }else {
                level =0;
            }
            FrmMain_user.reloadSLevelTable(level);
            this.setVisible(false);
        }
    }

}
