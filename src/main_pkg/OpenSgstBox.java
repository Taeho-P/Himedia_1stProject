package main_pkg;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import db_pkg.CenterDAO;
import db_pkg.SgstVo;
import db_pkg.TeacherVo;

public class OpenSgstBox extends WindowAdapter implements ActionListener{

	private CenterDAO cDAO;

	private TeacherVo tInfo;
	private JFrame fSgstBox;
	private Label lSgstBox;
	private DefaultTableModel model;
	private JTable tSgstTable;
	private JScrollPane scrollPane;
	private Button bOpen, btCau;
	private Dialog dCaution;
	private Font fDfont;
	private ArrayList<SgstVo> sList;
	private String sTitle1, sBody1, sName1, sWriteDay1;
	
	private int iCnt;
	

	public OpenSgstBox(TeacherVo tInfo) {
		this.tInfo = tInfo;
		
		fSgstBox = new JFrame("건의함 열람");
		fSgstBox.setSize(400, 400);
		fSgstBox.setLocationRelativeTo(null);
		fSgstBox.setLayout(null);
		fSgstBox.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fSgstBox.setIconImage(icon);

		lSgstBox = new Label("건의함");
		lSgstBox.setBounds(25, 15, 180, 25);

		bOpen = new Button("열람하기");
		bOpen.setBounds(152, 318, 80, 30);
		bOpen.setActionCommand("open");
		bOpen.addActionListener(this);

		tSgstTable = new JTable();

		tSgstTable.setSize(100, 100);
		fSgstBox.add(tSgstTable);
		String[] title = {"게시글번호", "작성일", "이름", "제목"};

		model = new DefaultTableModel(title, 0);

		cDAO = new CenterDAO();

		sList = cDAO.sgstList();
		
		iCnt = sList.size();

		for (int i = 0; i < sList.size(); i++) {
			SgstVo sInfo = sList.get(i);
			
			String sSgstNo = sInfo.getSgst_no();
			String sWriteDay = sInfo.getWriteDay();
			String sC_name = sInfo.getC_name();
			String sTitle = sInfo.getTitle();
			String sC_code = sInfo.getC_code();
			String sBody = sInfo.getBody();
			
			

			Object[] oSgstInfo = { sSgstNo, sWriteDay, sC_name, sTitle};

			model.addRow(oSgstInfo);
			System.out.println(oSgstInfo);
		}
//		Object[] child = 
//				{"1234","박호야","1991-01-27","여","호야아빠","호야엄마","하이미디어초등학교"};
//		
//		model.addRow(child);
		tSgstTable.setModel(model);

		tSgstTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		scrollPane = new JScrollPane(tSgstTable);
		resizeColumnWidth(tSgstTable);
		fSgstBox.add(scrollPane);
		scrollPane.setBounds(15, 50, 360, 260);
		
		fSgstBox.addWindowListener(this);
		fSgstBox.add(bOpen);
		fSgstBox.add(lSgstBox);
		fSgstBox.setVisible(true);
	}

	// 셀 내용에 맞춰서 셀너비를 조절해주는 메소드
	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width + 25);
		}
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}
		
		if (e.getComponent() == fSgstBox) {
			fSgstBox.dispose();
			new TeacherMain(tInfo);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("fail")) {
			dCaution.dispose();
		}
		if (e.getActionCommand().equals("open")) {

			boolean bOn = false;
			for (int i = 0; i < iCnt; i++) {
				bOn = tSgstTable.isRowSelected(i);
				if (bOn == true) {
					SgstVo sgstVo = sList.get(i);
					
					sName1 = (String) tSgstTable.getValueAt(i, 2);
					sWriteDay1 = (String) tSgstTable.getValueAt(i, 1);
					sTitle1 = (String) tSgstTable.getValueAt(i, 3);
					sBody1 = sgstVo.getBody();
					System.out.println(sTitle1);
					System.out.println(sBody1);
					break;
				}
			}

			if (bOn == false) {
				showNotice("게시물을 선택해주세요.", "fail");
				return;
			} else {
				new OpenOne(tInfo, sName1, sWriteDay1, sTitle1, sBody1);
				fSgstBox.dispose();
			}
		}
	}
	
	public void showNotice(String msg1, String msg2) {
		dCaution = new Dialog(fSgstBox, "호야 지역아동센터", true);
		dCaution.setSize(280, 170);
		dCaution.setLocationRelativeTo(null);
		dCaution.setLayout(null);
		dCaution.addWindowListener(this);
		dCaution.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		dCaution.setIconImage(icon);

		fDfont = new Font("SansSerif", Font.PLAIN, 12);

		JLabel lMsg1 = new JLabel(msg1);
		lMsg1.setSize(280, 50);
		lMsg1.setFont(fDfont);
		lMsg1.setLocation(0, 45);
		lMsg1.setHorizontalAlignment(JLabel.CENTER);

		btCau = new Button("확인");
		btCau.setBounds(115, 100, 50, 30);
		btCau.addActionListener(this);
		btCau.setActionCommand(msg2);

		dCaution.add(btCau);
		dCaution.add(lMsg1);
		dCaution.setVisible(true);
	}

}
