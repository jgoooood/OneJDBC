package com.kh.jdbc.day03.student.model.vo;

import java.sql.Date;

//주제선정 -> 시나리오 -> 와이어프레임(화면구성) -> DB설계 -> 클래스설계 -> 시퀀스설계
public class Student {
	//클래스의 필드는 Db완성 후 작성해야함->컬럼명과 똑같이 설계
	private String studentId; 
	private String studentPwd;
	private String studentName;
	private char gender;
	private int age;
	private String email;
	private String phone;
	private String address;
	private String hobby;
	private Date enrollDate;
	
	

	public Student() {
		super();
	}

	public Student(String studentId, String studentPwd, String studentName, char gender, int age, String email,
			String phone, String address, String hobby) {
		super();
		this.studentId = studentId;
		this.studentPwd = studentPwd;
		this.studentName = studentName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
	}
	
	public Student(String studentPwd, String email, String phone, String address, String hobby) {
		super();
		this.studentPwd = studentPwd;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
	}
	
	

	public Student(String studentId, String studentPwd) {
		super();
		this.studentId = studentId;
		this.studentPwd = studentPwd;
	}

	//getter setter
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentPwd() {
		return studentPwd;
	}
	public void setStudentPwd(String studentPwd) {
		this.studentPwd = studentPwd;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	
	
	//데이터확인용 toString 오버라이딩
	@Override
	public String toString() {
		return "학생 [아이디=" + studentId + ", 비밀번호=" + studentPwd + ", 이름=" + studentName
				+ ", 성별=" + gender + ", 나이=" + age + ", 이메일=" + email + ", 전화번호=" + phone + ", 주소="
				+ address + ", 취미=" + hobby + ", 등록일=" + enrollDate + "]";
	}
	
	
}
