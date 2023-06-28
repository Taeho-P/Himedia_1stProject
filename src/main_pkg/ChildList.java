package main_pkg;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
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
import db_pkg.ChildVo;
import db_pkg.TeacherVo;

public class ChildList extends WindowAdapter implements ActionListener{
	private CenterDAO cDAO = new CenterDAO();
	
	private ArrayList<ChildVo> cList;
	private ChildVo cInfo;
	private TeacherVo tInfo;
	private JFrame fChildList;
	private Label lTitle, lName;
	private TextField tfName;
	private Button btFind, btAttend, btManage, btCau;
	private DefaultTableModel model;
	private JTable tChildTable = new JTable();
	private JTable tChildTable2 = new JTable();
	private JScrollPane scrollPane = new JScrollPane(tChildTable);
	private JScrollPane scrollPane2 = new JScrollPane(tChildTable2);
	private Dialog dCaution;
	private Font fDfont;
	
	private int iCnt;
	
	public ChildList(TeacherVo tInfo) {
		this.tInfo = tInfo;
		fChildList = new JFrame("아동 관리");
		fChildList.setSize(400, 302);
		fChildList.setLocationRelativeTo(null);
		fChildList.setLayout(null);
		fChildList.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fChildList.setIconImage(icon);
		
		lTitle = new Label("아동 정보 조회");
		lTitle.setBounds(25, 15, 100, 25);
		
		lName = new Label("이름");
		lName.setBounds(25, 45, 30, 25);
		
		tfName = new TextField(20);
		tfName.setBounds(61, 48, 100, 20);
		
		btFind = new Button("조회");
		btFind.setBounds(170, 45, 50, 24);
		btFind.addActionListener(this);
		btFind.setActionCommand("find");
		
		btAttend = new Button("출결조회");
		btAttend.setBounds(92, 225, 80, 30);
		btAttend.addActionListener(this);
		btAttend.setActionCommand("attend");
		
		btManage = new Button("정보관리");
		btManage.setBounds(215, 225, 80, 30);
		btManage.addActionListener(this);
		btManage.setActionCommand("manage");
		
		setTable();
				
		fChildList.addWindowListener(this);
		fChildList.add(lTitle);
		fChildList.add(lName);
		fChildList.add(tfName);
		fChildList.add(btFind);
		fChildList.add(btAttend);
		fChildList.add(btManage);
		fChildList.setVisible(true);
	}
	
	public void setTable() {

		tChildTable.setSize(100, 100);
		String[] title = { "아동코드", "이름", "생년월일", "성별", "부", "모", "학교명" };

		model = new DefaultTableModel(title, 0);

		cList = cDAO.childList();

		iCnt = cList.size();

		for (int i = 0; i < cList.size(); i++) {
			ChildVo cInfo = cList.get(i);

			String sC_code = cInfo.getC_code();
			String sC_name = cInfo.getC_name();
			String sC_birthday = cInfo.getC_birthday();
			String sGender = cInfo.getGender();
			String sFather = cInfo.getFather();
			String sMother = cInfo.getMother();
			String sSchool = cInfo.getSchool();

			Object[] oChildInfo = { sC_code, sC_name, sC_birthday, sGender, sFather, sMother, sSchool };

			model.addRow(oChildInfo);
			System.out.println(oChildInfo);
		}

		tChildTable.setModel(model);
		fChildList.add(scrollPane);

		tChildTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

//		scrollPane = new JScrollPane(tChildTable);
		resizeColumnWidth(tChildTable);
		
		scrollPane.setBounds(15, 80, 360, 137);

	}
	
	public void setTable2(String name) {
		
		tChildTable2.setSize(100, 100);
		String[] title = { "아동코드", "이름", "생년월일", "성별", "부", "모", "학교명" };

		model = new DefaultTableModel(title, 0);

		cList = cDAO.childList2(name);

		iCnt = cList.size();

		for (int i = 0; i < cList.size(); i++) {
			ChildVo cInfo = cList.get(i);

			String sC_code = cInfo.getC_code();
			String sC_name = cInfo.getC_name();
			String sC_birthday = cInfo.getC_birthday();
			String sGender = cInfo.getGender();
			String sFather = cInfo.getFather();
			String sMother = cInfo.getMother();
			String sSchool = cInfo.getSchool();

			Object[] oChildInfo = { sC_code, sC_name, sC_birthday, sGender, sFather, sMother, sSchool };

			model.addRow(oChildInfo);
			System.out.println(oChildInfo);
		}

		tChildTable2.setModel(model);
		fChildList.add(scrollPane2);
		tChildTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		resizeColumnWidth(tChildTable2);
		
		scrollPane2.setBounds(15, 80, 360, 137);
		
	}
	
	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}
		if (e.getComponent() == fChildList) {
			new TeacherMain(tInfo);
			fChildList.dispose();
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("manage")) {
			if (tChildTable.isVisible()) {
				boolean bOn = false;
				for (int i = 0; i < iCnt; i++) {
					bOn = tChildTable.isRowSelected(i);
					if (bOn == true) {
						cInfo = cList.get(i);
						break;
					}
				}

				if (bOn == false) {
					showNotice("아동을 선택해주세요.", "fail");
					return;
				} else {
					System.out.println(cInfo.getC_name());
					fChildList.dispose();
					new ChildInfo(tInfo, cInfo);
				}
			} else {
				boolean bOn = false;
				for (int i = 0; i < iCnt; i++) {
					bOn = tChildTable2.isRowSelected(i);
					if (bOn == true) {
						cInfo = cList.get(i);
						break;
					}
				}

				if (bOn == false) {
					showNotice("아동을 선택해주세요.", "fail");
					return;
				} else {
					System.out.println(cInfo.getC_name());
					fChildList.dispose();
					new ChildInfo(tInfo, cInfo);
				}
			}
		}
		if (e.getActionCommand().equals("fail")) {
			dCaution.dispose();
		}
		if (e.getActionCommand().equals("find")) {
			tChildTable.setVisible(false);
			scrollPane.setVisible(false);
			System.out.println("조회 클릭");
			String sName = tfName.getText();
			setTable2(sName);
			tChildTable2.setVisible(true);
			scrollPane2.setVisible(true);
			tChildTable2.repaint();
			scrollPane2.repaint();
		}
		
		if (e.getActionCommand().equals("attend")) {
			if (tChildTable.isVisible()) {
				boolean bOn = false;
				for (int i = 0; i < iCnt; i++) {
					bOn = tChildTable.isRowSelected(i);
					if (bOn == true) {
						cInfo = cList.get(i);
						break;
					}
				}

				if (bOn == false) {
					showNotice("아동을 선택해주세요.", "fail");
					return;
				} else {
					System.out.println(cInfo.getC_name());
					fChildList.dispose();
					new ChildAttend(tInfo, cInfo);
				}
			} else {
				boolean bOn = false;
				for (int i = 0; i < iCnt; i++) {
					bOn = tChildTable2.isRowSelected(i);
					if (bOn == true) {
						cInfo = cList.get(i);
						break;
					}
				}

				if (bOn == false) {
					showNotice("아동을 선택해주세요.", "fail");
					return;
				} else {
					System.out.println(cInfo.getC_name());
					fChildList.dispose();
					new ChildAttend(tInfo, cInfo);
				}
			}
		}
		
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
				columnModel.getColumn(column).setPreferredWidth(width + 20);
			}
		}
		
		public void showNotice(String msg1, String msg2) {
			dCaution = new Dialog(fChildList, "호야 지역아동센터", true);
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
