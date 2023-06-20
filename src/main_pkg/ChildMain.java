package main_pkg;

import java.awt.Button;
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

import db_pkg.CenterDAO;
import db_pkg.ChildVo;
import login_pkg.Login_Frame;

public class ChildMain extends WindowAdapter implements ActionListener {
	private JFrame fCMain;
	private Label lGrt;
	private Button bAttend, bWallet, bSgst;
	private ChildVo cInfo;
	private Font fFont;
	private Login_Frame lf;
	private LocalDateTime now;
	private CenterDAO cDAO;
	private Calendar cal;

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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("attend")) {
			System.out.println("등원하원 클릭");

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
			System.out.println(sAttend_day);

			System.out.println("현재 시간 : " + sNow);
			System.out.println("아동 코드 + 현재 시간 : " + sCode_Date);

			String[] IorO;
			cDAO = new CenterDAO();
			IorO = cDAO.inOrOutCheck("120230620"); // sNow 입력

			for (int i = 0; i < IorO.length; i++) {
				System.out.println(IorO[i]);
			}

			String atndCheck = null;

			if (IorO[0].equals("in")) {
				atndCheck = cDAO.center_IN(sC_code, sC_name, sCenter_in, sAttend_day, sCode_Date);
			} else if (IorO[0].equals("out")) {

			}

			System.out.println("출석 입력 상태 : " + atndCheck);

		}
		if (e.getActionCommand().equals("ho")) {
			System.out.println("지갑조회 클릭");
		}
		if (e.getActionCommand().equals("sgst")) {
			System.out.println("건의함 클릭");
		}
	}
}