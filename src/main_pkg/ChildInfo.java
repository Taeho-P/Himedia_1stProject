package main_pkg;

import java.awt.Button;
import java.awt.Choice;
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
import db_pkg.ChildVo;
import db_pkg.TeacherVo;

public class ChildInfo extends WindowAdapter implements ActionListener {
	private TeacherVo tInfo;
	private ChildVo cInfo;
	private CenterDAO cDAO;

	private JFrame fChildInfo;
	private JLabel lImage;
	private Label lImageLoc, lName, lGender, lMorF, lBirthday, lCall, lFather, lMother, lSchool, lHo;
	private Image iChildImage;
	private TextField tfImageLoc, tfName, tfCall, tfFather, tfMother, tfSchool, tfHo;
	private Choice cBirthY, cBirthM, cBirthD;
	private Button btOk, btModify, btImage, btCau;

	private Dialog dCaution;
	private FileDialog fdImage;

	private Font fDfont;

	public ChildInfo(TeacherVo tInfo, ChildVo cInfo) {
		cDAO = new CenterDAO();
		this.tInfo = tInfo;
		this.cInfo = cInfo;
		fChildInfo = new JFrame("아동정보관리");
		fChildInfo.setSize(400, 580);
		fChildInfo.setLocationRelativeTo(null);
		fChildInfo.setLayout(null);
		fChildInfo.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fChildInfo.setIconImage(icon);

		iChildImage = new ImageIcon(cInfo.getImage()).getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);

		lImage = new JLabel();
		lImage.setBounds(135, 30, 120, 160);
		lImage.setIcon(new ImageIcon(iChildImage));

		lImageLoc = new Label("이미지");
		lImageLoc.setBounds(83, 200, 45, 25);

		lName = new Label("이름");
		lName.setBounds(95, 230, 30, 25);

		lGender = new Label("성별");
		lGender.setBounds(95, 260, 30, 25);

		lMorF = new Label(cInfo.getGender());
		lMorF.setBounds(137, 260, 20, 25);

		lBirthday = new Label("생년월일");
		lBirthday.setBounds(71, 290, 57, 25);

		lCall = new Label("연락처");
		lCall.setBounds(83, 320, 45, 25);

		lFather = new Label("부");
		lFather.setBounds(107, 350, 20, 25);

		lMother = new Label("모");
		lMother.setBounds(107, 380, 20, 25);

		lSchool = new Label("학교명");
		lSchool.setBounds(83, 410, 45, 25);

		lHo = new Label("보유 호");
		lHo.setBounds(79, 440, 45, 25);

		tfImageLoc = new TextField(100);
		tfImageLoc.setBounds(128, 203, 155, 20);
		tfImageLoc.setEditable(false);
		tfImageLoc.setText(cInfo.getImage());

		tfName = new TextField(20);
		tfName.setBounds(128, 233, 155, 20);
		tfName.setText(cInfo.getC_name());

		tfCall = new TextField(20);
		tfCall.setBounds(128, 321, 155, 20);
		tfCall.setText(cInfo.getC_call());

		tfFather = new TextField(20);
		tfFather.setBounds(128, 351, 155, 20);
		tfFather.setText(cInfo.getFather());

		tfMother = new TextField(20);
		tfMother.setBounds(128, 381, 155, 20);
		tfMother.setText(cInfo.getMother());

		tfSchool = new TextField(20);
		tfSchool.setBounds(128, 411, 155, 20);
		tfSchool.setText(cInfo.getSchool());

		tfHo = new TextField(20);
		tfHo.setBounds(128, 441, 155, 20);
		tfHo.setText(cInfo.getHo());

		cBirthY = new Choice();
		for (int i = 1990; i < 2024; i++) {
			cBirthY.add(Integer.toString(i));
		}
		cBirthY.setBounds(128, 291, 53, 30);
		cBirthY.select(cInfo.getC_birthday().substring(0, 4));

		cBirthM = new Choice();
		for (int i = 1; i <= 12; i++) {
			cBirthM.add(String.format("%02d", i));
		}
		cBirthM.setBounds(189, 291, 40, 30);
		cBirthM.select(cInfo.getC_birthday().substring(5, 7));

		cBirthD = new Choice();
		for (int i = 1; i <= 31; i++) {
			cBirthD.add(String.format("%02d", i));
		}
		cBirthD.setBounds(237, 291, 40, 30);
		cBirthD.select(cInfo.getC_birthday().substring(8, 10));

		btOk = new Button("확인");
		btOk.setBounds(93, 480, 80, 30);
		btOk.addActionListener(this);
		btOk.setActionCommand("back");

