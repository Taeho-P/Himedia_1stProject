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
import db_pkg.ChildVo;
import db_pkg.TeacherVo;

public class GraduateChild extends WindowAdapter implements ActionListener {
	private TeacherVo tInfo;

	private JFrame fGradu;
	private Label lMsg;
	private Button bGradu, btCau, btCau2;
	private DefaultTableModel model;
	private JTable tChildTable;
	private JScrollPane scrollPane;
	private CenterDAO cDAO;
	private int iCnt;
	private Dialog dCaution;
	private Font fDfont;
	private String sGraduCode, sGraduName;

	public GraduateChild(TeacherVo tInfo) {
		this.tInfo = tInfo;
		fGradu = new JFrame("졸업 아동 관리");
		fGradu.setSize(400, 400);
		fGradu.setLocationRelativeTo(null);
		fGradu.setLayout(null);
		fGradu.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fGradu.setIconImage(icon);

		lMsg = new Label("졸업 아동을 선택해주세요.");
		lMsg.setBounds(25, 15, 180, 25);

		bGradu = new Button("졸업");
		bGradu.setBounds(170, 318, 60, 30);
		bGradu.setActionCommand("gradu");
		bGradu.addActionListener(this);

		tChildTable = new JTable();

		tChildTable.setSize(100, 100);
		fGradu.add(tChildTable);
		String[] title = { "아동코드", "이름", "생년월일", "성별", "부", "모", "학교명" };

		model = new DefaultTableModel(title, 0);

		cDAO = new CenterDAO();

		ArrayList<ChildVo> cList = cDAO.childList();

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

		tChildTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		scrollPane = new JScrollPane(tChildTable);
		resizeColumnWidth(tChildTable);
		fGradu.add(scrollPane);
		scrollPane.setBounds(15, 50, 360, 260);

		fGradu.addWindowListener(this);
		fGradu.add(bGradu);
		fGradu.add(lMsg);
		fGradu.setVisible(true);

	}

	public void showNotice(String msg1, String msg2) {
		dCaution = new Dialog(fGradu, "호야 지역아동센터", true);
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

	public void showNotice2(String msg1, String msg2) {
		dCaution = new Dialog(fGradu, "호야 지역아동센터", true);
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

		btCau = new Button("예");
		btCau.setBounds(65, 100, 60, 30);
		btCau.addActionListener(this);
		btCau.setActionCommand(msg2);

		btCau2 = new Button("아니오");
		btCau2.setBounds(155, 100, 60, 30);
		btCau2.addActionListener(this);
		btCau2.setActionCommand("fail");

		dCaution.add(btCau);
		dCaution.add(btCau2);
		dCaution.add(lMsg1);
		dCaution.setVisible(true);
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

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}

		if (e.getComponent() == fGradu) {
			fGradu.dispose();
			new TeacherMain(tInfo);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("graduChild")) {
			dCaution.dispose();
			cDAO = new CenterDAO();
			String sIsGradu = cDAO.childGradu(sGraduCode);

			if (sIsGradu.equals("GraduSuccess")) {
				showNotice("아동 졸업처리가 완료되었습니다.", "fail");
				fGradu.dispose();
				new GraduateChild(tInfo);
			} else {
				showNotice("아동 졸업처리에 실패했습니다.", "fail");
			}
		}

		if (e.getActionCommand().equals("fail")) {
			dCaution.dispose();
		}
		if (e.getActionCommand().equals("gradu")) {

			boolean bOn = false;
			for (int i = 0; i < iCnt; i++) {
				bOn = tChildTable.isRowSelected(i);
				if (bOn == true) {
					sGraduCode = (String) tChildTable.getValueAt(i, 0);
					sGraduName = (String) tChildTable.getValueAt(i, 1);
					System.out.println(sGraduCode);
					System.out.println(sGraduName);
					break;
				}
			}

			if (bOn == false) {
				showNotice("졸업시킬 아동을 선택해주세요.", "fail");
				return;
			} else {
				showNotice2(sGraduName + " 아동을 졸업처리 하시겠습니까?", "graduChild");
			}

		}

	}
}
