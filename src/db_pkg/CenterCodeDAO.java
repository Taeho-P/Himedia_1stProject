package db_pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CenterCodeDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "c##firstproj";
	String password = "firstproj";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	public String Center_Check(String c_code) {
		
		String sCheck = null;
		try {
			connDB();
			
			String query = "SELECT * FROM CENTERCODE";
			query += " where code like '" + c_code + "'";
			System.out.println("SQL : " + query);
			
			rs = stmt.executeQuery(query);
			rs.last();
			
			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("일치하는 아이디가 없습니다.");
				sCheck = "unsame";
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				sCheck = "same";
				}

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return sCheck;
		
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
