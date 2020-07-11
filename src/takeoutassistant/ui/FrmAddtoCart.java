package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import static takeoutassistant.ui.FrmShowSeller.curGoods;
import static takeoutassistant.ui.FrmShowMyCart.curMyCart;
import static takeoutassistant.ui.FrmShowMyJidan.curUser;

public class FrmAddtoCart extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("ȷ��");
    private Button btnCancel = new Button("ȡ��");
    private JLabel labelCount = new JLabel("��������Ʒ������:");
    private JTextField edtCount = new JTextField(20);

    public FrmAddtoCart(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelCount);
        workPane.add(edtCount);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(280, 150);
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
            String count = this.edtCount.getText();
            Pattern p = Pattern.compile("[0-9]*");
            if(!p.matcher(count).matches() || count == null || "".equals(count)){
                JOptionPane.showMessageDialog(null, "������������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //�������������Ϲ��ﳵ��������ǲ��Ǵ�����Ʒ����
            int cartQuantity = 0;
            int goods_quantity = 0;  //��ѯ����ǰ���ﳵ��ĳ��Ʒ�Ŀ����
            try {
                cartQuantity = TakeoutAssistantUtil.orderInfoManager.getQuantity2(curUser, curGoods);
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            try {
                goods_quantity = TakeoutAssistantUtil.orderInfoManager.getGoodsQuantity2(curGoods);
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            System.out.println(cartQuantity);
            System.out.println(goods_quantity);
            if(Integer.parseInt(count)+cartQuantity > goods_quantity){
                JOptionPane.showMessageDialog(null, "��Ʒ��治��", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.orderInfoManager.addOrderInfo(curUser, curGoods, Integer.parseInt(count));
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
