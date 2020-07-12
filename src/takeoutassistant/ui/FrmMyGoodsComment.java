package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanGoodsComment;
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
import static takeoutassistant.ui.FrmMyOrder.curOrder;

public class FrmMyGoodsComment extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //商品评论管理按钮
    private JPanel menuBar = new JPanel();
    private JButton btnDelete = new JButton("删除评论");

    //商品评价表构造
    private static Object tblGoodsCommentTitle[] = BeanGoodsComment.tblGoodsCommentTitle;
    private static Object tblGoodsCommentData[][];
    private static DefaultTableModel tabGoodsCommentModel = new DefaultTableModel();
    private static JTable dataTableGoodsComment = new JTable(tabGoodsCommentModel);

    //初始化信息
    public static BeanGoodsComment curGoodsComment = null;
    public static List<BeanGoodsComment> allGoodsComment = null;
    //显示所有评价
    private void reloadGoodsCommentTable(){
        try {
            allGoodsComment = TakeoutAssistantUtil.goodsCommentManager.loadAll(currentLoginUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsCommentData = new Object[allGoodsComment.size()][BeanGoodsComment.tblGoodsCommentTitle.length];
        for(int i = 0 ; i < allGoodsComment.size() ; i++){
            for(int j = 0 ; j < BeanGoodsComment.tblGoodsCommentTitle.length ; j++)
                tblGoodsCommentData[i][j] = allGoodsComment.get(i).getCell(j);
        }
        tabGoodsCommentModel.setDataVector(tblGoodsCommentData,tblGoodsCommentTitle);
        this.dataTableGoodsComment.validate();
        this.dataTableGoodsComment.repaint();
    }
    //主界面
    public FrmMyGoodsComment(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(800,400);
        this.setTitle("我的商品评价");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //能按钮添加.设置监听
        menuBar.add(btnDelete); this.btnDelete.addActionListener(this);
        //菜单栏
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //评价信息显示
        this.getContentPane().add(new JScrollPane(this.dataTableGoodsComment), BorderLayout.CENTER);
        this.dataTableGoodsComment.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //得到目前选择的商品评价
                int i = FrmMyGoodsComment.this.dataTableGoodsComment.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curGoodsComment = allGoodsComment.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });
        this.reloadGoodsCommentTable();  //加载详情信息
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //删除评价
        if(e.getSource() == this.btnDelete){
            if(curGoodsComment == null){
                JOptionPane.showMessageDialog(null, "请选择一个评论", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.goodsCommentManager.deleteComment(curGoodsComment);
                JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                reloadGoodsCommentTable();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
        }

    }

}
