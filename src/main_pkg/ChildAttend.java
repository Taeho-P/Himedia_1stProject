package main_pkg;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import db_pkg.ChildAttendVo;
import db_pkg.ChildVo;
import db_pkg.TeacherVo;

public class ChildAttend extends WindowAdapter implements ActionListener {
	private CenterDAO cDAO = new CenterDAO();
	private ChildVo cInfo;
	private TeacherVo tInfo;
	private ArrayList<ChildAttendVo> cAList;
	private LocalDateTime now;
	private JFrame fChildAttend;
	private Label lName;
	private Choice cAttendY, cAttendM;
	private Button btSearch, btManage, btCau;
	private Dialog dCaution;
	private Font fDfont;

	private JTable tAttend1 = new JTable();
	private JTable tAttend2 = new JTable();

	private JScrollPane scrollPane1 = new JScrollPane(tAttend1);
	private JScrollPane scrollPane2 = new JScrollPane(tAttend2);

	private DefaultTableModel model;

	private String sNow, sNowY, sNowM;
	private int iCnt;

	public ChildAttend(TeacherVo tInfo, ChildVo cInfo) {
		this.tInfo = tInfo;
		this.cInfo = cInfo;
		now = LocalDateTime.now();
		sNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		sNowY = now.format(DateTimeFormatter.ofPattern("yyyy"));
		sNowM = now.format(DateTimeFormatter.ofPattern("MM"));

		fChildAttend = new JFrame("출결 조회");
		fChildAttend.setSize(400, 350);
		fChildAttend.setLocationRelativeTo(null);
		fChildAttend.setLayout(null);
		fChildAttend.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fChildAttend.setIconImage(icon);

		lName = new Label(cInfo.getC_name());
		lName.setBounds(20, 20, 100, 25);
		cAttendY = new Choice();
		for (int i = 2010; i <= 2023; i++) {
			cAttendY.add(Integer.toString(i));
		}
		cAttendY.setBounds(248, 22, 55, 25);

		cAttendM = new Choice();
		for (int i = 1; i <= 12; i++) {
			cAttendM.add(String.format("%02d", i));
		}
		cAttendM.setBounds(307, 22, 45, 25);

		cAttendY.select(sNowY);
		cAttendM.select(sNowM);

		setTable(cInfo.getC_code());

		btSearch = new Button("출석조회");
		btSearch.setBounds(100, 271, 70, 30);
		btSearch.addActionListener(this);
		btSearch.setActionCommand("search");

		btManage = new Button("수정하기");
		btManage.setBounds(210, 271, 70, 30);
		btManage.addActionListener(this);
		btManage.setActionCommand("update");

		fChildAttend.addWindowListener(this);
		fChildAttend.add(btSearch);
		fChildAttend.add(btManage);
		fChildAttend.add(cAttendY);
		fChildAttend.add(cAttendM);
		fChildAttend.add(lName);
		fChildAttend.setVisible(true);
	}

	public void setTable(String c_code) {
		String sCode_date = c_code + cAttendY.getSelectedItem() + cAttendM.getSelectedItem();

		tAttend1.setSize(100, 100);
		String[] title = { "날짜", "요일", "등원", "하원", "출결", "사유" };

		model = new DefaultTableModel(title, 0);

		cAList = cDAO.childAttendList(c_code, sCode_date);

		iCnt = cAList.size();

		for (int i = 0; i < cAList.size(); i++) {
			ChildAttendVo cAInfo = cAList.get(i);
			String sCodeDate = cAInfo.getCode_date();

			String sDate = sCodeDate.substring(sCodeDate.length() - 8, sCodeDate.length() - 4) + "-" + sCodeDate.substring(sCodeDate.length() - 4, sCodeDate.length() - 2) + "-"
					+ sCodeDate.substring(sCodeDate.length() - 2, sCodeDate.length());
			String sAttend = cAInfo.getAttend();
			String sCenter_in = cAInfo.getCenter_in();
			String sCenter_out = cAInfo.getCenter_out();
			String sReason = cAInfo.getReason();
			String sAttend_day = cAInfo.getAttend_day();

			Object[] oChildInfo = { sDate, sAttend_day, sCenter_in, sCenter_out, sAttend, sReason };

			model.addRow(oChildInfo);
			System.out.println(oChildInfo);
		}

		tAttend1.setModel(model);
		fChildAttend.add(scrollPane1);

		tAttend1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		resizeColumnWidth(tAttend1);

		scrollPane1.setBounds(15, 55, 360, 210);

	}

