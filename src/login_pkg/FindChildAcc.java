package login_pkg;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
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

public class FindChildAcc extends WindowAdapter implements ActionListener {
	private JFrame fFindAcc;
	private Label lFindId, lName, lBirth, lGender, lParent;
	private TextField tfName, tfParent;
	private Choice cBirthY, cBirthM, cBirthD, cForM;
	private CheckboxGroup cgGender;
	private Checkbox cbM, cbF;
	private Button bFind, btCau, btFindPw;
	private Dialog dCaution;
	private FindChildPw fpw;
	private Font fDfont;

	private CenterDAO cDAO;

	public FindChildAcc() {
		fFindAcc = new JFrame("아이디 찾기");
		fFindAcc.setSize(400, 260);
		fFindAcc.setLayout(null);
		fFindAcc.setResizable(false);
		fFindAcc.setLocationRelativeTo(null);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fFindAcc.setIconImage(icon);

		lFindId = new Label("아이디 찾기");
		lFindId.setBounds(160, 20, 65, 25);

		lName = new Label("이름");
		lName.setBounds(95, 50, 30, 25);

		lBirth = new Label("생년월일");
		lBirth.setBounds(70, 81, 50, 25);

		lGender = new Label("성별");
		lGender.setBounds(95, 109, 30, 25);

		lParent = new Label("부모님 성함");
		lParent.setBounds(55, 136, 65, 25);

		cBirthY = new Choice();
		for (int i = 1990; i < 2024; i++) {
			cBirthY.add(Integer.toString(i));
		}
		cBirthY.setBounds(135, 83, 55, 20);

		cBirthM = new Choice();
		for (int i = 1; i <= 12; i++) {
			cBirthM.add(Integer.toString(i));
		}
		cBirthM.setBounds(200, 83, 40, 20);

		cBirthD = new Choice();
		for (int i = 1; i <= 31; i++) {
			cBirthD.add(Integer.toString(i));
		}
		cBirthD.setBounds(250, 83, 40, 20);

		tfName = new TextField(20);
		tfName.setBounds(135, 53, 160, 20);

		cgGender = new CheckboxGroup();
		cbM = new Checkbox("남", cgGender, true);
		cbM.setBounds(160, 112, 30, 20);

		cbF = new Checkbox("여", cgGender, false);
		cbF.setBounds(230, 112, 30, 20);

		tfParent = new TextField(20);
		tfParent.setBounds(195, 138, 100, 20);
		cForM = new Choice();
		cForM.setBounds(135, 137, 50, 20);
		cForM.add("부");
		cForM.add("모");

		bFind = new Button("아이디 찾기");
		bFind.setBounds(161, 170, 75, 30);
		bFind.addActionListener(this);
		bFind.setActionCommand("find");

		fFindAcc.add(lFindId);
		fFindAcc.add(lName);
		fFindAcc.add(lBirth);
		fFindAcc.add(lGender);
		fFindAcc.add(lParent);

		fFindAcc.add(cBirthY);
		fFindAcc.add(cBirthM);
		fFindAcc.add(cBirthD);

		fFindAcc.add(cForM);

		fFindAcc.add(cbM);
		fFindAcc.add(cbF);

		fFindAcc.add(tfName);
		fFindAcc.add(tfParent);

		fFindAcc.addWindowListener(this);

		fFindAcc.add(bFind);

		fFindAcc.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("findPw")) {
			fpw = new FindChildPw();
			fFindAcc.setVisible(false);
			dCaution.dispose();
			return;
		}
		if (e.getActionCommand().equals("goLogin")) {
			fFindAcc.dispose();
			dCaution.dispose();
			return;
		}
		if (e.getActionCommand().equals("ok")) {
			dCaution.dispose();
			return;
		}

		if (e.getActionCommand().equals("find")) {
			if (tfName.getText().equals("") || tfParent.getText().equals("")) {
				showCaution("<html><body><center>비어있는 항목이 있습니다. <br>모두 입력해주세요.</center></body></html>");
				return;

			}

			String sCName = tfName.getText();
			String sCBirth = cBirthY.getSelectedItem() + "-"
					+ String.format("%02d", Integer.parseInt(cBirthM.getSelectedItem())) + "-"
					+ String.format("%02d", Integer.parseInt(cBirthD.getSelectedItem()));
			String sGender = cbM.getState() ? "남" : "여";
			String sForM = cForM.getSelectedItem();
			String sParent = tfParent.getText();

			System.out.println(sCName);
			System.out.println(sCBirth);
			System.out.println(sGender);
			System.out.println(sForM);
			System.out.println(sParent);

			cDAO = new CenterDAO();
			String sFindId = cDAO.findChildList(sCName, sCBirth, sGender, sForM, sParent);

			if (sFindId.equals(" ")) {
				System.out.println("일치하는 아이디 없음");
				showCaution("<html><body><center>일치하는 계정이 없습니다.<br>입력정보를 확인해주세요.</center></body></html>");

			} else {
				System.out.println(sFindId);
				showAcc(tfName.getText() + "님의 아이디는", sFindId);

			}
		}

	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == fFindAcc) {
			new Login_Frame();
			fFindAcc.dispose();
		}
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}
	}

	public void showAcc(String msg1, String Id) {
		dCaution = new Dialog(fFindAcc, "호야 지역아동센터", true);
		dCaution.setSize(280, 170);
		dCaution.setLocationRelativeTo(null);
		dCaution.setLayout(null);
		dCaution.addWindowListener(this);
		dCaution.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		dCaution.setIconImage(icon);

		Label lMsg1 = new Label(msg1);
		lMsg1.setSize(230, 23);
		lMsg1.setLocation(70, 45);

		Label lMsg2 = new Label(Id + " 입니다.");
		lMsg2.setSize(230, 25);
		lMsg2.setLocation(70, 65);

		btCau = new Button("로그인 하기");
		btCau.setBounds(48, 100, 85, 30);
		btCau.addActionListener(this);
		btCau.setActionCommand("goLogin");

		btFindPw = new Button("비밀번호 찾기");
		btFindPw.setBounds(146, 100, 85, 30);
		btFindPw.addActionListener(this);
		btFindPw.setActionCommand("findPw");

		dCaution.add(btCau);
		dCaution.add(lMsg1);
		dCaution.add(lMsg2);
		dCaution.add(btFindPw);
		dCaution.setVisible(true);
	}

	public void showCaution(String msg1) {
		dCaution = new Dialog(fFindAcc, "호야 지역아동센터", true);
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
		btCau.setActionCommand("ok");

		dCaution.add(btCau);
		dCaution.add(lMsg1);
		dCaution.setVisible(true);
	}
}
