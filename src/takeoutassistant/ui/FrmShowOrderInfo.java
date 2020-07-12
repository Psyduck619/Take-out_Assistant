package takeoutassistant.ui;

import takeoutassistant.TakeoutAssistantUtil;
import takeoutassistant.model.BeanOrderInfo;
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
import static takeoutassistant.ui.FrmConfirm.confirm;
import static takeoutassistant.ui.FrmMyOrder.curOrder;

public class FrmShowOrderInfo extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //��������ť
    private JPanel menuBar = new JPanel();
    private JButton btnGoodsComment = new JButton("������Ʒ");

    //�û���������
    private static Object tblOrderInfoTitle[] = BeanOrderInfo.tblMyCartTitle;
    private static Object tblOrderInfoData[][];
    private static DefaultTableModel tabOrderInfoModel = new DefaultTableModel();
    private static JTable dataTableOrderInfo = new JTable(tabOrderInfoModel);

    //��ʼ����Ϣ
    public static BeanOrderInfo curOrderInfo = null;
    public static List<BeanOrderInfo> allOrderInfo = null;
    //��ʾ���е�ַ
    private void reloadOrderInfoTable(){
        try {
            allOrderInfo = TakeoutAssistantUtil.orderInfoManager.loadMyOrderInfo(curOrder);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblOrderInfoData = new Object[allOrderInfo.size()][BeanOrderInfo.tblMyCartTitle.length];
        for(int i = 0 ; i < allOrderInfo.size() ; i++){
            for(int j = 0 ; j < BeanOrderInfo.tblMyCartTitle.length ; j++)
                tblOrderInfoData[i][j] = allOrderInfo.get(i).getCell(j);
        }
        tabOrderInfoModel.setDataVector(tblOrderInfoData,tblOrderInfoTitle);
        this.dataTableOrderInfo.validate();
        this.dataTableOrderInfo.repaint();
    }
    //������
    public FrmShowOrderInfo(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(800,400);
        this.setTitle("���������");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //������ܰ�ť���.���ü���
        menuBar.add(btnGoodsComment); this.btnGoodsComment.addActionListener(this);
        //�˵���
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //������Ϣ��ʾ
        this.getContentPane().add(new JScrollPane(this.dataTableOrderInfo), BorderLayout.CENTER);
        this.dataTableOrderInfo.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�õ�Ŀǰѡ��Ķ�������
                int i = FrmShowOrderInfo.this.dataTableOrderInfo.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curOrderInfo = allOrderInfo.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });
        this.reloadOrderInfoTable();  //���ض���������Ϣ
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //������Ʒ
        if(e.getSource() == this.btnGoodsComment){
            if(curOrderInfo == null){
                JOptionPane.showMessageDialog(null, "��ѡ��һ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!curOrder.getOrder_state().equals("���ʹ�")){
                JOptionPane.showMessageDialog(null, "����δ���,�޷�������Ʒ", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //�ж��Ƿ�������
            try {
                boolean flag = TakeoutAssistantUtil.goodsCommentManager.isComment(curOrderInfo);
                if(flag){
                    JOptionPane.showMessageDialog(null, "����Ʒ�����ۻ������ѱ�ɾ��", "����",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
            FrmAddGoodsComment dlg = new FrmAddGoodsComment(this,"��Ʒ����",true);
            dlg.setVisible(true);
            reloadOrderInfoTable();
        }

    }

}
