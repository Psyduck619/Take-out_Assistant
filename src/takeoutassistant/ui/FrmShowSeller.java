package takeoutassistant.ui;

import com.sun.deploy.security.WIExplorerMyKeyStore;
import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanGoods;
import takeoutassistant.model.BeanGoodsType;
import takeoutassistant.model.BeanSeller;
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
import static takeoutassistant.ui.FrmMain_user.curSeller;

public class FrmShowSeller extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //������������ť
    private JPanel  menuBar1 = new JPanel();
    private JPanel  menuBar2 = new JPanel();
    private JPanel  menuBarX = new JPanel();
    private JButton btManjian = new JButton("�̼�����");
    private JButton btCoupon = new JButton("�̼��Ż�ȯ");
    private JButton btMyCoupon = new JButton("�ҵ��Ż�ȯ");
    private JButton btAddCart = new JButton("���빺�ﳵ");
    private JButton btMyCart = new JButton("�ҵĹ��ﳵ");
    private JButton btBuy = new JButton("����");

    //�˵���
    private JPanel menuBar = new JPanel();
    //״̬��
    private JPanel statusBar = new JPanel();
    //��Ʒ������
    private Object tblGTypeTitle[] = BeanGoodsType.tblGTypeTitle;
    private Object tblGTypeData[][];
    DefaultTableModel tabGTypeModel = new DefaultTableModel();
    private JTable dataTableGType = new JTable(tabGTypeModel);
    //��Ʒ����
    private Object tblGoodsTitle[] = BeanGoods.tblGoodsTitle;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel = new DefaultTableModel();
    private JTable dataTableGoods = new JTable(tabGoodsModel);

    //��ʼ����Ϣ
    private BeanGoodsType curType = null;
    List<BeanGoodsType> allType = null;
    private BeanGoods curGoods = null;
    List<BeanGoods> allGoods = null;
    //��ʾ������Ʒ����
    private void reloadGTypeTabel(){
        try {
            allType = TakeoutAssistantUtil.goodsTypeManager.loadTypes(curSeller);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGTypeData = new Object[allType.size()][BeanGoodsType.tblGTypeTitle.length];
        for(int i = 0 ; i < allType.size() ; i++){
            for(int j = 0 ; j < BeanGoodsType.tblGTypeTitle.length ; j++)
                tblGTypeData[i][j] = allType.get(i).getCell(j);
        }

        tabGTypeModel.setDataVector(tblGTypeData,tblGTypeTitle);
        this.dataTableGType.validate();
        this.dataTableGType.repaint();
    }
    //��ʾ������Ʒ
    private void reloadGoodsTabel(int typeIdx){
        if(typeIdx <= 0) {
            while(tabGoodsModel.getRowCount() > 0){
                tabGoodsModel.removeRow(tabGoodsModel.getRowCount()-1);
            }
            return;
        }
        curType = allType.get(typeIdx);
        try {
            allGoods = TakeoutAssistantUtil.goodsManager.loadGoods(curType);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[allGoods.size()][BeanGoods.tblGoodsTitle.length];
        for(int i = 0 ; i < allGoods.size() ; i++){
            for(int j = 0 ; j < BeanGoods.tblGoodsTitle.length ; j++)
                tblGoodsData[i][j] = allGoods.get(i).getCell(j);
        }

        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    //������
    public FrmShowSeller(){
        //�п�
        if(curSeller == null){
            JOptionPane.showMessageDialog(null, "��ǰѡ���̼Ҵ���,������", "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(1000,600);
        String name = curSeller.getSeller_name();
        this.setTitle(name);
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //���ܲ˵�1��ť����
        menuBar1.add(btManjian);
        menuBar1.add(btCoupon);
        menuBar1.add(btMyCoupon);
        this.btManjian.addActionListener(this);
        this.btCoupon.addActionListener(this);
        this.btMyCoupon.addActionListener(this);
        //���ܲ˵�2��ť����
        menuBar2.add(btMyCart);
        menuBar2.add(btAddCart);
        menuBar2.add(btBuy);
        this.btMyCart.addActionListener(this);
        this.btAddCart.addActionListener(this);
        this.btBuy.addActionListener(this);
        //�˵���
        menuBar.add(menuBar1,BorderLayout.WEST);
        menuBar.add(menuBarX,BorderLayout.CENTER);
        menuBar.add(menuBar2,BorderLayout.EAST);
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //�����沼��
        JScrollPane js1 = new JScrollPane(this.dataTableGType);
        js1.setPreferredSize(new Dimension(300, 10));
        JScrollPane js2 = new JScrollPane(this.dataTableGoods);
        js2.setPreferredSize(new Dimension(400, 10));
        //��Ʒ�������
        this.getContentPane().add(js1, BorderLayout.WEST);
        this.dataTableGType.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {  //�����ʾѡ����Ϣ
                int i = FrmShowSeller.this.dataTableGType.getSelectedRow();
                if(i < 0) {
                    return;
                }
                FrmShowSeller.this.reloadGoodsTabel(i);
            }
        });
        //��Ʒ����
        this.getContentPane().add(js2, BorderLayout.CENTER);
        this.dataTableGoods.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = FrmShowSeller.this.dataTableGoods.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curGoods = allGoods.get(i);
            }
        });

        this.reloadGTypeTabel();  //���������Ϣ
        this.reloadGoodsTabel(0);  //����������Ϣ
        //״̬��
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String sellerName = curSeller.getSeller_name();
        JLabel label=new JLabel("����!��ӭ����" + sellerName + "!");
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //��ʾ�̼��Ż�ȯ
        if(e.getSource() == btManjian){
            new FrmShowSellerManjian();
        }
        //��ʾ�̼��Ż�ȯ
        else if(e.getSource() == btCoupon){
            new FrmShowSellerCoupon();
        }
        //��ʾ�ҵ��Ż�ȯ
        else if(e.getSource() == btMyCoupon){
            new FrmShowMyCoupon2();
        }

    }
}
