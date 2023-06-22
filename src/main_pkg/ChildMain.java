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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import db_pkg.CenterDAO;
import db_pkg.ChildVo;
import login_pkg.Login_Frame;

public class ChildMain extends WindowAdapter implements ActionListener {
	private JFrame fCMain;
	private Label lGrt;
	private Button bAttend, bWallet, bSgst;
	private ChildVo cInfo;
	private Font fFont, fDfont;
	private Login_Frame lf;
	private LocalDateTime now;
	private CenterDAO cDAO;
	private Calendar cal;
	private Dialog dCaution, atndScs;

	public ChildMain(ChildVo cInfo) {
		this.cInfo = cInfo;
		cal = Calendar.getInstance();

		fCMain = new JFrame("호야 지역아동센터");
		fCMain.setSize(400, 300);
		fCMain.setLocationRelativeTo(null);
		fCMain.setLayout(null);
		fCMain.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fCMain.setIconImage(icon);

		fFont = new Font("SansSerif", Font.PLAIN, 20);
		lGrt = new Label(cInfo.getC_name() + "님, 안녕하세요!");
		lGrt.setFont(fFont);
		lGrt.setBounds(50, 60, 220, 30);

		bAttend = new Button("등원/하원 체크");
		bAttend.setBounds(24, 134, 100, 60);
		bAttend.setActionCommand("attend");
		bAttend.addActionListener(this);

		bWallet = new Button("내 지갑 조회");
		bWallet.setBounds(143, 134, 100, 60);
		bWallet.setActionCommand("ho");
		bWallet.addActionListener(this);

		bSgst = new Button("건의함");
		bSgst.setBounds(263, 134, 100, 60);
		bSgst.setActionCommand("sgst");
		bSgst.addActionListener(this);

		fCMain.add(lGrt);
		fCMain.add(bAttend);
		fCMain.add(bWallet);
		fCMain.add(bSgst);

		fCMain.addWindowListener(this);

		fCMain.setVisible(true);
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == fCMain) {
			lf = new Login_Frame();
			fCMain.dispose();
		}
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}

		if (e.getComponent() == atndScs) {
			dCaution.dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 등원, 하원 성공 메시지 다이얼로그 확인버튼 구현
		if (e.getActionCommand().equals("atndScs")) {
			dCaution.dispose();
		}

		// 하원하기 다이얼로그 -> 하원확인 클릭 시 구현
		if (e.getActionCommand().equals("ctOut")) {

			now = LocalDateTime.now();
			String sNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			String sNow2 = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			String sCode_Date = cInfo.getC_code() + sNow2;

			String sCenter_out = sNow;

			String atndCheck = null;

			atndCheck = cDAO.center_OUT(sCenter_out, sCode_Date);

			System.out.println("출석 입력 상태 : " + atndCheck);

			if (atndCheck.equals("ctOutSuccess")) {
				atndScs = new Dialog(dCaution, "호야 지역아동센터", true);
				Image icon = new ImageIcon("./src/icon.png").getImage();
				atndScs.setIconImage(icon);
				atndScs.setSize(300, 190);
				atndScs.setLocationRelativeTo(null);
				atndScs.setLayout(null);
				atndScs.addWindowListener(this);
				atndScs.setResizable(false);
				Label msg = new Label("하원 완료됐습니다.");
				msg.setBounds(100, 80, 105, 25);

				Button bOK = new Button("확인");
				bOK.setBounds(124, 120, 50, 30);
				bOK.addActionListener(this);
				bOK.setActionCommand("atndScs");

				atndScs.addWindowListener(this);
				atndScs.add(bOK);
				atndScs.add(msg);
				atndScs.setVisible(true);
			}

		}

		// 출석 확인 다이얼로그 -> 확인버튼 클릭 시 구현
		if (e.getActionCommand().equals("atndOK")) {

			int iDay = cal.get(Calendar.DAY_OF_WEEK);

			now = LocalDateTime.now();
			String sNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			String sNow2 = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			String sCode_Date = cInfo.getC_code() + sNow2;
			String sC_code = cInfo.getC_code();
			String sC_name = cInfo.getC_name();
			String sCenter_in = sNow;

			String sAttend_day = null;

			switch (iDay) {

			case 1:
				sAttend_day = "일";
				break;

			case 2:
				sAttend_day = "월";
				break;

			case 3:
				sAttend_day = "화";
				break;

			case 4:
				sAttend_day = "수";
				break;

			case 5:
				sAttend_day = "목";
				break;

			case 6:
				sAttend_day = "금";
				break;

			case 7:
				sAttend_day = "토";
				break;

			}

			String atndCheck = null;

			atndCheck = cDAO.center_IN(sC_code, sC_name, sCenter_in, sAttend_day, sCode_Date);

			System.out.println("출석 입력 상태 : " + atndCheck); //

			if (atndCheck.equals("atndSuccess")) {
				atndScs = new Dialog(dCaution, "호야 지역아동센터", true);
				Image icon = new ImageIcon("./src/icon.png").getImage();
				atndScs.setIconImage(icon);
				atndScs.setSize(300, 190);
				atndScs.setLocationRelativeTo(null);
				atndScs.setLayout(null);
				atndScs.addWindowListener(this);
				atndScs.setResizable(false);
				Label msg = new Label("출석 완료됐습니다.");
				msg.setBounds(100, 80, 105, 25);

				Button bOK = new Button("확인");
				bOK.setBounds(124, 120, 50, 30);
				bOK.addActionListener(this);
				bOK.setActionCommand("atndScs");

				atndScs.addWindowListener(this);
				atndScs.add(bOK);
				atndScs.add(msg);
				atndScs.setVisible(true);
			}

		}

		// 등원/하원하기 버튼 구현
		if (e.getActionCommand().equals("attend")) {

			now = LocalDateTime.now();
			String sNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			String sNow2 = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			String sCode_Date = cInfo.getC_code() + sNow2;

			System.out.println("현재 시간 : " + sNow);
			System.out.println("아동 코드 + 현재 시간 : " + sCode_Date);

			String[] IorO;
			cDAO = new CenterDAO();
			IorO = cDAO.inOrOutCheck(sCode_Date);

			for (int i = 0; i < IorO.length; i++) {
				System.out.println(IorO[i]);
			}

			if (IorO[0].equals("in")) {
				showCaution(sNow + " 출석합니다.", "atndOK");

			} else if (IorO[0].equals("out")) {
				showCaution(sNow + " 하원합니다.", "ctOut");
			} else {
				showCaution(" 이미 하원처리가 완료됐습니다.", "atndScs");
			}

		}
		if (e.getActionCommand().equals("ho")) {
			System.out.println("지갑조회 클릭");
			new OpenWallet(cInfo);
			fCMain.dispose();

		}
		if (e.getActionCommand().equals("sgst")) {
			System.out.println("건의함 클릭");
			new OpenSgst(cInfo);
			fCMain.dispose();
		}
	}

	// 주의 다이얼로그 생성 메소드
	public void showCaution(String msg1, String msg3) {
		dCaution = new Dialog(fCMain, "호야 지역아동센터", true);
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