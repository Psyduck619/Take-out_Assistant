package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static takeoutassistant.ui.FrmShowOrderInfo.curOrderInfo;

public class FrmAddGoodsComment extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelLevel = new JLabel("请选择星级:                                                            ");
    private JLabel labelComment = new JLabel("评价一下商品吧~:");
    private JTextField content = new JTextField(20);
    private JRadioButton edtLevel1 = new JRadioButton("一星");
    private JRadioButton edtLevel2 = new JRadioButton("二星");
    private JRadioButton edtLevel3 = new JRadioButton("三星");
    private JRadioButton edtLevel4 = new JRadioButton("四星");
    private JRadioButton edtLevel5 = new JRadioButton("五星");
    private ButtonGroup group = new ButtonGroup();

    public FrmAddGoodsComment(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        group.add(edtLevel1);
        group.add(edtLevel2);
        group.add(edtLevel3);
        group.add(edtLevel4);
        group.add(edtLevel5);
        workPane.add(labelLevel);
        workPane.add(edtLevel1);
        workPane.add(edtLevel2);
        workPane.add(edtLevel3);
        workPane.add(edtLevel4);
        workPane.add(edtLevel5);
        edtLevel5.setSelected(true);
        workPane.add(labelComment);
        workPane.add(content);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 200);
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
            this.setVisible(false);
        }
        else if(e.getSource() == this.btnOk){
            String level = null;
            if(edtLevel1.isSelected())
                level = "一星";
            else if(edtLevel2.isSelected())
                level = "二星";
            else if(edtLevel3.isSelected())
                level = "三星";
            else if(edtLevel4.isSelected())
                level = "四星";
            else
                level = "五星";
            try {
                TakeoutAssistantUtil.goodsCommentManager.addGoodsComment(curOrderInfo, content.getText(), level);
                JOptionPane.showMessageDialog(null, "评价成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
