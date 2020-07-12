package takeoutassistant.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmPutTypeName extends JDialog implements ActionListener {

    public static String name = null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("查询");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("请输入类别名称：");
    private JTextField edtName = new JTextField(20);

    public FrmPutTypeName(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 180);
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
            name = this.edtName.getText();
            FrmGoodsForType dlg = new FrmGoodsForType();
            this.setVisible(false);
            dlg.setVisible(true);
        }
    }

}
