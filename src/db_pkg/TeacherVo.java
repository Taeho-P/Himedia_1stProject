package db_pkg;

public class TeacherVo {
	private String id;
	private String password;
	private String t_code;
	private String t_name;
	private String e_mail;
	private String gender;
	private String t_call;
	private String hire_date;

	public TeacherVo() {

	}

	public TeacherVo(String id, String pw, String t_code, String t_name, String e_mail, String gender, String t_call,
			String hire_date) {
		this.id = id;
		this.password = pw;
		this.t_code = t_code;
		this.t_name = t_name;
		this.e_mail = e_mail;
		this.gender = gender;
		this.t_call = t_call;
		this.hire_date = hire_date;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getT_code() {
		return t_code;
	}

	public String getT_name() {
		return t_name;
	}

	public String getE_mail() {
		return e_mail;
	}

	public String getGender() {
		return gender;
	}

	public String getT_call() {
		return t_call;
	}

	public String getHire_date() {
		return hire_date;
	}

}
