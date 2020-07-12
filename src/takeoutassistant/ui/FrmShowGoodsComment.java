package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanGoodsComment;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;
import static takeoutassistant.ui.FrmShowSeller.curGoods;

public class FrmShowGoodsComment extends JFrame {

    private static final long serialVersionUID = 1L;
    //商品评价表构造
    private static Object tblGoodsCommentTitle[] = BeanGoodsComment.tblGoodsCommentTitle2;
    private static Object tblGoodsCommentData[][];
    private static DefaultTableModel tabGoodsCommentModel = new DefaultTableModel();
    private static JTable dataTableGoodsComment = new JTable(tabGoodsCommentModel);

    //初始化信息
    private BeanUser curUser = currentLoginUser;
    private List<BeanGoodsComment> allGoodsComment = null;
    //显示所有商品评价
    private void reloadGoodsCommentTable(){
        try {
            allGoodsComment = TakeoutAssistantUtil.goodsCommentManager.loadGoods(curGoods);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsCommentData = new Object[allGoodsComment.size()][BeanGoodsComment.tblGoodsCommentTitle2.length];
        for(int i = 0 ; i < allGoodsComment.size() ; i++){
            for(int j = 0 ; j < BeanGoodsComment.tblGoodsCommentTitle2.length ; j++)
                tblGoodsCommentData[i][j] = allGoodsComment.get(i).getCell2(j);
        }
        tabGoodsCommentModel.setDataVector(tblGoodsCommentData,tblGoodsCommentTitle);
        this.dataTableGoodsComment.validate();
        this.dataTableGoodsComment.repaint();
    }

    //主界面
    public FrmShowGoodsComment(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(700,400);
        this.setTitle("商品评价");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableGoodsComment), BorderLayout.CENTER);

        this.reloadGoodsCommentTable();  //加载信息
        this.setVisible(true);
    }

}
