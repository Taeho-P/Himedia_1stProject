package main_pkg;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import db_pkg.CenterDAO;
import db_pkg.ChildVo;

public class OpenSgst extends WindowAdapter implements ActionListener {
	private ChildVo cInfo;

	private JFrame fSgst;
	private Label lGrt, lTitle;
	private TextField tfTitle;
	private TextArea taBody;
	private Button bSgst;
	private Font fFont, fDfont;
	private CenterDAO cDAO;
	private LocalDateTime now;
	private Dialog dCaution;

	public OpenSgst(ChildVo cInfo) {
		this.cInfo = cInfo;
		fSgst = new JFrame("건의함");
		fSgst.setSize(400, 400);
		fSgst.setLocationRelativeTo(null);
		fSgst.setLayout(null);
		fSgst.setResizable(false);
		fSgst.addWindowListener(this);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fSgst.setIconImage(icon);

		fFont = new Font("SansSerif", Font.PLAIN, 13);
		lGrt = new Label("센터에 하고싶은 말을 적어주세요.");
		lGrt.setBounds(55, 35, 230, 25);
		lGrt.setFont(fFont);

		lTitle = new Label("제목");
		lTitle.setBounds(50, 65, 40, 20);
		lTitle.setFont(fFont);

		tfTitle = new TextField(20);
		tfTitle.setBounds(89, 67, 220, 20);

		taBody = new TextArea("", 30, 30, 1);
		taBody.setBounds(45, 100, 310, 200);

		bSgst = new Button("건의하기");
		bSgst.setBounds(152, 310, 80, 30);
		bSgst.addActionListener(this);
		bSgst.setActionCommand("sgst");

		fSgst.add(lGrt);
		fSgst.add(lTitle);
		fSgst.add(tfTitle);
		fSgst.add(taBody);
		fSgst.add(bSgst);
		fSgst.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("sgstFail")) {
			dCaution.dispose();
			return;
		}

		if (e.getActionCommand().equals("sgstScs")) {
			dCaution.dispose();
			tfTitle.setText("");
			taBody.setText("");
			return;
		}

		if (e.getActionCommand().equals("sgst")) {

			if (tfTitle.getText().equals("") || taBody.getText().equals("")) {
				showCaution("제목과 내용을 입력해주세요.", "sgstFail");
			} else {
				System.out.println(tfTitle.getText());
				System.out.println(taBody.getText());

				now = LocalDateTime.now();
				String sWrite_date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

				String sC_code = cInfo.getC_code();
				String sC_name = cInfo.getC_name();
				String sTitle = tfTitle.getText();
				String sBody = taBody.getText();

				cDAO = new CenterDAO();

				String sSgstUp = cDAO.sgstUpdate(sC_code, sC_name, sTitle, sWrite_date, sBody);

				if (sSgstUp.equals("sgstScs")) {
					System.out.println("건의함 업데이트 성공");
					showCaution("건의내용이 제출됐습니다.", "sgstScs");
				} else {
					System.out.println("건의함 업데이트 실패");
					showCaution("건의내용 제출에 실패했습니다.", "sgstFail");
				}
			}

		}
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == dCaution) {
			dCaution.dispose();
		}

		if (e.getComponent() == fSgst) {
			fSgst.dispose();
			new ChildMain(cInfo);
		}
	}

	public void showCaution(String msg1, String msg3) {
		dCaution = new Dialog(fSgst, "호야 지역아동센터", true);
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
