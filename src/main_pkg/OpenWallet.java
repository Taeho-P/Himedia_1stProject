package main_pkg;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import db_pkg.CenterDAO;
import db_pkg.ChildVo;

public class OpenWallet extends WindowAdapter implements ActionListener {
	private ChildVo cInfo;
	private JFrame fWallet;
	private Label lMsg1, lMsg2;
	private Button bGetHo, bOK;
	private LocalDateTime now;
	private CenterDAO cDAO;
	private Dialog dCaution;
	private String sCode_Date;
	
	
	public OpenWallet(ChildVo cInfo) {
		this.cInfo = cInfo;

		fWallet = new JFrame("내 지갑");
		fWallet.setSize(300, 200);
		fWallet.setLocationRelativeTo(null);
		fWallet.setLayout(null);
		fWallet.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fWallet.setIconImage(icon);

		lMsg1 = new Label("내 보유 호 : ");
		lMsg1.setBounds(70, 50, 65, 25);

		lMsg2 = new Label(cInfo.getHo() + "호");
		lMsg2.setBounds(135, 50, 70, 25);
		
		bGetHo = new Button("호 받기");
		bGetHo.setBounds(60, 100, 70, 30);
		bGetHo.addActionListener(this);
		bGetHo.setActionCommand("getHo");

		bOK = new Button("확인");
		bOK.setBounds(160, 100, 70, 30);
		bOK.addActionListener(this);
		bOK.setActionCommand("ok");

		
		fWallet.addWindowListener(this);
		fWallet.add(bOK);
		fWallet.add(bGetHo);
		fWallet.add(lMsg1);
		fWallet.add(lMsg2);
		fWallet.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("noGet")) {
			dCaution.dispose();
		}
		
		if (e.getActionCommand().equals("getHo")) {
			now = LocalDateTime.now();
			String sNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			String sNow2 = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			this.sCode_Date = cInfo.getC_code() + sNow2;


			System.out.println("현재 시간 : " + sNow);
			System.out.println("아동 코드 + 현재 시간 : " + sCode_Date);

			String[] IorO;
			cDAO = new CenterDAO();
			IorO = cDAO.inOrOutCheck(sCode_Date);

			for (int i = 0; i < IorO.length; i++) {
				System.out.println(IorO[i]);
			}

			if (IorO[0].equals("in")) {
				showCaution(" 오늘 출석체크를", "진행해주세요.", "noGet");
			} else if (IorO[0].equals("out")) {
				showCaution(" 오늘 하원체크를", "진행해주세요.", "noGet");
			} else if (IorO[0].equals("HoAdded")) {
				showCaution(" 오늘의 호가 이미", "지급됐습니다.", "noGet");
			} else {
				String sIn = IorO[1];
				System.out.println(sIn);
				String sInTime = sIn.substring(11, 13);
				System.out.println(sInTime);
				int iInTime = Integer.parseInt(sInTime);
				System.out.println(iInTime);
				if(iInTime >= 16) {
					String AddHo = cDAO.addHo(cInfo.getC_code(), 50, sCode_Date);
					if(AddHo.equals("AddScs")) {
						showCaution(now.format(DateTimeFormatter.ofPattern("yy/MM/dd")) + " 50호가", "충전됐습니다.", "addHo");
					}
				} else {
					String AddHo = cDAO.addHo(cInfo.getC_code(), 100, sCode_Date);
					if(AddHo.equals("AddScs")) {
						showCaution(now.format(DateTimeFormatter.ofPattern("yy/MM/dd")) + " 100호가", "충전됐습니다.", "addHo");
					}
				}
			}
		}

		if (e.getActionCommand().equals("addHo")) {
			new ChildMain(cInfo);
			fWallet.dispose();
		}
		
		if (e.getActionCommand().equals("ok")) {
			new ChildMain(cInfo);
			fWallet.dispose();
		}

	}
	
	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == fWallet) {
			new ChildMain(cInfo);
			fWallet.dispose();
		}
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}
	}
	
	public void showCaution(String msg1, String msg2, String msg3) {
		dCaution = new Dialog(fWallet, "호야 지역아동센터", true);
		dCaution.setSize(300, 190);
		dCaution.setLocationRelativeTo(null);
		dCaution.setLayout(null);
		dCaution.addWindowListener(this);
		dCaution.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		dCaution.setIconImage(icon);

		Label lMsg1 = new Label(msg1);
		lMsg1.setSize(93, 25);
		lMsg1.setLocation(71, 70);

		Label lMsg2 = new Label(msg2);
		lMsg2.setSize(80, 25);
		lMsg2.setLocation(168, 70);

		Button bCheck = new Button("확인");
		bCheck.addActionListener(this);
		bCheck.setBounds(124, 110, 50, 30);
		bCheck.setActionCommand(msg3);

		dCaution.add(lMsg1);
		dCaution.add(lMsg2);
		dCaution.add(bCheck);
		dCaution.setVisible(true);
	}

}
