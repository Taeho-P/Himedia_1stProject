package login_pkg;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import db_pkg.ChildDAO;
import db_pkg.ChildVo;
import db_pkg.TeacherDAO;
import db_pkg.TeacherVo;
import join_pkg.TeacherJoin;
import tool_pkg.ImagePanel;

public class Login_Frame extends WindowAdapter implements ActionListener {
	private JFrame fLogin;
	private Label lID, lPW;
	private TextField tfID, tfPW;
	private CheckboxGroup cgTC;
	private Checkbox cbT, cbC;
	private JButton btTjoin, btFind, bts;
	private Button btLogin, btCau;
	private ImagePanel ip;
	private TeacherDAO Tdao;
	private ChildDAO Cdao;
	private Dialog dCaution;
	private TeacherJoin tjTJ;
	private String[] Login_info = new String[3];
	
	
	
	Color c = new Color(255, 0, 0);

	public Login_Frame() {
		fLogin = new JFrame("호야 지역아동센터");
		fLogin.setSize(400, 300); // 프레임 사이즈 설정
		fLogin.setLocationRelativeTo(null);
		fLogin.setLayout(null); // 레이아웃 설정
		fLogin.setResizable(false); // 창크기 변경 불가
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fLogin.setIconImage(icon);

		Image image = new ImageIcon("./src/logo.png").getImage(); // 띄울 이미지 불러오기
//		File f = new File("./src/logo.png");
//		System.out.println(f.exists()?"oo":"xx");
		ImagePanel ip = new ImagePanel(image); // 프레임에 이미지를 넣기위한 패널
		ip.setBounds(90, 30, 300, 50);
		ip.setVisible(true);

		cgTC = new CheckboxGroup(); // 선생님 or 아동 로그인 선택 버튼그룹
		cbT = new Checkbox("선생님 로그인", cgTC, true); // 선생님 로그인 버튼
		cbT.setBounds(90, 90, 90, 20);

		cbC = new Checkbox("아동 로그인", cgTC, false); // 아동 로그인 버튼
		cbC.setBounds(210, 90, 100, 20);

		lID = new Label("아이디"); // 아이디 라벨
		lID.setBounds(63, 120, 40, 25);

		lPW = new Label("비밀번호"); // 비밀번호 라벨
		lPW.setBounds(50, 150, 50, 25);

		tfID = new TextField(20);
		tfID.setBounds(110, 123, 160, 20);

		tfPW = new TextField(20);
		tfPW.setBounds(110, 153, 160, 20);
		tfPW.setEchoChar('*');

		btLogin = new Button("로그인"); // 로그인 버튼 생성
		btLogin.setBounds(280, 130, 60, 30);
		btLogin.addActionListener(this);

		btTjoin = new JButton("선생님 등록"); // 선생님 등록 버튼 생성
		btTjoin.setBounds(80, 180, 100, 25);
		btTjoin.setBorderPainted(false); // 버튼 외곽 없애기
		btTjoin.setContentAreaFilled(false); // 버튼 내부배경 없애기
		btTjoin.addActionListener(this); //액션리스너 부착
		
		
		
		bts = new JButton("｜"); // 선생님 등록버튼과 회원정보찾기 버튼 사이 '/' 구현
		bts.setBounds(150, 180, 50, 25);
		bts.setBorderPainted(false);
		bts.setContentAreaFilled(false);

		btFind = new JButton("아이디/비밀번호 찾기"); // 아동 계정 찾기 버튼
		btFind.setBounds(162, 180, 170, 25);
		btFind.setBorderPainted(false);
		btFind.setContentAreaFilled(false);
		btFind.setActionCommand("findAcc");
		btFind.addActionListener(this); //액션리스너 부착

		fLogin.add(cbT);
		fLogin.add(cbC);
		fLogin.add(lID);
		fLogin.add(lPW);
		fLogin.add(tfID);
		fLogin.add(tfPW);
		fLogin.add(btLogin);
		fLogin.add(btTjoin);
		fLogin.add(btFind);
		fLogin.add(bts);
		fLogin.add(ip);
		fLogin.setDefaultCloseOperation(fLogin.EXIT_ON_CLOSE);
		fLogin.setVisible(true);
		
		
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
			tfID.setText("");
			tfPW.setText("");
		}
	}

	public void failLogin() {
		System.out.println("로그인 실패");
		dCaution = new Dialog(fLogin, "로그인 실패", true);
		dCaution.setSize(280, 170);
		dCaution.setLocationRelativeTo(null);
		dCaution.setLayout(null);
		dCaution.addWindowListener(this);
		dCaution.setResizable(false);

		Label lMsg = new Label("아이디/비밀번호가 일치하지 않습니다.");
		lMsg.setBounds(35, 70, 230, 30);

		btCau = new Button("확인");
		btCau.setBounds(113, 100, 50, 30);
		btCau.addActionListener(this);
		btCau.setActionCommand("비번오류");

		dCaution.add(btCau);
		dCaution.add(lMsg);
		dCaution.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id = null;
		String pw = null;
		String TorC = null;

		if (e.getActionCommand().equals("비번오류")) {
			dCaution.dispose();
			tfID.setText("");
			tfPW.setText("");
		}

		if (e.getActionCommand().equals("로그인")) {
			TorC = getTorC();

			System.out.println(getTorC());
			System.out.println(getID());
			System.out.println(getPW());
			if (TorC.equals("teacher")) {
				Tdao = new TeacherDAO();

				String sId = getID();
				ArrayList<TeacherVo> Tlist = Tdao.list(sId);

				for (int i = 0; i < Tlist.size(); i++) {
					TeacherVo data = (TeacherVo) Tlist.get(i);
					id = data.getId();
					pw = data.getPassword();

					System.out.println(id + " : " + pw);

					if (getPW().equals(pw)) {
						System.out.println("로그인 성공");
						System.out.println(data.getHire_date());
						
						
						Login_info[0] = "teacher";
						Login_info[1] = data.getT_code();
						Login_info[2] = data.getT_name();
						
						

					} else {
						failLogin();
					}
				}
			}

			if (TorC.equals("child")) {
				Cdao = new ChildDAO();

				String sId = getID();
				ArrayList<ChildVo> Clist = Cdao.list(sId);

				for (int i = 0; i < Clist.size(); i++) {
					ChildVo data = (ChildVo) Clist.get(i);
					id = data.getId();
					pw = data.getPassword();

					System.out.println(id + " : " + pw);

					if (getPW().equals(pw)) {
						System.out.println("로그인 성공");
						System.out.println(data.getC_birthday());
						// 선생님 메인창 띄우기

					} else {
						System.out.println("로그인 실패");
						dCaution = new Dialog(fLogin, "로그인 실패", true);
						dCaution.setSize(280, 170);
						dCaution.setLocationRelativeTo(null);
						dCaution.setLayout(null);
						dCaution.addWindowListener(this);
						dCaution.setResizable(false);

						Label lMsg = new Label("아이디/비밀번호가 일치하지 않습니다.");
						lMsg.setBounds(35, 70, 230, 30);

						btCau = new Button("확인");
						btCau.setBounds(113, 100, 50, 30);
						btCau.addActionListener(this);
						btCau.setActionCommand("비번오류");

						dCaution.add(btCau);
						dCaution.add(lMsg);
						dCaution.setVisible(true);
					}
				}
			}

			if (id == null) {
				failLogin();
			}

		}
		
		
		if (e.getActionCommand().equals("선생님 등록")) {
			System.out.println("선생님 등록버튼 클릭");
			tjTJ = new TeacherJoin();
			tjTJ.startTJ();
		}
		
		if (e.getActionCommand().equals("findAcc")) {
			System.out.println("아동 계정찾기 버튼 클릭");
			FindChildAcc fca = new FindChildAcc();
			
			
		}
		
	}
	
	public String[] loginSuccess() {
		return Login_info;
	}
	

	public String getID() {
		return tfID.getText();
	}

	public String getPW() {
		return tfPW.getText();
	}

	public String getTorC() {
		if (cbT.getState()) {
			return "teacher";
		} else {
			return "child";
		}
	}
}
