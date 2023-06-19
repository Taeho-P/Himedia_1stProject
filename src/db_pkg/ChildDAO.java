package db_pkg;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ChildDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "c##firstproj";
	String password = "firstproj";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	public ArrayList<ChildVo> list(String id) {
		ArrayList<ChildVo> list = new ArrayList<ChildVo>();

		try {
			connDB();

			String query = "SELECT c_code, image, id, password, c_name, to_char(c_birthday, 'yyyy-mm-dd') AS c_birthday, gender, c_call, father, mother, school, ho FROM CHILD_INFO";
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
					String sCcode = rs.getString("c_code");//
					Blob bImage = rs.getBlob("image");//
					String sId = rs.getString("id");//
					String sPw = rs.getString("password");//
					String sCname = rs.getString("c_name");//
					String sCbirthday = rs.getString("c_birthday");//
					String sGender = rs.getString("gender");//
					String sCcall = rs.getString("c_call");//
					String sFather = rs.getString("father");//
					String sMother = rs.getString("mother");//
					String sSchool = rs.getString("school");//
					String sHo = rs.getString("ho");//

					ChildVo data = new ChildVo(sCcode, bImage, sId, sPw, sCname, sCbirthday, sGender, sCcall, sFather, sMother, sSchool, sHo);
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
