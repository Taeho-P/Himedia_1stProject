package db_pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TeacherJoinDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "c##firstproj";
	String password = "firstproj";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	private String Tjoin;

	public TeacherJoinDAO(String id, String pw, String t_name, String e_mail, String gender, String t_call,
			String hire_date) {

		String sT_code = null;
		int iT_code = 0;

		try {

			connDB();

			String query = "SELECT max(t_code) AS t_code FROM TEACHER_INFO";
			rs = stmt.executeQuery(query);
			rs.last();

			sT_code = rs.getString("t_code");
			iT_code = Integer.parseInt(sT_code);
			
			System.out.println(iT_code);
		} catch (Exception e) {
			e.printStackTrace();
			Tjoin = "JoinFail";
		}

		try {
			String query = "INSERT INTO teacher_info(t_code, id, password, t_name, e_mail, gender, t_call, hire_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, iT_code + 1);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			pstmt.setString(4, t_name);
			pstmt.setString(5, e_mail);
			pstmt.setString(6, gender);
			pstmt.setString(7, t_call);
			pstmt.setString(8, hire_date);
			pstmt.executeUpdate();

			Tjoin = "JoinSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			Tjoin = "JoinFail";
		}

	}

	public void connDB() {
		try {
			Class.forName(driver);
			System.out.println("jdbc driver loading success.");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("oracle connection success.");
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			System.out.println("statement create success.");
			System.out.println("statement create success.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTjoin() {
		return Tjoin;
	}

}
