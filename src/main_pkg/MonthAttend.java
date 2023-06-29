package main_pkg;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
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

public class MonthAttend extends WindowAdapter implements ActionListener {

	private ArrayList<ChildVo> cList;
	private CenterDAO cDAO;
	private TeacherVo tInfo;
	private JFrame fMonthAttend;
	private Choice cYear, cMonth;
	private Button btSearch, btOk, btToday, btCau;
	private DefaultTableModel model;
	private int iCnt;
	private LocalDateTime now;
	private String sNowY, sNowM, sNowD;
	private JTable tAttend1 = new JTable();
	private JTable tAttend2 = new JTable();

	private JScrollPane scrollPane1 = new JScrollPane(tAttend1);
	private JScrollPane scrollPane2 = new JScrollPane(tAttend2);

	private Dialog dCaution;
	private Font fDfont;

	public MonthAttend(TeacherVo tInfo) {
		this.tInfo = tInfo;
		cDAO = new CenterDAO();
		now = LocalDateTime.now();
		sNowY = now.format(DateTimeFormatter.ofPattern("yyyy"));
		sNowM = now.format(DateTimeFormatter.ofPattern("MM"));
		sNowD = now.format(DateTimeFormatter.ofPattern("dd"));

		fMonthAttend = new JFrame("월별 출결 조회");
		fMonthAttend.setSize(600, 400);
		fMonthAttend.setLocationRelativeTo(null);
		fMonthAttend.setLayout(null);
		fMonthAttend.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fMonthAttend.setIconImage(icon);

		cYear = new Choice();
		for (int i = 2000; i <= 2023; i++) {
			cYear.add(Integer.toString(i));
		}
		cYear.setBounds(435, 17, 55, 30);

		cMonth = new Choice();
		for (int i = 1; i <= 12; i++) {
			cMonth.add(String.format("%02d", i));
		}
		cMonth.setBounds(495, 17, 50, 30);

		cYear.select(sNowY);
		cMonth.select(sNowM);

		iCnt = cntDay();

		for (int i = 1; i <= iCnt; i++) {
			System.out.println(String.format("%02d", i));
		}

		setTable();

		btSearch = new Button("조회하기");
		btSearch.setBounds(195, 310, 70, 30);
		btSearch.addActionListener(this);
		btSearch.setActionCommand("search");

		btOk = new Button("확인");
		btOk.setBounds(310, 310, 70, 30);
		btOk.addActionListener(this);
		btOk.setActionCommand("back");

		btToday = new Button("금일 출결체크");
		btToday.setBounds(20, 15, 90, 25);
		btToday.addActionListener(this);
		btToday.setActionCommand("attendCheck");

		
		fMonthAttend.addWindowListener(this);
		fMonthAttend.add(btSearch);
		fMonthAttend.add(btOk);
		fMonthAttend.add(btToday);
		fMonthAttend.add(cYear);
		fMonthAttend.add(cMonth);
		fMonthAttend.setVisible(true);
	}

	public void setTable() {

		tAttend1.setSize(100, 100);
		String[] title = new String[iCnt + 1];
		Object[] oChildAttend = new Object[iCnt + 1];

		title[0] = "이름";
		for (int i = 1; i <= iCnt; i++) {
			String sDay = String.format("%02d", i);
			title[i] = cMonth.getSelectedItem() + "/" + sDay;
		}

		model = new DefaultTableModel(title, 0);

		cList = cDAO.childList();

		for (int i = 0; i < cList.size(); i++) {

			String sC_code = cList.get(i).getC_code();
			String sCode_date = "%" + cYear.getSelectedItem() + cMonth.getSelectedItem();

			oChildAttend[0] = cList.get(i).getC_name();
			ArrayList<ChildAttendVo> aCAList = cDAO.childAttendList(sC_code, sCode_date);

			for (int j = 0; j < iCnt; j++) {
				try {
					ChildAttendVo cA = aCAList.get(j);
					String sWday = cA.getCode_date().substring(cA.getCode_date().length() - 2,
							cA.getCode_date().length());
					int iWday = Integer.parseInt(sWday);
					oChildAttend[iWday] = cA.getAttend();
					System.out.println(oChildAttend[iWday]);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}

			}
			model.addRow(oChildAttend);
			for (int k = 0; k < oChildAttend.length; k++) {
				oChildAttend[k] = null;
			}
		}

		tAttend1.setModel(model);
		fMonthAttend.add(scrollPane1);

		tAttend1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		resizeColumnWidth(tAttend1);

		scrollPane1.setBounds(18, 50, 550, 250);

	}

