package db_pkg;

public class SgstVo {
	
	private String c_code;
	private String c_name;
	private String title;
	private String writeDay;
	private String body;
	private String sgst_no;

	public SgstVo() {

	}

	public SgstVo(String c_code, String c_name, String title, String writeDay, String body, String sgst_no) {
		this.c_code = c_code;
		this.c_name = c_name;
		this.title = title;
		this.writeDay = writeDay;
		this.body = body;
		this.sgst_no = sgst_no;
	}

	public String getC_code() {
		return c_code;
	}

	public String getC_name() {
		return c_name;
	}

	public String getTitle() {
		return title;
	}

	public String getWriteDay() {
		return writeDay;
	}

	public String getBody() {
		return body;
	}

	public String getSgst_no() {
		return sgst_no;
	}

}
