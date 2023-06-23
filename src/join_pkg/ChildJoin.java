package join_pkg;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FileDialog;
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
import db_pkg.TeacherVo;
import main_pkg.TeacherMain;

public class ChildJoin extends WindowAdapter implements ActionListener {

	private JFrame fCJoin;
	private Label lImage, lId, lPw, lPwCheck, lName, lGender, lCall, lFather, lHireDate, lCcode, lIdCheck1, lIdCheck2,
			lIdCheck3, lMother;
	private CheckboxGroup cgGender;
	private Checkbox cbM, cbF;
	private TextField tfId, tfPw, tfPwCheck, tfName, tfCall, tfFather, tfMother, tfSchool, tfImage;
	private Choice HireY, HireM, HireD;
	private Button bIdCheck, bJoin, btCau, bFindImg;
	private Dialog dCaution, dCaution2;
	private FileDialog fFindImage;
	private CenterDAO id_Check;
	private String sGender, sImageLocation;
	private Font fDfont;

	private TeacherVo tInfo;

	public void startCJ(TeacherVo tInfo) {
		this.tInfo = tInfo;
		fCJoin = new JFrame("신규 아동 등록");
		fCJoin.setSize(400, 455);
		fCJoin.setLocationRelativeTo(null);
		fCJoin.setLayout(null);
		fCJoin.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fCJoin.setIconImage(icon);

		lId = new Label("아이디");
		lId.setBounds(78, 23, 40, 25);

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

		lImage = new Label("사진첨부");
		lImage.setBounds(67, 128, 55, 25);

		lName = new Label("이름");
		lName.setBounds(90, 156, 30, 25);

		lHireDate = new Label("생년월일");
		lHireDate.setBounds(67, 184, 55, 25);

		lGender = new Label("성별");
		lGender.setBounds(90, 215, 30, 25);

		lCall = new Label("연락처");
		lCall.setBounds(78, 245, 40, 25);

		lFather = new Label("부");
		lFather.setBounds(102, 275, 20, 25);

		lMother = new Label("모");
		lMother.setBounds(102, 305, 20, 25);

		lCcode = new Label("학교명");
		lCcode.setBounds(78, 335, 40, 25);////////

		tfId = new TextField(20); // 아이디 입력창
		tfId.setBounds(125, 26, 160, 20);

		tfPw = new TextField(20); // 비밀번호 입력창
		tfPw.setBounds(125, 73, 160, 20);
		tfPw.setEchoChar('*');

		tfPwCheck = new TextField(20); // 비밀번호 확인 입력창
		tfPwCheck.setBounds(125, 101, 160, 20);
		tfPwCheck.setEchoChar('*');

		tfImage = new TextField(20); // 이미지파일 이름
		tfImage.setBounds(125, 129, 160, 20);
		tfImage.setEditable(false);

		tfName = new TextField(20); // 이름 입력창
		tfName.setBounds(125, 159, 160, 20);

		cgGender = new CheckboxGroup();
		cbM = new Checkbox("남", cgGender, true);
		cbM.setBounds(135, 217, 50, 20);

		cbF = new Checkbox("여", cgGender, false);
		cbF.setBounds(185, 217, 50, 20);

		tfCall = new TextField(20); // 연락처 입력창
		tfCall.setBounds(125, 248, 160, 20);

		tfFather = new TextField(20); // 부 성함 입력창
		tfFather.setBounds(125, 278, 160, 20);

		tfMother = new TextField(20); // 부 성함 입력창
		tfMother.setBounds(125, 308, 160, 20);

		HireY = new Choice(); // 입사년도 선택
		for (int i = 2000; i < 2024; i++) {
			HireY.add(Integer.toString(i));
		}
		HireY.setBounds(125, 186, 50, 20);

		HireM = new Choice(); // 입사달 선택
		for (int i = 1; i < 13; i++) {
			HireM.add(Integer.toString(i));
		}
		HireM.setBounds(183, 186, 50, 20);

		HireD = new Choice(); // 입사일 선택
		for (int i = 1; i < 32; i++) {
			HireD.add(Integer.toString(i));
		}
		HireD.setBounds(240, 186, 50, 20);

		tfSchool = new TextField(20); // 학교명 입력창
		tfSchool.setBounds(125, 337, 160, 20);

		bIdCheck = new Button("중복확인");
		bIdCheck.setBounds(290, 23, 60, 25);
		bIdCheck.addActionListener(this);
		bIdCheck.setActionCommand("중복확인");

		bFindImg = new Button("불러오기");
		bFindImg.setBounds(290, 126, 60, 25);
		bFindImg.addActionListener(this);
		bFindImg.setActionCommand("bFindImg");

		bJoin = new Button("등록하기");
		bJoin.setBounds(170, 368, 60, 30);
		bJoin.addActionListener(this);

		fCJoin.add(lId);
		fCJoin.add(lPw);
		fCJoin.add(lPwCheck);
		fCJoin.add(lName);
		fCJoin.add(lGender);
		fCJoin.add(lCall);
		fCJoin.add(lFather);
		fCJoin.add(lHireDate);
		fCJoin.add(lCcode);
		fCJoin.add(lImage);
		fCJoin.add(lMother);

		fCJoin.add(lIdCheck1);
		fCJoin.add(lIdCheck2);
		fCJoin.add(lIdCheck3);

		fCJoin.add(tfId);
		fCJoin.add(tfPw);
		fCJoin.add(tfPwCheck);
		fCJoin.add(tfImage);
		fCJoin.add(tfName);
		fCJoin.add(tfCall);
		fCJoin.add(tfFather);
		fCJoin.add(tfMother);
		fCJoin.add(HireY);
		fCJoin.add(HireM);
		fCJoin.add(HireD);

		fCJoin.add(tfSchool);

		fCJoin.add(bIdCheck);
		fCJoin.add(bJoin);
		fCJoin.add(bFindImg);

		fCJoin.add(cbF);
		fCJoin.add(cbM);

		fCJoin.addWindowListener(this);

		fCJoin.setVisible(true);
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == fCJoin) {
			new TeacherMain(tInfo);
			fCJoin.dispose();
		}

		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}

		if (e.getComponent() == dCaution2) {
			new TeacherMain(tInfo);
			dCaution2.dispose();
		}
	}

	public void failJoin(String msg1) {
		dCaution = new Dialog(fCJoin, "호야 지역아동센터", true);
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

	public void successJoin(String msg1) {
		dCaution2 = new Dialog(fCJoin, "호야 지역아동센터", true);
		dCaution2.setSize(280, 170);
		dCaution2.setLocationRelativeTo(null);
		dCaution2.setLayout(null);
		dCaution2.addWindowListener(this);
		dCaution2.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		dCaution2.setIconImage(icon);

		fDfont = new Font("SansSerif", Font.PLAIN, 12);

		JLabel lMsg1 = new JLabel(msg1);
		lMsg1.setSize(280, 50);
		lMsg1.setFont(fDfont);
		lMsg1.setLocation(0, 45);
		lMsg1.setHorizontalAlignment(JLabel.CENTER);

		btCau = new Button("확인");
		btCau.setBounds(113, 100, 50, 30);
		btCau.addActionListener(this);
		btCau.setActionCommand("JoinScs");

		dCaution2.add(btCau);
		dCaution2.add(lMsg1);
		dCaution2.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("JoinScs")) {
			fCJoin.dispose();
			new TeacherMain(tInfo);
		}
		if (e.getActionCommand().equals("bFindImg")) {
			fFindImage = new FileDialog(fCJoin, "사진 찾기", FileDialog.LOAD);

			fFindImage.setDirectory("C:\\");
			fFindImage.setVisible(true);

			sImageLocation = fFindImage.getDirectory() + fFindImage.getFile();
			tfImage.setText(fFindImage.getFile());
		}

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

			if (tfImage.getText().equals("")) {
				failJoin("사진을 첨부해주세요.");
				return;
			}

			if (tfName.getText().equals("")) {
				failJoin("이름을 입력해주세요.");
				return;
			} else {
				System.out.println(tfName.getText());
			} // 이름 입력여부 검사

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

			if (cbM.getState()) {
				sGender = "남";
				System.out.println("남");
			} else {
				sGender = "여";
				System.out.println("여");
			} // 성별 확인

			String sCall = tfCall.getText();

			if (!(sCall.equals(""))) {
				try {
					int iCall = Integer.parseInt(sCall);
					System.out.println(sCall);
				} catch (NumberFormatException e1) {
					failJoin("연락처를 숫자로만 입력해주세요.");
					return;
				}
			}

			if (tfFather.getText().equals("") && tfMother.getText().equals("")) {
				failJoin("아빠 또는 엄마 성함을 입력해주세요.");
				return;
			} else {
				System.out.println(tfFather.getText());
			} // 부모님 성함 기입 여부 확인

			String sCenterCheck;

			if (tfSchool.getText().equals("")) {
				failJoin("학교이름을 입력해주세요.");
				return;
			}

			// String id, String pw, String c_name, String c_birthday, String gender, String
			// c_call, String father, String mother, String school, String image

			String sCID = tfId.getText();
			String sCPW = tfPw.getText();
			String sCName = tfName.getText();
			String sC_birthday = HireY.getSelectedItem() + "-"
					+ String.format("%02d", Integer.parseInt(HireM.getSelectedItem())) + "-"
					+ String.format("%02d", Integer.parseInt(HireD.getSelectedItem()));
			String sCGender = sGender;
			String sCCall = sCall;
			String sFather = tfFather.getText();
			String sMother = tfMother.getText();
			String sSchool = tfSchool.getText();
			String sImage = sImageLocation;

			System.out.println(sCID);
			System.out.println(sCPW);
			System.out.println(sCName);
			System.out.println(sC_birthday);
			System.out.println(sCGender);
			System.out.println(sCCall);
			System.out.println(sFather);
			System.out.println(sMother);
			System.out.println(sSchool);
			System.out.println(sImage);

			// sCID, sCPW, sCName, sC_birthday, sCGender, sCCall, sFather, sMother, sSchool,
			// sImage

			id_Check = new CenterDAO();
			id_Check.ChildJoin(sCID, sCPW, sCName, sC_birthday, sCGender, sCCall, sFather, sMother, sSchool, sImage);
			String JoinCheck = id_Check.getCjoin();

			if (JoinCheck.equals("JoinSuccess")) {
				fCJoin.dispose();
				successJoin("신규 아동 등록이 완료되었습니다.");
			} else {
				failJoin("신규 아동 등록에 실패했습니다.");
			}
		}
	}

}