	public void setTable2() {

		tAttend2.setSize(100, 100);
		String[] title = new String[iCnt + 1];
		Object[] oChildAttend = new Object[iCnt + 1];

		title[0] = "이름";
		for (int i = 1; i <= iCnt; i++) {
			String sDay = String.format("%02d", i);
			title[i] = cMonth.getSelectedItem() + "/" + sDay;
		}

		model = new DefaultTableModel(title, 0);

		cList = cDAO.childList();

		for (int i = 0; i < cList.size(); i++) {

			String sC_code = cList.get(i).getC_code();
			String sCode_date = "%" + cYear.getSelectedItem() + cMonth.getSelectedItem();

			oChildAttend[0] = cList.get(i).getC_name();
			ArrayList<ChildAttendVo> aCAList = cDAO.childAttendList(sC_code, sCode_date);

			for (int j = 0; j < iCnt; j++) {
				try {
					ChildAttendVo cA = aCAList.get(j);
					String sWday = cA.getCode_date().substring(cA.getCode_date().length() - 2,
							cA.getCode_date().length());
					int iWday = Integer.parseInt(sWday);
					oChildAttend[iWday] = cA.getAttend();
					System.out.println(oChildAttend[iWday]);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}

			}
			model.addRow(oChildAttend);
			for (int k = 0; k < oChildAttend.length; k++) {
				oChildAttend[k] = null;
			}
		}

		tAttend2.setModel(model);
		fMonthAttend.add(scrollPane2);

		tAttend2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		resizeColumnWidth(tAttend2);

		scrollPane2.setBounds(18, 50, 550, 250);

	}

	public int cntDay() {
		boolean bLeapYear;

		int iHYear = Integer.parseInt(cYear.getSelectedItem());

		int iY4 = iHYear % 4;
		int iY100 = iHYear % 100;
		int iY400 = iHYear % 400;

		if (iY4 == 0 && !(iY100 == 0) || iY400 == 0) {
			bLeapYear = true;
		} else {
			bLeapYear = false;
		}

		int iM = Integer.parseInt(cMonth.getSelectedItem());

		if (bLeapYear && iM == 2) {
			return 29;
		} else if (iM == 2) {
			return 28;
		} else if (iM == 4 || iM == 6 || iM == 9 || iM == 11) {
			return 30;
		} else {
			return 31;
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
			columnModel.getColumn(column).setPreferredWidth(width + 15);
		}
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}

		if (e.getComponent() == fMonthAttend) {
			new TeacherMain(tInfo);
			fMonthAttend.dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("checkDone")) {
			dCaution.dispose();
		}

		if (e.getActionCommand().equals("attendCheck")) {
			System.out.println("연 = " + sNowY);
			System.out.println("월 = " + sNowM);
			System.out.println("일 = " + sNowD);
			String sToday = sNowY + sNowM + sNowD;
			ArrayList<ChildAttendVo> todayList = cDAO.todayCheck(sToday);
			for (int i = 0; i < todayList.size(); i++) {
				ChildAttendVo cav = todayList.get(i);
				if (cav.getCenter_in() == null) {
					String sCodedate = cav.getCode_date();
					cDAO.todayCheckDone(sCodedate, "결석");
					System.out.println("결석");
				} else {
					String sCodedate = cav.getCode_date();
					cDAO.todayCheckDone(sCodedate, "출석");
					System.out.println("출석");
				}
			}
			showNotice("오늘의 출석 체크가 완료됐습니다.", "checkDone");
		}

		if (e.getActionCommand().equals("back")) {
			new TeacherMain(tInfo);
			fMonthAttend.dispose();
		}

		if (e.getActionCommand().equals("search")) {
			tAttend1.setVisible(false);
			scrollPane1.setVisible(false);
			iCnt = cntDay();
			setTable2();
			tAttend2.setVisible(true);
			scrollPane2.setVisible(true);
		}

	}

	public void showNotice(String msg1, String msg2) {
		dCaution = new Dialog(fMonthAttend, "호야 지역아동센터", true);
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
