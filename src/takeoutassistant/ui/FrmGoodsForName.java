package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static takeoutassistant.model.BeanUser.currentLoginUser;

public class FrmGoodsForName extends JFrame {

    private static final long serialVersionUID = 1L;
    //商品表构造
    private static Object tblGoodsTitle[] = BeanGoods.tblGoodsTitle3;
    private static Object tblGoodsData[][];
    private static DefaultTableModel tabGoodsModel = new DefaultTableModel();
    private static JTable dataTableGoods = new JTable(tabGoodsModel);

    //初始化信息
    private BeanUser curUser = currentLoginUser;
    private List<BeanGoods> allGoods = null;
    //显示
    private void reloadGoodsTable(){
        try {
            allGoods = TakeoutAssistantUtil.goodsManager.loadForName(FrmPutGoodsName.name);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[allGoods.size()][BeanGoods.tblGoodsTitle3.length];
        for(int i = 0 ; i < allGoods.size() ; i++){
            for(int j = 0 ; j < BeanGoods.tblGoodsTitle3.length ; j++)
                tblGoodsData[i][j] = allGoods.get(i).getCell3(j);
        }
        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    //主界面
    public FrmGoodsForName(){
        //设置窗口信息
        this.setExtendedState(Frame.NORMAL);
        this.setSize(900,500);
        this.setTitle("查询结果");
        // 窗口居中
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);

        this.reloadGoodsTable();  //加载信息
        this.setVisible(true);
    }

}
