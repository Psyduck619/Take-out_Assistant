package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanRecommend;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmShowRecommend extends JFrame {

    private static final long serialVersionUID = 1L;
    //我的优惠券表构造
    private static Object tblRecommendTitle[] = BeanRecommend.tblRecommendTitle;
    private static Object tblRecommendData[][];
    private static DefaultTableModel tabRecommendModel = new DefaultTableModel();
    private static JTable dataTableRecommend = new JTable(tabRecommendModel);

    //初始化信息
    private BeanUser curUser = currentLoginUser;
    private List<BeanRecommend> allRecommend = null;
    //显示所有我的优惠券
    private void reloadRecommendTable(){
        try {
            allRecommend = TakeoutAssistantUtil.orderInfoManager.Recommend(curUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblRecommendData = new Object[allRecommend.size()][BeanRecommend.tblRecommendTitle.length];
        for(int i = 0 ; i < allRecommend.size() ; i++){
            for(int j = 0 ; j < BeanRecommend.tblRecommendTitle.length ; j++)
                tblRecommendData[i][j] = allRecommend.get(i).getCell(j);
        }
        tabRecommendModel.setDataVector(tblRecommendData,tblRecommendTitle);
        this.dataTableRecommend.validate();
        this.dataTableRecommend.repaint();
    }

    //主界面
    public FrmShowRecommend(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("商品推荐");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableRecommend), BorderLayout.CENTER);

        this.reloadRecommendTable();  //加载信息
        this.setVisible(true);
    }

}
