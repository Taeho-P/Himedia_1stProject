package db_pkg;

public class ChildAttendVo {
	private String c_code;
	private String c_name;
	private String center_in;
	private String center_out;
	private String attend_day;
	private String attend;
	private String reason;
	private String code_date;

	public ChildAttendVo(String c_code, String c_name, String center_in, String center_out, String attend_day,
			String attend, String reason, String code_date) {
		this.c_code = c_code;
		this.c_name = c_name;
		this.center_in = center_in;
		this.center_out = center_out;
		this.attend_day = attend_day;
		this.attend = attend;
		this.reason = reason;
		this.code_date = code_date;
	}

	public String getC_code() {
		return c_code;
	}

	public String getC_name() {
		return c_name;
	}

	public String getCenter_in() {
		return center_in;
	}

	public String getCenter_out() {
		return center_out;
	}

	public String getAttend_day() {
		return attend_day;
	}

	public String getAttend() {
		return attend;
	}

	public String getReason() {
		return reason;
	}
	
	public String getCode_date() {
		return code_date;
	}

}
