package login_pkg;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FindChildAcc {
	private JFrame fFindAcc;
	private Label lFindId, lName, lBirth, lGender, lParent;
	private TextField tfName, tfParent;
	private Choice cBirthY, cBirthM, cBirthD;
	private CheckboxGroup cgGender;
	private Checkbox cbM, cbF;
	private Button bFind;
	
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
		lName.setBounds(95, 45, 30, 25);
		
		lBirth = new Label("생년월일");
		lBirth.setBounds(70, 72, 50, 25);
		
		lGender = new Label("성별");
		lGender.setBounds(95, 99, 30, 25);
		
		lParent = new Label("부모님 성함");
		lParent.setBounds(55, 126, 65, 25);
		
		cBirthY = new Choice();
		for(int i = 1990; i < 2024; i++) {
			cBirthY.add(Integer.toString(i));
		}
		cBirthY.setBounds(135, 73, 55, 20);
		
		cBirthM = new Choice();
		for(int i = 1; i <= 12; i++) {
			cBirthM.add(Integer.toString(i));
		}
		cBirthM.setBounds(195, 73, 40, 20);
		
		//cBirthD적용하기
		
		tfName = new TextField(20);
		tfName.setBounds(135, 48, 160, 20);
		
		
		fFindAcc.add(lFindId);
		fFindAcc.add(lName);
		fFindAcc.add(lBirth);
		fFindAcc.add(lGender);
		fFindAcc.add(lParent);
		
		fFindAcc.add(cBirthY);
		fFindAcc.add(cBirthM);
		
		
		fFindAcc.add(tfName);
		
		
		fFindAcc.setVisible(true);
	}
}
