package main_pkg;

import java.awt.Button;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import db_pkg.TeacherVo;

public class OpenOne extends WindowAdapter implements ActionListener {
	private TeacherVo tInfo;

	private JFrame fSgstOne;
	private Label lWriter, lWriteDay, lTitle;
	private TextField tfTitle;
	private TextArea taBody;
	private Button btOk;

	public OpenOne(TeacherVo tInfo, String c_name, String writeDay, String title, String body) {
		this.tInfo = tInfo;

		fSgstOne = new JFrame("건의함");
		fSgstOne.setSize(400, 400);
		fSgstOne.setLocationRelativeTo(null);
		fSgstOne.setLayout(null);
		fSgstOne.setResizable(false);
		fSgstOne.addWindowListener(this);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fSgstOne.setIconImage(icon);

		lWriter = new Label("작성자 : " + c_name);
		lWriter.setBounds(30, 20, 100, 25);

		lWriteDay = new Label("작성일 : " + writeDay);
		lWriteDay.setBounds(30, 50, 120, 25);

		lTitle = new Label("제목");
		lTitle.setBounds(30, 80, 30, 25);

		tfTitle = new TextField(20);
		tfTitle.setBounds(70, 82, 280, 20);
		tfTitle.setEditable(false);
		tfTitle.setText(title);

		taBody = new TextArea("", 30, 30, 1);
		taBody.setBounds(30, 120, 330, 190);
		taBody.setEditable(false);
		taBody.setText(body);

		btOk = new Button("확인");
		btOk.setBounds(162, 320, 60, 30);
		btOk.addActionListener(this);
		btOk.setActionCommand("ok");

		fSgstOne.add(lWriter);
		fSgstOne.add(lWriteDay);
		fSgstOne.add(lTitle);
		fSgstOne.add(tfTitle);
		fSgstOne.add(taBody);
		fSgstOne.add(btOk);
		fSgstOne.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")) {
			fSgstOne.dispose();
			new OpenSgstBox(tInfo);
		}
	}

	public void windowClosing(WindowEvent e) {
		if (e.getComponent() == fSgstOne) {
			fSgstOne.dispose();
			new OpenSgstBox(tInfo);
		}
	}
}
