package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanAddress;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmSelectAddress extends JDialog implements ActionListener {

    private JPanel  menuBar = new JPanel();
    private JButton btSelect = new JButton("选择");//选择按钮
    //我的优惠券表构造
    private static Object tblAddressTitle[] = BeanAddress.tblAddressTitle;
    private static Object tblAddressData[][];
    private static DefaultTableModel tabAddressModel = new DefaultTableModel();
    private static JTable dataTableAddress = new JTable(tabAddressModel);

    //初始化信息
    private BeanUser curUser = currentLoginUser;
    private List<BeanAddress> allAddress = null;
    public static BeanAddress curAddress = null;
    //显示所有我的优惠券
    private void reloadAddressTable(){
        try {
            allAddress = TakeoutAssistantUtil.addressManager.loadAddress(curUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblAddressData = new Object[allAddress.size()][BeanAddress.tblAddressTitle.length];
        for(int i = 0 ; i < allAddress.size() ; i++){
            for(int j = 0 ; j < BeanAddress.tblAddressTitle.length ; j++)
                tblAddressData[i][j] = allAddress.get(i).getCell(j);
        }
        tabAddressModel.setDataVector(tblAddressData,tblAddressTitle);
        this.dataTableAddress.validate();
        this.dataTableAddress.repaint();
    }

    //主界面
    public FrmSelectAddress(FrmBuy f,String s, boolean b){
        //设置窗口信息
        super(f,s,b);
        this.setSize(700,400);
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        menuBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        menuBar.add(btSelect);
        this.btSelect.addActionListener(this);
        this.getContentPane().add(menuBar, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(this.dataTableAddress), BorderLayout.CENTER);
        this.dataTableAddress.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {  //点击显示选择信息
                int i = FrmSelectAddress.this.dataTableAddress.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curAddress = allAddress.get(i);
            }
        });

        this.reloadAddressTable();  //加载信息
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btSelect){
            if(this.curAddress == null) {
                JOptionPane.showMessageDialog(null, "请选择一项地址", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.setVisible(false);
        }
    }

}
