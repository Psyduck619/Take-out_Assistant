package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static takeoutassistant.ui.FrmMyOrder.curOrder;

public class FrmAddGoodsComment extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelComment = new JLabel("请选择评价：                  ");
    private JRadioButton edtGood = new JRadioButton("好评");
    private JRadioButton edtNormal = new JRadioButton("中评");
    private JRadioButton edtBad = new JRadioButton("差评");
    private ButtonGroup group = new ButtonGroup();

    public FrmAddGoodsComment(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        group.add(edtGood);
        group.add(edtNormal);
        group.add(edtBad);
        workPane.add(labelComment);
        workPane.add(edtGood);
        workPane.add(edtNormal);
        workPane.add(edtBad);
        edtGood.setSelected(true);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(190, 160);
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
            String comment = null;
            if(edtGood.isSelected())
                comment = "好评";
            else if(edtNormal.isSelected())
                comment = "中评";
            else
                comment = "差评";
            try {
                TakeoutAssistantUtil.riderAccountManager.addRiderComment(curOrder, comment);
                JOptionPane.showMessageDialog(null, "评价成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
