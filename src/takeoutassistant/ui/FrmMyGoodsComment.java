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
    //��Ʒ���۹���ť
    private JPanel menuBar = new JPanel();
    private JButton btnDelete = new JButton("ɾ������");

    //��Ʒ���۱���
    private static Object tblGoodsCommentTitle[] = BeanGoodsComment.tblGoodsCommentTitle;
    private static Object tblGoodsCommentData[][];
    private static DefaultTableModel tabGoodsCommentModel = new DefaultTableModel();
    private static JTable dataTableGoodsComment = new JTable(tabGoodsCommentModel);

    //��ʼ����Ϣ
    public static BeanGoodsComment curGoodsComment = null;
    public static List<BeanGoodsComment> allGoodsComment = null;
    //��ʾ��������
    private void reloadGoodsCommentTable(){
        try {
            allGoodsComment = TakeoutAssistantUtil.goodsCommentManager.loadAll(currentLoginUser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
    //������
    public FrmMyGoodsComment(){
        //���ô�����Ϣ
        this.setExtendedState(Frame.NORMAL);
        this.setSize(800,400);
        this.setTitle("�ҵ���Ʒ����");
        // ���ھ���
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        //�ܰ�ť���.���ü���
        menuBar.add(btnDelete); this.btnDelete.addActionListener(this);
        //�˵���
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(menuBar,BorderLayout.NORTH);
        //������Ϣ��ʾ
        this.getContentPane().add(new JScrollPane(this.dataTableGoodsComment), BorderLayout.CENTER);
        this.dataTableGoodsComment.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {  //�õ�Ŀǰѡ�����Ʒ����
                int i = FrmMyGoodsComment.this.dataTableGoodsComment.getSelectedRow();
                if(i < 0) {
                    return;
                }
                curGoodsComment = allGoodsComment.get(i);
                //FrmMain_Rider.this.reloadRiderAccountTabel(i);
            }
        });
        this.reloadGoodsCommentTable();  //����������Ϣ
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //ɾ������
        if(e.getSource() == this.btnDelete){
            if(curGoodsComment == null){
                JOptionPane.showMessageDialog(null, "��ѡ��һ������", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                TakeoutAssistantUtil.goodsCommentManager.deleteComment(curGoodsComment);
                JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                reloadGoodsCommentTable();
            } catch (BaseException baseException) {
                baseException.printStackTrace();
            }
        }

    }

}
