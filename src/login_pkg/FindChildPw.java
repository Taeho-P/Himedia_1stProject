package login_pkg;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import db_pkg.CenterDAO;

public class FindChildPw extends WindowAdapter implements ActionListener{
	private JFrame fFindAcc;
	private Label lId, lFindId, lName, lBirth, lGender, lParent;
	private TextField tfId, tfName, tfParent;
	private Choice cBirthY, cBirthM, cBirthD, cForM;
	private CheckboxGroup cgGender;
	private Checkbox cbM, cbF;
	private Button bFind, btCau, btFindPw;
	private Dialog dCaution;

	private CenterDAO cDAO;

	public FindChildPw() {
		fFindAcc = new JFrame("비밀번호 찾기");
		fFindAcc.setSize(400, 280);
		fFindAcc.setLayout(null);
		fFindAcc.setResizable(false);
		fFindAcc.setLocationRelativeTo(null);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fFindAcc.setIconImage(icon);
		
		lFindId = new Label("비밀번호 찾기");
		lFindId.setBounds(155, 20, 80, 25);

		lId = new Label("아이디");
		lId.setBounds(83, 50, 50, 25);
		
		lName = new Label("이름");
		lName.setBounds(95, 80, 30, 25);

		lBirth = new Label("생년월일");
		lBirth.setBounds(70, 111, 50, 25);

		lGender = new Label("성별");
		lGender.setBounds(95, 139, 30, 25);

		lParent = new Label("부모님 성함");
		lParent.setBounds(55, 166, 65, 25);

		cBirthY = new Choice();
		for (int i = 1990; i < 2024; i++) {
			cBirthY.add(Integer.toString(i));
		}
		cBirthY.setBounds(135, 113, 55, 20);

		cBirthM = new Choice();
		for (int i = 1; i <= 12; i++) {
			cBirthM.add(Integer.toString(i));
		}
		cBirthM.setBounds(200, 113, 40, 20);

		cBirthD = new Choice();
		for (int i = 1; i <= 31; i++) {
			cBirthD.add(Integer.toString(i));
		}
		cBirthD.setBounds(250, 113, 40, 20);
		
		tfId = new TextField(20);
		tfId.setBounds(135, 53, 160, 20);

		tfName = new TextField(20);
		tfName.setBounds(135, 83, 160, 20);

		cgGender = new CheckboxGroup();
		cbM = new Checkbox("남", cgGender, true);
		cbM.setBounds(160, 142, 30, 20);

		cbF = new Checkbox("여", cgGender, false);
		cbF.setBounds(230, 142, 30, 20);

		tfParent = new TextField(20);
		tfParent.setBounds(195, 168, 100, 20);
		cForM = new Choice();
		cForM.setBounds(135, 167, 50, 20);
		cForM.add("부");
		cForM.add("모");

		bFind = new Button("비밀번호 찾기");
		bFind.setBounds(152, 200, 90, 30);
		bFind.addActionListener(this);
		bFind.setActionCommand("find");

		fFindAcc.add(lFindId);
		fFindAcc.add(lId);
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

		fFindAcc.add(tfId);
		fFindAcc.add(tfName);
		fFindAcc.add(tfParent);

		fFindAcc.add(bFind);

		fFindAcc.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
			if (tfId.getText().equals("") || tfName.getText().equals("") || tfParent.getText().equals("")) {
				showCaution("비어있는 항목이 있습니다.", "모두 입력해주세요.");
				return;
			}
			
			
			String sId = tfId.getText();
			String sCName = tfName.getText();
			String sCBirth = cBirthY.getSelectedItem() + "-"
					+ String.format("%02d", Integer.parseInt(cBirthM.getSelectedItem())) + "-"
					+ String.format("%02d", Integer.parseInt(cBirthD.getSelectedItem()));
			String sGender = cbM.getState() ? "남" : "여";
			String sForM = cForM.getSelectedItem();
			String sParent = tfParent.getText();
			
			System.out.println(sId);
			System.out.println(sCName);
			System.out.println(sCBirth);
			System.out.println(sGender);
			System.out.println(sForM);
			System.out.println(sParent);

			cDAO = new CenterDAO();
			String sFindPw = cDAO.findChildList(sId, sCName, sCBirth, sGender, sForM, sParent);

			if (sFindPw.equals(" ")) {
				System.out.println("일치하는 아이디 없음");
				showCaution("일치하는 계정이 없습니다.", "입력정보를 확인해주세요.");
			} else {
				System.out.println(sFindPw);
				showAcc(tfId.getText() + "님의 비밀번호는", sFindPw);
				
			}
		}

	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}
	}

	public void showAcc(String msg1, String Id) {
		dCaution = new Dialog(fFindAcc, "Notice", true);
		dCaution.setSize(280, 170);
		dCaution.setLocationRelativeTo(null);
		dCaution.setLayout(null);
		dCaution.addWindowListener(this);
		dCaution.setResizable(false);

		Label lMsg1 = new Label(msg1);
		lMsg1.setSize(230, 23);
		lMsg1.setLocation(70, 45);

		Label lMsg2 = new Label(Id + " 입니다.");
		lMsg2.setSize(230, 25);
		lMsg2.setLocation(70, 65);

		btCau = new Button("로그인 하기");
		btCau.setBounds(95, 100, 85, 30);
		btCau.addActionListener(this);
		btCau.setActionCommand("goLogin");
		
		

		dCaution.add(btCau);
		dCaution.add(lMsg1);
		dCaution.add(lMsg2);
		dCaution.setVisible(true);
	}

	public void showCaution(String msg1, String msg2) {
		dCaution = new Dialog(fFindAcc, "Notice", true);
		dCaution.setSize(280, 170);
		dCaution.setLocationRelativeTo(null);
		dCaution.setLayout(null);
		dCaution.addWindowListener(this);
		dCaution.setResizable(false);

		Label lMsg1 = new Label(msg1);
		lMsg1.setSize(230, 23);
		lMsg1.setLocation(70, 45);

		Label lMsg2 = new Label(msg2);
		lMsg2.setSize(230, 25);
		lMsg2.setLocation(70, 65);

		btCau = new Button("확인");
		btCau.setBounds(113, 100, 50, 30);
		btCau.addActionListener(this);
		btCau.setActionCommand("ok");

		dCaution.add(btCau);
		dCaution.add(lMsg1);
		dCaution.add(lMsg2);
		dCaution.setVisible(true);
	}
}
