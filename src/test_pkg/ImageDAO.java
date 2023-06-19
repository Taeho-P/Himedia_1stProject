package test_pkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ImageDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "c##himedia";
	String Password = "himedia";
	
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	public void list() {
		
		try {
			connDB();
			
			
			File file = new File("C:\\taeho\\abc\\test.png");
			long fileLength = file.length();
			InputStream fis = new FileInputStream(file);
			String query = "INSERT INTO imagetest (name, image) VALUES (?,?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, "test");
			pstmt.setBinaryStream(2, fis, fileLength);
			pstmt.executeUpdate();
			System.out.println("SQL : " + query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void connDB() {
		try {
			Class.forName(driver);
			System.out.println("jdbc driver loading success.");
			con = DriverManager.getConnection(url, user, Password);
			System.out.println("oracle connection success.");
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			System.out.println("statement create success.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