	public void setTable2(String c_code) {
		String sCode_date = c_code + cAttendY.getSelectedItem() + cAttendM.getSelectedItem();

		tAttend2.setSize(100, 100);
		String[] title = { "날짜", "요일", "등원", "하원", "출결", "사유" };

		model = new DefaultTableModel(title, 0);

		cAList = cDAO.childAttendList(c_code, sCode_date);

		iCnt = cAList.size();

		for (int i = 0; i < cAList.size(); i++) {
			ChildAttendVo cAInfo = cAList.get(i);
			String sCodeDate = cAInfo.getCode_date();

			String sDate = sCodeDate.substring(sCodeDate.length() - 8, sCodeDate.length() - 4) + "-" + sCodeDate.substring(sCodeDate.length() - 4, sCodeDate.length() - 2) + "-"
					+ sCodeDate.substring(sCodeDate.length() - 2, sCodeDate.length());
			String sAttend = cAInfo.getAttend();
			String sCenter_in = cAInfo.getCenter_in();
			String sCenter_out = cAInfo.getCenter_out();
			String sReason = cAInfo.getReason();
			String sAttend_day = cAInfo.getAttend_day();

			Object[] oChildInfo = { sDate, sAttend_day, sCenter_in, sCenter_out, sAttend, sReason };

			model.addRow(oChildInfo);
			System.out.println(oChildInfo);
		}

		tAttend2.setModel(model);
		fChildAttend.add(scrollPane2);

		tAttend2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		resizeColumnWidth(tAttend2);

		scrollPane2.setBounds(15, 55, 360, 210);

	}

	// 셀 너비를 자동으로 맞춰주는 메소드
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("success")) {
			dCaution.dispose();
			fChildAttend.dispose();
			new ChildAttend(tInfo, cInfo);
		}
		if (e.getActionCommand().equals("fail")) {
			dCaution.dispose();
		}
		if (e.getActionCommand().equals("search")) {
			System.out.println("조회 클릭");
			tAttend1.setVisible(false);
			scrollPane1.setVisible(false);
			setTable2(cInfo.getC_code());
			tAttend2.setVisible(true);
			scrollPane2.setVisible(true);
		}

		if (e.getActionCommand().equals("update")) {
			System.out.println("수정 클릭");
			boolean bOn = false;
			String sAttend = null;
			String sReason = null;
			String sCode_date = null;
			String sIsUpdate = null;
			if (tAttend1.isVisible()) {
				for (int i = 0; i < cAList.size(); i++) {
					bOn = tAttend1.isRowSelected(i);
					if (bOn == true) {
						sAttend = (String) tAttend1.getValueAt(i, 4);
						sReason = (String) tAttend1.getValueAt(i, 5);
						sCode_date = cAList.get(i).getCode_date();
						System.out.println("수정 할 출석코드" + sCode_date);
						System.out.println("수정 확인" + sAttend);
						System.out.println("수정 확인" + sReason);
						sIsUpdate = cDAO.modifyAttend(sCode_date, sAttend, sReason);
						break;
					}
				}
				if (bOn == false) {
					showNotice("날짜를 선택해주세요.", "fail");
					return;
				}
				if (sIsUpdate.equals("modifySuccess")) {
					showNotice("수정이 완료됐습니다.", "success");
				} else {
					showNotice("수정이 완료됐습니다.", "fail");
				}
			} else {
				for (int i = 0; i < cAList.size(); i++) {
					bOn = tAttend2.isRowSelected(i);
					if (bOn == true) {
						sAttend = (String) tAttend2.getValueAt(i, 4);
						sReason = (String) tAttend2.getValueAt(i, 5);
						sCode_date = cAList.get(i).getCode_date();
						System.out.println("수정 할 출석코드" + sCode_date);
						System.out.println("수정 확인" + sAttend);
						System.out.println("수정 확인" + sReason);
						sIsUpdate = cDAO.modifyAttend(sCode_date, sAttend, sReason);
						break;
					}
				}
				if (bOn == false) {
					showNotice("날짜를 선택해주세요.", "fail");
					return;
				}
				if (sIsUpdate.equals("modifySuccess")) {
					showNotice("수정이 완료됐습니다.", "success");
				} else {
					showNotice("수정이 완료됐습니다.", "fail");
				}
			}
		}
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == fChildAttend) {
			new ChildList(tInfo);
			fChildAttend.dispose();
		}
	}

	public void showNotice(String msg1, String msg2) {
		dCaution = new Dialog(fChildAttend, "호야 지역아동센터", true);
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
