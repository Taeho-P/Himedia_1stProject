package join_pkg;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import db_pkg.CenterDAO;

public class TeacherJoin extends WindowAdapter implements ActionListener {
	private JFrame fTJoin;
	private Label lId, lPw, lPwCheck, lName, lGender, lCall, lEmail, lHireDate, lCcode, lIdCheck1, lIdCheck2, lIdCheck3;
	private CheckboxGroup cgGender;
	private Checkbox cbM, cbF;
	private TextField tfId, tfPw, tfPwCheck, tfName, tfCall, tfEmail, tfCcode;
	private Choice HireY, HireM, HireD;
	private Button bIdCheck, bJoin, btCau;
	private Dialog dCaution;
	private CenterDAO id_Check;
	private String sGender;
	private Font fDfont;

	public TeacherJoin() {
	}

	public void startTJ() {
		fTJoin = new JFrame("선생님 등록");
		fTJoin.setSize(400, 400);
		fTJoin.setLocationRelativeTo(null);
		fTJoin.setLayout(null);
		fTJoin.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fTJoin.setIconImage(icon);

		lId = new Label("아이디");
		lId.setBounds(78, 25, 40, 25);

		Color cRed = new Color(255, 0, 0);
		Color cGreen = new Color(0, 255, 0);

		lIdCheck1 = new Label("사용 가능한 아이디입니다.");
		lIdCheck1.setBounds(125, 48, 160, 23);
		lIdCheck1.setVisible(false);
		lIdCheck1.setForeground(cGreen);

		lIdCheck2 = new Label("중복된 아이디입니다.");
		lIdCheck2.setBounds(125, 48, 160, 23);
		lIdCheck2.setVisible(false);
		lIdCheck2.setForeground(cRed);

		lIdCheck3 = new Label("아이디를 입력해주세요.");
		lIdCheck3.setBounds(125, 48, 160, 23);
		lIdCheck3.setVisible(false);

		lPw = new Label("비밀번호");
		lPw.setBounds(67, 70, 55, 25);

		lPwCheck = new Label("비밀번호 확인");
		lPwCheck.setBounds(39, 100, 80, 25);

		lName = new Label("이름");
		lName.setBounds(90, 130, 30, 25);

		lGender = new Label("성별");
		lGender.setBounds(90, 160, 30, 25);

		lCall = new Label("연락처");
		lCall.setBounds(78, 190, 40, 25);

		lEmail = new Label("이메일");
		lEmail.setBounds(78, 220, 40, 25);

		lHireDate = new Label("입사일");
		lHireDate.setBounds(78, 250, 40, 25);

		lCcode = new Label("학원코드");
		lCcode.setBounds(67, 280, 55, 25);

		tfId = new TextField(20); // 아이디 입력창
		tfId.setBounds(125, 26, 160, 20);

		tfPw = new TextField(20); // 비밀번호 입력창
		tfPw.setBounds(125, 73, 160, 20);
		tfPw.setEchoChar('*');

		tfPwCheck = new TextField(20); // 비밀번호 확인 입력창
		tfPwCheck.setBounds(125, 102, 160, 20);
		tfPwCheck.setEchoChar('*');

		tfName = new TextField(20); // 이름 입력창
		tfName.setBounds(125, 133, 160, 20);

		cgGender = new CheckboxGroup();
		cbM = new Checkbox("남", cgGender, true);
		cbM.setBounds(135, 162, 50, 20);

		cbF = new Checkbox("여", cgGender, false);
		cbF.setBounds(185, 162, 50, 20);

		tfCall = new TextField(20); // 연락처 입력창
		tfCall.setBounds(125, 193, 160, 20);

		tfEmail = new TextField(20); // 이메일 입력창
		tfEmail.setBounds(125, 223, 160, 20);

		HireY = new Choice(); // 입사년도 선택
		for (int i = 2000; i < 2024; i++) {
			HireY.add(Integer.toString(i));
		}
		HireY.setBounds(125, 252, 50, 20);

		HireM = new Choice(); // 입사달 선택
		for (int i = 1; i < 13; i++) {
			HireM.add(Integer.toString(i));
		}
		HireM.setBounds(183, 252, 50, 20);

		HireD = new Choice(); // 입사일 선택
		for (int i = 1; i < 32; i++) {
			HireD.add(Integer.toString(i));
		}
		HireD.setBounds(240, 252, 50, 20);

		tfCcode = new TextField(20); // 학원코드 입력창
		tfCcode.setBounds(125, 282, 160, 20);
		tfCcode.setEchoChar('*');

		bIdCheck = new Button("중복확인");
		bIdCheck.setBounds(290, 23, 60, 25);
		bIdCheck.addActionListener(this);
		bIdCheck.setActionCommand("중복확인");

		bJoin = new Button("등록하기");
		bJoin.setBounds(170, 315, 60, 30);
		bJoin.addActionListener(this);

		fTJoin.add(lId);
		fTJoin.add(lPw);
		fTJoin.add(lPwCheck);
		fTJoin.add(lName);
		fTJoin.add(lGender);
		fTJoin.add(lCall);
		fTJoin.add(lEmail);
		fTJoin.add(lHireDate);
		fTJoin.add(lCcode);

		fTJoin.add(lIdCheck1);
		fTJoin.add(lIdCheck2);
		fTJoin.add(lIdCheck3);

		fTJoin.add(tfId);
		fTJoin.add(tfPw);
		fTJoin.add(tfPwCheck);
		fTJoin.add(tfName);
		fTJoin.add(tfCall);
		fTJoin.add(tfEmail);
		fTJoin.add(HireY);
		fTJoin.add(HireM);
		fTJoin.add(HireD);

		fTJoin.add(tfCcode);

		fTJoin.add(bIdCheck);
		fTJoin.add(bJoin);

		fTJoin.add(cbF);
		fTJoin.add(cbM);

		fTJoin.setVisible(true);
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}
	}

	public void failJoin(String msg1) {
		dCaution = new Dialog(fTJoin, "호야 지역아동센터", true);
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
		btCau.setBounds(113, 100, 50, 30);
		btCau.addActionListener(this);
		btCau.setActionCommand("윤달오류");

		dCaution.add(btCau);
		dCaution.add(lMsg1);
		dCaution.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("중복확인")) {
			if (tfId.getText().equals("")) {
				lIdCheck1.setVisible(false);
				lIdCheck2.setVisible(false);
				lIdCheck3.setVisible(true);
				return;
			} else {
				String check;
				id_Check = new CenterDAO();
				check = id_Check.C_ID_Check(tfId.getText());

				if (check.equals("joinable")) {
					lIdCheck1.setVisible(true);
					lIdCheck2.setVisible(false);
					lIdCheck3.setVisible(false);
					return;
				} else if (check.equals("joinunable")) {
					lIdCheck1.setVisible(false);
					lIdCheck2.setVisible(true);
					lIdCheck3.setVisible(false);
					return;
				}
			}
		}

		if (e.getActionCommand().equals("윤달오류")) {
			dCaution.dispose();
			return;
		}

		if (e.getActionCommand().equals("등록하기")) {
			if (lIdCheck1.isVisible()) {

			} else {
				failJoin("아이디를 확인해주세요.");
				return;
			} // 아이디 중복확인 여부 검사

			if (!(tfPw.getText().equals("")) && tfPw.getText().equals(tfPwCheck.getText())) {
				System.out.println("비밀번호 통과");
			} else if (tfPw.getText().equals("")) {
				failJoin("비밀번호를 넣어주세요.");
				return;
			} else {
				failJoin("비밀번호가 일치하지 않습니다.");
				return;
			} // 비밀번호 확인 검사

			if (tfName.getText().equals("")) {
				failJoin("이름을 입력해주세요.");
				return;
			} else {
				System.out.println(tfName.getText());
			} // 이름 입력여부 검사

			if (cbM.getState()) {
				sGender = "남";
				System.out.println("남");
			} else {
				sGender = "여";
				System.out.println("여");
			} // 성별 확인

			String sCall = tfCall.getText();

			try {
				int iCall = Integer.parseInt(sCall);
				System.out.println(sCall);
			} catch (NumberFormatException e1) {
				failJoin("연락처를 숫자로만 입력해주세요.");
				return;
			}

			if (tfEmail.getText().equals("")) {
				failJoin("이메일을 입력해주세요.");
				return;
			} else {
				System.out.println(tfEmail.getText());
			} // 이메일 입력여부 확인

			boolean bLeapYear;

			int iHYear = Integer.parseInt(HireY.getSelectedItem());

			int iY4 = iHYear % 4;
			int iY100 = iHYear % 100;
			int iY400 = iHYear % 400;

			if (iY4 == 0 && !(iY100 == 0) || iY400 == 0) {
				bLeapYear = true;
			} else {
				bLeapYear = false;
			}

			int iM = Integer.parseInt(HireM.getSelectedItem());

			int iD = Integer.parseInt(HireD.getSelectedItem());

			if (bLeapYear && iM == 2) {
				if (iD > 29) {
					failJoin("<html><body><center>해당 달은 윤달입니다.<br> 날짜를 확인해주세요.</center></body></html>");
					return;
				}
			} else if (iM == 2) {
				if (iD > 28) {
					failJoin("<html><body><center>해당 달은 2월입니다.<br>날짜를 확인해주세요.</center></body></html>");
					return;
				}
			} else if (iM == 4 || iM == 6 || iM == 9 || iM == 11) {
				if (iD > 30) {
					failJoin("<html><body><center>해당 달은 30일까지입니다.<br> 날짜를 확인해주세요.</center></body></html>");
					return;
				}

			}

			String sCenterCheck;

			if (tfCcode.getText().equals("")) {
				failJoin("학원코드를 입력해주세요.");
				return;
			} else {
				id_Check = new CenterDAO();
				sCenterCheck = id_Check.Center_Check(tfCcode.getText());
			}

			if (sCenterCheck.equals("unsame")) {
				failJoin("학원코드가 다릅니다. 다시 입력해주세요.");
				return;
			}

			// 1 3 5 7 8 10 12

			String sTID = tfId.getText();
			String sTPW = tfPw.getText();
			String sTName = tfName.getText();
			String sTGender = sGender;
			String sTCall = sCall;
			String sTEmail = tfEmail.getText();
			String sTHireDate = HireY.getSelectedItem() + "-"
					+ String.format("%02d", Integer.parseInt(HireM.getSelectedItem())) + "-"
					+ String.format("%02d", Integer.parseInt(HireD.getSelectedItem()));

			System.out.println(sTID);
			System.out.println(sTPW);
			System.out.println(sTName);
			System.out.println(sTGender);
			System.out.println(sTCall);
			System.out.println(sTEmail);
			System.out.println(sTHireDate);

			id_Check = new CenterDAO();
			id_Check.TeacherJoin(sTID, sTPW, sTName, sTEmail, sTGender, sTCall, sTHireDate);
			String JoinCheck = id_Check.getTjoin();

			if (JoinCheck.equals("JoinSuccess")) {
				fTJoin.dispose();
				failJoin("선생님 등록이 완료되었습니다.");
			} else {
				failJoin("선생님 등록에 실패했습니다.");
			}
		}
	}
}