		btModify = new Button("수정하기");
		btModify.setBounds(210, 480, 80, 30);
		btModify.addActionListener(this);
		btModify.setActionCommand("modify");

		btImage = new Button("불러오기");
		btImage.setBounds(289, 200, 60, 25);
		btImage.addActionListener(this);
		btImage.setActionCommand("image");

		fChildInfo.addWindowListener(this);
		fChildInfo.add(lImage);
		fChildInfo.add(lImageLoc);
		fChildInfo.add(lName);
		fChildInfo.add(lGender);
		fChildInfo.add(lMorF);
		fChildInfo.add(lBirthday);
		fChildInfo.add(lCall);
		fChildInfo.add(lFather);
		fChildInfo.add(lMother);
		fChildInfo.add(lSchool);
		fChildInfo.add(lHo);

		fChildInfo.add(tfImageLoc);
		fChildInfo.add(tfName);
		fChildInfo.add(tfCall);
		fChildInfo.add(tfFather);
		fChildInfo.add(tfMother);
		fChildInfo.add(tfSchool);
		fChildInfo.add(tfHo);

		fChildInfo.add(cBirthY);
		fChildInfo.add(cBirthM);
		fChildInfo.add(cBirthD);

		fChildInfo.add(btOk);
		fChildInfo.add(btModify);
		fChildInfo.add(btImage);

		fChildInfo.setVisible(true);
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}

		if (e.getComponent() == fChildInfo) {
			new ChildList(tInfo);
			fChildInfo.dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("modified")) {
			dCaution.dispose();
		}

		if (e.getActionCommand().equals("modifyY")) {
			dCaution.dispose();

			String sC_code = cInfo.getC_code();
			String sC_name = tfName.getText();
			String sC_birthday = cBirthY.getSelectedItem() + "-" + cBirthM.getSelectedItem() + "-"
					+ cBirthD.getSelectedItem();
			String sC_call = tfCall.getText();
			String sFather = tfFather.getText();
			String sMother = tfMother.getText();
			String sSchool = tfSchool.getText();
			String sHo = tfHo.getText();
			String sImage = tfImageLoc.getText();

			String sModify = cDAO.childUpdate(sC_code, sC_name, sC_birthday, sC_call, sFather, sMother, sSchool, sHo,
					sImage);

			if (sModify.equals("updateSuccess")) {
				showCaution("정보 수정이 완료됐습니다.", "modified");
			} else {
				showCaution("정보 수정에 실패했습니다.", "modified");
			}
		}

		if (e.getActionCommand().equals("back")) {
			new ChildList(tInfo);
			fChildInfo.dispose();
		}

		if (e.getActionCommand().equals("modify")) {
			showCaution("정보 수정을 진행합니다.", "modifyY");
		}

		if (e.getActionCommand().equals("image")) {
			fdImage = new FileDialog(fChildInfo, "사진 찾기", FileDialog.LOAD);

			fdImage.setDirectory("C:/");
			fdImage.setVisible(true);

			tfImageLoc.setText(fdImage.getDirectory() + fdImage.getFile());

			lImage.setVisible(false);
			iChildImage = new ImageIcon(fdImage.getDirectory() + fdImage.getFile()).getImage().getScaledInstance(120,
					160, Image.SCALE_SMOOTH);

			lImage = new JLabel();
			lImage.setBounds(135, 30, 120, 160);
			lImage.setIcon(new ImageIcon(iChildImage));
			lImage.repaint();
			lImage.setVisible(true);
			fChildInfo.add(lImage);
		}

	}

	public void showCaution(String msg1, String msg3) {
		dCaution = new Dialog(fChildInfo, "호야 지역아동센터", true);
		dCaution.setSize(300, 190);
		dCaution.setLocationRelativeTo(null);
		dCaution.setLayout(null);
		dCaution.addWindowListener(this);
		dCaution.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		dCaution.setIconImage(icon);

		fDfont = new Font("SansSerif", Font.PLAIN, 12);

		JLabel lMsg1 = new JLabel(msg1);
		lMsg1.setSize(300, 25);
		lMsg1.setFont(fDfont);
		lMsg1.setLocation(0, 70);
		lMsg1.setHorizontalAlignment(JLabel.CENTER);

		Button bCheck = new Button("확인");
		bCheck.addActionListener(this);
		bCheck.setBounds(124, 110, 50, 30);
		bCheck.setActionCommand(msg3);

		dCaution.add(lMsg1);
		dCaution.add(bCheck);
		dCaution.setVisible(true);
	}
}
