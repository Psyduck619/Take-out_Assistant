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
    private Button btnOk = new Button("ȷ��");
    private Button btnCancel = new Button("ȡ��");
    private JLabel labelLevel = new JLabel("��ѡ���Ǽ�:                                                            ");
    private JLabel labelComment = new JLabel("����һ����Ʒ��~:");
    private JTextField content = new JTextField(20);
    private JRadioButton edtLevel1 = new JRadioButton("һ��");
    private JRadioButton edtLevel2 = new JRadioButton("����");
    private JRadioButton edtLevel3 = new JRadioButton("����");
    private JRadioButton edtLevel4 = new JRadioButton("����");
    private JRadioButton edtLevel5 = new JRadioButton("����");
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
            String level = null;
            if(edtLevel1.isSelected())
                level = "һ��";
            else if(edtLevel2.isSelected())
                level = "����";
            else if(edtLevel3.isSelected())
                level = "����";
            else if(edtLevel4.isSelected())
                level = "����";
            else
                level = "����";
            try {
                TakeoutAssistantUtil.goodsCommentManager.addGoodsComment(curOrderInfo, content.getText(), level);
                JOptionPane.showMessageDialog(null, "���۳ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
