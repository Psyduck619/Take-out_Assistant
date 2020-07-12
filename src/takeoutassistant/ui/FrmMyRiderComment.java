package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanRiderAccount;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmMyRiderComment extends JFrame {

    private static final long serialVersionUID = 1L;

    //商品评价表构造
    private static Object tblRiderCommentTitle[] = BeanRiderAccount.tblRiderCommentTitle;
    private static Object tblRiderCommentData[][];
    private static DefaultTableModel tabRiderCommentModel = new DefaultTableModel();
    private static JTable dataTableRiderComment = new JTable(tabRiderCommentModel);

    //初始化信息
    public static BeanRiderAccount curRiderComment = null;
    public static List<BeanRiderAccount> allRiderComment = null;
    //显示所有评价
    private void reloadRiderCommentTable(){
        try {
            allRiderComment = TakeoutAssistantUtil.riderAccountManager.loadUsers(currentLoginUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblRiderCommentData = new Object[allRiderComment.size()][BeanRiderAccount.tblRiderCommentTitle.length];
        for(int i = 0 ; i < allRiderComment.size() ; i++){
            for(int j = 0 ; j < BeanRiderAccount.tblRiderCommentTitle.length ; j++)
                tblRiderCommentData[i][j] = allRiderComment.get(i).getCell2(j);
        }
        tabRiderCommentModel.setDataVector(tblRiderCommentData,tblRiderCommentTitle);
        this.dataTableRiderComment.validate();
        this.dataTableRiderComment.repaint();
    }
    //主界面
    public FrmMyRiderComment(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(800,400);
        this.setTitle("我的骑手评价");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //评价信息显示
        this.getContentPane().add(new JScrollPane(this.dataTableRiderComment), BorderLayout.CENTER);
        this.reloadRiderCommentTable();  //加载详情信息
        this.setVisible(true);
    }

}
