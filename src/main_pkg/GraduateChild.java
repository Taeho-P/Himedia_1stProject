package main_pkg;

import java.awt.Button;
import java.awt.Image;
import java.awt.Label;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import db_pkg.TeacherVo;

public class GraduateChild {
	private TeacherVo tInfo;
	
	private JFrame fGradu;
	private Label lMsg;
	private Button bGradu;
	private Vector vChildList, vTitle;
	private DefaultTableModel model;
	
	public GraduateChild(TeacherVo tInfo) {
		this.tInfo = tInfo;
		fGradu = new JFrame("졸업 아동 관리");
		fGradu.setSize(400, 400);
		fGradu.setLocationRelativeTo(null);
		fGradu.setLayout(null);
		fGradu.setResizable(false);
		Image icon = new ImageIcon("./src/icon.png").getImage();
		fGradu.setIconImage(icon);
		
//		vTitle = new Vector();
//		vTitle.add
//		
//		fGradu.setVisible(true);
	}
}
