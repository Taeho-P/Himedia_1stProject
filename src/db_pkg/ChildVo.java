package db_pkg;

import java.sql.Blob;

public class ChildVo {
	private String c_code;
	private Blob image;
	private String id;
	private String password;
	private String c_name;
	private String c_birthday;
	private String gender;
	private String c_call;
	private String father;
	private String mother;
	private String school;
	private String ho;
	
	
	
	public ChildVo() {
		
	}
	
	public ChildVo(String c_code, Blob image, String id, String password, String c_name, String c_birthday, String gender, String c_call, String father, String mother, String school, String ho) {
		this.c_code = c_code;//
		this.image = image;
		this.id = id;//
		this.password = password;//
		this.c_name = c_name;//
		this.c_birthday = c_birthday;//
		this.gender = gender;//
		this.c_call = c_call;//
		this.father = father;//
		this.mother = mother;//
		this.school = school;
		this.ho = ho;
	}
	
	public String getC_code() {
		return c_code;
	}
	
	public Blob getImage() {
		return image;
	}
	
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getC_name() {
		return c_name;
	}
	
	public String getC_birthday() {
		return c_birthday;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getC_call() {
		return c_call;
	}
	
	public String getFather() {
		return father;
	}
	
	public String getMother() {
		return mother;
	}
	
	public String getSchool() {
		return school;
	}
	
	public String getHo() {
		return ho;
	}
}

