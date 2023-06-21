package main_pkg;

import java.awt.Button;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import db_pkg.ChildVo;

public class OpenSgst extends WindowAdapter implements ActionListener{
	private ChildVo cInfo;
	
	private JFrame fSgst;
	private Label lGrt, lTitle;
	private TextField tfTitle;
	private TextArea taBody;
	private Button bSgst;
	private Font fFont;
	
	public OpenSgst(ChildVo cInfo) {
		this.cInfo = cInfo;
		fSgst = new JFrame("건의함");
		fSgst.setSize(400, 400);
		fSgst.setLocationRelativeTo(null);
		fSgst.setLayout(null);
		fSgst.setResizable(false);
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
		if(e.getActionCommand().equals("sgst")) {
			System.out.println(tfTitle.getText());
			System.out.println(taBody.getText());
			//건의함 작성 및 저장 구현하기
		}
		
	}
}
