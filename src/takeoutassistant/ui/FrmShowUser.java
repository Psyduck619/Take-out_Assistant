package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanUserCus;
import takeoutassistant.model.BeanOrderInfo;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmShowUser extends JFrame {

    private static final long serialVersionUID = 1L;
    //用户消费表构造
    private static Object tblUserInfoTitle[] = BeanUserCus.tblUserCusTitle;
    private static Object tblUserInfoData[][];
    private static DefaultTableModel tabUserInfoModel = new DefaultTableModel();
    private static JTable dataTableUserInfo = new JTable(tabUserInfoModel);

    //初始化信息
    private BeanUser curUser = currentLoginUser;
    private List<BeanUserCus> allUserCus = null;
    //用户消费情况表
    private void reloadUserInfoTable(){
        try {
            allUserCus = TakeoutAssistantUtil.orderManager.loadUserCus();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblUserInfoData = new Object[allUserCus.size()][BeanUserCus.tblUserCusTitle.length];
        for(int i = 0 ; i < allUserCus.size() ; i++){
            for(int j = 0; j < BeanUserCus.tblUserCusTitle.length ; j++)
                tblUserInfoData[i][j] = allUserCus.get(i).getCell(j);
        }
        tabUserInfoModel.setDataVector(tblUserInfoData,tblUserInfoTitle);
        this.dataTableUserInfo.validate();
        this.dataTableUserInfo.repaint();
    }

    //主界面
    public FrmShowUser(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("用户消费情况");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableUserInfo), BorderLayout.CENTER);

        this.reloadUserInfoTable();  //加载信息
        this.setVisible(true);
    }

}
