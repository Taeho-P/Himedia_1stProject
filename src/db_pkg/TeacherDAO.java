package db_pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TeacherDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "c##firstproj";
	String password = "firstproj";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	public ArrayList<TeacherVo> list(String id) {
		ArrayList<TeacherVo> list = new ArrayList<TeacherVo>();

		try {
			connDB();

			String query = "SELECT t_code, id, password, t_name, e_mail, gender, t_call, TO_CHAR(hire_date, 'yyyy-mm-dd') AS hire_date FROM teacher_info";
			if (id != null) {
				query += " where id='" + id + "'";
			}
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("일치하는 아이디가 없습니다.");
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				rs.previous();

				while (rs.next()) {
					String sTcode = rs.getString("t_code");
					String sId = rs.getString("id");
					String sPw = rs.getString("password");
					String sTname = rs.getString("t_name");
					String sEmail = rs.getString("e_mail");
					String sGender = rs.getString("gender");
					String sTcall = rs.getString("t_call");
					String sHireDate = rs.getString("hire_date");

					TeacherVo data = new TeacherVo(sId, sPw, sTcode, sTname, sEmail, sGender, sTcall, sHireDate);
					list.add(data);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
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

}
