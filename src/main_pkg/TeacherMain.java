package main_pkg;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import db_pkg.TeacherVo;
import join_pkg.ChildJoin;
import login_pkg.Login_Frame;

public class TeacherMain extends WindowAdapter implements ActionListener {

	private JFrame fTMain;
	private Label lGrt;
	private Button bNGChild, bSgst, bChildInfo, bAttend;
	private Font fFont, fDfont;
	private Dialog dCaution;

	private TeacherVo tInfo;

	public TeacherMain(TeacherVo tInfo) {
		this.tInfo = tInfo;

		fTMain = new JFrame("호야 지역아동센터");
		fTMain.setSize(400, 300);
		fTMain.setLocationRelativeTo(null);
		fTMain.setLayout(null);
		fTMain.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fTMain.setIconImage(icon);

		bNGChild = new Button("신규/졸업 아동");
		bNGChild.setBounds(70, 90, 100, 60);
		bNGChild.setActionCommand("ChildJoin");
		bNGChild.addActionListener(this);

		bSgst = new Button("건의함 열람");
		bSgst.setBounds(215, 90, 100, 60);
		bSgst.setActionCommand("OpenSgst");
		bSgst.addActionListener(this);

		bChildInfo = new Button("아동 관리");
		bChildInfo.setBounds(70, 170, 100, 60);
		bChildInfo.setActionCommand("ChildInfo");
		bChildInfo.addActionListener(this);

		bAttend = new Button("월별 출석 조회");
		bAttend.setBounds(215, 170, 100, 60);
		bAttend.setActionCommand("attend");
		bAttend.addActionListener(this);

		fFont = new Font("SansSerif", Font.PLAIN, 20);
		lGrt = new Label(tInfo.getT_name() + " 선생님, 안녕하세요!");
		lGrt.setFont(fFont);
		lGrt.setBounds(50, 40, 350, 30);

		fTMain.add(lGrt);
		fTMain.add(bAttend);
		fTMain.add(bNGChild);
		fTMain.add(bChildInfo);
		fTMain.add(bSgst);

		fTMain.addWindowListener(this);

		fTMain.setVisible(true);
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == fTMain) {
			new Login_Frame();
			fTMain.dispose();
		}
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("newChild")) {
			ChildJoin cj = new ChildJoin();
			cj.startCJ(tInfo);
			dCaution.dispose();
			fTMain.dispose();
		}
		if (e.getActionCommand().equals("ChildJoin")) {
			System.out.println("신규/졸업 아동 클릭");
			showCaution("신규, 졸업 중 선택해주세요.", "newChild", "grdChild");
		}
		if (e.getActionCommand().equals("OpenSgst")) {
			System.out.println("건의함 열람 클릭");
			new OpenSgstBox(tInfo);
			fTMain.dispose();
		}
		if (e.getActionCommand().equals("ChildInfo")) {
			System.out.println("아동 관리 클릭");
			new ChildList(tInfo);
			fTMain.dispose();
		}
		if (e.getActionCommand().equals("attend")) {
			System.out.println("월별 출석 조회 클릭");
		}
		if (e.getActionCommand().equals("grdChild")) {
			System.out.println("아동 졸업 클릭");
			fTMain.dispose();
			new GraduateChild(tInfo);
		}

	}

	public void showCaution(String msg1, String bA1, String bA2) {
		dCaution = new Dialog(fTMain, "호야 지역아동센터", true);
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

		Button bCheck1 = new Button("신규");
		bCheck1.addActionListener(this);
		bCheck1.setBounds(64, 110, 70, 30);
		bCheck1.setActionCommand(bA1);

		Button bCheck2 = new Button("졸업");
		bCheck2.addActionListener(this);
		bCheck2.setBounds(164, 110, 70, 30);
		bCheck2.setActionCommand(bA2);

		dCaution.add(lMsg1);
		dCaution.add(bCheck1);
		dCaution.add(bCheck2);
		dCaution.setVisible(true);
	}

}
