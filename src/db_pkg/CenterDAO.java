package db_pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class CenterDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "c##firstproj";
	String password = "firstproj";

	private LocalDateTime now;
	private Calendar cal;

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	private String Tjoin;
	private String Cjoin;
	private String sAtnd;
	private String sCtOut;
	private String sAddHo;
	private String sSgst;
	private String sGradu;
	private String sChildUpdate;

	public CenterDAO() {
		connDB();
	}

	public ArrayList<TeacherVo> teacherList(String id) {
		ArrayList<TeacherVo> list = new ArrayList<TeacherVo>();

		try {

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

	public void createAttendRecord() {
		now = LocalDateTime.now();
		cal = Calendar.getInstance();
		String sNow2 = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		ArrayList<ChildVo> list = new ArrayList<ChildVo>();

		try {
			String query = "SELECT * FROM attend_table WHERE code_date = '";
			query += 1 + sNow2 + "'";
			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				String sAttend_day = null;
				int iDay = cal.get(Calendar.DAY_OF_WEEK);
				switch (iDay) {

				case 1:
					sAttend_day = "일";
					break;

				case 2:
					sAttend_day = "월";
					break;

				case 3:
					sAttend_day = "화";
					break;

				case 4:
					sAttend_day = "수";
					break;

				case 5:
					sAttend_day = "목";
					break;

				case 6:
					sAttend_day = "금";
					break;

				case 7:
					sAttend_day = "토";
					break;

				}
				try {
					query = "SELECT * FROM CHILD_INFO ORDER BY c_code";

					System.out.println("SQL : " + query);

					rs = stmt.executeQuery(query);
					rs.last();
					System.out.println("rs.getRow() : " + rs.getRow());

					if (rs.getRow() == 0) {
						System.out.println("0 row selected...");
						System.out.println("조회된 아동이 없습니다.");
					} else {
						System.out.println(rs.getRow() + "rows selected...");
						rs.beforeFirst();

						while (rs.next()) {
							String sCcode = rs.getString("c_code");//
							String bImage = rs.getString("image");//
							String sId = rs.getString("id");//
							String sPw = rs.getString("password");//
							String sCname = rs.getString("c_name");//
							String sCbirthday = rs.getString("c_birthday").substring(0, 10);//
							String sGender = rs.getString("gender");//
							String sCcall = rs.getString("c_call");//
							String sFather = rs.getString("father");//
							String sMother = rs.getString("mother");//
							String sSchool = rs.getString("school");//
							String sHo = rs.getString("ho");//

							ChildVo data = new ChildVo(sCcode, bImage, sId, sPw, sCname, sCbirthday, sGender, sCcall,
									sFather, sMother, sSchool, sHo);
							list.add(data);
							System.out.println("리스트 추가");
						}
					}
					// INSERT INTO ATTEND_TABLE (c_code, c_name, attend_day, code_date) VALUES (2,
					// '이하얀', '수', '220230628')
					for (int i = 0; i < list.size(); i++) {
						query = "INSERT INTO ATTEND_TABLE (c_code, c_name, attend_day, code_date) VALUES (?, ?, ?, ?)";
						PreparedStatement pstmt = con.prepareStatement(query);
						ChildVo cvInfo = list.get(i);

						int iC_code = Integer.parseInt(cvInfo.getC_code());
						String sC_name = cvInfo.getC_name();
						String sAttendDay = sAttend_day;
						String sCode_date = iC_code + sNow2;

						pstmt.setInt(1, iC_code);
						pstmt.setString(2, sC_name);
						pstmt.setString(3, sAttendDay);
						pstmt.setString(4, sCode_date);

						pstmt.executeUpdate();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<ChildVo> childList() {
		ArrayList<ChildVo> list = new ArrayList<ChildVo>();

		try {

			String query = "SELECT * FROM CHILD_INFO ORDER BY c_code";

			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("조회된 아동이 없습니다.");
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				rs.beforeFirst();

				while (rs.next()) {
					String sCcode = rs.getString("c_code");//
					String bImage = rs.getString("image");//
					String sId = rs.getString("id");//
					String sPw = rs.getString("password");//
					String sCname = rs.getString("c_name");//
					String sCbirthday = rs.getString("c_birthday").substring(0, 10);//
					String sGender = rs.getString("gender");//
					String sCcall = rs.getString("c_call");//
					String sFather = rs.getString("father");//
					String sMother = rs.getString("mother");//
					String sSchool = rs.getString("school");//
					String sHo = rs.getString("ho");//

					ChildVo data = new ChildVo(sCcode, bImage, sId, sPw, sCname, sCbirthday, sGender, sCcall, sFather,
							sMother, sSchool, sHo);
					list.add(data);
					System.out.println("리스트 추가");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ChildVo> childList(String id) {
		ArrayList<ChildVo> list = new ArrayList<ChildVo>();

		try {

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
					String bImage = rs.getString("image");//
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

					ChildVo data = new ChildVo(sCcode, bImage, sId, sPw, sCname, sCbirthday, sGender, sCcall, sFather,
							sMother, sSchool, sHo);
					list.add(data);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ChildVo> childList2(String name) {
		ArrayList<ChildVo> list = new ArrayList<ChildVo>();

		try {

			// SELECT * FROM CHILD_INFO WHERE c_name LIKE '%하%'
			String query = "SELECT * FROM CHILD_INFO ";
			query += "WHERE c_name LIKE '%" + name + "%' ORDER BY c_code";
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("조회된 아동이 없습니다.");
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				rs.beforeFirst();

				while (rs.next()) {
					String sCcode = rs.getString("c_code");//
					String bImage = rs.getString("image");//
					String sId = rs.getString("id");//
					String sPw = rs.getString("password");//
					String sCname = rs.getString("c_name");//
					String sCbirthday = rs.getString("c_birthday").substring(0, 10);//
					String sGender = rs.getString("gender");//
					String sCcall = rs.getString("c_call");//
					String sFather = rs.getString("father");//
					String sMother = rs.getString("mother");//
					String sSchool = rs.getString("school");//
					String sHo = rs.getString("ho");//

					ChildVo data = new ChildVo(sCcode, bImage, sId, sPw, sCname, sCbirthday, sGender, sCcall, sFather,
							sMother, sSchool, sHo);
					list.add(data);
					System.out.println("리스트 추가");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<SgstVo> sgstList() {
		ArrayList<SgstVo> list = new ArrayList<SgstVo>();

		try {

			String query = "SELECT * FROM sgst_box ORDER BY sgst_no";

			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("조회된 게시글이 없습니다.");
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				rs.beforeFirst();

				while (rs.next()) {
					String sCcode = rs.getString("c_code");//
					String sCname = rs.getString("c_name");//
					String sTitle = rs.getString("title");//
					String sWriteDay = rs.getString("write_date").substring(0, 10);//
					String sBody = rs.getString("body");
					String sSgstNo = rs.getString("sgst_no");//
//					String c_code, String c_name, String title, String writeDay, String c_body, String sgst_no
					SgstVo data = new SgstVo(sCcode, sCname, sTitle, sWriteDay, sBody, sSgstNo);
					list.add(data);
					System.out.println("리스트 추가");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ChildAttendVo> childAttendList(String c_code, String code_date) {
		ArrayList<ChildAttendVo> list = new ArrayList<ChildAttendVo>();

		try {

			String query = "SELECT * FROM attend_table WHERE c_code = ";

			query += c_code + " AND code_date LIKE '" + code_date + "%'  ORDER BY code_date";
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("조회된 기록이 없습니다.");
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				rs.beforeFirst();
				// "날짜", "출결", "등원", "하원", "사유"
				while (rs.next()) {
					String sCcode = rs.getString("c_code");//
					String sCname = rs.getString("c_name");
					String sCenter_in = rs.getString("center_in");
					String sCenter_out = rs.getString("center_out");
					String sAttend_day = rs.getString("attend_day");
					String sAttend = rs.getString("attend");
					String sReason = rs.getString("reason");
					String sCode_date = rs.getString("code_date");

					// c_code, c_name, center_in, center_out, attend_day, attend, reason

					ChildAttendVo data = new ChildAttendVo(sCcode, sCname, sCenter_in, sCenter_out, sAttend_day,
							sAttend, sReason, sCode_date);
					list.add(data);
					System.out.println("리스트 추가");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public void todayCheckDone(String code_date, String attend) {
		try {
			//UPDATE ATTEND_TABLE SET attend = '결석' WHERE code_date = '1320230629'
			String query = "UPDATE ATTEND_TABLE SET attend = '";
			query += attend + "' WHERE code_date = '" + code_date + "'";
			System.out.println("SQL : " + query);
			
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(query);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public ArrayList<ChildAttendVo> todayCheck(String code_date) {
		ArrayList<ChildAttendVo> list = new ArrayList<ChildAttendVo>();

		try {
			//SELECT * FROM ATTEND_TABLE WHERE code_date LIKE '%20230629%' ORDER BY c_code
			String query = "SELECT * FROM ATTEND_TABLE WHERE code_date LIKE '%";

			query += code_date + "%' ORDER BY c_code";
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("조회된 기록이 없습니다.");
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				rs.beforeFirst();
				while (rs.next()) {
					String sCcode = rs.getString("c_code");//
					String sCname = rs.getString("c_name");
					String sCenter_in = rs.getString("center_in");
					String sCenter_out = rs.getString("center_out");
					String sAttend_day = rs.getString("attend_day");
					String sAttend = rs.getString("attend");
					String sReason = rs.getString("reason");
					String sCode_date = rs.getString("code_date");

					ChildAttendVo data = new ChildAttendVo(sCcode, sCname, sCenter_in, sCenter_out, sAttend_day,
							sAttend, sReason, sCode_date);
					list.add(data);
					System.out.println("리스트 추가");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

	public String findChildList(String name, String birth, String gender, String ForM, String parent) {

		String sId = " ";

		try {

			String query = "SELECT id FROM CHILD_INFO WHERE c_name";
			if (ForM.equals("부")) {
				query += " ='" + name + "' AND gender = '" + gender + "' AND c_birthday = '" + birth + "' AND father ='"
						+ parent + "'";
			} else {
				query += " ='" + name + "' AND gender = '" + gender + "' AND c_birthday = '" + birth + "' AND mother ='"
						+ parent + "'";
			}
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("일치하는 아이디가 없습니다.");
				return sId;
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				rs.previous();

				while (rs.next()) {
					sId = rs.getString("id");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sId;
	}

	public String findChildList(String id, String name, String birth, String gender, String ForM, String parent) {

		String sPw = " ";

		try {

			String query = "SELECT password FROM CHILD_INFO WHERE c_name";
			if (ForM.equals("부")) {
				query += " ='" + name + "' AND gender = '" + gender + "' AND c_birthday = '" + birth + "' AND father ='"
						+ parent + "' AND id = '" + id + "'";
			} else {
				query += " ='" + name + "' AND gender = '" + gender + "' AND c_birthday = '" + birth + "' AND mother ='"
						+ parent + "'AND id = '" + id + "'";
			}
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("일치하는 아이디가 없습니다.");
				return sPw;
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				rs.previous();

				while (rs.next()) {
					sPw = rs.getString("password");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sPw;
	}

	public String T_ID_Check(String id) {

		String sCheck = null;
		try {

			String query = "SELECT id FROM teacher_info";
			query += " where id like '" + id + "'";
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("일치하는 아이디가 없습니다.");
				sCheck = "joinable";
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				sCheck = "joinunable";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sCheck;

	}

	public String C_ID_Check(String id) {

		String sCheck = null;
		try {

			String query = "SELECT id FROM child_info";
			query += " where id like '" + id + "'";
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
				System.out.println("일치하는 아이디가 없습니다.");
				sCheck = "joinable";
			} else {
				System.out.println(rs.getRow() + "rows selected...");
				sCheck = "joinunable";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sCheck;

	}
	
	

	public String[] inOrOutCheck(String code_date) {

		String[] sCheck = new String[3];
		try {

			String query = "SELECT center_in, center_out, code_date, add_ho FROM attend_table";
			query += " where code_date like '" + code_date + "'";
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();

			if (rs.getString("center_in") == null) {
				System.out.println("출석 기록이 없습니다.");
				sCheck[0] = "in";
			} else if (rs.getString("center_out") == null) {
				sCheck[0] = "out";
				sCheck[1] = rs.getString("center_in");
				sCheck[2] = rs.getString("center_out");
			} else if (rs.getString("add_ho") == null) {
				sCheck[0] = "home";
				sCheck[1] = rs.getString("center_in");
				sCheck[2] = rs.getString("center_out");
			} else {
				sCheck[0] = "HoAdded";
				sCheck[1] = rs.getString("center_in");
				sCheck[2] = rs.getString("center_out");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sCheck;

	}

	public String Center_Check(String c_code) {

		String sCheck = null;
		try {

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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sCheck;

	}

	public void TeacherJoin(String id, String pw, String t_name, String e_mail, String gender, String t_call,
			String hire_date) {

		String sT_code = null;
		int iT_code = 0;

		try {

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

	// String id, String pw, String c_name, String c_birthday, String gender, String
	// c_call, String father, String mother, String school, String image

	public void ChildJoin(String id, String pw, String c_name, String c_birthday, String gender, String c_call,
			String father, String mother, String school, String image) {

		String sC_code = null;
		int iC_code = 0;

		try {

			String query = "SELECT max(c_code) AS c_code FROM child_INFO";
			rs = stmt.executeQuery(query);
			rs.last();

			sC_code = rs.getString("c_code");
			iC_code = Integer.parseInt(sC_code);

			System.out.println(iC_code);
		} catch (Exception e) {
			e.printStackTrace();
			Cjoin = "JoinFail";
		}

		// String id, String pw, String c_name, String c_birthday, String gender, String
		// c_call, String father, String mother, String school, String image

		try {
			String query = "INSERT INTO child_info(c_code, id, password, c_name, c_birthday, gender, c_call, father, mother, school, ho, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, iC_code + 1);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			pstmt.setString(4, c_name);
			pstmt.setString(5, c_birthday);
			pstmt.setString(6, gender);
			pstmt.setString(7, c_call);
			pstmt.setString(8, father);
			pstmt.setString(9, mother);
			pstmt.setString(10, school);
			pstmt.setInt(11, 1000);
			pstmt.setString(12, image);
			pstmt.executeUpdate();

			Cjoin = "JoinSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			Cjoin = "JoinFail";
		}

	}

	public String childGradu(String c_code) {
		try {

			String query = "DELETE FROM CHILD_INFO WHERE c_code = ";
			query += c_code;
			System.out.println("쿼리 : " + query);
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(query);

			sGradu = "GraduSuccess";
			return sGradu;

		} catch (Exception e) {
			e.printStackTrace();
			sGradu = "GraduFail";
			return sGradu;
		}

	}

	public String childUpdate(String c_code, String c_name, String c_birthday, String c_call, String father,
			String mother, String school, String ho, String image) {

		try {
			
			String query = "UPDATE CHILD_INFO SET ";
			query += "c_name = '" + c_name + "', c_birthday = TO_DATE('" + c_birthday + "', 'yyyy-mm-dd'), c_call = '"
					+ c_call + "', father = '" + father + "', mother = '" + mother + "', school = '" + school
					+ "', ho = " + ho + ", image = '" + image + "' WHERE c_code = " + c_code;

			System.out.println("쿼리 : " + query);

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(query);

			sChildUpdate = "updateSuccess";
			return sChildUpdate;
		} catch (Exception e) {
			e.printStackTrace();
			sChildUpdate = "updateFail";
			return sChildUpdate;
		}

	}

	public String center_IN(String c_code, String c_name, String center_in, String attend_day, String code_Date) {

		try {

			System.out.println("쿼리에 들어갈 날짜 : " + center_in);
			// UPDATE ATTEND_TABLE SET center_in = to_date ('2023-06-28 10:42', 'yyyy-mm-dd
			// hh24:mi') WHERE code_date = '120230628'
			String query = "UPDATE ATTEND_TABLE SET center_in = to_date ";
			query += "('" + center_in + "', 'yyyy-mm-dd hh24:mi') WHERE code_date = '" + code_Date + "'";

			System.out.println("쿼리 : " + query);

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(query);

			sAtnd = "atndSuccess";
			return sAtnd;
		} catch (Exception e) {
			e.printStackTrace();
			sAtnd = "atndFail";
			return sAtnd;
		}

	}

	public String center_OUT(String center_out, String code_date) {

		try {
			String query = "UPDATE ATTEND_TABLE SET center_out = to_date ";
			query += "('" + center_out + "', 'yyyy-mm-dd hh24:mi') WHERE code_date = '" + code_date + "'";

			System.out.println("쿼리 : " + query);
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(query);

			sCtOut = "ctOutSuccess";
			return sCtOut;
		} catch (Exception e) {
			e.printStackTrace();
			sCtOut = "ctOutFail";
			return sCtOut;
		}

	}

	public String modifyAttend(String code_date, String attend, String reason) {

		try {
			// 공휴일', reason = '어린이날' WHERE code_date = '220230505'
			String query = "UPDATE ATTEND_TABLE SET attend = '";
			query += attend + "', reason = '" + reason + "' WHERE code_date = '" + code_date + "'";

			System.out.println("쿼리 : " + query);

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(query);

			sAtnd = "modifySuccess";
			return sAtnd;
		} catch (Exception e) {
			e.printStackTrace();
			sAtnd = "modifyFail";
			return sAtnd;
		}

	}

	public String addHo(String c_code, int ho, String code_date) {

		try {

			String query = "UPDATE CHILD_INFO SET ho = ho + ";
			query += ho + " WHERE c_code = " + c_code;

			System.out.println("쿼리 : " + query);
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(query);

			query = "UPDATE ATTEND_TABLE SET add_ho = 'added' WHERE code_date = '";
			query += code_date + "'";
			pstmt.executeUpdate(query);

			sAddHo = "AddScs";
			return sAddHo;
		} catch (Exception e) {
			e.printStackTrace();
			sAddHo = "AddFail";
			return sAddHo;
		}

	}

	public String sgstUpdate(String c_code, String c_name, String title, String write_date, String body) {

		int iSgst_no = 0;

		try {

			String query = "SELECT max(sgst_no) AS sgst_no FROM sgst_box";

			rs = stmt.executeQuery(query);
			rs.last();

			iSgst_no = rs.getInt("sgst_no");

		} catch (Exception e) {
			e.printStackTrace();
			sSgst = "sgstFail";
			return sSgst;
		}

		try {

			String query = "INSERT INTO sgst_box (c_code, c_name, title, write_date, body, sgst_no) VALUES (?, ?, ?, to_date (?, 'yyyy-mm-dd hh24:mi'), ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(c_code));
			pstmt.setString(2, c_name);
			pstmt.setString(3, title);
			pstmt.setString(4, write_date);
			pstmt.setString(5, body);
			pstmt.setInt(6, iSgst_no + 1);
			pstmt.executeUpdate();

			sSgst = "sgstScs";
			return sSgst;
		} catch (Exception e) {
			e.printStackTrace();
			sSgst = "sgstFail";
			return sSgst;
		}

	}

	public String getTjoin() {
		return Tjoin;
	}

	public String getCjoin() {
		return Cjoin;
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
